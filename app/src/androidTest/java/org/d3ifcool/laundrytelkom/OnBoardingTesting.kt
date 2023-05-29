package org.d3ifcool.laundrytelkom

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.d3ifcool.laundrytelkom.ui.onboarding.OnboardingScreen
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnBoardingTesting {

    @Test
    fun testOB() {
        val activityScenario = ActivityScenario.launch(
            OnboardingScreen::class.java
        )
//        Declare Wait For Animation
        Thread.sleep(5000)
//        SWIPE OB 1
        onView(withId(R.id.pager)).perform(swipeLeft())
//        SWIPE OB 2
        onView(withId(R.id.pager)).perform(swipeLeft())
        //        SWIPE OB 3
        onView(withId(R.id.pager)).perform(swipeLeft())
//        login btn

        onView(withId(R.id.login_btn)).perform(ViewActions.click())
        Thread.sleep(3000)

        activityScenario.close()
    }

}