package org.d3ifcool.laundrytelkom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import org.d3ifcool.laundrytelkom.databinding.ActivityMainBinding
import org.d3ifcool.laundrytelkom.helper.SharedPreference

class MainActivity : AppCompatActivity() {

    private lateinit var sp: SharedPreference
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.hide()


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
        sp = SharedPreference(this)

        setupActionBarWithNavController(navController)
    }
}