package com.app.eei.ui.guest.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import com.app.eei.R
import com.app.eei.databinding.ActivityAdminDetailBinding
import com.app.eei.databinding.ActivityGuestDetail2Binding
import com.app.eei.entity.News
import com.app.eei.ui.admin.detail.AdminDetailActivity
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
        binding.contentNews.text= htmlAsSpanned
        Glide.with(this)
            .load(data.imgNews)
            .into(binding.imgNews)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}