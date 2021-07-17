package com.app.eei.ui.admin.addform

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build.ID
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.eei.R
import com.app.eei.databinding.ActivityAdminAddBinding
import com.app.eei.ui.admin.beranda.MainActivity
import com.app.eei.ui.admin.beranda.viewmodel.NewsViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.thecode.aestheticdialogs.*
import jp.wasabeef.richeditor.RichEditor
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AdminAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminAddBinding
    private lateinit var mEditor:RichEditor
    var Image_Request_Code = 7
    var FilePathUri: Uri? = null
    var storageReference: StorageReference? = null
    var databaseReference: DatabaseReference? = null
    var db: FirebaseFirestore? = null
    var progressDialog: ProgressDialog? = null
    var urlPathPublic: String? = null
    var id:Int=1
    var type:String=""
    var dokument:String=""
    private lateinit var viewmodel: NewsViewModel
    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add)
        binding = ActivityAdminAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(NewsViewModel::class.java)

        viewmodel.setNews()
        viewmodel.getNews().observe(this,{data->
//            id=data.size-1
            val idTerakhir:Int=data.size.toInt()
            Log.d("idTerakhir",idTerakhir.toString())
            if(data.size==0){
                dokument="a 1"
            } else {
                val dataSplit=data[idTerakhir-1].id.split(" ").toTypedArray().toList()
                Log.d("dataSplit",dataSplit.toString())
                id=idTerakhir+1
                Log.d("dataSplit1",id.toString())
                dokument= "a $id"
                Log.d("dataSplit2",dokument.toString())
            }

            Log.d("dokument",dokument.toString())
        })
        val upArrow =resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + "Postingan Baru" + "</font>")

        val typeForm=intent.getStringExtra("type")
        type=typeForm.toString()
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

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.data != null) {
            FilePathUri = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, FilePathUri)
                binding.imgNews.setImageBitmap(bitmap)
                binding.imgNews.visibility=View.VISIBLE
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
                if (binding.edtTitleNews.getTextValue=="" && mEditor.html==null && FilePathUri==null){
                    binding.status.visibility=View.VISIBLE
                    binding.status.text="Semua Form Harus Terisi"
                }
                else if (binding.edtTitleNews.getTextValue==""){
                    binding.status.visibility=View.VISIBLE
                    binding.status.text="Judul Postingan Harus Terisi"
                    binding.edtTitleNews.setIsErrorEnable(true)
                }
                else if (mEditor.html==null){
                    binding.status.visibility=View.VISIBLE
                    binding.status.text="Isi Konten Postingan Harus Terisi"
                }
                else if (FilePathUri==null){
                    binding.status.visibility=View.VISIBLE
                    binding.status.text="Silakan Pilih Gambar atau Tambahkan Nama Gambar"
                }
                else{
                    Upload()
                }

                true
            }
            16908332->{
                startActivity(Intent(this,MainActivity::class.java))
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
                    binding.urlpath.text=urlPathPublic

                    if(url!=null){
                        val sdf = SimpleDateFormat("E. dd MMMM, yyyy hh:mm", Locale.US)
                        val currentDate = sdf.format(Date())
                        val titleSplit = binding.edtTitleNews.getTextValue.split(" ").toTypedArray().toList()
                        val news = hashMapOf(
                            "imgNews" to urlPathPublic.toString(),
                            "idNews" to dokument,
                            "titleNews" to binding.edtTitleNews.getTextValue,
                            "dateNews" to currentDate,
                            "contentNews" to mEditor.html,
                            "titleSplit" to titleSplit,
                            "type" to type
                        )
                        db?.collection("news")?.document(dokument)
                            ?.set(news)
                            ?.addOnSuccessListener {
                                Log.d(TAG, "DocumentSnapshot successfully written!")
                                AestheticDialog.Builder(this, DialogStyle.FLAT, DialogType.SUCCESS)
                                    .setTitle(type)
                                    .setMessage("Berhasil Di Simpan")
                                    .setCancelable(false)
                                    .setDarkMode(false)
                                    .setGravity(Gravity.CENTER)
                                    .setAnimation(DialogAnimation.SHRINK)
                                    .setOnClickListener(object : OnDialogClickListener {
                                        override fun onClick(dialog: AestheticDialog.Builder) {
                                            dialog.dismiss()
                                            kembali()
                                            finish()
                                            //actions...
                                        }
                                    })
                                    .show()
                            }
                            ?.addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
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
            Toast.makeText(
                this,
                "Silakan Pilih Gambar atau Tambahkan Nama Gambar",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
    private fun kembali(){
        startActivity(Intent(this@AdminAddActivity, MainActivity::class.java))
    }
}