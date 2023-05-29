package org.d3ifcool.laundrytelkom.ui.login

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.d3ifcool.laundrytelkom.ui.register.RegisterFragment

class LoginAdapter(fm: FragmentManager?, context: Context, totalTabs: Int) :
    FragmentPagerAdapter(fm!!) {
    private var context: Context
    var totalTabs: Int

    init {
        this.context = context
        this.totalTabs = totalTabs
    }
    fun LoginAdapter(fm: FragmentManager?, context: Context, totalTabs: Int) {
//        super(fm)
        this.context = context
        this.totalTabs = totalTabs
    }

    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                LoginFragment()
            }
            1 -> {
                RegisterFragment()
            }
            else -> throw IllegalStateException("position $position is invalid for this viewpager")
        }
    }
}