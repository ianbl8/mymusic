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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.adapters.TrackAdapter
import com.ianbl8.mymusic.adapters.TrackListener
import com.ianbl8.mymusic.databinding.FragmentTrackListBinding
import com.ianbl8.mymusic.helpers.SwipeToDeleteCallback
import com.ianbl8.mymusic.helpers.SwipeToEditCallback
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ReleaseManager
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.models.TrackModel
import timber.log.Timber

@Suppress("DEPRECATION")
class TrackListFragment : Fragment(), TrackListener {

    lateinit var app: MainApp
    private lateinit var release: ReleaseModel
    private val args by navArgs<TrackListFragmentArgs>()
    private var _fragBinding: FragmentTrackListBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentTrackListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        setupMenu()
        val releaseid = args.releaseid
        release = ReleaseManager.findById(releaseid)!!
        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragBinding.recyclerView.adapter = TrackAdapter(release.tracks as ArrayList, this)
        render(release)

        val swipeDeleteHandler = object: SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = fragBinding.recyclerView.adapter as TrackAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object: SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val ridtid = viewHolder.itemView.tag.toString()
                val action = TrackListFragmentDirections.actionTrackListFragmentToTrackFragment(ridtid)
                findNavController().navigate(action)
            }
        }

        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val releaseid = args.releaseid
        release = ReleaseManager.findById(releaseid)!!
        (activity as AppCompatActivity).supportActionBar?.title = "tracks: ${release.title}"
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // handle visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_track_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem?.itemId) {
                    R.id.trackFragment -> {
                        val releaseid = release.id
                        val ridtid = releaseid.plus("#")
                        val action = TrackListFragmentDirections.actionTrackListFragmentToTrackFragment(ridtid)
                        findNavController().navigate(action)
                        return true
                    }
                    R.id.releaseFragment -> {
                        findNavController().popBackStack()
                        return true
                    }
                    else -> return NavigationUI.onNavDestinationSelected(menuItem, requireView().findNavController())
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(release: ReleaseModel) {
        fragBinding.recyclerView.adapter = TrackAdapter(release.tracks as ArrayList<TrackModel>, this)
        val noTracks: String = getString(R.string.no_tracks_found).plus(release.title)
        fragBinding.noTracksFound.text = noTracks
        if (release.tracks.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.noTracksFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.noTracksFound.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onTrackClick(release: ReleaseModel, track: TrackModel) {
        Timber.i("TrackClick $track")
        val releaseid = release.id
        val trackid = track.id
        val ridtid = releaseid.plus("#").plus(trackid)
        val action = TrackListFragmentDirections.actionTrackListFragmentToTrackFragment(ridtid)
        findNavController().navigate(action)
    }
}