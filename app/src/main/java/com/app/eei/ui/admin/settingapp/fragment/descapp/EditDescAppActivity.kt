package com.app.eei.ui.admin.settingapp.fragment.descapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.app.eei.R
import com.app.eei.databinding.ActivityDescAppBinding
import com.app.eei.databinding.ActivityEditDescAppBinding
import com.app.eei.extensions.Extensions.toast
import com.app.eei.ui.admin.beranda.MainActivity
import com.app.eei.ui.admin.settingapp.fragment.namaapp.NameAppActivity
import com.app.eei.ui.splashscreen.viewmodel.InfoViewModel
import com.google.firebase.firestore.FirebaseFirestore
import es.dmoral.toasty.Toasty
import jp.wasabeef.richeditor.RichEditor

class EditDescAppActivity : AppCompatActivity() {
    private lateinit var mEditor: RichEditor
    private lateinit var viewModel: InfoViewModel
    private lateinit var binding: ActivityEditDescAppBinding
    var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_desc_app)
        binding = ActivityEditDescAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = FirebaseFirestore.getInstance()
        val upArrow =resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + "Edit deskripsi app" + "</font>")

        showFormContent()
        viewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(InfoViewModel::class.java)
        viewModel.setInfoDetail()
        viewModel.getAppInfo().observe(this,{data->
            if (data.size==0){
                Toasty.error(this, "No Internet Access.", Toast.LENGTH_SHORT, true).show();
            }
            binding.editor.html= data[0].deskripsiAplikasi
        })
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.submit->{
                Toast.makeText(this, "Deskripsi Disimpan", Toast.LENGTH_SHORT).show()
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

    private fun Upload() {
        db?.collection("informasi_aplikasi")?.document("1")
            ?.update("deskripsiAplikasi",binding.editor.html)
            ?.addOnSuccessListener {
                Log.d("edit", binding.editor.html)
                toast("Deskripsi aplikasi berhasil di simpan")
                startActivity(Intent(this, DescAppActivity::class.java))
                finish()
            }
            ?.addOnFailureListener { e -> Log.w("editNama", "Error writing document", e) }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, DescAppActivity::class.java))
    }
}