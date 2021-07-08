package com.app.eei.ui.admin.akun.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.app.eei.R
import com.app.eei.databinding.FragmentAdminAkunBinding
import com.app.eei.databinding.FragmentBerandaBinding
import com.app.eei.extensions.Extensions.toast
import com.app.eei.ui.admin.beranda.viewmodel.NewsViewModel
import com.app.eei.ui.guest.GuestMainActivity
import com.app.eei.utils.FirebaseUtils.firebaseAuth
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase


class AdminAkunFragment : Fragment() {
    private lateinit var binding: FragmentAdminAkunBinding
    var name:String?="Admin"
    var email :String?=""
    var photoUrl :String?=""
    var emailVerified :String?=""
    var uid :String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_akun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentAdminAkunBinding.bind(view)
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            name = user.displayName
            email = user.email
            photoUrl = user.photoUrl.toString()
            emailVerified = user.isEmailVerified.toString()
            uid = user.uid
        }

        Glide.with(this)
            .load(R.drawable.ic_admin)
            .into(binding.icProfile)

        binding.nameUser.text=email
        binding.btnPersonal.setOnClickListener {
            Toast.makeText(context, "Personal", Toast.LENGTH_SHORT).show()
        }

        binding.btnTentang.setOnClickListener {
            Toast.makeText(context, "Tentang", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(context, GuestMainActivity::class.java))
            activity?.toast("signed out")
            activity?.finish()
        }
    }
}