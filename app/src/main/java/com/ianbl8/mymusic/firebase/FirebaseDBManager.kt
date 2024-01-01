package com.ianbl8.mymusic.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.ReleaseStore
import com.ianbl8.mymusic.models.TrackModel
import timber.log.Timber

object FirebaseDBManager: ReleaseStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(releasesList: MutableLiveData<List<ReleaseModel>>) {
        database.child("releases").addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Timber.i("Firebase Release error: ${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val localList = ArrayList<ReleaseModel>()
                val children = snapshot.children
                children.forEach {
                    val release = it.getValue(ReleaseModel::class.java)
                    localList.add(release!!)
                }
                database.child("releases").removeEventListener(this)
                releasesList.value = localList
            }
        })
    }

    override fun findAll(userId: String, releasesList: MutableLiveData<List<ReleaseModel>>) {

        database.child("user-releases").child(userId).addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Timber.i("Firebase Release error: ${error.message}")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val localList = ArrayList<ReleaseModel>()
                val children = snapshot.children
                children.forEach {
                    val release = it.getValue(ReleaseModel::class.java)
                    localList.add(release!!)
                }
                database.child("user-releases").child(userId).removeEventListener(this)
                releasesList.value = localList
            }
        })
    }

    override fun findById(
        userId: String,
        releaseId: String,
        release: MutableLiveData<ReleaseModel>
    ) {
        database.child("user-releases").child(userId).child(releaseId).get().addOnSuccessListener {
            release.value = it.getValue(ReleaseModel::class.java)
            Timber.i("Firebase got value ${it.value}")
        }.addOnFailureListener { 
            Timber.e("Firebase error getting data $it")
        }
    }

    override fun findByTitle(
        userId: String,
        releaseTitle: String,
        release: MutableLiveData<ReleaseModel>
    ) {
        database.child("user-releases").child(userId).child(releaseTitle).get().addOnSuccessListener {
            release.value = it.getValue(ReleaseModel::class.java)
            Timber.i("Firebase got value ${it.value}")
        }.addOnFailureListener {
            Timber.e("Firebase error getting data $it")
        }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, release: ReleaseModel) {
        Timber.i("Firebase DB reference: $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("releases").push().key
        if (key == null) {
            Timber.i("Firebase error: Key empty")
            return
        }
        release.uid = key
        val releaseValues = release.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/releases/$key"] = releaseValues
        childAdd["/user-releases/$uid/$key"] = releaseValues

        database.updateChildren(childAdd)
    }

    override fun update(userId: String, releaseId: String, release: ReleaseModel) {
        val releaseValues = release.toMap()

        val childUpdate = HashMap<String, Any>()
        childUpdate["/releases/$releaseId"] = releaseValues
        childUpdate["/user-releases/$userId/$releaseId"] = releaseValues

        database.updateChildren(childUpdate)
    }

    override fun delete(userId: String, releaseId: String) {
        val childDelete: MutableMap<String, Any?> = HashMap()
        childDelete["/releases/$releaseId"] = null
        childDelete["/user-releases/$userId/$releaseId"] = null

        database.updateChildren(childDelete)
    }

    // Track functions to be updated
    override fun findTrack(userId: String, releaseId: String, trackId: String, track: MutableLiveData<TrackModel>) {
            database.child("user-releases").child(userId).child(releaseId).child("tracks").child(trackId).get().addOnSuccessListener {
                track.value = it.getValue(TrackModel::class.java)
                Timber.i("Firebase got value ${it.value}")
            }.addOnFailureListener {
                Timber.e("Firebase error getting data $it")
            }
    }

    override fun createTrack(userId: String, releaseId: String, track: TrackModel) {
        val key = database.child("releases").child(releaseId).child("tracks").push().key
        if (key == null) {
            Timber.i("Firebase error: Key empty")
            return
        }
        track.uid = key
        val trackValues = track.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/releases/$releaseId/tracks/$key"] = trackValues
        childAdd["/user-releases/$userId/$releaseId/tracks/$key"] = trackValues

        database.updateChildren(childAdd)
    }

    override fun updateTrack(userId: String, releaseId: String, trackId: String, track: TrackModel) {
        val trackValues = track.toMap()

        val childUpdate = HashMap<String, Any>()
        childUpdate["/releases/$releaseId/tracks/$trackId"] = trackValues
        childUpdate["/user-releases/$userId/$releaseId/tracks/$trackId"] = trackValues

        database.updateChildren(childUpdate)
    }

    override fun deleteTrack(userId: String, releaseId: String, trackId: String) {
        val childDelete: MutableMap<String, Any?> = HashMap()
        childDelete["/releases/$releaseId/tracks/$trackId"] = null
        childDelete["/user-releases/$userId/$releaseId/tracks/$trackId"] = null

        database.updateChildren(childDelete)
    }


}