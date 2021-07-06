package com.app.eei.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import coil.ComponentRegistry
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.app.eei.R
import com.app.eei.databinding.ActivitySplashSreenBinding
import com.app.eei.ui.admin.beranda.MainActivity
import com.app.eei.ui.guest.GuestMainActivity
import com.app.eei.ui.splashscreen.viewmodel.InfoViewModel
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty

class SplashSreenActivity : AppCompatActivity() {
    companion object{
        const val TIME:Long=8000
    }
    private lateinit var binding: ActivitySplashSreenBinding
    private lateinit var viewModel: InfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_sreen)
        supportActionBar?.hide()

        binding = ActivitySplashSreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(InfoViewModel::class.java)
        viewModel.setInfoDetail()
        viewModel.getAppInfo().observe(this,{data->
            if (data.size==0){
                Toasty.error(this, "No Internet Access.", Toast.LENGTH_SHORT, true).show();
            }
            val imageLoader = ImageLoader.Builder(this)
                .componentRegistry {
                    add(SvgDecoder(applicationContext))
                }
                .build()
            val request = ImageRequest.Builder(this)
                .data(data[0].bgSplash)
                .crossfade(true)
                .target( binding.bg)
                .build()
            imageLoader.enqueue(request)

            Glide.with(this)
                .load(data[0].icLogo)
                .into(binding.icLogo)

            Handler(Looper.getMainLooper()).postDelayed({ moveActivity() },TIME)

        })
    }

    private fun moveActivity() {
        startActivity(Intent(this, GuestMainActivity::class.java))
        finish()
    }

}


