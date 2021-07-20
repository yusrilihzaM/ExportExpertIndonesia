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
    val listNewsSearch = MutableLiveData<ArrayList<News>>()
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
    fun setSearch(title:String):String{
        var nilai=""
        if (setSearchNews(title)=="ada"){
            setSearchNews(title)
            Log.d("setSearchNews1", setSearchNews(title))
            nilai="news"
        }
        if(setSearchHastag(title)=="ada"){
            setSearchHastag(title)
            Log.d("setSearchHastag1",  setSearchHastag(title))
            nilai="hastag"
        }
        return nilai
    }
    fun setSearchNews(title:String): String {
            val listItems = ArrayList<News>()
        val db = FirebaseFirestore.getInstance()
        var nilai=""
        FirebaseFirestore.setLoggingEnabled(true)
        db.collection("news")
            .whereArrayContains("titleSplit",title)
            .get()
            .addOnCompleteListener { task ->
                Log.d("resultSize", task.result?.documents.toString()!!)
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d("dataSearch", "${document.id} => ${document.data}")
                        val idNews=document["idNews"].toString()
                        val titleNews=document["titleNews"].toString()
                        val imgNews=document["imgNews"].toString()
                        val dateNews=document["dateNews"].toString()
                        val contentNews=document["contentNews"].toString()
                        val type=document["type"].toString()
                        val titleSplit= arrayOf(document["titleSplit"])[0]
                        val hastag=arrayOf(document["hastag"])[0]
                        val tag=arrayOf(document["hastag"])[0]
                        val app= News(
                            idNews,
                            titleNews,
                            imgNews,
                            dateNews,
                            contentNews,
                            type,
                            titleSplit as ArrayList<String>,
                            hastag as ArrayList<String>

                        )
                        listItems.add(app)
                    }
                    Log.d("listItems1", listItems.size.toString())
                    Log.d("setSearchNews", listItems.toString())
                    listNewsSearch.postValue(listItems)
                    nilai = if (listItems.size==0){
                        "tidak"
                    }else{
                        "ada"
                    }
                    Log.d("log1", nilai.toString())
                } else {
                    Log.w(ContentValues.TAG, "news", task.exception)
                }
            }
        return nilai
    }
    fun setSearchHastag(title:String): String {
        val listItems = ArrayList<News>()
        val db = FirebaseFirestore.getInstance()
        var nilai=""
        FirebaseFirestore.setLoggingEnabled(true)
        db.collection("news")
            .whereArrayContains("hastag",title)
            .get()
            .addOnCompleteListener { task ->
                Log.d("setSearchHastag", "Suksess")
                if (task.isSuccessful) {
                    Log.d("setSearchHastag", task.result?.documents.toString())
                    for (document in task.result!!) {

                        val idNews=document["idNews"].toString()
                        val titleNews=document["titleNews"].toString()
                        val imgNews=document["imgNews"].toString()
                        val dateNews=document["dateNews"].toString()
                        val contentNews=document["contentNews"].toString()
                        val type=document["type"].toString()
                        val titleSplit= arrayOf(document["titleSplit"])[0]
                        val hastag=arrayOf(document["hastag"])[0]
                        val tag=arrayOf(document["hastag"])[0]
                        val app= News(
                            idNews,
                            titleNews,
                            imgNews,
                            dateNews,
                            contentNews,
                            type,
                            titleSplit as ArrayList<String>,
                            hastag as ArrayList<String>
                        )
                        listItems.add(app)
                    }

                    Log.d("listItems", listItems.size.toString())
                    listNewsSearch.postValue(listItems)
                    if (listItems.size!=0){
                        nilai="ada"
                    }else{
                        nilai="tidak"
                    }
                    Log.d("log", nilai.toString())
                } else {
                    Log.w(ContentValues.TAG, "news", task.exception)
                }
            }
        return nilai
    }
    fun setSearchTag(title:String): Boolean {
        val listItems = ArrayList<News>()
        val db = FirebaseFirestore.getInstance()
        FirebaseFirestore.setLoggingEnabled(true)
        var nilai=false
        db.collection("news")
            .whereArrayContains("tag",title)
            .get()
            .addOnCompleteListener { task ->
                Log.d("news", "Suksess")
                if (task.isSuccessful) {
                    Log.d("news", task.result?.documents.toString())
                    for (document in task.result!!) {

                        val idNews=document["idNews"].toString()
                        val titleNews=document["titleNews"].toString()
                        val imgNews=document["imgNews"].toString()
                        val dateNews=document["dateNews"].toString()
                        val contentNews=document["contentNews"].toString()
                        val type=document["type"].toString()
                        val titleSplit= arrayOf(document["titleSplit"])[0]
                        val hastag=arrayOf(document["hastag"])[0]
                        val tag=arrayOf(document["hastag"])[0]
                        val app= News(
                            idNews,
                            titleNews,
                            imgNews,
                            dateNews,
                            contentNews,
                            type,
                            titleSplit as ArrayList<String>,
                            hastag as ArrayList<String>
                        )
                        listItems.add(app)
                    }
                    Log.d("news", listItems.toString())
                    listNewsSearch.postValue(listItems)
                    Log.d("listItems", listItems.size.toString())
                    if (listItems.size!=0){
                        nilai=true
                    }else{
                        nilai=false
                    }
                    Log.d("log", nilai.toString())
                } else {
                    Log.w(ContentValues.TAG, "news", task.exception)
                }
            }
        return nilai
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

                        val idNews=document["idNews"].toString()
                        val titleNews=document["titleNews"].toString()
                        val imgNews=document["imgNews"].toString()
                        val dateNews=document["dateNews"].toString()
                        val contentNews=document["contentNews"].toString()
                        val type=document["type"].toString()
                        val titleSplit= arrayOf(document["titleSplit"])[0]
                        val hastag=arrayOf(document["hastag"])[0]
                        val tag=arrayOf(document["hastag"])[0]

                        Log.d("titleSplit", arrayOf(document["titleSplit"])[0].toString())
                        Log.d("hastag", hastag.toString())
                        Log.d("tag", tag.toString())
                        val app= News(
                            idNews,
                            titleNews,
                            imgNews,
                            dateNews,
                            contentNews,
                            type,
                            titleSplit as ArrayList<String>,
                            hastag as ArrayList<String>
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
    fun setNewsID(){
        val listItems = ArrayList<News>()
        val db = FirebaseFirestore.getInstance()
        FirebaseFirestore.setLoggingEnabled(true)
        db.collection("news")
            .orderBy("idNews",Query.Direction.ASCENDING)
            .get()
            .addOnCompleteListener { task ->
                Log.d("news", "Suksess")
                if (task.isSuccessful) {
                    Log.d("news", task.result?.documents.toString())
                    for (document in task.result!!) {

                        val idNews=document["idNews"].toString()
                        val titleNews=document["titleNews"].toString()
                        val imgNews=document["imgNews"].toString()
                        val dateNews=document["dateNews"].toString()
                        val contentNews=document["contentNews"].toString()
                        val type=document["type"].toString()
                        val titleSplit= arrayOf(document["titleSplit"])[0]
                        val hastag=arrayOf(document["hastag"])[0]
                        val tag=arrayOf(document["hastag"])[0]
                        val app= News(
                            idNews,
                            titleNews,
                            imgNews,
                            dateNews,
                            contentNews,
                            type,
                            titleSplit as ArrayList<String>,
                            hastag as ArrayList<String>
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
    fun getNewsSearch(): LiveData<ArrayList<News>> {
        return listNewsSearch
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
                            val idNews= task.result?.documents?.get(document)?.get("idNews").toString()
                            val titleNews=task.result?.documents?.get(document)?.get("titleNews").toString()
                            val imgNews=task.result?.documents?.get(document)?.get("imgNews").toString()
                            val dateNews=task.result?.documents?.get(document)?.get("dateNews").toString()
                            val contentNews=task.result?.documents?.get(document)?.get("contentNews").toString()
                            val type=task.result?.documents?.get(document)?.get("type").toString()
                            val titleSplit=arrayOf(task.result?.documents?.get(document)?.get("titleSplit"))[0]
                            val hastag=arrayOf(task.result?.documents?.get(document)?.get("hastag"))[0]

//                                val tag=arrayOf(task.result?.documents?.get(document)?.get("tag").toString().toList()

                        val app= News(
                            idNews,
                            titleNews,
                            imgNews,
                            dateNews,
                            contentNews,
                            type,
                            titleSplit as ArrayList<String>,
                            hastag as ArrayList<String>

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