package com.ianbl8.mymusic.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.databinding.FragmentTrackBinding
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.TrackModel
import timber.log.Timber

@Suppress("DEPRECATION")
class TrackFragment : Fragment() {

    lateinit var app: MainApp
    var track = TrackModel()
    var release = ReleaseModel()
    var edit = false
    private var _fragBinding: FragmentTrackBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentTrackBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.menu_add_track)

        /*
        // get release and track details from TrackListFragment
        setFragmentResultListener("TrackList_Track") { requestKey, bundle ->
            release = bundle.getParcelable("release")!!
            track = bundle.getParcelable("track")!!
            edit = bundle.getBoolean("track_edit")
        }
         */

        // if edit, populate fields from track
        if (edit) {
            fragBinding.etDiscNumber.setText(track.discNumber.toString())
            fragBinding.etTrackNumber.setText(track.trackNumber.toString())
            fragBinding.etTrackTitle.setText(track.trackTitle)
            fragBinding.etTrackArtist.setText(track.trackArtist)
            fragBinding.toolbar.title = "edit ${track.trackTitle}"
            fragBinding.btnAddTrack.setText(R.string.btn_edit_track)
            fragBinding.btnDeleteTrack.isEnabled = true
        }

        setButtonListener(fragBinding)

        return root
    }

    private fun setButtonListener(layout: FragmentTrackBinding) {
        layout.btnAddTrack.setOnClickListener {
            Timber.i("btnAddTrack pressed")
            track.discNumber = fragBinding.etDiscNumber.text.toString().toInt()
            track.trackNumber = fragBinding.etTrackNumber.text.toString().toInt()
            track.trackTitle = fragBinding.etTrackTitle.text.toString()
            track.trackArtist = fragBinding.etTrackArtist.text.toString()
            if (track.trackArtist.isEmpty()) {
                track.trackArtist = release.artist
            }
            if (track.trackTitle.isNotEmpty()) {
                if (edit) {
                    Timber.i("Update track: ${track.id}")
                    app.releases.updateTrack(release, track.copy())
                } else {
                    Timber.i("Add track: ${track.trackTitle}")
                    app.releases.createTrack(release, track.copy())
                }
                edit = false
                /*
                setFragmentResult("Track_TrackList", bundleOf(
                    Pair("track_update", "save"),
                ))
                 */
                findNavController().navigate(R.id.trackListFragment)
            } else {
                Snackbar.make(it, "Enter valid track details", Snackbar.LENGTH_LONG).show()
                Timber.i("Invalid track")
            }
        }

        layout.btnDeleteTrack.setOnClickListener {
            Timber.i("btnDeleteTrack pressed")
            app.releases.deleteTrack(release, track)
            /*
            setFragmentResult("Track_TrackList", bundleOf(
                Pair("track_update", "delete"),
            ))
             */
            findNavController().navigate(R.id.trackListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_track, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TrackFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}