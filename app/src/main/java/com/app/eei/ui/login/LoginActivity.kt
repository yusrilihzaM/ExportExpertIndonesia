package com.app.eei.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.lifecycle.ViewModelProvider
import com.app.eei.R
import com.app.eei.databinding.ActivityLoginBinding
import com.app.eei.databinding.ActivityTentangBinding
import com.app.eei.extensions.Extensions.toast
import com.app.eei.ui.admin.beranda.MainActivity
import com.app.eei.ui.guest.GuestMainActivity
import com.app.eei.ui.splashscreen.viewmodel.InfoViewModel
import com.app.eei.utils.FirebaseUtils.firebaseAuth
import com.google.android.gms.tasks.OnFailureListener;
import com.bumptech.glide.Glide
import com.thecode.aestheticdialogs.*
import es.dmoral.toasty.Toasty
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: InfoViewModel
    lateinit var signInEmail: String
    lateinit var signInPassword: String
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

            if (binding.edtEmail.getTextValue.isEmpty() and binding.edtPassword.getTextValue.isEmpty() ){
                binding.edtEmail.setIsErrorEnable(true)
                binding.edtEmail.setIsErrorEnable(true)
                status(true,"Email dan Password tidak boleh kosong.")
            }
            else if (binding.edtEmail.getTextValue.isEmpty()){
                binding.edtEmail.setIsErrorEnable(true)
                status(true,"Email tidak boleh kosong.")
            }
            else if (binding.edtPassword.getTextValue.isEmpty()){
                binding.edtPassword.setIsErrorEnable(true)
                status(true,"Password tidak boleh kosong.")

            }
            else{
                signInEmail = binding.edtEmail.getTextValue.toString().trim()
                signInPassword = binding.edtPassword.getTextValue.toString().trim()
                binding.btnMasuk.startAnimation()
                firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
                    .addOnCompleteListener { signIn ->
                        if (signIn.isSuccessful) {
                            binding.btnMasuk.revertAnimation()
                            AestheticDialog.Builder(this@LoginActivity, DialogStyle.TOASTER, DialogType.SUCCESS)
                                .setTitle("Sign In")
                                .setMessage("Success")
                                .setCancelable(false)
                                .setDarkMode(false)
                                .setGravity(Gravity.CENTER)
                                .setAnimation(DialogAnimation.SHRINK)
                                .setOnClickListener(object : OnDialogClickListener {
                                    override fun onClick(dialog: AestheticDialog.Builder) {
                                        dialog.dismiss()
                                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                        finish()
                                        //actions...
                                    }
                                })
                                .show()
//                            startActivity(Intent(this,MainActivity::class.java))
//                            toast("signed in successfully")
//                            finish()
                        }
//                        else {
//                            AestheticDialog.Builder(this, DialogStyle.TOASTER, DialogType.ERROR)
//                                .setTitle("Sign In")
//                                .setMessage("Failed")
//                                .setCancelable(false)
//                                .setDarkMode(false)
//                                .setGravity(Gravity.CENTER)
//                                .setAnimation(DialogAnimation.SHRINK)
//                                .setOnClickListener(object : OnDialogClickListener {
//                                    override fun onClick(dialog: AestheticDialog.Builder) {
//                                        dialog.dismiss()
//                                        //actions...
//                                    }
//                                })
//                                .show()
//                        }
                    }
                    .addOnFailureListener(object :OnFailureListener{
                        override fun onFailure(p0: Exception) {
                            binding.btnMasuk.revertAnimation()
                            AestheticDialog.Builder(this@LoginActivity, DialogStyle.TOASTER, DialogType.ERROR)
                                .setTitle("Sign In")
                                .setMessage(p0.localizedMessage)
                                .setCancelable(false)
                                .setDarkMode(false)
                                .setGravity(Gravity.CENTER)
                                .setAnimation(DialogAnimation.SLIDE_DOWN)
                                .setOnClickListener(object : OnDialogClickListener {
                                    override fun onClick(dialog: AestheticDialog.Builder) {
                                        dialog.dismiss()
                                        //actions...
                                    }
                                })
                                .show()
                        }
                    })
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