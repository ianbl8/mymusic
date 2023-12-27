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
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.ianbl8.mymusic.adapters.TrackAdapter
import com.ianbl8.mymusic.adapters.TrackListener
import com.ianbl8.mymusic.databinding.FragmentTrackListBinding
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.TrackModel
import timber.log.Timber

@Suppress("DEPRECATION")
class TrackListFragment : Fragment(), TrackListener {

    lateinit var app: MainApp
    private lateinit var release: ReleaseModel
    private lateinit var status: String
    private var position: Int = 0
    private var _fragBinding: FragmentTrackListBinding? = null
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
        _fragBinding = FragmentTrackListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = "tracks: ${release.title}"

        /*
        // use setFragmentResultListener to get release from ReleaseFragment
        setFragmentResultListener("Release_TrackList") { requestKey, bundle ->
            release = bundle.getParcelable("release")!!
        }

        // use setFragmentResultListener to get save success or delete success from TrackFragment
        setFragmentResultListener("Track_TrackList") { requestKey, bundle ->
            status = bundle.getString("track_update")!!
        }
         */

        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragBinding.recyclerView.adapter = TrackAdapter(app.releases.findById(release.id)!!.tracks, this)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_track_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*
        setFragmentResult("TrackList_Track", bundleOf(
            Pair("release", release),
            Pair("track_edit", false)
        ))
         */
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    override fun onTrackClick(track: TrackModel, position: Int) {
        Timber.i("Track $position clicked")
        /*
        setFragmentResult("TrackList_Track", bundleOf(
            Pair("release", release),
            Pair("track", track),
            Pair("track_edit", true)
        ))
         */
        findNavController().navigate(R.id.trackFragment)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TrackListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}