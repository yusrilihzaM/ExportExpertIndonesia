package com.app.eei.ui.admin.akun.fragment

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
import com.app.eei.ui.admin.beranda.viewmodel.NewsViewModel


class AdminAkunFragment : Fragment() {
    private lateinit var binding: FragmentAdminAkunBinding
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

        binding.btnPersonal.setOnClickListener {
            Toast.makeText(context, "Personal", Toast.LENGTH_SHORT).show()
        }

        binding.btnTentang.setOnClickListener {
            Toast.makeText(context, "Tentang", Toast.LENGTH_SHORT).show()
        }

        binding.btnLogout.setOnClickListener {
            Toast.makeText(context, "Proses Logout", Toast.LENGTH_SHORT).show()
        }
    }
}