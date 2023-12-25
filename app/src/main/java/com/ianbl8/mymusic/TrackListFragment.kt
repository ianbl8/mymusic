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
    private var position: Int = 0
    private var _fragBinding: FragmentTrackListBinding? = null
    private val fragBinding get() = _fragBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        // use setFragmentResultListener to get release from Release Fragment

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentTrackListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = "tracks" // fix
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
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    override fun onTrackClick(track: TrackModel, position: Int) {
        Timber.i("Track $position clicked")
        // use setFragmentResult to send release and track to TrackFragment
        // set track_edit
        // navigate to TrackFragment
    }

    // implement other functions from TrackListActivity

    companion object {
        @JvmStatic
        fun newInstance() =
            TrackListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}