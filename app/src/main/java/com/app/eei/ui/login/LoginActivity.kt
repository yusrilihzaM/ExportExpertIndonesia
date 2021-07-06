package com.app.eei.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.lifecycle.ViewModelProvider
import com.app.eei.R
import com.app.eei.databinding.ActivityLoginBinding
import com.app.eei.databinding.ActivityTentangBinding
import com.app.eei.ui.admin.beranda.MainActivity
import com.app.eei.ui.guest.GuestMainActivity
import com.app.eei.ui.splashscreen.viewmodel.InfoViewModel
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: InfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(InfoViewModel::class.java)
        viewModel.setInfoDetail()
        viewModel.getAppInfo().observe(this,{data->
            if (data.size==0){
                Toasty.error(this, "No Internet Access.", Toast.LENGTH_SHORT, true).show();
            }

            Glide.with(this)
                .load(data[0].icLogo)
                .into(binding.icLogo)


        })
        binding.btnTamu.setOnClickListener {
            startActivity(Intent(this, GuestMainActivity::class.java))
            finish()
        }
        binding.btnMasuk.setOnClickListener {


            if (binding.edtEmail.getTextValue.isEmpty()){
                binding.edtEmail.setIsErrorEnable(true)
                status(true,"Email tidak boleh kosong.")
            }
            else if (binding.edtPassword.getTextValue.isEmpty()){
                binding.edtPassword.setIsErrorEnable(true)
                status(true,"Password tidak boleh kosong.")

            }
            else{
                binding.btnMasuk.startAnimation()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
                binding.btnMasuk.revertAnimation()
            }


        }

    }
    fun status(muncul:Boolean, pesan:String){
        if(muncul){
            binding.status.visibility=View.VISIBLE
            binding.status.text=pesan
        }
    }
}