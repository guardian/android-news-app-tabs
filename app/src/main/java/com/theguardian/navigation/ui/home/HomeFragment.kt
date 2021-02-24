package com.theguardian.navigation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theguardian.navigation.R
import com.theguardian.navigation.ui.content.ArticleFragment
import com.theguardian.navigation.ui.content.FrontFragment
import com.theguardian.navigation.ui.content.ListFragment
import com.theguardian.navigation.ui.content.ScreenLauncher

class HomeFragment : Fragment(R.layout.fragment_home),
    ScreenLauncher {

    private lateinit var homeViewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        childFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view,
                FrontFragment()
            )
            .setReorderingAllowed(true)
            .commit()
    }

    override fun openArticle() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view,
                ArticleFragment()
            )
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .commit()
    }

    override fun openList() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view,
                ListFragment()
            )
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .commit()
    }

    override fun openFront() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view,
                FrontFragment()
            )
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .commit()
    }
}
