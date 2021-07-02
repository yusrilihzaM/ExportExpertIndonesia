package com.app.eei.ui.admin.beranda.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.eei.entity.News
import com.google.firebase.firestore.FirebaseFirestore

class NewsViewModel:ViewModel() {
    val listNews = MutableLiveData<ArrayList<News>>()
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
}