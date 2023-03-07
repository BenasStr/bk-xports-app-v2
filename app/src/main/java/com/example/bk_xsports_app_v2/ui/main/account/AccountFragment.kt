package com.example.bk_xsports_app_v2.ui.main.account

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.databinding.FragmentAccountBinding
import com.google.android.material.card.MaterialCardView

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? =null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logout = view.findViewById<MaterialCardView>(R.id.account_logout)
        logout.setOnClickListener {
            removeUserCredentials()
            navigateToLogin()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun removeUserCredentials() {
        val sharedPreference = activity?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        editor?.putString("email", null)
        editor?.putString("password", null)
        editor?.apply()
    }

    private fun navigateToLogin() {
        val action = AccountFragmentDirections.actionNavigationAccountToLoginActivity()
        findNavController().navigate(action)
    }
}