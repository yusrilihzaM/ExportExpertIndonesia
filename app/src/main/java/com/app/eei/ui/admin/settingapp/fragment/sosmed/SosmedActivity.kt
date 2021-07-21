package com.app.eei.ui.admin.settingapp.fragment.sosmed

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.eei.R
import com.app.eei.databinding.ActivitySosmedBinding
import com.app.eei.databinding.ActivityTentangBinding
import com.app.eei.ui.admin.detail.AdminDetailActivity
import com.app.eei.ui.guest.GuestMainActivity
import com.app.eei.ui.tentang.EditSosmedActivity
import com.app.eei.ui.tentang.SosmedViewModel


class SosmedActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySosmedBinding
    private lateinit var viewModel: SosmedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sosmed)
        binding = ActivitySosmedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val upArrow = resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + getString(R.string.sosial_media) + "</font>")

        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SosmedViewModel::class.java)
        viewModel.setSosmed()
        viewModel.getSosmed().observe(this,{data->

            binding.tvig.text=data[0].ig
            binding.tvWa.text=data[0].wa
            binding.tvtiktok.text=data[0].tiktok
            binding.tvfacebook.text="Expert - Export Indonesia"
            binding.tvlinkedn.text=data[0].linkedn
            binding.tvTelegram.text=data[0].telegram
            binding.tvemail.text=data[0].email

            binding.btnWA.setOnClickListener {
                val intent= Intent(this, EditSosmedActivity::class.java)
                intent.putExtra(EditSosmedActivity.EXTRA_SOSMED,data)
                intent.putExtra("data",getString(R.string.Whatsapp))
                startActivity(intent)
            }

            binding.btnTelegram.setOnClickListener {
                val intent= Intent(this, EditSosmedActivity::class.java)
                intent.putExtra(EditSosmedActivity.EXTRA_SOSMED,data)
                intent.putExtra("data",getString(R.string.Telegram))
                startActivity(intent)
            }

            binding.btnig.setOnClickListener {
                val intent= Intent(this, EditSosmedActivity::class.java)
                intent.putExtra(EditSosmedActivity.EXTRA_SOSMED,data)
                intent.putExtra("data",getString(R.string.Instagram))
                startActivity(intent)
            }

            binding.btntiktok.setOnClickListener {
                val intent= Intent(this, EditSosmedActivity::class.java)
                intent.putExtra(EditSosmedActivity.EXTRA_SOSMED,data)
                intent.putExtra("data",getString(R.string.Tiktok))
                startActivity(intent)
            }

            binding.btnfacebook.setOnClickListener {
                val intent= Intent(this, EditSosmedActivity::class.java)
                intent.putExtra(EditSosmedActivity.EXTRA_SOSMED,data)
                intent.putExtra("data",getString(R.string.Facebook))
                startActivity(intent)
            }

            binding.btnlinkedn.setOnClickListener {
                val intent= Intent(this, EditSosmedActivity::class.java)
                intent.putExtra(EditSosmedActivity.EXTRA_SOSMED,data)
                intent.putExtra("data",getString(R.string.Linkedn))
                startActivity(intent)
            }
            binding.btnemail.setOnClickListener {
                val intent= Intent(this, EditSosmedActivity::class.java)
                intent.putExtra(EditSosmedActivity.EXTRA_SOSMED,data)
                intent.putExtra("data",getString(R.string.Email))
                startActivity(intent)
            }
        })
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            16908332 -> {
                finish()
                true
            }
            else -> true
        }
    }
}