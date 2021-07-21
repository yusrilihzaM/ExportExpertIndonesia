package com.app.eei.ui.tentang

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.eei.entity.InfoApp
import com.app.eei.entity.Sosmed
import com.google.firebase.firestore.FirebaseFirestore

class SosmedViewModel: ViewModel() {
    val listSosmed = MutableLiveData<ArrayList<Sosmed>>()
    fun setSosmed(){
        val listItems = ArrayList<Sosmed>()
        val db = FirebaseFirestore.getInstance()

        db.collection("sosmed")
            .get()
            .addOnCompleteListener { task ->
                Log.d("setSosmed", "Suksess")
                Log.d("setSosmed", task.result.toString())
                if (task.isSuccessful) {
                    for (document in task.result!!) {

                        val wa=document["wa"].toString()
                        val ig=document["ig"].toString()
                        val tiktok=document["tiktok"].toString()
                        val facebook=document["facebook"].toString()
                        val linkedn=document["linkedn"].toString()
                        val telegram=document["telegram"].toString()
                        val email=document["email"].toString()
                        val app= Sosmed(
                            wa,
                            ig,
                            tiktok,
                            facebook,
                            linkedn,
                            telegram,
                            email
                        )
                        listItems.add(app)
                    }
                    Log.d("setSosmed", listItems.toString())
                    listSosmed.postValue(listItems)
                } else {
                    Log.w(ContentValues.TAG, "setSosmed", task.exception)
                }
            }

    }
    fun getSosmed(): LiveData<ArrayList<Sosmed>> {
        return listSosmed
    }
}