package org.d3ifcool.laundrytelkom.fiturtest

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.ui.fiture.satuan.SatuanActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategorySatuanTesting {
    @Test
    fun testCategory() {
        val activityScenario = ActivityScenario.launch(
            SatuanActivity::class.java
        )
//        Lakukan Aksi

//      Goes To Setrika Activity
        Espresso.onView(ViewMatchers.withId(R.id.plus_baju)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.plus_celana)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.send_message)).perform(ViewActions.click())
        Thread.sleep(3000)
        activityScenario.close()
    }
}