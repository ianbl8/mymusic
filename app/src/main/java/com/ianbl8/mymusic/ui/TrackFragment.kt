package com.ianbl8.mymusic.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.databinding.FragmentTrackBinding
import com.ianbl8.mymusic.models.ReleaseManager
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.TrackModel
import timber.log.Timber

@Suppress("DEPRECATION")
class TrackFragment : Fragment() {

    private lateinit var releaseViewModel: ReleaseViewModel
    private lateinit var trackViewModel: TrackViewModel
    private val args by navArgs<TrackFragmentArgs>()
    private var _fragBinding: FragmentTrackBinding? = null
    private val fragBinding get() = _fragBinding!!
    var release = ReleaseModel()
    var track = TrackModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        val ridtid: String? = args.ridtid
        val ids = ridtid?.split("#")
        val releaseid = ids?.elementAtOrNull(0).toString()
        var trackid = ids?.elementAtOrNull(1).toString()
        if (trackid == "null") trackid = ""

        Timber.i("rid: \"$releaseid\"")
        Timber.i("tid: \"$trackid\"")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentTrackBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        setupMenu()
        releaseViewModel = ViewModelProvider(this).get(ReleaseViewModel::class.java)
        releaseViewModel.observableRelease.observe(viewLifecycleOwner, Observer { render() })

        val possRelease = releaseViewModel.observableRelease.value?.id
        Timber.i("R?: $possRelease")

        trackViewModel = ViewModelProvider(this).get(TrackViewModel::class.java)
        trackViewModel.observableTrack.observe(viewLifecycleOwner, Observer { render() })

        val ridtid: String? = args.ridtid
        val ids = ridtid?.split("#")
        val releaseid = ids?.elementAtOrNull(0).toString()
        var trackid = ids?.elementAtOrNull(1).toString()
        if (trackid == "null") trackid = ""

        if (releaseid.isNotEmpty() && trackid.isNotEmpty()) {
            release = ReleaseManager.findById(releaseid)!!
            fragBinding.etDiscNumber.setText(track.discNumber.toString())
            fragBinding.etTrackNumber.setText(track.trackNumber.toString())
            fragBinding.etTrackTitle.setText(track.trackTitle)
            fragBinding.etTrackArtist.setText(track.trackArtist)
            fragBinding.btnAddTrack.setText(R.string.btn_edit_track)
            fragBinding.btnDeleteTrack.isEnabled = true
        }

        setButtonListener(fragBinding)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ridtid: String? = args.ridtid
        val ids = ridtid?.split("#")
        val releaseid = ids?.elementAtOrNull(0).toString()
        var trackid = ids?.elementAtOrNull(1).toString()
        if (trackid == "null") trackid = ""

        if (releaseid.isNotEmpty() && trackid.isNotEmpty()) {
            track = ReleaseManager.findTrack(releaseid, trackid)!!
            (activity as AppCompatActivity).supportActionBar?.title = "edit ${track.trackTitle}"
        }
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // handle visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_track, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return NavigationUI.onNavDestinationSelected(
                    menuItem,
                    requireView().findNavController()
                )
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render() {
        //
    }

    private fun setButtonListener(layout: FragmentTrackBinding) {
        layout.btnAddTrack.setOnClickListener {
            Timber.i("btnAddTrack pressed")
            val addTrack = TrackModel()
            val ridtid: String? = args.ridtid
            val ids = ridtid?.split("#")
            val releaseid = ids?.elementAtOrNull(0).toString()
            var trackid = ids?.elementAtOrNull(1).toString()
            if (trackid == "null") trackid = ""

            if (releaseid.isNotEmpty()) {
                release = ReleaseManager.findById(releaseid)!!
            }
            addTrack.discNumber = fragBinding.etDiscNumber.text.toString().toInt()
            addTrack.trackNumber = fragBinding.etTrackNumber.text.toString().toInt()
            addTrack.trackTitle = fragBinding.etTrackTitle.text.toString()
            addTrack.trackArtist = fragBinding.etTrackArtist.text.toString()
            if (addTrack.trackArtist.isEmpty()) {
                addTrack.trackArtist = release.artist
            }
            if (addTrack.trackTitle.isNotEmpty()) {
                if (releaseid.isNotEmpty() && trackid.isNotEmpty()) {
                    Timber.i("Update track: ${addTrack.trackTitle}")
                    trackViewModel.updateTrack(release, track.copy())
                } else {
                    Timber.i("Add track: ${addTrack.trackTitle}")
                    trackViewModel.createTrack(release, track.copy())
                }
                val action = TrackFragmentDirections.actionTrackFragmentToTrackListFragment(release.id)
                findNavController().navigate(action)
            } else {
                Snackbar.make(it, "Enter valid track details", Snackbar.LENGTH_LONG).show()
                Timber.i("Invalid track")
            }
        }

        layout.btnDeleteTrack.setOnClickListener {
            Timber.i("btnDeleteTrack pressed")
            trackViewModel.deleteTrack(release, track)
            val action = TrackFragmentDirections.actionTrackFragmentToTrackListFragment(release.id)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}