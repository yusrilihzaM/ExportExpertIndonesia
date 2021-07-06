package com.app.eei.ui.guest.akun

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.eei.R
import com.app.eei.databinding.FragmentAdminAkunBinding
import com.app.eei.databinding.FragmentGuestAkunBinding
import com.app.eei.ui.login.LoginActivity
import com.app.eei.ui.tentang.TentangActivity


class GuestAkunFragment : Fragment() {
    private lateinit var binding: FragmentGuestAkunBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guest_akun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentGuestAkunBinding.bind(view)



        binding.btnTentang.setOnClickListener {
            startActivity(Intent(context,TentangActivity::class.java))
        }

        binding.btnMasuk.setOnClickListener {
            Toast.makeText(context, "Masuk", Toast.LENGTH_SHORT).show()
            startActivity(Intent(context,LoginActivity::class.java))
            activity?.finish()
        }
    }
}