package org.d3ifcool.laundrytelkom.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.databinding.ActivityHomeBinding
import org.d3ifcool.laundrytelkom.ui.fiture.sprei.SpreiActivity
import org.d3ifcool.laundrytelkom.helper.SharedPreference
import org.d3ifcool.laundrytelkom.ui.fiture.karpet.KarpetActivity
import org.d3ifcool.laundrytelkom.ui.fiture.karpet.KarpetRecyclerViewActivity
import org.d3ifcool.laundrytelkom.ui.fiture.satuan.SatuanActivity
import org.d3ifcool.laundrytelkom.ui.fiture.satuan.SatuanRecyclerViewActivity
import org.d3ifcool.laundrytelkom.ui.login.LoginActivity
import org.d3ifcool.laundrytelkom.ui.profile.ProfileActivity
import org.d3ifcool.laundrytelkom.ui.fiture.setrika.SetrikaActivity
import org.d3ifcool.laundrytelkom.ui.fiture.setrika.SetrikaRecyclerViewActivity
import org.d3ifcool.laundrytelkom.ui.fiture.sprei.SpreiRecyclerViewActivity
import org.d3ifcool.laundrytelkom.ui.home.aboutapps.AboutApps
import org.d3ifcool.laundrytelkom.ui.maps.MapsActivity


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    lateinit var bottomNav: BottomNavigationView
    lateinit var sp: SharedPreference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        Hide Action Bar
        supportActionBar?.hide()
        bottomNav = binding.navView
//        init
        auth = FirebaseAuth.getInstance()
        sp = SharedPreference(this)


        val user = FirebaseAuth.getInstance().currentUser

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_profile -> changePageIntent(
                    Intent(
                        this,
                        ProfileActivity::class.java
                    )
                )
                R.id.logout_account -> {
                    logOut()
                    sp.setStatusSignin(false)
                    Toast.makeText(this, "Akun Berhasil Log Out", Toast.LENGTH_SHORT).show()
                    changePageIntent(Intent(this, LoginActivity::class.java))
                }
            }
            true
        }

        binding.nameUser.setText(user?.displayName)
        binding.maps.setOnClickListener{
            changePageIntent(Intent(this, MapsActivity::class.java))
        }
        binding.question.setOnClickListener{
            changePageIntent(Intent(this,AboutApps::class.java))
        }

        kategoriNav()
    }
    private fun kategoriNav(){
        binding.satuan.setOnClickListener {
            changePageIntent(Intent(this, SatuanRecyclerViewActivity::class.java))
        }

        binding.karpet.setOnClickListener {
            changePageIntent(Intent(this,KarpetRecyclerViewActivity::class.java))
        }

        binding.setrika.setOnClickListener {
            changePageIntent(Intent(this, SetrikaRecyclerViewActivity::class.java))
        }

        binding.ekspress.setOnClickListener {
            changePageIntent(Intent(this, SpreiRecyclerViewActivity::class.java))
        }
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        AuthUI.getInstance().signOut(this)
    }

    override fun onBackPressed() {
        return
    }

    private fun changePageIntent(listIntent: Intent) {
        startActivity(listIntent)
    }

}