package com.theguardian.navigation

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class BackStacktest {

    @Test
    fun saveBackStack() {
        with(ActivityScenario.launch(FragmentTestActivity::class.java)) {
            onActivity {
                val fm = it.supportFragmentManager


                val fragmentBase = StrictFragment()
                val fragmentReplacement = StrictFragment()
                val fragmentReplacement2 = StrictFragment()
                fm.beginTransaction()
                    .add(R.id.content, fragmentBase)
                    .commit()
                fm.executePendingTransactions()
                fm.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.content, fragmentReplacement)
                    .addToBackStack("replacement")
                    .commit()
                fm.executePendingTransactions()
                fm.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.content, fragmentReplacement2)
                    .addToBackStack("replacement")
                    .commit()
                fm.executePendingTransactions()
                fm.saveBackStack("replacement")
                fm.executePendingTransactions()
                
                // Saved Fragments should be destroyed
                assertTrue(fragmentReplacement.calledOnDestroy)
                assertTrue(fragmentReplacement2.calledOnDestroy)
            }

        }
    }
}