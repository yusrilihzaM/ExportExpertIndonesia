package com.app.eei.ui.admin.settingapp.fragment.descapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.eei.R
import com.app.eei.databinding.ActivityDescAppBinding
import com.app.eei.databinding.ActivityEditNamaBinding
import com.app.eei.databinding.ActivityNameAppBinding
import com.app.eei.ui.admin.settingapp.fragment.namaapp.EditNamaActivity
import com.app.eei.ui.splashscreen.viewmodel.InfoViewModel
import es.dmoral.toasty.Toasty
import jp.wasabeef.richeditor.RichEditor

class DescAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescAppBinding
    private lateinit var viewModel: InfoViewModel
    private lateinit var mEditor: RichEditor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desc_app)
        binding = ActivityDescAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.btnBack.setOnClickListener { finish() }
        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(InfoViewModel::class.java)
        viewModel.setInfoDetail()
        viewModel.getAppInfo().observe(this,{data->
            if (data.size==0){
                Toasty.error(this, "No Internet Access.", Toast.LENGTH_SHORT, true).show();
            }
            binding.descApp.text= Html.fromHtml(data[0].deskripsiAplikasi)
        })
        binding.btnEdit.setOnClickListener {
            startActivity(Intent(this,EditDescAppActivity::class.java))
            finish()
        }
    }

}