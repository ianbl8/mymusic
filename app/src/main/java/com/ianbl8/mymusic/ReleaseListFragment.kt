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
import com.ianbl8.mymusic.adapters.ReleaseAdapter
import com.ianbl8.mymusic.adapters.ReleaseListener
import com.ianbl8.mymusic.databinding.FragmentReleaseListBinding
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ReleaseModel
import timber.log.Timber

@Suppress("DEPRECATION")
class ReleaseListFragment : Fragment(), ReleaseListener {

    lateinit var app: MainApp
    private var position: Int = 0
    private var _fragBinding: FragmentReleaseListBinding? = null
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
        _fragBinding = FragmentReleaseListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.menu_list)
        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragBinding.recyclerView.adapter = ReleaseAdapter(app.releases.findAll(), this)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_release_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    override fun onReleaseClick(release: ReleaseModel, position: Int) {
        Timber.i("Release $position clicked")
        // use setFragmentResult to send release to ReleaseFragment
        // set release_edit
        // navigate to ReleaseFragment
    }

    // implement other functions from ReleaseListActivity

    companion object {
        @JvmStatic
        fun newInstance() =
            ReleaseListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}