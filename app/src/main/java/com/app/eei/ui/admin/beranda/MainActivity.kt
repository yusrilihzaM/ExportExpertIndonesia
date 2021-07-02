package com.app.eei.ui.admin.beranda

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.eei.R
import com.app.eei.databinding.ActivityMainBinding
import com.app.eei.ui.admin.akun.fragment.AdminAkunFragment
import com.app.eei.ui.admin.beranda.fragment.BerandaFragment
import com.app.eei.ui.admin.settingapp.fragment.AdminSettingFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val fragmentManager = supportFragmentManager
        val berandaFragment = BerandaFragment()
        fragmentManager
            .beginTransaction()
            .add(R.id.container, berandaFragment)
            .commit()
        binding.bottomNavigation.setItemSelected(R.id.home,true)
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
                    val adminSettingFragment = AdminSettingFragment()
                    fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, adminSettingFragment)
                        .commit()
                }
                R.id.akun->{
                    Toast.makeText(this, getString(R.string.akun), Toast.LENGTH_SHORT).show()
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