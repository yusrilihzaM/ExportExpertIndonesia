package com.app.eei.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.eei.ui.admin.beranda.fragment.BerandaFragment
import com.app.eei.ui.admin.settingapp.fragment.AdminSettingFragment

private const val ARG_PARAM1="param1"
class AdminViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
       return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = BerandaFragment()
            1 -> fragment = AdminSettingFragment()
            2 -> fragment = AdminSettingFragment()
        }
        return fragment as Fragment
    }


}