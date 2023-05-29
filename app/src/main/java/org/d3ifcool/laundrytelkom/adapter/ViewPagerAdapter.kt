package org.d3ifcool.laundrytelkom.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.d3ifcool.laundrytelkom.ui.onboarding.OnBoardingFragment1
import org.d3ifcool.laundrytelkom.ui.onboarding.OnBoardingFragment2
import org.d3ifcool.laundrytelkom.ui.onboarding.OnBoardingFragment3

class ViewPagerAdapter  // creation of constructor of viewPager class
    (fm: FragmentManager, behaviour: Int) :
    FragmentPagerAdapter(fm, behaviour) {
    // create the getItem method of
    // FragmentPagerAdapter class
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return OnBoardingFragment1() // Fragment1 is the name of first blank fragment,
            1 -> return OnBoardingFragment2() // Fragment2 is the name of second blank fragment,
            2 -> return OnBoardingFragment3()
        }
        throw IllegalStateException("position $position is invalid for this viewpager")
    }

    override fun getCount(): Int {
        return 3
    }
}