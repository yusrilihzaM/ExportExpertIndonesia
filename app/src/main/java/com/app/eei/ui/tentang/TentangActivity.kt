package com.app.eei.ui.tentang

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.eei.R
import com.app.eei.databinding.ActivityTentangBinding
import com.app.eei.ui.splashscreen.viewmodel.InfoViewModel
import com.bumptech.glide.Glide
import es.dmoral.toasty.Toasty


class TentangActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTentangBinding
    private lateinit var viewModel: InfoViewModel
    private lateinit var viewModelSosmed: SosmedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang)
        binding = ActivityTentangBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnBack.setOnClickListener { finish() }
        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(InfoViewModel::class.java)
        viewModelSosmed= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(SosmedViewModel::class.java)
        viewModel.setInfoDetail()
        viewModel.getAppInfo().observe(this,{data->
            if (data.size==0){
                Toasty.error(this, "No Internet Access.", Toast.LENGTH_SHORT, true).show();
            }
            binding.namaAplikasi.text=data[0].namaAplikasi
            binding.deskripsiAplikasi.text= Html.fromHtml(data[0].deskripsiAplikasi)
            Glide.with(this)
                .load(data[0].icLogo)
                .into(binding.icLogoogo)

        })

        viewModelSosmed.setSosmed()
        viewModelSosmed.getSosmed().observe(this,{data->

            binding.tvig.text=data[0].ig
            binding.tvWa.text=data[0].wa
            binding.tvtiktok.text=data[0].tiktok
            binding.tvfacebook.text="Expert - Export Indonesia"
            binding.tvlinkedn.text=data[0].linkedn
            binding.tvTelegram.text=data[0].telegram
            binding.tvemail.text=data[0].email

            binding.btnWA.setOnClickListener {
                val url = "https://api.whatsapp.com/send?phone=${data[0].wa}"
                try {
                    val pm: PackageManager = this.packageManager
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                } catch (e: PackageManager.NameNotFoundException) {
                    Toast.makeText(
                        this,
                        "Whatsapp app not installed in your phone",
                        Toast.LENGTH_SHORT
                    ).show()
                    e.printStackTrace()
                }
            }

            binding.btnTelegram.setOnClickListener {
                try {
                    val telegram =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/"+data[0].telegram))
                    telegram.setPackage("org.telegram.messenger")
                    startActivity(telegram)
                } catch (e: Exception) {
                    Toast.makeText(this, "Telegram app is not installed", Toast.LENGTH_LONG).show()
                }
            }

            binding.btnig.setOnClickListener {
                val uri: Uri = Uri.parse("http://instagram.com/_u/"+data[0].ig)
                val likeIng = Intent(Intent.ACTION_VIEW, uri)

                likeIng.setPackage("com.instagram.android")

                try {
                    startActivity(likeIng)
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/"+data[0].ig)
                        )
                    )
                }
            }

            binding.btntiktok.setOnClickListener {
                val uri: Uri = Uri.parse("https://www.tiktok.com/"+data[0].tiktok)
                val likeIng = Intent(Intent.ACTION_VIEW, uri)

                likeIng.setPackage("com.zhiliaoapp.musically")

                try {
                    startActivity(likeIng)
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.tiktok.com/@"+data[0].tiktok)
                        )
                    )
                }
            }

            binding.btnfacebook.setOnClickListener {
                val uri: Uri = Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/"+data[0].facebook)
                val likeIng = Intent(Intent.ACTION_VIEW, uri)

                likeIng.setPackage("com.facebook.katana")

                try {
                    startActivity(likeIng)
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/"+data[0].facebook)
                        )
                    )
                }
            }

            binding.btnlinkedn.setOnClickListener {
                val uri: Uri = Uri.parse("linkedin://"+data[0].linkedn)
                val likeIng = Intent(Intent.ACTION_VIEW, uri)

                likeIng.setPackage("com.linkedin.android")

                try {
                    startActivity(likeIng)
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://www.linkedin.com/profile/view?id="+data[0].linkedn)
                        )
                    )
                }
            }
            binding.btnemail.setOnClickListener {
                val testIntent = Intent(Intent.ACTION_VIEW)
                val data =
                    Uri.parse("mailto:?subject=" + "text subject" + "&body=" + "" + "&to=" + data[0].email)
                testIntent.data = data
                startActivity(testIntent)
            }
        })
    }

}