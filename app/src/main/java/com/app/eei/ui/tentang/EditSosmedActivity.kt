package com.app.eei.ui.tentang

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import com.app.eei.R
import com.app.eei.databinding.ActivityEditSosmedBinding
import com.app.eei.databinding.ActivitySosmedBinding
import com.app.eei.entity.News
import com.app.eei.entity.Sosmed
import com.app.eei.extensions.Extensions.toast
import com.app.eei.ui.admin.settingapp.fragment.namaapp.NameAppActivity
import com.app.eei.ui.admin.settingapp.fragment.sosmed.SosmedActivity
import com.app.eei.ui.guest.GuestMainActivity
import com.app.eei.ui.guest.detail.GuestDetailActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.thecode.aestheticdialogs.*

class EditSosmedActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SOSMED = "extra_sosmed"
    }
    private lateinit var binding: ActivityEditSosmedBinding
    private lateinit var viewModel: SosmedViewModel
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sosmed)

        binding = ActivityEditSosmedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val upArrow = resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        val db = FirebaseFirestore.getInstance()
        val data=intent.getParcelableExtra<Sosmed>(EXTRA_SOSMED) as Sosmed
        val dataletak=intent.getStringExtra("data").toString()
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">$dataletak</font>")

        when(dataletak){
            getString(R.string.Whatsapp)->{
                binding.wa.visibility=View.VISIBLE
                binding.edtWa.setText(data.wa)
                binding.btnWa.setOnClickListener {
                    binding.btnWa.startAnimation()
                    val wa=binding.edtWa.text.toString().trim()
                    db?.collection("sosmed")?.document("1111")
                        ?.update("wa",wa)
                        ?.addOnSuccessListener {
                            Log.d("edit", wa)
                            AestheticDialog.Builder(this@EditSosmedActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                                .setTitle(getString(R.string.Whatsapp))
                                .setMessage("Perubahan Berhasil Di Simpan")
                                .setCancelable(false)
                                .setDarkMode(false)
                                .setGravity(Gravity.CENTER)
                                .setAnimation(DialogAnimation.SHRINK)
                                .setOnClickListener(object : OnDialogClickListener {
                                    override fun onClick(dialog: AestheticDialog.Builder) {
                                        binding.btnWa.stopProgressAnimation()
                                        startActivity(Intent(this@EditSosmedActivity,SosmedActivity::class.java))
                                        finish()
                                        //actions...
                                    }
                                })
                                .show()
                        }
                        ?.addOnFailureListener { e -> Log.w("wa", "Error writing document", e) }
                }
            }
            getString(R.string.Telegram)->{
                binding.telegram.visibility=View.VISIBLE
                binding.edtTelegram.setText(data.telegram)
                binding.btnTele.setOnClickListener {
                    binding.btnTele.startAnimation()
                    val telegram=binding.edtWa.text.toString().trim()
                    db?.collection("sosmed")?.document("1111")
                        ?.update("telegram",telegram)
                        ?.addOnSuccessListener {
                            Log.d("edit", telegram)
                            AestheticDialog.Builder(this@EditSosmedActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                                .setTitle(getString(R.string.Telegram))
                                .setMessage("Perubahan Berhasil Di Simpan")
                                .setCancelable(false)
                                .setDarkMode(false)
                                .setGravity(Gravity.CENTER)
                                .setAnimation(DialogAnimation.SHRINK)
                                .setOnClickListener(object : OnDialogClickListener {
                                    override fun onClick(dialog: AestheticDialog.Builder) {
                                        binding.btnWa.stopProgressAnimation()
                                        startActivity(Intent(this@EditSosmedActivity,SosmedActivity::class.java))
                                        finish()
                                        //actions...
                                    }
                                })
                                .show()
                        }
                        ?.addOnFailureListener { e -> Log.w("wa", "Error writing document", e) }
                }
            }
            getString(R.string.Instagram)->{
                binding.ig.visibility=View.VISIBLE
                binding.edtIg.setText(data.ig)
                binding.btnIg.setOnClickListener {
                    binding.btnIg.startAnimation()
                    val ig=binding.edtIg.text.toString().trim()
                    db?.collection("sosmed")?.document("1111")
                        ?.update("ig",ig)
                        ?.addOnSuccessListener {
                            Log.d("edit", ig)
                            AestheticDialog.Builder(this@EditSosmedActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                                .setTitle(getString(R.string.Instagram))
                                .setMessage("Perubahan Berhasil Di Simpan")
                                .setCancelable(false)
                                .setDarkMode(false)
                                .setGravity(Gravity.CENTER)
                                .setAnimation(DialogAnimation.SHRINK)
                                .setOnClickListener(object : OnDialogClickListener {
                                    override fun onClick(dialog: AestheticDialog.Builder) {
                                        binding.btnWa.stopProgressAnimation()
                                        startActivity(Intent(this@EditSosmedActivity,SosmedActivity::class.java))
                                        finish()
                                        //actions...
                                    }
                                })
                                .show()
                        }
                        ?.addOnFailureListener { e -> Log.w("ig", "Error writing document", e) }
                }
            }
            getString(R.string.Tiktok)->{
                binding.tiktok.visibility=View.VISIBLE
                binding.edtTiktok.setText(data.tiktok)
                binding.btnTiktok.setOnClickListener {
                    binding.btnTiktok.startAnimation()
                    val tiktok=binding.edtTiktok.text.toString().trim()
                    db?.collection("sosmed")?.document("1111")
                        ?.update("tiktok",tiktok)
                        ?.addOnSuccessListener {
                            Log.d("tiktok", tiktok)
                            AestheticDialog.Builder(this@EditSosmedActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                                .setTitle(getString(R.string.Tiktok))
                                .setMessage("Perubahan Berhasil Di Simpan")
                                .setCancelable(false)
                                .setDarkMode(false)
                                .setGravity(Gravity.CENTER)
                                .setAnimation(DialogAnimation.SHRINK)
                                .setOnClickListener(object : OnDialogClickListener {
                                    override fun onClick(dialog: AestheticDialog.Builder) {
                                        binding.btnWa.stopProgressAnimation()
                                        startActivity(Intent(this@EditSosmedActivity,SosmedActivity::class.java))
                                        finish()
                                        //actions...
                                    }
                                })
                                .show()
                        }
                        ?.addOnFailureListener { e -> Log.w("tiktok", "Error writing document", e) }
                }
            }
            getString(R.string.Facebook)->{
                binding.fb.visibility=View.VISIBLE
                binding.edtFb.setText(data.facebook)
                binding.btnFb.setOnClickListener {
                    binding.btnFb.startAnimation()
                    val facebook=binding.edtFb.text.toString().trim()
                    db?.collection("sosmed")?.document("1111")
                        ?.update("facebook",facebook)
                        ?.addOnSuccessListener {
                            Log.d("facebook", facebook)
                            AestheticDialog.Builder(this@EditSosmedActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                                .setTitle(getString(R.string.Facebook))
                                .setMessage("Perubahan Berhasil Di Simpan")
                                .setCancelable(false)
                                .setDarkMode(false)
                                .setGravity(Gravity.CENTER)
                                .setAnimation(DialogAnimation.SHRINK)
                                .setOnClickListener(object : OnDialogClickListener {
                                    override fun onClick(dialog: AestheticDialog.Builder) {
                                        binding.btnWa.stopProgressAnimation()
                                        startActivity(Intent(this@EditSosmedActivity,SosmedActivity::class.java))
                                        finish()
                                        //actions...
                                    }
                                })
                                .show()
                        }
                        ?.addOnFailureListener { e -> Log.w("facebook", "Error writing document", e) }
                }
            }
            getString(R.string.Linkedn)->{
                binding.likedin.visibility=View.VISIBLE
                binding.edtLikedin.setText(data.linkedn)
                binding.btnLikedin.setOnClickListener {
                    binding.btnLikedin.startAnimation()
                    val linkedn=binding.edtLikedin.text.toString().trim()
                    db?.collection("sosmed")?.document("1111")
                        ?.update("linkedn",linkedn)
                        ?.addOnSuccessListener {
                            Log.d("linkedn", linkedn)
                            AestheticDialog.Builder(this@EditSosmedActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                                .setTitle(getString(R.string.Linkedn))
                                .setMessage("Perubahan Berhasil Di Simpan")
                                .setCancelable(false)
                                .setDarkMode(false)
                                .setGravity(Gravity.CENTER)
                                .setAnimation(DialogAnimation.SHRINK)
                                .setOnClickListener(object : OnDialogClickListener {
                                    override fun onClick(dialog: AestheticDialog.Builder) {
                                        binding.btnWa.stopProgressAnimation()
                                        startActivity(Intent(this@EditSosmedActivity,SosmedActivity::class.java))
                                        finish()
                                        //actions...
                                    }
                                })
                                .show()
                        }
                        ?.addOnFailureListener { e -> Log.w("linkedn", "Error writing document", e) }
                }
            }
            getString(R.string.Email)->{
                binding.email.visibility=View.VISIBLE
                binding.edtEmail.setText(data.email)
                binding.btnEmail.setOnClickListener {
                    binding.btnEmail.startAnimation()
                    val email=binding.edtEmail.text.toString().trim()
                    db?.collection("sosmed")?.document("1111")
                        ?.update("email",email)
                        ?.addOnSuccessListener {
                            Log.d("email", email)
                            AestheticDialog.Builder(this@EditSosmedActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                                .setTitle(getString(R.string.Email))
                                .setMessage("Perubahan Berhasil Di Simpan")
                                .setCancelable(false)
                                .setDarkMode(false)
                                .setGravity(Gravity.CENTER)
                                .setAnimation(DialogAnimation.SHRINK)
                                .setOnClickListener(object : OnDialogClickListener {
                                    override fun onClick(dialog: AestheticDialog.Builder) {
                                        binding.btnWa.stopProgressAnimation()
                                        startActivity(Intent(this@EditSosmedActivity,SosmedActivity::class.java))
                                        finish()
                                        //actions...
                                    }
                                })
                                .show()
                        }
                        ?.addOnFailureListener { e -> Log.w("email", "Error writing document", e) }
                }
            }
        }


    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            16908332 -> {
                startActivity(Intent(this@EditSosmedActivity,SosmedActivity::class.java))
                finish()
                true
            }
            else -> true
        }
    }
}