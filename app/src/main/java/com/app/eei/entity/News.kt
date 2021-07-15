package com.app.eei.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
data class News (
        val id:String,
        val title:String,
        val imgNews:String,
        val dateNews:String,
        val contentNews:String
        ):Parcelable