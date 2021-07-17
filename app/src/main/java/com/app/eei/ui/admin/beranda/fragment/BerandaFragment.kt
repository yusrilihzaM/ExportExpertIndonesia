package com.app.eei.ui.admin.beranda.fragment

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eei.R
import com.app.eei.adapter.BerandaLimitListAdapter
import com.app.eei.adapter.MenuListAdapter
import com.app.eei.databinding.FragmentBerandaBinding
import com.app.eei.entity.Menu
import com.app.eei.entity.News
import com.app.eei.ui.admin.addform.AdminAddActivity
import com.app.eei.ui.admin.menu.berita.AdminNewsActivity
import com.app.eei.ui.admin.beranda.viewmodel.NewsViewModel
import com.app.eei.ui.admin.detail.AdminDetailActivity
import com.app.eei.ui.admin.detail.AdminDetailActivity.Companion.EXTRA_DATA
import com.app.eei.ui.admin.menu.event.AdminEventActivity
import com.app.eei.ui.admin.menu.komunitas.AdminKomunitasActivity
import com.app.eei.ui.admin.menu.mitra.AdminMitraActivity
import com.app.eei.ui.admin.menu.podcast.AdminPodcastActivity
import com.app.eei.ui.admin.menu.tips.AdminTipsActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.Shimmer


class BerandaFragment : Fragment() {

    private lateinit var binding: FragmentBerandaBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewmodel: NewsViewModel
    private lateinit var shimmer: Shimmer
    private var username: String? = null
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var berandaListAdapter: BerandaLimitListAdapter
    private lateinit var dataTitle: Array<String>
    private lateinit var dataIc: TypedArray
    private lateinit var menuAdapter: MenuListAdapter
    private var list: ArrayList<Menu> = arrayListOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beranda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentBerandaBinding.bind(view)
        viewmodel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(NewsViewModel::class.java)
        swipeContainer=binding.swipeContainer

        showData()
        showMenu()
//        binding.btnSearch.setOnClickListener {
//
//            val dataSearch=binding.edtSearch.text.toString()
//            Toast.makeText(context, dataSearch, Toast.LENGTH_SHORT).show()
//            showSearch(dataSearch)
//        }
//        binding.edtSearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
//            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
//                val dataSearch=binding.edtSearch.text.toString()
//                Toast.makeText(context, dataSearch, Toast.LENGTH_SHORT).show()
//                showSearch(dataSearch)
//                return@OnKeyListener true
//            }
//            false
//        })
        Log.d("activty","Beranda")
        swipeContainer.setOnRefreshListener {
            swipeContainer.isRefreshing = true
            showData()
            berandaListAdapter.notifyDataSetChanged()
        }
//        binding.btnAdd.setOnClickListener {
//            startActivity(Intent(context,AdminAddActivity::class.java))
//            activity?.finish()
//        }
        binding.btnLihata.setOnClickListener {
            startActivity(Intent(context, AdminNewsActivity::class.java))
            activity?.finish()
        }

    }
    private fun showSearch(title:String){
        showShimmer(true)
        recyclerView=binding.rvNews
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager= LinearLayoutManager(context)
        viewmodel.setSearchNews(title)
        viewmodel.getNews().observe(viewLifecycleOwner,{data->
            showShimmer(false)
            berandaListAdapter=BerandaLimitListAdapter(data)
            berandaListAdapter.notifyDataSetChanged()
            recyclerView.adapter=berandaListAdapter
            swipeContainer.isRefreshing = false
            berandaListAdapter.setOnItemClickCallback(object :BerandaLimitListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: News) {
                    Toast.makeText(context, data.title, Toast.LENGTH_SHORT).show()
                    val intent= Intent(context, AdminDetailActivity::class.java)
                    intent.putExtra(EXTRA_DATA,data)
                    startActivity(intent)
                    activity?.finish()
                }
            })
        })
    }
    private fun showData(){
        showShimmer(true)
        recyclerView=binding.rvNews
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager= LinearLayoutManager(context)

        viewmodel.setNews()
        viewmodel.getNews().observe(viewLifecycleOwner,{data->
            showShimmer(false)
            berandaListAdapter=BerandaLimitListAdapter(data)
            berandaListAdapter.notifyDataSetChanged()
            recyclerView.adapter=berandaListAdapter

            swipeContainer.isRefreshing = false
            if (data.size==0){
                binding.noData.visibility=View.VISIBLE
                binding.tvnodata.visibility=View.VISIBLE
                Glide.with(this)
                    .asGif()
                    .load(R.drawable.nodatagif)
                    .addListener(object : RequestListener<GifDrawable?> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<GifDrawable?>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }
                        override fun onResourceReady(
                            resource: GifDrawable?,
                            model: Any?,
                            target: Target<GifDrawable?>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            resource?.setLoopCount(1)
                            return false
                        }

                    })
                    .into(binding.noData)

            }else{
                binding.noData.visibility=View.GONE
                binding.tvnodata.visibility=View.GONE
            }
            berandaListAdapter.setOnItemClickCallback(object :BerandaLimitListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: News) {
                    Toast.makeText(context, data.title, Toast.LENGTH_SHORT).show()
                    val intent= Intent(context, AdminDetailActivity::class.java)
                    intent.putExtra(EXTRA_DATA,data)
                    startActivity(intent)
                }
            })

        })
    }
    private  fun showMenu(){

        binding.rvMenu.setHasFixedSize(true)
        binding.rvMenu.layoutManager= GridLayoutManager(context,3)
        list.addAll(getListMenu())
        menuAdapter = MenuListAdapter(list)
        binding.rvMenu.adapter=menuAdapter

       menuAdapter.setOnItemClickCallback(object :MenuListAdapter.OnItemClickCallback{
           override fun onItemClicked(data: Menu) {
               val intent: Intent
               when(data.title){
                   getString(R.string.berita)->{
                       intent= Intent(context, AdminNewsActivity::class.java)
                       startActivity(intent)
                       activity?.finish()
                   }
                   getString(R.string.event)->{
                       intent= Intent(context, AdminEventActivity::class.java)
                       startActivity(intent)
                       activity?.finish()
                   }
                   getString(R.string.tipsdantricks)->{
                       intent= Intent(context, AdminTipsActivity::class.java)
                       startActivity(intent)
                       activity?.finish()
                   }
                   getString(R.string.mitra)->{
                       intent= Intent(context, AdminMitraActivity::class.java)
                       startActivity(intent)
                       activity?.finish()
                   }
                   getString(R.string.podcast)->{
                       intent= Intent(context, AdminPodcastActivity::class.java)
                       startActivity(intent)
                       activity?.finish()
                   }
                   getString(R.string.komunitas)->{
                       intent= Intent(context, AdminKomunitasActivity::class.java)
                       startActivity(intent)
                       activity?.finish()
                   }

               }
           }
       })
    }
    private fun showShimmer(boolean: Boolean){
        if(boolean){
            binding.shimmer.startShimmer()
            binding.shimmer.showShimmer(true)
        }
        else{
            binding.shimmer.stopShimmer()
            binding.shimmer.showShimmer(false)
            binding.shimmer.visibility=View.GONE
        }
    }

    private fun getListMenu(): ArrayList<Menu> {
        val listMenu= ArrayList<Menu>()
        dataTitle = resources.getStringArray(R.array.data_title_menu)
        dataIc = resources.obtainTypedArray(R.array.data_ic_menu)
        for(position in dataTitle.indices){
            val menu= Menu(
                dataTitle[position],
                dataIc.getResourceId(position, -1)
            )
            listMenu.add(menu)
        }
        return listMenu
    }
}