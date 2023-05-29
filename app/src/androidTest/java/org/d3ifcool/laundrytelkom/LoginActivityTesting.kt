package org.d3ifcool.laundrytelkom

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.laundrytelkom.ui.login.LoginActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTesting {

    lateinit var reference: DatabaseReference
    var name: String = ""
    var email: String = ""
    var iteration: Int = 0

    var nameArrayList : MutableList<String> = mutableListOf()
    var emailArrayList : MutableList<String> = mutableListOf()

    fun getDataFromFirebase() {
        reference = FirebaseDatabase.getInstance().getReference("test")
        reference.get().addOnSuccessListener {
            for (childSnapshot in it.children) {
                val key = childSnapshot.key.toString()
                reference.child(key).get().addOnSuccessListener {
                    name = it.child("name").value.toString()
                    email = it.child("email").value.toString()
//
                    nameArrayList.add(name)
                    emailArrayList.add(email)
                    iteration++
                    Log.d("userganteng", "nama ${emailArrayList[iteration-1]} key $key")
                }

            }
        }
    }

    @Test
    fun testLogin() {
        val activityScenario = ActivityScenario.launch(
            LoginActivity::class.java
        )

        //        DECLARE WAIT
        getDataFromFirebase()
        Thread.sleep(2000)
        for (a in 0 until 4) {

//      Goes To Login Activity
            onView(withId(R.id.email_login)).perform(
                replaceText(emailArrayList[a])
            )
            onView(withId(R.id.password_login)).perform(
                replaceText(nameArrayList[a])
            )
            onView(withId(R.id.login_btn_login)).perform(click())
//
            Thread.sleep(3000)
        }
        activityScenario.close()
    }
}