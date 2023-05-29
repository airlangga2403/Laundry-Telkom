package org.d3ifcool.laundrytelkom.ui.onboarding

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.d3ifcool.laundrytelkom.databinding.FragmentOnBoarding1Binding
import org.d3ifcool.laundrytelkom.ui.login.LoginActivity


class OnBoardingFragment1 : Fragment() {

    private lateinit var binding: FragmentOnBoarding1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentOnBoarding1Binding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        getActivity()?.setRequestedOrientation(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        )
        binding = FragmentOnBoarding1Binding.inflate(inflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_on_boarding1, container, false)
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