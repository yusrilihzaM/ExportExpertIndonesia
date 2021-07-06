package com.app.eei.ui.guest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.eei.R
import com.app.eei.databinding.ActivityGuestMainBinding
import com.app.eei.databinding.ActivityMainBinding
import com.app.eei.ui.admin.akun.fragment.AdminAkunFragment
import com.app.eei.ui.admin.beranda.fragment.BerandaFragment
import com.app.eei.ui.admin.settingapp.fragment.AdminSettingFragment
import com.app.eei.ui.guest.akun.GuestAkunFragment
import com.app.eei.ui.guest.beranda.GuestBerandaFragment

class GuestMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuestMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_main)
        supportActionBar?.hide()
        binding = ActivityGuestMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val fragmentManager = supportFragmentManager
        val berandaFragment = GuestBerandaFragment()
        fragmentManager
            .beginTransaction()
            .add(R.id.container, berandaFragment)
            .commit()
        binding.bottomNavigation.setItemSelected(R.id.home,true)
        binding.bottomNavigation.setOnItemSelectedListener { id ->
            when (id) {
                R.id.home -> {
                    fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, berandaFragment)
                        .commit()
                }

                R.id.akun -> {
                    val guestAkunFragment = GuestAkunFragment()
                    fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, guestAkunFragment)
                        .commit()
                }
            }
        }
    }
}