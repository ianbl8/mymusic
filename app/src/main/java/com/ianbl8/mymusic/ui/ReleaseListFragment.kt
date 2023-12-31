package com.ianbl8.mymusic.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.ianbl8.mymusic.R
import com.ianbl8.mymusic.adapters.ReleaseAdapter
import com.ianbl8.mymusic.adapters.ReleaseListener
import com.ianbl8.mymusic.databinding.FragmentReleaseListBinding
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ReleaseModel
import com.ianbl8.mymusic.ui.auth.LoggedInViewModel
import com.ianbl8.mymusic.utils.createLoader
import com.ianbl8.mymusic.utils.hideLoader
import com.ianbl8.mymusic.utils.showLoader
import timber.log.Timber

@Suppress("DEPRECATION")
class ReleaseListFragment : Fragment(), ReleaseListener {

    lateinit var app: MainApp
    private val releaseListViewModel: ReleaseListViewModel by activityViewModels()
    private val loggedInViewModel: LoggedInViewModel by activityViewModels()
    lateinit var loader: AlertDialog
    private var _fragBinding: FragmentReleaseListBinding? = null
    private val fragBinding get() = _fragBinding!!

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
        loader = createLoader(requireActivity())
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        showLoader(loader, "downloading collection")
        releaseListViewModel.observableReleasesList.observe(
            viewLifecycleOwner,
            Observer { releases ->
                releases?.let {
                    render(releases as ArrayList<ReleaseModel>)
                    hideLoader(loader)
                }
            })

        return root
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // handle visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_release_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return NavigationUI.onNavDestinationSelected(
                    menuItem,
                    requireView().findNavController()
                )
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render(releasesList: List<ReleaseModel>) {
        fragBinding.recyclerView.adapter = ReleaseAdapter(releasesList, this)
        if (releasesList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.noReleasesFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.noReleasesFound.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        showLoader(loader, "downloading collection")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                releaseListViewModel.liveFirebaseUser.value = firebaseUser
                releaseListViewModel.loadAll()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onReleaseClick(release: ReleaseModel) {
        Timber.i("ReleaseClick $release")
        val action =
            ReleaseListFragmentDirections.actionReleaseListFragmentToReleaseFragment(release.id)
        findNavController().navigate(action)
    }

}