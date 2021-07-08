package com.app.eei.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoApp(
    var namaAplikasi:String,
    var deskripsiAplikasi:String,
    var icLogo:String,
    var bgSplash:String
):Parcelable
