package com.app.eei.ui.admin.beranda.viewmodel

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.eei.entity.News
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class NewsViewModel:ViewModel() {
    val listNews = MutableLiveData<ArrayList<News>>()
    val listNewsByType = MutableLiveData<ArrayList<News>>()
    fun delNews(id:String):Boolean{
        var boolean=false
        val db = FirebaseFirestore.getInstance()
        db.collection("news").document(id)
            .delete()
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
                 boolean=true
            }
            .addOnFailureListener {
                    e -> Log.w(TAG, "Error deleting document", e)
                boolean=false
            }
        return boolean
    }
    fun setSearchNews(title:String){
        val listItems = ArrayList<News>()
        val db = FirebaseFirestore.getInstance()
        FirebaseFirestore.setLoggingEnabled(true)
        db.collection("news")
            .whereEqualTo("titleNews", title)
            .get()
            .addOnCompleteListener { task ->
                Log.d("news", "Suksess")
                if (task.isSuccessful) {
                    Log.d("news", task.result?.documents.toString())
                    for (document in task.result!!) {
                        
                        val idNews=document["idNews"].toString().toInt()
                        val titleNews=document["titleNews"].toString()
                        val imgNews=document["imgNews"].toString()
                        val dateNews=document["dateNews"].toString()
                        val contentNews=document["contentNews"].toString()

                        val app= News(
                            idNews,
                            titleNews,
                            imgNews,
                            dateNews,
                            contentNews
                        )
                        listItems.add(app)
                    }
                    Log.d("news", listItems.toString())
                    listNews.postValue(listItems)
                } else {
                    Log.w(ContentValues.TAG, "news", task.exception)
                }
            }
    }
    fun setNews(){
        val listItems = ArrayList<News>()
        val db = FirebaseFirestore.getInstance()
        FirebaseFirestore.setLoggingEnabled(true)
        db.collection("news")
            .orderBy("idNews",Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                Log.d("news", "Suksess")
                if (task.isSuccessful) {
                    Log.d("news", task.result?.documents.toString())
                    for (document in task.result!!) {

                        val idNews=document["idNews"].toString().toInt()
                        val titleNews=document["titleNews"].toString()
                        val imgNews=document["imgNews"].toString()
                        val dateNews=document["dateNews"].toString()
                        val contentNews=document["contentNews"].toString()

                        val app= News(
                            idNews,
                            titleNews,
                            imgNews,
                            dateNews,
                            contentNews
                        )
                        listItems.add(app)
                    }
                    Log.d("news", listItems.toString())
                    listNews.postValue(listItems)
                } else {
                    Log.w(ContentValues.TAG, "news", task.exception)
                }
            }
    }
    //3 siap ditampilkan
    fun getNews(): LiveData<ArrayList<News>> {
        return listNews
    }

    fun setNewsByType(type:String){
        val listItems = ArrayList<News>()
        val db = FirebaseFirestore.getInstance()
        FirebaseFirestore.setLoggingEnabled(true)
        db.collection("news")
            .whereEqualTo("type",type)
            .get()
            .addOnCompleteListener { task ->
                Log.d("setNewsByType", "Suksess")
                if (task.isSuccessful) {
                    Log.d("setNewsByType", task.result?.documents.toString())
                    var s:Int= task.result?.documents?.size?.toString()!!.toInt()-1
                    Log.d("sss", s.toString())
                    if (s != null) {
                        for (document in s downTo 0) {
                            Log.d("aaaa", document.toString())
                            val idNews= task.result?.documents?.get(document)?.get("idNews").toString().toInt()
                            val titleNews=task.result?.documents?.get(document)?.get("titleNews").toString()
                            val imgNews=task.result?.documents?.get(document)?.get("imgNews").toString()
                            val dateNews=task.result?.documents?.get(document)?.get("dateNews").toString()
                            val contentNews=task.result?.documents?.get(document)?.get("contentNews").toString()

                        val app= News(
                            idNews,
                            titleNews,
                            imgNews,
                            dateNews,
                            contentNews
                        )
                        listItems.add(app)
                        }
                    }
                    Log.d("setNewsByType", listItems.toString())
                    listNewsByType.postValue(listItems)
                } else {
                    Log.w(ContentValues.TAG, "setNewsByType", task.exception)
                }
            }
    }
    fun getNewsByType(): LiveData<ArrayList<News>> {
        return listNewsByType
    }
}