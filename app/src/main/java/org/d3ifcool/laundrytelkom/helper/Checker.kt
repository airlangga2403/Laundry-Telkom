package org.d3ifcool.laundrytelkom.helper

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.ui.fiture.satuan.SatuanRecyclerViewActivity
import org.d3ifcool.laundrytelkom.ui.home.HomeActivity
import org.d3ifcool.laundrytelkom.ui.onboarding.OnboardingScreen

class Checker : Fragment() {
    private var statusSignin = false
    private lateinit var sp: SharedPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().actionBar?.hide()
        // Inflate the layout for this fragment
        sp = SharedPreference(requireActivity())
        if (sp.getStatusSignin() == true) {
//            Goes To DaSHBOARD
            changePageIntent(Intent(requireActivity(), HomeActivity::class.java))
//            changePageIntent(Intent(requireActivity(), SatuanRecyclerViewActivity::class.java))

        } else {
//            GOES TO LOGIN ACTIVITY
            changePageIntent(Intent(requireActivity(), OnboardingScreen::class.java))
        }
        return inflater.inflate(R.layout.fragment_checker, container, false)
    }

    private fun changePageIntent(listIntent: Intent) {
        startActivity(listIntent)
    }
}