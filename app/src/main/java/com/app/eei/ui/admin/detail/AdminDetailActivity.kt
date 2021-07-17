package com.app.eei.ui.admin.detail

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Base64
import android.util.Log
import android.view.Gravity
import android.webkit.WebChromeClient
import android.webkit.WebSettings
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
import com.app.eei.ui.admin.menu.berita.AdminNewsActivity
import com.app.eei.ui.admin.menu.event.AdminEventActivity
import com.app.eei.ui.admin.menu.komunitas.AdminKomunitasActivity
import com.app.eei.ui.admin.menu.mitra.AdminMitraActivity
import com.app.eei.ui.admin.menu.podcast.AdminPodcastActivity
import com.app.eei.ui.admin.menu.tips.AdminTipsActivity
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
    private var typeData:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_detail)
        Log.d("activty","AdminDetailActivity")
        binding = ActivityAdminDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(NewsViewModel::class.java)
        supportActionBar?.hide()

        val data=intent.getParcelableExtra<News>(EXTRA_DATA) as News
        val contentString=data.contentNews
        val htmlAsSpanned:Spanned=Html.fromHtml(contentString)
        typeData=data.type.toString()
        val unencodedHtml =
            "<html><style>" +
                    ".container {\n" +
                    "  position: relative;\n" +
                    "  overflow: hidden;\n" +
                    "  width: 100%;\n" +
                    " }" +
                    "iframe {\n" +
                    "  position: sticky;\n" +
                    "  top: 0;\n" +
                    "  left: 0;\n" +
                    "  bottom: 0;\n" +
                    "  right: 0;\n" +
                    "  width: 100%;\n" +
                    "  height: 30%;\n" +
                    "}" +
                    "</style><body ><div class=\"\">"+data.contentNews +"</div></body></html>";
        val encodedHtml = Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING)


        binding.webview.webChromeClient = WebChromeClient()
        binding.webview.settings.javaScriptEnabled=true
        binding.webview.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webview.settings.pluginState = WebSettings.PluginState.ON
        binding.webview.settings.mediaPlaybackRequiresUserGesture = false

        binding.webview.loadData(encodedHtml, "text/html", "base64")
        binding.titleNews.text=data.title
        binding.dateNews.text=data.dateNews
        Glide.with(this)
            .load(data.imgNews)
            .into(binding.imgNews)

        binding.btnBack.setOnClickListener {
            pindah()
            finish()
        }
        binding.btnEdit.setOnClickListener {
            val intent= Intent(this, AdminEditActivity::class.java)
            intent.putExtra(EXTRA_DATA_EDIT,data)
            startActivity(intent)
            finish()
        }
        binding.btnHapus.setOnClickListener {
            delData(ALERT_DIALOG_DELETE,data.id.toString())

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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
                        .setTitle(typeData)
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
    private fun pindah(){
        when(typeData){
            getString(R.string.podcast)->{
                startActivity(Intent(this, AdminPodcastActivity::class.java))
                finish()
            }
            getString(R.string.tipsdantricks)->{
                startActivity(Intent(this, AdminTipsActivity::class.java))
                finish()
            }
            getString(R.string.berita)->{
                startActivity(Intent(this, AdminNewsActivity::class.java))
                finish()
            }
            getString(R.string.event)->{
                startActivity(Intent(this, AdminEventActivity::class.java))
                finish()
            }
            getString(R.string.mitra)->{
                startActivity(Intent(this, AdminMitraActivity::class.java))
                finish()
            }
            getString(R.string.komunitas)->{
                startActivity(Intent(this, AdminKomunitasActivity::class.java))
                finish()
            }
        }
    }
}