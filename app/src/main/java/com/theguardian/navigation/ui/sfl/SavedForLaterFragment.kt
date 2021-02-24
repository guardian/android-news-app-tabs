package com.theguardian.navigation.ui.sfl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.theguardian.navigation.R

class SavedForLaterFragment : Fragment() {

    private lateinit var savedForLaterViewModel: SavedForLaterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        savedForLaterViewModel =
            ViewModelProvider(this).get(SavedForLaterViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_sfl, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        savedForLaterViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}