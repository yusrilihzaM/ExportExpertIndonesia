package com.app.eei.ui.tentang

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import com.app.eei.R
import com.app.eei.databinding.ActivityEditSosmedBinding
import com.app.eei.databinding.ActivitySosmedBinding
import com.app.eei.ui.guest.GuestMainActivity

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


        val dataletak=intent.getStringExtra("data").toString()
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">$dataletak</font>")

        when(dataletak){
            getString(R.string.Whatsapp)->{

            }
            getString(R.string.Telegram)->{

            }
            getString(R.string.Instagram)->{

            }
            getString(R.string.Tiktok)->{

            }
            getString(R.string.Facebook)->{

            }
            getString(R.string.Linkedn)->{

            }
            getString(R.string.Email)->{

            }
        }
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