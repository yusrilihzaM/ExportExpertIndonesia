package com.app.eei.ui.guest.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Base64
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import com.app.eei.R
import com.app.eei.databinding.ActivityAdminDetailBinding
import com.app.eei.databinding.ActivityGuestDetail2Binding
import com.app.eei.entity.News
import com.app.eei.extensions.Extensions.toast
import com.app.eei.ui.admin.beranda.MainActivity
import com.app.eei.ui.admin.detail.AdminDetailActivity
import com.app.eei.ui.admin.menu.berita.AdminNewsActivity
import com.app.eei.ui.admin.menu.event.AdminEventActivity
import com.app.eei.ui.admin.menu.komunitas.AdminKomunitasActivity
import com.app.eei.ui.admin.menu.mitra.AdminMitraActivity
import com.app.eei.ui.admin.menu.podcast.AdminPodcastActivity
import com.app.eei.ui.admin.menu.tips.AdminTipsActivity
import com.app.eei.ui.guest.GuestMainActivity
import com.app.eei.ui.guest.menu.berita.GuestNewsActivity
import com.app.eei.ui.guest.menu.event.GuestEventActivity
import com.app.eei.ui.guest.menu.komunitas.GuestKomunitasActivity
import com.app.eei.ui.guest.menu.mitra.GuestMitraActivity
import com.app.eei.ui.guest.menu.podcast.GuestPodcastActivity
import com.app.eei.ui.guest.menu.tips.GuestTipsActivity
import com.bumptech.glide.Glide

class GuestDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }
    private lateinit var binding: ActivityGuestDetail2Binding
    private var typeData:String=""
    private var letak:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_detail2)

        binding = ActivityGuestDetail2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val data=intent.getParcelableExtra<News>(EXTRA_DATA) as News
        val dataletak=intent.getStringExtra("data").toString()
        letak=dataletak
        val list=data.hastag
        val separator = "\n"
        val hastag = list.joinToString(separator)
        binding.hastag.text=hastag
        typeData=data.type.toString()
        val contentString=data.contentNews
        val htmlAsSpanned: Spanned = Html.fromHtml(contentString)
        binding.titleNews.text=data.title
        binding.dateNews.text=data.dateNews
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
        Glide.with(this)
            .load(data.imgNews)
            .into(binding.imgNews)


        binding.btnBack.setOnClickListener {
            pindah()
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        pindah()
    }
    private fun pindah(){

        if(letak=="beranda"){
            startActivity(Intent(this, GuestMainActivity::class.java))
            finish()
        }else{
            when(typeData){
                getString(R.string.podcast)->{
                    startActivity(Intent(this, GuestPodcastActivity::class.java))
                    finish()
                }
                getString(R.string.tipsdantricks)->{
                    startActivity(Intent(this, GuestTipsActivity::class.java))
                    finish()
                }
                getString(R.string.berita)->{
                    startActivity(Intent(this, GuestNewsActivity::class.java))
                    finish()
                }
                getString(R.string.event)->{
                    startActivity(Intent(this, GuestEventActivity::class.java))
                    finish()
                }
                getString(R.string.mitra)->{
                    startActivity(Intent(this, GuestMitraActivity::class.java))
                    finish()
                }
                getString(R.string.komunitas)->{
                    startActivity(Intent(this, GuestKomunitasActivity::class.java))
                    finish()
                }
            }
        }

    }
}