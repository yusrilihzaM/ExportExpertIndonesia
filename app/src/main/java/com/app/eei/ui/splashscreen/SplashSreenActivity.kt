package com.app.eei.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.eei.R
import com.app.eei.databinding.ActivitySplashSreenBinding
import com.app.eei.ui.login.MainActivity
import com.app.eei.ui.splashscreen.viewmodel.InfoViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
            Glide.with(this)
                .load(data[0].icLogo)
                .into(binding.icLogo)

            Glide.with(this)
                .load(data[0].bgSplash)
                .into(binding.bg)
            Handler(Looper.getMainLooper()).postDelayed({ moveActivity() },TIME)

        })
    }
    private fun moveActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}