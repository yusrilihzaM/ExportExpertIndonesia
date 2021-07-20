package com.app.eei.ui.guest.beranda

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eei.R
import com.app.eei.adapter.BerandaLimitListAdapter
import com.app.eei.adapter.BerandaListAdapter
import com.app.eei.adapter.MenuListAdapter
import com.app.eei.databinding.FragmentGuestBerandaBinding
import com.app.eei.entity.Menu
import com.app.eei.entity.News
import com.app.eei.ui.admin.beranda.viewmodel.NewsViewModel
import com.app.eei.ui.admin.detail.AdminDetailActivity
import com.app.eei.ui.admin.menu.berita.AdminNewsActivity
import com.app.eei.ui.admin.menu.event.AdminEventActivity
import com.app.eei.ui.admin.menu.komunitas.AdminKomunitasActivity
import com.app.eei.ui.admin.menu.mitra.AdminMitraActivity
import com.app.eei.ui.admin.menu.podcast.AdminPodcastActivity
import com.app.eei.ui.admin.menu.tips.AdminTipsActivity
import com.app.eei.ui.guest.detail.GuestDetailActivity
import com.app.eei.ui.guest.menu.berita.GuestNewsActivity
import com.app.eei.ui.guest.menu.event.GuestEventActivity
import com.app.eei.ui.guest.menu.komunitas.GuestKomunitasActivity
import com.app.eei.ui.guest.menu.mitra.GuestMitraActivity
import com.app.eei.ui.guest.menu.podcast.GuestPodcastActivity
import com.app.eei.ui.guest.menu.tips.GuestTipsActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class GuestBerandaFragment : Fragment() {
    private lateinit var binding: FragmentGuestBerandaBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewmodel: NewsViewModel
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var berandaListAdapter: BerandaLimitListAdapter
    private lateinit var mberandaListAdapter: BerandaListAdapter
    private lateinit var dataTitle: Array<String>
    private lateinit var dataIc: TypedArray
    private var list: ArrayList<Menu> = arrayListOf()
    private lateinit var menuAdapter: MenuListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest_beranda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentGuestBerandaBinding.bind(view)
        viewmodel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(NewsViewModel::class.java)
        swipeContainer=binding.swipeContainer

        showData()
        showMenu()
        binding.btnClose.setOnClickListener {
            binding.edtSearch.text.clear()
            mberandaListAdapter.clear()
            showView(true)
        }
        binding.btnSearch.setOnClickListener {
            binding.edtSearch.hideKeyboard()
            binding.btnClose.visibility=View.VISIBLE
            val dataSearch=binding.edtSearch.text.toString()
            Toast.makeText(context, dataSearch, Toast.LENGTH_SHORT).show()
            showSearch(dataSearch)
        }
        binding.edtSearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                binding.edtSearch.hideKeyboard()
                binding.btnClose.visibility=View.VISIBLE
                val dataSearch=binding.edtSearch.text.toString()
                Toast.makeText(context, dataSearch, Toast.LENGTH_SHORT).show()
                showSearch(dataSearch)
                return@OnKeyListener true
            }
            false
        })
        swipeContainer.setOnRefreshListener {
            swipeContainer.isRefreshing = true
            showData()
            berandaListAdapter.notifyDataSetChanged()
        }
        binding.btnLihata.setOnClickListener {
            startActivity(Intent(context, GuestNewsActivity::class.java))
            activity?.finish()
        }
    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    private fun showSearch(title:String){
        showShimmer(true)
        showView(false)
        recyclerView=binding.rvNews
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager= LinearLayoutManager(context)
        viewmodel.setSearchNews(title)
        viewmodel.getNews().observe(viewLifecycleOwner,{data->
            if(data.size!=0){
                mberandaListAdapter= BerandaListAdapter(data)
                mberandaListAdapter.notifyDataSetChanged()
                binding.rvNews.adapter=mberandaListAdapter
                swipeContainer.isRefreshing = false
                mberandaListAdapter.setOnItemClickCallback(object :BerandaListAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: News) {
                        val intent= Intent(context, GuestDetailActivity::class.java)
                        intent.putExtra(GuestDetailActivity.EXTRA_DATA,data)
                        intent.putExtra("data","beranda")
                        startActivity(intent)
                        activity?.finish()
                    }
                })
            }else{

            }

        })
    }
    private fun showView(boolean: Boolean){
        if (boolean){
            binding.rvMenu.visibility=View.VISIBLE
            binding.tvwhatnew.visibility=View.VISIBLE
            binding.rvNews.visibility=View.VISIBLE
            binding.rvSearch.visibility=View.GONE
            showData()
            showMenu()
        }
        else{
            binding.rvMenu.visibility=View.GONE
            binding.rvNews.visibility=View.GONE
            binding.rvSearch.visibility=View.VISIBLE
            binding.tvwhatnew.visibility=View.GONE
            binding.noData.visibility=View.GONE
            binding.tvnodata.visibility=View.GONE
        }

    }
    private fun showShimmerSearch(boolean: Boolean){
        if(boolean){
            binding.shimmerSearch.startShimmer()
            binding.shimmerSearch.showShimmer(true)
            binding.shimmerSearch.visibility=View.INVISIBLE
        }
        else{
            binding.shimmerSearch.stopShimmer()
            binding.shimmerSearch.showShimmer(false)
            binding.shimmerSearch.visibility=View.GONE
        }
    }
    override fun onPause() {
        super.onPause()
        berandaListAdapter.notifyDataSetChanged()
    }
    private fun showData(){


        viewmodel.setNews()
        viewmodel.getNews().observe(viewLifecycleOwner,{data->
            showShimmer(false)
            berandaListAdapter=BerandaLimitListAdapter(data)
            recyclerView.adapter=berandaListAdapter
            berandaListAdapter.notifyDataSetChanged()
            swipeContainer.isRefreshing = false
            if (data.size==0){
                binding.noData.visibility=View.VISIBLE
                binding.tvnodata.visibility=View.VISIBLE
                Glide.with(this)
                    .asGif()
                    .load(R.drawable.nodatagif) // Replace with a valid url
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
                showShimmer(true)
                recyclerView=binding.rvNews
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager= LinearLayoutManager(context)
            }

            berandaListAdapter.setOnItemClickCallback(object :BerandaLimitListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: News) {
                    val intent= Intent(context, GuestDetailActivity::class.java)
                    intent.putExtra(GuestDetailActivity.EXTRA_DATA,data)
                    intent.putExtra("data","beranda")
                    startActivity(intent)
                    activity?.finish()
                }
            })

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

    private  fun showMenu(){

        binding.rvMenu.setHasFixedSize(true)
        binding.rvMenu.layoutManager= GridLayoutManager(context,3)
        list.addAll(getListMenu())
        menuAdapter = MenuListAdapter(list)
        binding.rvMenu.adapter=menuAdapter

        menuAdapter.setOnItemClickCallback(object : MenuListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Menu) {
                val intent: Intent
                when(data.title){
                    getString(R.string.berita)->{
                        intent= Intent(context, GuestNewsActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                    getString(R.string.event)->{
                        intent= Intent(context, GuestEventActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                    getString(R.string.tipsdantricks)->{
                        intent= Intent(context, GuestTipsActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                    getString(R.string.mitra)->{
                        intent= Intent(context, GuestMitraActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                    getString(R.string.podcast)->{
                        intent= Intent(context, GuestPodcastActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                    getString(R.string.komunitas)->{
                        intent= Intent(context, GuestKomunitasActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }

                }
            }
        })
    }
}