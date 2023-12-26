package com.ianbl8.mymusic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.ianbl8.mymusic.databinding.FragmentReleaseBinding
import com.ianbl8.mymusic.helpers.showImagePicker
import com.ianbl8.mymusic.main.MainApp
import com.ianbl8.mymusic.models.ReleaseModel
import com.squareup.picasso.Picasso
import timber.log.Timber
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

    // registerImagePickerCallback() and showImagePicker() require updating
    // private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp

        // get release details from ReleaseListFragment 
        setFragmentResultListener("ReleaseList_Release") { requestKey, bundle ->
            release = bundle.getParcelable("release")!!
            edit = bundle.getBoolean("release_edit")
        }

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
            activity?.title = "edit ${release.title}"
            fragBinding.btnAddRelease.setText(R.string.btn_edit_release)
            fragBinding.btnTracks.isEnabled = true
            fragBinding.btnDeleteRelease.isEnabled = true
        }

        setButtonListener(fragBinding)

        // registerImagePickerCallback() and showImagePicker() require updating
        // registerImagePickerCallback()

        return root
    }
    
    private fun setButtonListener(layout: FragmentReleaseBinding) {
        layout.btnAddRelease.setOnClickListener {
            Timber.i("btnAddRelease pressed")
            release.title = fragBinding.etTitle.text.toString()
            release.artist = fragBinding.etArtist.text.toString()
            release.year = fragBinding.etYear.text.toString()
            val discs = fragBinding.etDiscs.text.toString()
            if (discs.isNotEmpty()) {
                release.discs = discs.toInt()
            } else {
                release.discs = 1
            }
            release.physical = fragBinding.cbPhysical.isChecked
            release.digital = fragBinding.cbDigital.isChecked
            if (release.title.isNotEmpty() && release.artist.isNotEmpty() && release.year.isNotEmpty()) {
                if (release.year.toLong() < 1900 || release.year.toLong() > nextYear) {
                    Snackbar.make(
                        it,
                        "Choose a valid year between 1900 and $nextYear",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Timber.i("Invalid year")
                } else {
                    if (edit) {
                        Timber.i("Update release: ${release.id}")
                        app.releases.update(release.copy())
                    } else {
                        Timber.i("Create release: ${release.id}")
                        app.releases.create(release.copy())
                    }
                    // setResult(AppCompatActivity.RESULT_OK)
                    // finish()
                }
            } else {
                Snackbar.make(it, "Enter a valid title, artist and year", Snackbar.LENGTH_LONG)
                    .show()
                Timber.i("Invalid release")
            }
        }

        layout.btnAddCover.setOnClickListener {
            Timber.i("btnAddCover pressed")
            Snackbar.make(it, "Add Cover pressed", Snackbar.LENGTH_SHORT)
                .show()
            // registerImagePickerCallback() and showImagePicker() require updating
            // showImagePicker(imageIntentLauncher, this)
        }

        layout.btnTracks.setOnClickListener {
            Timber.i("btnTracks pressed")
            setFragmentResult("Release_TrackList", bundleOf(
                Pair("release", release),
            ))
            requireView().findNavController().navigate(R.id.action_releaseFragment_to_trackListFragment)
        }

        layout.btnDeleteRelease.setOnClickListener {
            Timber.i("btnDeleteRelease pressed")
            app.releases.delete(release)
            setFragmentResult("Release_ReleaseList", bundleOf(
                Pair("release_update", "delete"),
            ))
            requireView().findNavController().navigate(R.id.action_releaseFragment_to_releaseListFragment)
        }
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

    companion object {
        @JvmStatic
        fun newInstance() =
            ReleaseFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    // registerImagePickerCallback() and showImagePicker() require updating
    /*
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got result ${result.data!!.data}")
                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(
                                image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                            )
                            release.cover = image
                            Picasso.get().load(release.cover).into(fragBinding.ivCover)
                        }
                    }

                    AppCompatActivity.RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }
     */

}