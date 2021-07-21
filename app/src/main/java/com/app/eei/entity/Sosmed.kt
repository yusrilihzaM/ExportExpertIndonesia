package com.app.eei.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sosmed (
        val wa:String,
        val ig:String,
        val tiktok:String,
        val facebook:String,
        val linkedn:String,
        val telegram:String,
        val email:String,
        ):Parcelable