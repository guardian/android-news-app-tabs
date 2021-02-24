package com.theguardian.navigation.ui.content

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.theguardian.navigation.R


class ListFragment : Fragment(R.layout.fragment_screen) {

    private lateinit var screenLauncher: ScreenLauncher

    override fun onAttach(context: Context) {
        super.onAttach(context)
        screenLauncher = parentFragment as ScreenLauncher
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tvScreen).text = "List"
        view.findViewById<Button>(R.id.bGoToArticle).setOnClickListener {
            screenLauncher.openArticle()
        }
        view.findViewById<Button>(R.id.bGoToFront).setOnClickListener {
            screenLauncher.openFront()
        }
        view.findViewById<Button>(R.id.bGoToList).setOnClickListener {
            screenLauncher.openList()
        }
    }

}