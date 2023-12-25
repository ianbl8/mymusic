package com.ianbl8.mymusic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TrackListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_track_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TrackListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}