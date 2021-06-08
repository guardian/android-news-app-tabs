package com.theguardian.navigation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.theguardian.navigation.R
import com.theguardian.navigation.ui.content.FrontFragment

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeViewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        childFragmentManager.executePendingTransactions()
        if (childFragmentManager.backStackEntryCount == 0) {
            childFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container_view,
                    FrontFragment()
                )
                .commit()
        }

    }

}

