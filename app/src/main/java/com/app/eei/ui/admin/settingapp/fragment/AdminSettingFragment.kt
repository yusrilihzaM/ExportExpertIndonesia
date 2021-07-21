package com.app.eei.ui.admin.settingapp.fragment

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.eei.R
import com.app.eei.adapter.SettingsMenuAdapter
import com.app.eei.databinding.FragmentAdminSettingBinding
import com.app.eei.databinding.FragmentBerandaBinding
import com.app.eei.entity.Menu
import com.app.eei.ui.admin.settingapp.fragment.bgsplashapp.BgSplashActivity
import com.app.eei.ui.admin.settingapp.fragment.descapp.DescAppActivity
import com.app.eei.ui.admin.settingapp.fragment.logoapp.LogoAppActivity
import com.app.eei.ui.admin.settingapp.fragment.namaapp.NameAppActivity
import com.app.eei.ui.admin.settingapp.fragment.sosmed.SosmedActivity


class AdminSettingFragment : Fragment() {
    private lateinit var dataTitle: Array<String>
    private lateinit var dataIc: TypedArray
    private lateinit var rvMenu: RecyclerView
    private lateinit var menuListAdapter: SettingsMenuAdapter
    private lateinit var binding: FragmentAdminSettingBinding
    private var list: ArrayList<Menu> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentAdminSettingBinding.bind(view)
        showMenu()
    }

    private fun showMenu() {
        rvMenu=binding.rvSetting
        rvMenu.setHasFixedSize(true)
        rvMenu.layoutManager = LinearLayoutManager(context)
        menuListAdapter = SettingsMenuAdapter(list)
        list.addAll(getListMenu())
        rvMenu.adapter = menuListAdapter
        menuListAdapter.setOnItemClickCallback(object :SettingsMenuAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Menu) {
                val intent: Intent
                when(data.title){
                    getString(R.string.nama_app)->{
                        intent= Intent(context, NameAppActivity::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.logo_aplikasi)->{
                        intent= Intent(context, LogoAppActivity::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.desc_app)->{
                        intent= Intent(context, DescAppActivity::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.bg_splash)->{

                        intent= Intent(context, BgSplashActivity::class.java)
                        startActivity(intent)
                    }
                    getString(R.string.sosial_media)->{

                        intent= Intent(context, SosmedActivity::class.java)
                        startActivity(intent)
                    }
                }

            }
        })
    }
    private fun getListMenu(): ArrayList<Menu> {
        val listMenu= ArrayList<Menu>()
        dataTitle = resources.getStringArray(R.array.data_title)
        dataIc = resources.obtainTypedArray(R.array.data_ic)
        for(position in dataTitle.indices){
            val menu= Menu(
                dataTitle[position],
                dataIc.getResourceId(position, -1)
            )
            listMenu.add(menu)
        }
        return listMenu
    }
}