package org.d3ifcool.laundrytelkom.fiturtest


import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.ui.fiture.setrika.SetrikaActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategorySetrikaTesting {
    @Test
    fun testCategory() {
        val activityScenario = ActivityScenario.launch(
            SetrikaActivity::class.java
        )
//        Lakukan Aksi

//      Goes To Setrika Activity
        onView(withId(R.id.plus_baju)).perform(click())
        onView(withId(R.id.plus_celana)).perform(click())
        onView(withId(R.id.send_message)).perform(click())
        Thread.sleep(3000)
        activityScenario.close()

    }
}