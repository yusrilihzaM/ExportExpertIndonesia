package com.app.eei.ui.admin.addform

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.eei.R
import com.app.eei.databinding.ActivityAdminAddBinding
import jp.wasabeef.richeditor.RichEditor

class AdminAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminAddBinding
    private lateinit var mEditor:RichEditor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add)
        binding = ActivityAdminAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val upArrow =resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + "Postingan Baru" + "</font>")

        showFormContent()
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
                true
            }
            16908332->{
                this.finish()
                true
            }
            else -> true
        }
    }
}