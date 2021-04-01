package com.theguardian.navigation.ui.sections

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.theguardian.navigation.R
import com.theguardian.navigation.ui.content.ArticleFragment
import com.theguardian.navigation.ui.content.FrontFragment
import com.theguardian.navigation.ui.content.ListFragment
import com.theguardian.navigation.ui.content.ScreenLauncher

class SectionsFragment : Fragment(R.layout.fragment_sections),
    ScreenLauncher {

    private lateinit var sectionsViewModel: SectionsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sectionsViewModel =
            ViewModelProvider(requireActivity()).get(SectionsViewModel::class.java)

        sectionsViewModel.topContent.observe(viewLifecycleOwner, Observer { content ->
            val fragment = when (content) {
                Content.Front -> FrontFragment()
                Content.List -> ListFragment()
                Content.Article -> ArticleFragment()
            }

            childFragmentManager.beginTransaction().apply {
                if (childFragmentManager.fragments.size == 0) {
                    add(
                        R.id.fragment_container_view,
                        fragment
                    )
                } else {
                    replace(
                        R.id.fragment_container_view,
                        fragment
                    )
                }
            }.commit()
        })
        sectionsViewModel.init()
    }

    override fun openArticle() {
        sectionsViewModel.openArticle()
        childFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_view,
                ArticleFragment()
            )
            .commit()
    }

    override fun openList() {
        sectionsViewModel.openList()
        childFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_view,
                ListFragment()
            )
            .commit()
    }

    override fun openFront() {
        sectionsViewModel.openFront()
        childFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container_view,
                FrontFragment()
            )
            .commit()
    }

    fun goBack(): Boolean {
        return sectionsViewModel.goBack()
    }
}
