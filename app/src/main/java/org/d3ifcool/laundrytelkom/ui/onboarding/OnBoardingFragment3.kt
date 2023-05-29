package org.d3ifcool.laundrytelkom.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.d3ifcool.laundrytelkom.MainActivity
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.databinding.FragmentOnBoarding3Binding
import org.d3ifcool.laundrytelkom.ui.login.LoginActivity

class OnBoardingFragment3 : Fragment() {

    private lateinit var binding: FragmentOnBoarding3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentOnBoarding3Binding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoarding3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener{
            changePageIntent(Intent(requireActivity(), LoginActivity::class.java))
        }
    }

    private fun changePageIntent(listIntent: Intent) {
        startActivity(listIntent)
    }
}