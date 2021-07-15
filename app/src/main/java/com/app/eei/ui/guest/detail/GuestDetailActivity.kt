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
import com.app.eei.ui.admin.beranda.MainActivity
import com.app.eei.ui.admin.detail.AdminDetailActivity
import com.app.eei.ui.guest.GuestMainActivity
import com.bumptech.glide.Glide

class GuestDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }
    private lateinit var binding: ActivityGuestDetail2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_detail2)

        binding = ActivityGuestDetail2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val data=intent.getParcelableExtra<News>(EXTRA_DATA) as News
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
            startActivity(Intent(this, GuestMainActivity::class.java))
            finish()
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, GuestMainActivity::class.java))
        finish()
    }
}