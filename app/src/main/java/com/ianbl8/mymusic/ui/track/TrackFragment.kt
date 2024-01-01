package com.ianbl8.mymusic.ui.track

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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.databinding.FragmentTrackBinding
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.TrackModel
import com.ianbl8.mymusic.ui.auth.LoggedInViewModel
import com.ianbl8.mymusic.ui.release.ReleaseViewModel
import com.ianbl8.mymusic.ui.releaselist.ReleaseListViewModel
import com.ianbl8.mymusic.ui.tracklist.TrackListViewModel
import timber.log.Timber

@Suppress("DEPRECATION")
class TrackFragment : Fragment() {

    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    private val releaseListViewModel: ReleaseListViewModel by activityViewModels()
    private val releaseViewModel: ReleaseViewModel by activityViewModels()
    private val trackListViewModel: TrackListViewModel by activityViewModels()
    private val trackViewModel: TrackViewModel by activityViewModels()
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
        releaseViewModel.observableRelease.observe(viewLifecycleOwner, Observer { render() })

        val possRelease = releaseViewModel.observableRelease.value?.uid
        Timber.i("R?: $possRelease")

        trackViewModel.observableTrack.observe(viewLifecycleOwner, Observer { render() })

        val ridtid: String? = args.ridtid
        val ids = ridtid?.split("#")
        val releaseid = ids?.elementAtOrNull(0).toString()
        var trackid = ids?.elementAtOrNull(1).toString()
        if (trackid == "null") trackid = ""

        if (releaseid.isNotEmpty() && trackid.isNotEmpty()) {
            track = trackViewModel.observableTrack.value!!
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
            track = trackViewModel.observableTrack.value!!
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
                when (menuItem?.itemId) {
                    R.id.trackListFragment -> {
                        findNavController().popBackStack()
                        return true
                    }

                    else -> return NavigationUI.onNavDestinationSelected(
                        menuItem,
                        requireView().findNavController()
                    )
                }
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
                track = trackViewModel.observableTrack.value!!
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
                    addTrack.uid = trackid
                    trackViewModel.updateTrack(
                        loggedInViewModel.liveFirebaseUser.value?.uid!!,
                        releaseid,
                        trackid,
                        addTrack.copy()
                    )
                } else {
                    Timber.i("Add track: ${addTrack.trackTitle}")
                    trackViewModel.createTrack(
                        loggedInViewModel.liveFirebaseUser.value?.uid!!,
                        releaseid,
                        addTrack.copy()
                    )
                }
                // val action = TrackFragmentDirections.actionTrackFragmentToTrackListFragment(release.id)
                // findNavController().navigate(action)
                findNavController().popBackStack()
            } else {
                Snackbar.make(it, "Enter valid track details", Snackbar.LENGTH_LONG).show()
                Timber.i("Invalid track")
            }
        }

        layout.btnDeleteTrack.setOnClickListener {
            Timber.i("btnDeleteTrack pressed")
            val ridtid: String? = args.ridtid
            val ids = ridtid?.split("#")
            val releaseid = ids?.elementAtOrNull(0).toString()
            val trackid = ids?.elementAtOrNull(1).toString()
            trackViewModel.deleteTrack(
                loggedInViewModel.liveFirebaseUser.value?.uid!!,
                releaseid,
                trackid
            )
            // val action = TrackFragmentDirections.actionTrackFragmentToTrackListFragment(release.id)
            // findNavController().navigate(action)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }
}