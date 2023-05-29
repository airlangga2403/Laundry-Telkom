package org.d3ifcool.laundrytelkom.ui.onboarding

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.cuberto.liquid_swipe.LiquidPager
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.databinding.OnboardingScreenBinding
import org.d3ifcool.laundrytelkom.adapter.ViewPagerAdapter

class OnboardingScreen : AppCompatActivity() {
    var pager: LiquidPager? = null
    // declare viewPager
    var viewPager: ViewPagerAdapter? = null
    private lateinit var binding: OnboardingScreenBinding
//    Declare animation
    var anim: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = OnboardingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pager = binding.pager
        actionBar?.hide()

        // calling the constructor of viewPager class
        viewPager = ViewPagerAdapter(supportFragmentManager, 1)
        pager!!.setAdapter(viewPager)

        anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        pager!!.startAnimation(anim)
    }
    override fun onBackPressed() {
        return
    }
}