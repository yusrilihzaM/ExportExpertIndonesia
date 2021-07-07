package com.app.eei.ui.admin.beranda.fragment

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eei.R
import com.app.eei.adapter.BerandaListAdapter
import com.app.eei.databinding.FragmentBerandaBinding
import com.app.eei.entity.News
import com.app.eei.ui.admin.addform.AdminAddActivity
import com.app.eei.ui.admin.beranda.viewmodel.NewsViewModel
import com.app.eei.ui.admin.detail.AdminDetailActivity
import com.app.eei.ui.admin.detail.AdminDetailActivity.Companion.EXTRA_DATA
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
    private lateinit var berandaListAdapter: BerandaListAdapter
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
        binding.btnSearch.setOnClickListener {

            val dataSearch=binding.edtSearch.text.toString()
            Toast.makeText(context, dataSearch, Toast.LENGTH_SHORT).show()
            showSearch(dataSearch)
        }
        binding.edtSearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                val dataSearch=binding.edtSearch.text.toString()
                Toast.makeText(context, dataSearch, Toast.LENGTH_SHORT).show()
                showSearch(dataSearch)
                return@OnKeyListener true
            }
            false
        })
        swipeContainer.setOnRefreshListener {
            swipeContainer.isRefreshing = true
            berandaListAdapter.clear()
            showShimmer(true)
            recyclerView.adapter=berandaListAdapter

            showData()
        }
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(context,AdminAddActivity::class.java))
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
            berandaListAdapter=BerandaListAdapter(data)
            berandaListAdapter.notifyDataSetChanged()
            recyclerView.adapter=berandaListAdapter
            swipeContainer.isRefreshing = false
            berandaListAdapter.setOnItemClickCallback(object :BerandaListAdapter.OnItemClickCallback{
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
            berandaListAdapter=BerandaListAdapter(data)
            berandaListAdapter.notifyDataSetChanged()
            recyclerView.adapter=berandaListAdapter

            swipeContainer.isRefreshing = false
            if (data.size==0){
                binding.linear.visibility=View.VISIBLE
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
            }
            berandaListAdapter.setOnItemClickCallback(object :BerandaListAdapter.OnItemClickCallback{
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
}