package com.app.eei.ui.admin.settingapp.fragment.bgsplashapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.app.eei.R
import com.app.eei.databinding.ActivityBgSplashBinding
import com.app.eei.databinding.ActivityLogoAppBinding
import com.app.eei.ui.splashscreen.viewmodel.InfoViewModel
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import es.dmoral.toasty.Toasty
import java.io.IOException

class BgSplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBgSplashBinding
    var Image_Request_Code = 7
    var FilePathUri: Uri? = null
    var storageReference: StorageReference? = null
    var db: FirebaseFirestore? = null
    var progressDialog: ProgressDialog? = null
    var urlPathPublic: String? = null
    var id:Int=0
    private lateinit var viewModel: InfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bg_splash)
        binding = ActivityBgSplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val upArrow =resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + "Splash Screen Aplikasi" + "</font>")
        progressDialog = ProgressDialog(this)
        storageReference = FirebaseStorage.getInstance().getReference("Images")
        db = FirebaseFirestore.getInstance()
        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(InfoViewModel::class.java)
        viewModel.setInfoDetail()
        viewModel.getAppInfo().observe(this,{data->
            if (data.size==0){
                Toasty.error(this, "No Internet Access.", Toast.LENGTH_SHORT, true).show();
            }
            binding.imgNews.visibility= View.VISIBLE
            val imageLoader = ImageLoader.Builder(this)
                .componentRegistry {
                    add(SvgDecoder(applicationContext))
                }
                .build()
            val request = ImageRequest.Builder(this)
                .data(data[0].bgSplash)
                .crossfade(true)
                .target( binding.imgNews)
                .build()
            imageLoader.enqueue(request)
//            Glide.with(this)
//                .load(data[0].bgSplash)
//                .into(binding.imgNews)
        })
        binding.btnBrowse.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.data != null) {
            FilePathUri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, FilePathUri)
                binding.imgNews.setImageBitmap(bitmap)
                binding.imgNews.visibility= View.VISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun GetFileExtension(uri: Uri?): String? {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri!!))
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.submit->{
                Upload()
                true
            }
            16908332->{
                finish()
                true
            }
            else -> true
        }
    }
    fun Upload() {
        if (FilePathUri != null) {
            progressDialog!!.setTitle("Image is Uploading...")
            progressDialog!!.show()
            val storageReference2 = storageReference!!.child(
                System.currentTimeMillis().toString() + "." + GetFileExtension(FilePathUri)
            )
            val ref = storageReference!!.child(
                System.currentTimeMillis().toString() + "." + GetFileExtension(FilePathUri)
            )
            ref.putFile(FilePathUri!!).addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    val url = uri.toString()
                    urlPathPublic = url

                    if(url!=null){
                        db?.collection("informasi_aplikasi")?.document("1")
                            ?.update("bgSplash",urlPathPublic)
                            ?.addOnSuccessListener {
                                Log.d("edit", "berhasil")
//                                startActivity(Intent(this,MainActivity::class.java))
//                                finish()
                            }
                            ?.addOnFailureListener { e -> Log.w("edit", "Error writing document", e) }

                        Log.d("imagePathDownload", urlPathPublic.toString())
                    }

                }
            }
            storageReference2.putFile(FilePathUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    progressDialog!!.dismiss()
                    Toast.makeText(
                        applicationContext,
                        "Image Uploaded Successfully ",
                        Toast.LENGTH_LONG
                    )
                        .show()

                }
        } else {
            Log.d("edit", "fail")
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}