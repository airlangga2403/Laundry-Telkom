package org.d3ifcool.laundrytelkom.fiturtest

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.ui.fiture.sprei.SpreiActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CateogoryExpressTesting {
    @Test
    fun testCategory() {
        val activityScenario = ActivityScenario.launch(
            SpreiActivity::class.java
        )
//        Lakukan Aksi

//      Goes To Setrika Activity
        Espresso.onView(ViewMatchers.withId(R.id.plus_tebal)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.plus_tipis)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.send_message)).perform(ViewActions.click())
        Thread.sleep(3000)
        activityScenario.close()
    }
}