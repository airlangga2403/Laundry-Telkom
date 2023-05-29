package org.d3ifcool.laundrytelkom.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.d3ifcool.laundrytelkom.R
import org.d3ifcool.laundrytelkom.databinding.FragmentOnBoarding1Binding
import org.d3ifcool.laundrytelkom.databinding.FragmentOnBoarding2Binding
import org.d3ifcool.laundrytelkom.ui.login.LoginActivity

class OnBoardingFragment2 : Fragment() {

    private lateinit var binding: FragmentOnBoarding2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentOnBoarding2Binding.inflate(layoutInflater)


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoarding2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.skip.setOnClickListener {
            changePageIntent(Intent(requireActivity(), LoginActivity::class.java))
        }
    }

    private fun changePageIntent(listIntent: Intent) {
        startActivity(listIntent)
    }


}