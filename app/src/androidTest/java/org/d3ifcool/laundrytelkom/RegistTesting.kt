package org.d3ifcool.laundrytelkom

import android.util.Log
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.*

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.laundrytelkom.ui.login.LoginActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistTesting {

    lateinit var reference: DatabaseReference
    var name: String = ""
    var email: String = ""
    var address: String = ""
    var mobileNum: String = ""
    var iteration: Int = 0

    var nameArrayList : MutableList<String> = mutableListOf()
    var emailArrayList : MutableList<String> = mutableListOf()
    var addressArrayList : MutableList<String> = mutableListOf()
    var mobileNumArrayList : MutableList<String> = mutableListOf()


    fun getDataFromFirebase() {
        reference = FirebaseDatabase.getInstance().getReference("test")
        reference.get().addOnSuccessListener {
            for (childSnapshot in it.children) {
                val key = childSnapshot.key.toString()
                reference.child(key).get().addOnSuccessListener {
                    name = it.child("name").value.toString()
                    email = it.child("email").value.toString()
                    address = it.child("address").value.toString()
                    mobileNum = it.child("mobileNum").value.toString()
//
                    nameArrayList.add(name)
                    emailArrayList.add(email)
                    addressArrayList.add(address)
                    mobileNumArrayList.add(mobileNum)
                    iteration++
                    Log.d("userganteng", "nama ${emailArrayList[iteration-1]} key $key")
                }

            }
        }
    }

    @Test
    fun testInputUser() {

        val activityScenario = ActivityScenario.launch(
            LoginActivity::class.java
        )
//        DECLARE WAIT
        getDataFromFirebase()
        Thread.sleep(2000)


        for (a in 0 until iteration-1) {
            onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(1))

            Thread.sleep(2000)

            onView(withId(R.id.email)).perform(
                ViewActions.replaceText(emailArrayList[a])
            )
            onView(withId(R.id.password)).perform(
                ViewActions.replaceText(nameArrayList[a])
            )
            onView(withId(R.id.nama)).perform(
                ViewActions.replaceText(nameArrayList[a])
            )
            onView(withId(R.id.mobile_num)).perform(
                ViewActions.replaceText(mobileNumArrayList[a])
            )
            onView(withId(R.id.alamat)).perform(
                ViewActions.replaceText(addressArrayList[a])
            )
            onView(withId(R.id.signup)).perform(ViewActions.click())
        }


        Thread.sleep(10000)
        activityScenario.close()

    }


    fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() =
                allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }

}