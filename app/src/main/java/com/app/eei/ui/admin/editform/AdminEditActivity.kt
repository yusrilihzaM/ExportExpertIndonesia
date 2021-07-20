package com.app.eei.ui.admin.editform

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import com.app.eei.R
import com.app.eei.databinding.ActivityAdminAddBinding
import com.app.eei.databinding.ActivityAdminEditBinding
import com.app.eei.databinding.ActivityAdminPodcastBinding
import com.app.eei.databinding.ActivityAdminTipsBinding
import com.app.eei.entity.News
import com.app.eei.extensions.Extensions.toast
import com.app.eei.ui.admin.beranda.MainActivity
import com.app.eei.ui.admin.beranda.viewmodel.NewsViewModel
import com.app.eei.ui.admin.detail.AdminDetailActivity
import com.app.eei.ui.admin.menu.berita.AdminNewsActivity
import com.app.eei.ui.admin.menu.event.AdminEventActivity
import com.app.eei.ui.admin.menu.komunitas.AdminKomunitasActivity
import com.app.eei.ui.admin.menu.mitra.AdminMitraActivity
import com.app.eei.ui.admin.menu.podcast.AdminPodcastActivity
import com.app.eei.ui.admin.menu.tips.AdminTipsActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.thecode.aestheticdialogs.*
import jp.wasabeef.richeditor.RichEditor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AdminEditActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityAdminEditBinding
    private lateinit var mEditor: RichEditor
    private lateinit var spinner: Spinner
    var Image_Request_Code = 7
    var FilePathUri: Uri? = null
    var storageReference: StorageReference? = null
    var db: FirebaseFirestore? = null
    var progressDialog: ProgressDialog? = null
    var urlPathPublic: String? = null
    var id:Int=0
    var dokument:String=""
    var news =hashMapOf<String, Any?>()
    var status:String=""
    var type:String=""
    private lateinit var viewmodel: NewsViewModel
    companion object {
        const val EXTRA_DATA_EDIT = "extra_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_edit)

        binding = ActivityAdminEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(NewsViewModel::class.java)
        val data=intent.getParcelableExtra<News>(EXTRA_DATA_EDIT) as News
        val list=data.hastag
        val separator = " "
        val hastag = list.joinToString(separator)
        binding.edtHastag.setText(hastag)
        dokument=data.id.toString()
        Log.d("activty","AdminEditActivity")
        binding.edtTitleNews.setTextValue(data.title)
        Glide.with(this)
            .load(data.imgNews)
            .into(binding.imgNews)
        binding.imgNews.visibility=View.VISIBLE
        binding.editor.html=data.contentNews
        val upArrow =resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + "Edit postingan" + "</font>")
        progressDialog = ProgressDialog(this)
        showFormContent()
        storageReference = FirebaseStorage.getInstance().getReference("Images")
        db = FirebaseFirestore.getInstance()
        binding.btnBrowse.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code)
        }
        spinnerType(data.type)
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
    private fun showFormContent() {
        mEditor=binding.editor
        mEditor = findViewById<View>(R.id.editor) as RichEditor
        mEditor.setEditorHeight(200)
        mEditor.setEditorFontSize(22)
        mEditor.setEditorFontColor(Color.BLACK)
        mEditor.setEditorBackgroundColor(getColor(R.color.purple_500))
        mEditor.setBackgroundColor(getColor(R.color.purple_500))

        mEditor.setPadding(10, 10, 10, 10)
        mEditor.setPlaceholder("Konten")
        mEditor.isInEditMode
        mEditor.setInputEnabled(true)
//        mEditor.html="<h1>This is a Heading</h1>"
        binding.actionUndo.setOnClickListener {
            mEditor.undo()
        }
        binding.actionRedo.setOnClickListener {
            mEditor.redo()
        }
        binding.actionBold.setOnClickListener {
            mEditor.setBold()
        }
        binding.actionItalic.setOnClickListener {
            mEditor.setItalic()
        }
        binding.actionSubscript.setOnClickListener {
            mEditor.setSubscript()
        }
        binding.actionSuperscript.setOnClickListener {
            mEditor.setSuperscript()
        }
        binding.actionStrikethrough.setOnClickListener {
            mEditor.setStrikeThrough()
        }
        binding.actionUnderline.setOnClickListener {
            mEditor.setUnderline()
        }
        binding.actionHeading1.setOnClickListener {
            mEditor.setHeading(1)
        }
        binding.actionHeading2.setOnClickListener {
            mEditor.setHeading(2)
        }
        binding.actionHeading3.setOnClickListener {
            mEditor.setHeading(3)
        }
        binding.actionHeading4.setOnClickListener {
            mEditor.setHeading(4)
        }
        binding.actionHeading5.setOnClickListener {
            mEditor.setHeading(5)
        }
        binding.actionHeading6.setOnClickListener {
            mEditor.setHeading(6)
        }


        binding.actionIndent.setOnClickListener {
            mEditor.setIndent()
        }
        binding.actionOutdent.setOnClickListener {
            mEditor.setOutdent()
        }
        binding.actionAlignLeft.setOnClickListener {
            mEditor.setAlignLeft()
        }
        binding.actionAlignCenter.setOnClickListener {
            mEditor.setAlignCenter()
        }
        binding.actionAlignRight.setOnClickListener {
            mEditor.setAlignRight()
        }
        binding.actionBlockquote.setOnClickListener {
            mEditor.setBlockquote()
        }
        binding.actionInsertBullets.setOnClickListener {
            mEditor.setBullets()
        }
        binding.actionInsertNumbers.setOnClickListener {
            mEditor.setNumbers()
        }
        binding.actionInsertLink.setOnClickListener {


            val mBuilder = AlertDialog.Builder(this)
            val customLayout=layoutInflater.inflate(R.layout.dialog_link,null)
            mBuilder.setView(customLayout)
            mBuilder.setTitle("Tambahkan HyperLink")

            mBuilder
                .setCancelable(false)
                .setIcon(R.drawable.hyperlink)
                .setPositiveButton("Simpan") { _, _ ->
                    val judul=customLayout.findViewById<EditText>(R.id.titleLink)
                    val url=customLayout.findViewById<EditText>(R.id.urlLink)

                    if (judul.text.isEmpty() && url.text.isEmpty()){
                        judul.error="Judul Hyperlink Harus Terisi"
                        url.error="Judul Hyperlink Harus Terisi"
                    }
                    else if(judul.text.isEmpty()){
                        judul.error="Judul Hyperlink Harus Terisi"
                    }
                    else if(url.text.isEmpty()){
                        url.error="Judul Hyperlink Harus Terisi"
                    }else{
                        mEditor.insertLink( url.text.toString().trim(), judul.text.toString().trim())
                    }
                }
                .setNegativeButton("Batal") { dialog, _ -> dialog.cancel() }
            val alertDialog = mBuilder.create()
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.black))
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.bg_del))

        }

        findViewById<View>(R.id.action_insert_youtube).setOnClickListener {

            val mBuilder = AlertDialog.Builder(this)
            val customLayout=layoutInflater.inflate(R.layout.dialog_youtube,null)
            mBuilder.setView(customLayout)
            mBuilder.setTitle("Tambahkan Video Youtube")

            mBuilder
                .setCancelable(false)
                .setIcon(R.drawable.ic_utube)
                .setPositiveButton("Simpan") { _, _ ->

                    val url=customLayout.findViewById<EditText>(R.id.urlUtube)

                    if(url.text.isEmpty()){
                        url.error="Url Youtube Harus Terisi"
                    }else{
                        mEditor.insertYoutubeVideo(
                            url.text.toString().trim()
                        )
                    }
                }
                .setNegativeButton("Batal") { dialog, _ -> dialog.cancel() }
            val alertDialog = mBuilder.create()
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.black))
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getColor(R.color.bg_del))

        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.submit->{
                Toast.makeText(this, "Postingan Disimpan", Toast.LENGTH_SHORT).show()
                Upload()
                true
            }
            16908332->{
                pindah()
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
                        val sdf = SimpleDateFormat("E. dd MMMM, yyyy hh:mm", Locale.US)
                        val currentDate = sdf.format(Date())
                        val titleSplit = binding.edtTitleNews.getTextValue.split(" ").toTypedArray().toList()
                        val hastag=binding.edtHastag.text.split(" ").toTypedArray().toList()
                        news = hashMapOf(
                            "imgNews" to urlPathPublic.toString(),
                            "idNews" to dokument,
                            "titleNews" to binding.edtTitleNews.getTextValue,
//                            "dateNews" to currentDate,
                            "contentNews" to mEditor.html,
                            "titleSplit" to titleSplit,
                            "type" to type,
                            "hastag" to hastag
                        )
                        db?.collection("news")?.document(dokument)
                            ?.update(news)
                            ?.addOnSuccessListener {
                                Log.d("edit", news.toString())
                                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                                    .setTitle(type)
                                    .setMessage("Perubahan Berhasil Di Simpan")
                                    .setCancelable(false)
                                    .setDarkMode(false)
                                    .setGravity(Gravity.CENTER)
                                    .setAnimation(DialogAnimation.SHRINK)
                                    .setOnClickListener(object : OnDialogClickListener {
                                        override fun onClick(dialog: AestheticDialog.Builder) {
                                            dialog.dismiss()
                                            pindah()
                                            finish()
                                            //actions...
                                        }
                                    })
                                    .show()

                            }
                            ?.addOnFailureListener { e -> Log.w("edit", "Error writing document", e) }
                        status="gambar berhasil"
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
            val sdf = SimpleDateFormat("E. dd MMMM, yyyy hh:mm", Locale.US)
            val currentDate = sdf.format(Date())
            val titleSplit = binding.edtTitleNews.getTextValue.split(" ").toTypedArray().toList()
            val hastag=binding.edtHastag.text.split(" ").toTypedArray().toList()
            news = hashMapOf(
                "titleNews" to binding.edtTitleNews.getTextValue,
//                "dateNews" to currentDate,
                "contentNews" to mEditor.html,
                "titleSplit" to titleSplit,
                "type" to type,
                "hastag" to hastag
            )
            db?.collection("news")?.document(dokument)
                ?.update(news)
                ?.addOnSuccessListener {
                    AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                        .setTitle(type)
                        .setMessage("Perubahan Berhasil Di Simpan")
                        .setCancelable(false)
                        .setDarkMode(false)
                        .setGravity(Gravity.CENTER)
                        .setAnimation(DialogAnimation.SHRINK)
                        .setOnClickListener(object : OnDialogClickListener {
                            override fun onClick(dialog: AestheticDialog.Builder) {
                                dialog.dismiss()
                                pindah()
                                finish()
                                //actions...
                            }
                        })
                        .show()
                }
                ?.addOnFailureListener { e -> Log.w("edit", "Error writing document", e) }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
    private fun spinnerType(ty:String){
        spinner=binding.type


        ArrayAdapter.createFromResource(
            this,
            R.array.type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            when(ty){
                getString(R.string.podcast)->{
                    spinner.setSelection(adapter.getPosition(getString(R.string.podcast)))
                }
                getString(R.string.tipsdantricks)->{
                    spinner.setSelection(adapter.getPosition(getString(R.string.tipsdantricks)))
                }
                getString(R.string.berita)->{
                    spinner.setSelection(adapter.getPosition(getString(R.string.berita)))
                }
                getString(R.string.event)->{
                    spinner.setSelection(adapter.getPosition(getString(R.string.event)))
                }
                getString(R.string.mitra)->{
                    spinner.setSelection(adapter.getPosition(getString(R.string.mitra)))
                }
                getString(R.string.komunitas)->{
                    spinner.setSelection(adapter.getPosition(getString(R.string.komunitas)))
                }
            }
        }
        spinner.onItemSelectedListener = this

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        type=spinner.selectedItem.toString()

        toast(type)
//        when(position){
//            0->{
//                type=getString(R.string.podcast)
//            }
//            1->{
//                type=getString(R.string.tipsdantricks)
//            }
//            2->{
//                type=getString(R.string.berita)
//            }
//            3->{
//                type=getString(R.string.event)
//            }
//            4->{
//                type=getString(R.string.mitra)
//            }
//            5->{
//                type=getString(R.string.komunitas)
//            }
//        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun pindah(){
        when(type){
            getString(R.string.podcast)->{
                startActivity(Intent(this,AdminPodcastActivity::class.java))
                finish()
            }
            getString(R.string.tipsdantricks)->{
                startActivity(Intent(this,AdminTipsActivity::class.java))
                finish()
            }
            getString(R.string.berita)->{
                startActivity(Intent(this,AdminNewsActivity::class.java))
                finish()
            }
            getString(R.string.event)->{
                startActivity(Intent(this,AdminEventActivity::class.java))
                finish()
            }
            getString(R.string.mitra)->{
                startActivity(Intent(this,AdminMitraActivity::class.java))
                finish()
            }
            getString(R.string.komunitas)->{
                startActivity(Intent(this,AdminKomunitasActivity::class.java))
                finish()
            }
        }
    }
}