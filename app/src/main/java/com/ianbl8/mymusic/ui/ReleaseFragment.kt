package com.ianbl8.mymusic.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import com.ianbl8.mymusic.databinding.FragmentReleaseBinding
import com.ianbl8.mymusic.helpers.showImagePicker
import com.ianbl8.mymusic.models.ReleaseManager
import com.ianbl8.mymusic.models.ReleaseModel
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.util.Calendar

@Suppress("DEPRECATION")
class ReleaseFragment : Fragment() {

    val thisYear = Calendar.getInstance().get(Calendar.YEAR)
    val nextYear = thisYear + 1
    private lateinit var releaseViewModel: ReleaseViewModel
    private val args by navArgs<ReleaseFragmentArgs>()
    private var _fragBinding: FragmentReleaseBinding? = null
    private val fragBinding get() = _fragBinding!!
    var release = ReleaseModel()
    private var imageUri: Uri? = null

    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragBinding = FragmentReleaseBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        setupMenu()
        releaseViewModel = ViewModelProvider(this).get(ReleaseViewModel::class.java)
        releaseViewModel.observableRelease.observe(viewLifecycleOwner, Observer { render() })

        val releaseid = args.releaseid
        if (releaseid.isNotEmpty()) {
            release = ReleaseManager.findById(releaseid)!!
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

        setButtonListener(fragBinding)

        registerImagePickerCallback()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val releaseid = args.releaseid
        if (releaseid.isNotEmpty()) {
            release = ReleaseManager.findById(releaseid)!!
            (activity as AppCompatActivity).supportActionBar?.title = "edit ${release.title}"
        }
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // handle visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_release, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem?.itemId) {
                    R.id.releaseListFragment -> {
                        findNavController().popBackStack()
                        return true
                    }
                    else -> return NavigationUI.onNavDestinationSelected(menuItem, requireView().findNavController())
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun render() {
        //
    }

    private fun setButtonListener(layout: FragmentReleaseBinding) {
        layout.btnAddRelease.setOnClickListener {
            Timber.i("btnAddRelease pressed")
            val addRelease = ReleaseModel()
            val releaseid = args.releaseid
            if (releaseid.isNotEmpty()) {
                release = ReleaseManager.findById(releaseid)!!
                addRelease.tracks = release.tracks
                addRelease.cover = release.cover
            }
            if (imageUri != null) {
                addRelease.cover = imageUri as Uri
            }
            addRelease.title = fragBinding.etTitle.text.toString()
            addRelease.artist = fragBinding.etArtist.text.toString()
            addRelease.year = fragBinding.etYear.text.toString()
            val discs = fragBinding.etDiscs.text.toString()
            if (discs.isNotEmpty()) {
                addRelease.discs = discs.toInt()
            } else {
                addRelease.discs = 1
            }
            addRelease.physical = fragBinding.cbPhysical.isChecked
            addRelease.digital = fragBinding.cbDigital.isChecked
            if (addRelease.title.isNotEmpty() && addRelease.artist.isNotEmpty() && addRelease.year.isNotEmpty()) {
                if (addRelease.year.toLong() < 1900 || addRelease.year.toLong() > nextYear) {
                    Snackbar.make(
                        it,
                        "Choose a valid year between 1900 and $nextYear",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Timber.i("Invalid year")
                } else {
                    if (releaseid.isNotEmpty()) {
                        Timber.i("Update release: ${addRelease.title}")
                        addRelease.id = releaseid
                        releaseViewModel.updateRelease(addRelease.copy())
                    } else {
                        Timber.i("Create release: ${addRelease.title}")
                        releaseViewModel.createRelease(addRelease.copy())
                    }
                    // findNavController().navigate(R.id.releaseListFragment)
                    findNavController().popBackStack()
                }
            } else {
                Snackbar.make(it, "Enter a valid title, artist and year", Snackbar.LENGTH_LONG)
                    .show()
                Timber.i("Invalid release")
            }
        }

        layout.btnAddCover.setOnClickListener {
            Timber.i("btnAddCover pressed")
            showImagePicker(imageIntentLauncher, requireContext())
        }

        layout.btnTracks.setOnClickListener {
            Timber.i("btnTracks pressed")
            val action = ReleaseFragmentDirections.actionReleaseFragmentToTrackListFragment(release.id)
            findNavController().navigate(action)
        }

        layout.btnDeleteRelease.setOnClickListener {
            Timber.i("btnDeleteRelease pressed")
            releaseViewModel.deleteRelease(release)
            // findNavController().navigate(R.id.releaseListFragment)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got result ${result.data!!.data}")
                            val image = result.data!!.data!!
                            requireActivity().contentResolver.takePersistableUriPermission(
                                image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                            )
                            imageUri = image
                            Picasso.get().load(imageUri).into(fragBinding.ivCover)
                        }
                    }

                    AppCompatActivity.RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }

}