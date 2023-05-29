package org.d3ifcool.laundrytelkom.ui.onboarding.splash_screen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import org.d3ifcool.laundrytelkom.MainActivity
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.ui.home.HomeActivity
import org.d3ifcool.laundrytelkom.ui.maps.MapsActivity


@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activit_splash_screen)

        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
//            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.
    }

}