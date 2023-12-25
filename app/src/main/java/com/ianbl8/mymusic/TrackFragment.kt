package com.ianbl8.mymusic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.ianbl8.mymusic.databinding.FragmentTrackBinding
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.TrackModel

@Suppress("DEPRECATION")
class TrackFragment : Fragment() {

    lateinit var app: MainApp
    var track = TrackModel()
    var release = ReleaseModel()
    var add = true
    var edit = false
    private var _fragBinding: FragmentTrackBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        // use setFragmentResultListener to get release and track from TrackListFragment
        // if track_add set, add = true
        // if not set, add remains false
        // if track_edit set, edit = true
        // if not set, edit remains false

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentTrackBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.menu_add_track)

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

        return root
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

    // implement other functions from TrackActivity

    companion object {
        @JvmStatic
        fun newInstance() =
            TrackFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}