package com.app.eei.ui.admin.settingapp.fragment.namaapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.app.eei.R
import com.app.eei.databinding.ActivityEditNamaBinding
import com.app.eei.databinding.ActivityTentangBinding
import com.app.eei.entity.InfoApp
import com.app.eei.entity.News
import com.app.eei.extensions.Extensions.toast
import com.app.eei.ui.admin.beranda.MainActivity
import com.app.eei.ui.admin.editform.AdminEditActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.thecode.aestheticdialogs.*

class EditNamaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNamaBinding
    var db: FirebaseFirestore? = null
    var nama:String="Export Expert Indonesia"
    companion object {
        const val DATA_NAMA_EDIT = "data_nama_edit"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_nama)
        binding = ActivityEditNamaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val upArrow =resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + "Edit Nama Aplikasi" + "</font>")
        db = FirebaseFirestore.getInstance()
        val data=intent.getParcelableExtra<InfoApp>(DATA_NAMA_EDIT) as InfoApp
        Log.d("dataNama", data.namaAplikasi.toString())
        binding.edtTitleApp.setTextValue(data.namaAplikasi.toString())


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.submit->{
                nama=binding.edtTitleApp.getTextValue.toString()
                db?.collection("informasi_aplikasi")?.document("1")
                    ?.update("namaAplikasi",nama)
                    ?.addOnSuccessListener {
                        Log.d("edit", nama)
                        toast("Nama aplikasi berhasil di simpan")
                        AestheticDialog.Builder(this@EditNamaActivity, DialogStyle.FLAT, DialogType.SUCCESS)
                            .setTitle("Deskripsi Aplikasi")
                            .setMessage("Perubahan Berhasil Di Simpan")
                            .setCancelable(false)
                            .setDarkMode(false)
                            .setGravity(Gravity.CENTER)
                            .setAnimation(DialogAnimation.SHRINK)
                            .setOnClickListener(object : OnDialogClickListener {
                                override fun onClick(dialog: AestheticDialog.Builder) {
                                    dialog.dismiss()
                                    startActivity(Intent(this@EditNamaActivity,NameAppActivity::class.java))
                                    finish()
                                    //actions...
                                }
                            })
                            .show()
                    }
                    ?.addOnFailureListener { e -> Log.w("editNama", "Error writing document", e) }
                true
            }
            16908332->{
                finish()
                true
            }
            else -> true
        }
    }
}