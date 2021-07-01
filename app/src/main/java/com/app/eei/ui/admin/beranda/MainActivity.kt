package com.app.eei.ui.admin.beranda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.eei.R
import com.app.eei.adapter.AdminViewPagerAdapter
import com.app.eei.databinding.ActivityMainBinding
import com.app.eei.ui.admin.akun.fragment.AdminAkunFragment
import com.app.eei.ui.admin.beranda.fragment.BerandaFragment
import com.app.eei.ui.admin.settingapp.fragment.AdminSettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: AdminViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPagerAdapter=AdminViewPagerAdapter(this)

        val fragmentManager = supportFragmentManager
        val berandaFragment = BerandaFragment()
        fragmentManager
            .beginTransaction()
            .add(R.id.container, berandaFragment)
            .commit()

        binding.bottomNavigation.setOnItemSelectedListener { id->
            when(id){
                R.id.home ->{
                    Toast.makeText(this, getString(R.string.beranda), Toast.LENGTH_SHORT).show()
                    fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, berandaFragment)
                        .commit()
                }
                R.id.settings->{
                    Toast.makeText(this, getString(R.string.pengaturan_app), Toast.LENGTH_SHORT).show()
                    viewPagerAdapter.createFragment(1)
                    val adminSettingFragment = AdminSettingFragment()
                    fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, adminSettingFragment)
                        .commit()
                }
                R.id.akun->{
                    Toast.makeText(this, getString(R.string.akun), Toast.LENGTH_SHORT).show()
                    viewPagerAdapter.createFragment(2)
                    val adminAkunFragment = AdminAkunFragment()
                    fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, adminAkunFragment)
                        .commit()
                }
            }

        }
    }

}