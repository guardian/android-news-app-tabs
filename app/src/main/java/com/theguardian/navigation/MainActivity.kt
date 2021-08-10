package com.theguardian.navigation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.theguardian.navigation.ui.content.ArticleFragment
import com.theguardian.navigation.ui.content.FrontFragment
import com.theguardian.navigation.ui.content.ListFragment
import com.theguardian.navigation.ui.content.ScreenLauncher


class MainActivity : AppCompatActivity(), ScreenLauncher,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val backstackManager: BackstackManager by lazy {
        BackstackManager(
            supportFragmentManager,
            R.id.fragment_container,
            BackstackManager.BackstackRootFactoryImpl()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_without_navigation)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(this)
        navView.selectedItemId = R.id.navigation_home

    }

    override fun openArticle() {
        backstackManager.addFragment(ArticleFragment(), "article")
    }

    override fun openList() {
        backstackManager.addFragment(ListFragment(), "list")
    }

    override fun openFront() {
        backstackManager.addFragment(FrontFragment(), "front")
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_home -> {
                backstackManager.changeBackstack(BackstackManager.Backstack.Home)
                true
            }
            R.id.navigation_discover -> {
                backstackManager.changeBackstack(BackstackManager.Backstack.Discover)
                true
            }
            else -> false
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}
