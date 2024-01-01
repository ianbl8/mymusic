package com.ianbl8.mymusic.ui.releaselist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.adapters.ReleaseAdapter
import com.ianbl8.mymusic.adapters.ReleaseListener
import com.ianbl8.mymusic.databinding.FragmentReleaseListBinding
import com.ianbl8.mymusic.utils.SwipeToDeleteCallback
import com.ianbl8.mymusic.utils.SwipeToEditCallback
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.ui.auth.LoggedInViewModel
import com.ianbl8.mymusic.ui.release.ReleaseViewModel
import timber.log.Timber

@Suppress("DEPRECATION")
class ReleaseListFragment : Fragment(), ReleaseListener {

    lateinit var app: MainApp
    private var _fragBinding: FragmentReleaseListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    private val releaseListViewModel: ReleaseListViewModel by activityViewModels()
    private val releaseViewModel: ReleaseViewModel by activityViewModels()
    private var releaseListEmpty: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentReleaseListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        setupMenu()
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        releaseListViewModel.observableReleasesList.observe(
            viewLifecycleOwner,
            Observer { releases ->
                releases?.let { render(releases as ArrayList<ReleaseModel>)
                checkSwipeRefresh()
                }
            })

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = fragBinding.recyclerView.adapter as ReleaseAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                releaseViewModel.deleteRelease(loggedInViewModel.liveFirebaseUser.value?.uid!!, (viewHolder.itemView.tag as ReleaseModel).uid!!)
            }
        }

        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val release = viewHolder.itemView.tag as ReleaseModel
                val action =
                    ReleaseListFragmentDirections.actionReleaseListFragmentToReleaseFragment(
                        release.uid.toString()
                    )
                findNavController().navigate(action)
            }
        }

        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)

        return root
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // handle visibility of menu items
                if (!releaseListEmpty) {
                    menu.removeItem(R.id.btnSampleData)
                }
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_release_list, menu)

                val item = menu.findItem(R.id.toggleReleases) as MenuItem
                item.setActionView(R.layout.togglebutton_layout)
                val toggleReleases: SwitchCompat = item.actionView!!.findViewById(R.id.toggleButton)
                toggleReleases.isChecked = false

                toggleReleases.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) releaseListViewModel.loadAll()
                    else releaseListViewModel.loadUser()
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem?.itemId) {
                    R.id.btnSampleData -> {
                        releaseListViewModel.sampleData()
                        releaseListEmpty = false
                        val fragmentid = findNavController().currentDestination?.id
                        findNavController().popBackStack(fragmentid!!, true)
                        findNavController().navigate(fragmentid)
                        true
                    }

                    else -> NavigationUI.onNavDestinationSelected(
                        menuItem,
                        requireView().findNavController()
                    )
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(releasesList: ArrayList<ReleaseModel>) {
        fragBinding.recyclerView.adapter =
            ReleaseAdapter(releasesList, this, releaseListViewModel.readOnly.value!!)
        if (releasesList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.noReleasesFound.visibility = View.VISIBLE
            releaseListEmpty = true
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.noReleasesFound.visibility = View.GONE
            releaseListEmpty = false
        }
    }

    private fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            if (releaseListViewModel.readOnly.value!!) releaseListViewModel.loadAll()
            else releaseListViewModel.loadUser()
        }
    }

    private fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    override fun onResume() {
        super.onResume()
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                releaseListViewModel.liveFirebaseUser.value = firebaseUser
                releaseListViewModel.loadUser()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onReleaseClick(release: ReleaseModel) {
        Timber.i("ReleaseClick $release")
        val action = ReleaseListFragmentDirections.actionReleaseListFragmentToReleaseFragment(
            release.uid.toString()
        )
        if (!releaseListViewModel.readOnly.value!!) findNavController().navigate(action)
    }

}