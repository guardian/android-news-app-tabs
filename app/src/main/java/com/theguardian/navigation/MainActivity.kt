package com.theguardian.navigation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.theguardian.navigation.ui.content.ArticleFragment
import com.theguardian.navigation.ui.content.FrontFragment
import com.theguardian.navigation.ui.content.ListFragment
import com.theguardian.navigation.ui.content.ScreenLauncher


class MainActivity : AppCompatActivity(), ScreenLauncher {

    private var backstack: Backstack = Backstack.HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_without_navigation)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    changeBackstack(Backstack.HOME)
                    true
                }
                R.id.navigation_discover -> {
                    changeBackstack(Backstack.DISCOVER)
                    true
                }
                else -> false
            }
        }
        navView.selectedItemId = R.id.navigation_home
    }


    private fun changeBackstack(newBackstack: Backstack) {
        supportFragmentManager.saveBackStack(backstack.stack)
        backstack = newBackstack
        supportFragmentManager.executePendingTransactions()
        createBackstackRootIfRequired()
        supportFragmentManager.restoreBackStack(backstack.stack)
    }

    private fun createBackstackRootIfRequired() {
        val root = createRootForCurrentStack(backstack)
        supportFragmentManager.commit {
            add(R.id.fragment_container, root)
            setReorderingAllowed(true)
        }
        supportFragmentManager.executePendingTransactions()
    }

    private fun createRootForCurrentStack(currentStack: Backstack): Fragment {
        return when (currentStack) {
            Backstack.HOME -> FrontFragment()
            Backstack.DISCOVER -> ListFragment()
        }
    }

    override fun openArticle() {
        addFragmentToBackStack(ArticleFragment())
    }

    override fun openList() {
        addFragmentToBackStack(ListFragment())
    }

    override fun openFront() {
        addFragmentToBackStack(FrontFragment())
    }

    private fun addFragmentToBackStack(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment, backstack.name)
            setReorderingAllowed(true)
            addToBackStack(backstack.name)
        }
        supportFragmentManager.executePendingTransactions()
    }

    enum class Backstack(val stack: String) {
        HOME("home"), DISCOVER("discover");
    }
}