package com.app.eei.ui.admin.addform

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
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
    var id:Int=0
    private lateinit var viewmodel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add)
        binding = ActivityAdminAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(NewsViewModel::class.java)

        viewmodel.setNews()
        viewmodel.getNews().observe(this,{data->
            id=data.size+1
        })
        val upArrow =resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + "Postingan Baru" + "</font>")
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
            mEditor.insertLink("www.google.com", "title")
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
                        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                        val currentDate = sdf.format(Date())
                        val news = hashMapOf(
                            "imgNews" to urlPathPublic.toString(),
                            "idNews" to id,
                            "titleNews" to binding.edtTitleNews.getTextValue,
                            "dateNews" to currentDate,
                            "contentNews" to mEditor.html
                        )
                        db?.collection("news")?.document(id.toString())
                            ?.set(news)
                            ?.addOnSuccessListener {
                                Log.d(TAG, "DocumentSnapshot successfully written!")
                                startActivity(Intent(this,MainActivity::class.java))
                                finish()

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
                "Please Select Image or Add Image Name",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}