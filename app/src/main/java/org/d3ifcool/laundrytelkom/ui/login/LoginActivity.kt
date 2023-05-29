package org.d3ifcool.laundrytelkom.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import org.d3ifcool.laundrytelkom.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    var viewPager: ViewPager? = null
    var google: FloatingActionButton? = null
    var loginAdapter: LoginAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        viewPager = binding.viewPager

        var tabLayout: TabLayout

        tabLayout = binding.tabLayout!!

        tabLayout.addTab(tabLayout.newTab().setText("Login"))
        tabLayout.addTab(tabLayout.newTab().setText("Register"))
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL)


        loginAdapter = LoginAdapter(supportFragmentManager, this, tabLayout.tabCount)
        viewPager?.setAdapter(loginAdapter)

        viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.setOnTabSelectedListener(
            object : TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewPager!!.setCurrentItem(tab.position)
                }
            })

        google?.setTranslationY(300F)
        google?.setAlpha(0F)

        google?.animate()?.translationY(0F)?.alpha(1F)?.setDuration(1000)?.setStartDelay(400)
            ?.start()
    }

    //    Disabled Back Button
    override fun onBackPressed() {
        return
    }
}