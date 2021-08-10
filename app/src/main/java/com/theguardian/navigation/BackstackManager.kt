package com.theguardian.navigation;

import android.util.Log
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.theguardian.navigation.ui.content.FrontFragment
import com.theguardian.navigation.ui.discover.DiscoverFragment

class BackstackManager(
    private val fragmentManager: FragmentManager,
    @IdRes private val containerId: Int,
    private val backstackRootFactory: BackstackRootFactory,
) {

    private var backstack: Backstack? = null

    private val initialisedStacks: MutableSet<Backstack> = hashSetOf()

    fun changeBackstack(newBackstack: Backstack) {
        val oldBackstack = backstack
        backstack = newBackstack
        onBackstackChanged(oldBackstack, newBackstack)
    }

    private fun onBackstackChanged(oldBackstack: Backstack?, newBackstack: Backstack) {
        if (oldBackstack != null) {
            fragmentManager.saveBackStack(oldBackstack.stack) // Save & pop the current backstack
            fragmentManager.executePendingTransactions()
        }
        setUpBackstack(newBackstack)
    }

    /**
     * If a backstack has not been setup, we need to set up the new root element of the backstack.
     *
     * If it has already been setup we need to create a root fragment.
     */
    private fun setUpBackstack(backstackToSetUp: Backstack) {
        if (backstackToSetUp in initialisedStacks) {
            fragmentManager.restoreBackStack(backstackToSetUp.stack) // Restore the new backstack
            fragmentManager.executePendingTransactions()
        } else {
            val root = backstackRootFactory.createRoot(backstackToSetUp)
            fragmentManager.commit {
                setReorderingAllowed(true)
                add(containerId, root, backstackToSetUp.stack)
                addToBackStack(backstackToSetUp.stack)
            }
            initialisedStacks.add(backstackToSetUp)
        }
    }

    fun addFragment(fragment: Fragment, name: String) {
        fragmentManager.commit {
            setReorderingAllowed(true)
            replace(containerId, fragment, name)
            addToBackStack(name)
        }
    }

    sealed class Backstack(val stack: String) {
        object Home : Backstack("home")
        object Discover : Backstack("discover")
        object Live : Backstack("live")
        object Sections : Backstack("live")
    }

    interface BackstackRootFactory {
        fun createRoot(backstack: Backstack): Fragment
    }

    class BackstackRootFactoryImpl : BackstackRootFactory {
        override fun createRoot(backstack: Backstack): Fragment {
            return when (backstack) {
                is Backstack.Home -> FrontFragment()
                is Backstack.Discover -> DiscoverFragment()
                else -> TODO("${backstack.stack} is not implemented yet.")
            }
        }
    }

    private fun FragmentManager.printBackstack() {
        val backstackEntryCount = fragmentManager.backStackEntryCount
        for (entryIndex in backstackEntryCount - 1 downTo 0) {
            val entry = fragmentManager.getBackStackEntryAt(entryIndex)
            val entryString = "ID: ${entry.id}, Name: ${entry.name}"
            Log.d("Backstack", entryString)
        }
    }

    fun interface RootFragment {
        operator fun invoke(): Fragment?
    }

}