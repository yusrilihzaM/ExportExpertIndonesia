package com.app.eei.ui.admin.settingapp.fragment.namaapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.eei.R
import com.app.eei.databinding.ActivityNameAppBinding
import com.app.eei.ui.admin.settingapp.fragment.namaapp.EditNamaActivity.Companion.DATA_NAMA_EDIT
import com.app.eei.ui.splashscreen.viewmodel.InfoViewModel
import es.dmoral.toasty.Toasty

class NameAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNameAppBinding
    private lateinit var viewModel: InfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name_app)
        binding = ActivityNameAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.btnBack.setOnClickListener { finish() }

        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(InfoViewModel::class.java)
        viewModel.setInfoDetail()
        viewModel.getAppInfo().observe(this,{data->
            if (data.size==0){
                Toasty.error(this, "No Internet Access.", Toast.LENGTH_SHORT, true).show();
            }
            binding.titleApp.text=data[0].namaAplikasi
            binding.edit.setOnClickListener {
                Log.d("data", data.toString())
                val intent= Intent(this, EditNamaActivity::class.java)
                intent.putExtra(DATA_NAMA_EDIT,data[0])
                startActivity(intent)
                finish()
            }
        })
    }
}