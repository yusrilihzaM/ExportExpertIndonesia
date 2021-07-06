package com.app.eei.ui.tentang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.app.eei.R
import com.app.eei.databinding.ActivitySplashSreenBinding
import com.app.eei.databinding.ActivityTentangBinding
import com.app.eei.ui.splashscreen.SplashSreenActivity
import com.app.eei.ui.splashscreen.viewmodel.InfoViewModel
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty

class TentangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTentangBinding
    private lateinit var viewModel: InfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang)
        binding = ActivityTentangBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnBack.setOnClickListener { finish() }
        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(InfoViewModel::class.java)
        viewModel.setInfoDetail()
        viewModel.getAppInfo().observe(this,{data->
            if (data.size==0){
                Toasty.error(this, "No Internet Access.", Toast.LENGTH_SHORT, true).show();
            }
            binding.namaAplikasi.text=data[0].namaAplikasi
            binding.deskripsiAplikasi.text=data[0].deskripsiAplikasi
            Glide.with(this)
                .load(data[0].icLogo)
                .into(binding.icLogoogo)


        })
    }

}