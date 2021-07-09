package com.app.eei.ui.admin.detail

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.eei.R
import com.app.eei.databinding.ActivityAdminDetailBinding
import com.app.eei.entity.News
import com.app.eei.ui.admin.beranda.MainActivity
import com.app.eei.ui.admin.beranda.viewmodel.NewsViewModel
import com.app.eei.ui.admin.editform.AdminEditActivity
import com.app.eei.ui.admin.editform.AdminEditActivity.Companion.EXTRA_DATA_EDIT
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.thecode.aestheticdialogs.*

class AdminDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    private lateinit var binding: ActivityAdminDetailBinding
    private lateinit var viewmodel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_detail)

        binding = ActivityAdminDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(NewsViewModel::class.java)
        supportActionBar?.hide()

        val data=intent.getParcelableExtra<News>(EXTRA_DATA) as News

        val contentString=data.contentNews
        val htmlAsSpanned:Spanned=Html.fromHtml(contentString)
        binding.titleNews.text=data.title
        binding.dateNews.text=data.dateNews
        binding.contentNews.text= htmlAsSpanned
        Glide.with(this)
            .load(data.imgNews)
            .into(binding.imgNews)

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.btnEdit.setOnClickListener {
            val intent= Intent(this, AdminEditActivity::class.java)
            intent.putExtra(EXTRA_DATA_EDIT,data)
            startActivity(intent)
        }
        binding.btnHapus.setOnClickListener {
            delData(ALERT_DIALOG_DELETE,data.id.toString())

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
    private fun delData(type: Int,id:String) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String

        if (isDialogClose) {
            dialogTitle = getString(R.string.batal)
            dialogMessage = getString(R.string.yakin_batal_menghapus)
        } else {
            dialogMessage = getString(R.string.yakin_menghapus)
            dialogTitle = getString(R.string.hapus_data)
        }

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMessage)
            .setCancelable(false)
            .setIcon(R.drawable.ic_baseline_warning_24)
            .setPositiveButton("Ya") { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {
                   viewmodel.delNews(id)
                    AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                        .setTitle("Postingan")
                        .setMessage("Berhasil Di Hapus")
                        .setCancelable(false)
                        .setDarkMode(false)
                        .setGravity(Gravity.CENTER)
                        .setAnimation(DialogAnimation.SHRINK)
                        .setOnClickListener(object : OnDialogClickListener {
                            override fun onClick(dialog: AestheticDialog.Builder) {
                                dialog.dismiss()
                                startActivity(Intent(this@AdminDetailActivity,MainActivity::class.java))
                                finish()
                                //actions...
                            }
                        })
                        .show()

                }
            }
            .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.black))
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.bg_del))
    }

}