package com.app.eei.ui.admin.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.widget.Toast
import com.app.eei.R
import com.app.eei.databinding.ActivityAdminDetailBinding
import com.app.eei.entity.News
import com.bumptech.glide.Glide

class AdminDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DATA = "extra_data"
    }
    private lateinit var binding: ActivityAdminDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_detail)

        binding = ActivityAdminDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            finish()
        }
        binding.btnEdit.setOnClickListener {
            Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show()
        }
        binding.btnHapus.setOnClickListener {
            Toast.makeText(this, "Hapus", Toast.LENGTH_SHORT).show()
        }
    }
}