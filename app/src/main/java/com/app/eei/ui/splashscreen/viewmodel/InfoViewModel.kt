package com.app.eei.ui.splashscreen.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.eei.entity.InfoApp
import com.google.firebase.firestore.FirebaseFirestore

class InfoViewModel:ViewModel() {
    val listInfoDetail = MutableLiveData<ArrayList<InfoApp>>()
    fun setInfoDetail(){
        val listItems = ArrayList<InfoApp>()
        val db = FirebaseFirestore.getInstance()

        db.collection("informasi_aplikasi")
            .get()
            .addOnCompleteListener { task ->
                Log.d("informasi_aplikasi", "Suksess")
                Log.d("informasi_aplikasi", task.result.toString())
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val icLogo=document["icLogo"].toString()
                        val namaAplikasi=document["namaAplikasi"].toString()
                        val deskripsiAplikasi=document["deskripsiAplikasi"].toString()
                        val bgSplash=document["bgSplash"].toString()

                        val app= InfoApp(
                            namaAplikasi,
                            deskripsiAplikasi,
                            icLogo,
                            bgSplash
                        )
                        listItems.add(app)
                    }
                    Log.d("informasi_aplikasi", listItems.toString())
                    listInfoDetail.postValue(listItems)
                } else {
                    Log.w(ContentValues.TAG, "informasi_aplikasi", task.exception)
                }
            }

    }
    fun getAppInfo(): LiveData<ArrayList<InfoApp>> {
        return listInfoDetail
    }
}