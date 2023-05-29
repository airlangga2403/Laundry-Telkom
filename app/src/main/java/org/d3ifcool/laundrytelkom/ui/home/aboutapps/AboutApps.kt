package org.d3ifcool.laundrytelkom.ui.home.aboutapps

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.databinding.ActivityAboutAppsBinding

class AboutApps : AppCompatActivity() {
    private lateinit var binding: ActivityAboutAppsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutAppsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar: ActionBar? = supportActionBar

        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_ios_new_24)
        actionBar?.title = "Tentang Aplikasi"

        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}