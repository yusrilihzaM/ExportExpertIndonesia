package com.app.eei.ui.admin.menu.event

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.eei.R
import com.app.eei.adapter.BerandaListAdapter
import com.app.eei.databinding.ActivityAdminEventBinding
import com.app.eei.databinding.ActivityAdminNewsBinding
import com.app.eei.entity.News
import com.app.eei.ui.admin.addform.AdminAddActivity
import com.app.eei.ui.admin.beranda.MainActivity
import com.app.eei.ui.admin.beranda.viewmodel.NewsViewModel
import com.app.eei.ui.admin.detail.AdminDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class AdminEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminEventBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewmodel: NewsViewModel
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var berandaListAdapter: BerandaListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_event)
        binding = ActivityAdminEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val upArrow = resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.title = Html.fromHtml("<font color=\"black\">" + getString(R.string.event) + "</font>")

        viewmodel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(NewsViewModel::class.java)
        swipeContainer = binding.swipeContainer

        showData()
        swipeContainer.setOnRefreshListener {
            swipeContainer.isRefreshing = true
            showData()
            berandaListAdapter.notifyDataSetChanged()
        }
        binding.btnAdd.setOnClickListener {
            val intent = Intent(Intent(this, AdminAddActivity::class.java))
            intent.putExtra("type",getString(R.string.event))
            startActivity(intent)
            finish()
        }
    }
    override fun onPause() {
        super.onPause()
        berandaListAdapter.notifyDataSetChanged()
    }

    private fun showData() {
        showShimmer(true)
        recyclerView = binding.rvNews
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewmodel.setNewsByType("Event")
        viewmodel.getNewsByType().observe(this, { data ->
            showShimmer(false)
            berandaListAdapter = BerandaListAdapter(data)
            recyclerView.adapter = berandaListAdapter
            berandaListAdapter.notifyDataSetChanged()
            swipeContainer.isRefreshing = false
            if (data.size == 0) {
                binding.noData.visibility = View.VISIBLE
                binding.tvnodata.visibility = View.VISIBLE
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

            } else {
                binding.noData.visibility = View.GONE
                binding.tvnodata.visibility = View.GONE
            }

            berandaListAdapter.setOnItemClickCallback(object :
                BerandaListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: News) {
                    Toast.makeText(this@AdminEventActivity, data.title, Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AdminEventActivity, AdminDetailActivity::class.java)
                    intent.putExtra(AdminDetailActivity.EXTRA_DATA, data)
                    startActivity(intent)
                    finish()
                }
            })

        })
    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showShimmer(boolean: Boolean) {
        if (boolean) {
            binding.shimmer.startShimmer()
            binding.shimmer.showShimmer(true)
        } else {
            binding.shimmer.stopShimmer()
            binding.shimmer.showShimmer(false)
            binding.shimmer.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            16908332 -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                true
            }
            else -> true
        }
    }
}