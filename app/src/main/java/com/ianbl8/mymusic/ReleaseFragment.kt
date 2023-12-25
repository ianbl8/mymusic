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
import com.ianbl8.mymusic.databinding.FragmentReleaseBinding
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ReleaseModel
import com.squareup.picasso.Picasso
import java.util.Calendar

@Suppress("DEPRECATION")
class ReleaseFragment : Fragment() {

    lateinit var app: MainApp
    val thisYear = Calendar.getInstance().get(Calendar.YEAR)
    val nextYear = thisYear + 1
    var release = ReleaseModel()
    var edit = false
    private var _fragBinding: FragmentReleaseBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        // use setFragmentResultListener to get release from ReleaseListFragment
        // if release_edit set, edit = true
        // if not set, edit remains false

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentReleaseBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.menu_release)

        // if edit, populate fields from release
        if (edit) {
            fragBinding.etTitle.setText(release.title)
            fragBinding.etArtist.setText(release.artist)
            fragBinding.etYear.setText(release.year)
            fragBinding.etDiscs.setText(release.discs.toString())
            fragBinding.etTracks.setText(release.tracks.size.toString())
            fragBinding.cbPhysical.isChecked = release.physical
            fragBinding.cbDigital.isChecked = release.digital
            if (release.cover.toString().isNotEmpty()) {
                Picasso.get().load(release.cover).into(fragBinding.ivCover)
            }
            fragBinding.btnAddRelease.setText(R.string.btn_edit_release)
            fragBinding.btnTracks.isEnabled = true
            fragBinding.btnDeleteRelease.isEnabled = true
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_release, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    // use setFragmentResult to send release to TrackListFragment

    // implement other functions from ReleaseActivity

    companion object {
        @JvmStatic
        fun newInstance() =
            ReleaseFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}