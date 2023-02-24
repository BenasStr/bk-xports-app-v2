package com.example.bk_xsports_app_v2.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.bk_xsports_app_v2.R
import kotlinx.android.synthetic.main.fragment_login_mediator.*

class LoginMediatorFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_mediator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_button.setOnClickListener{
            val action = LoginMediatorFragmentDirections.actionLoginMediatorFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        register_button.setOnClickListener{
            val action = LoginMediatorFragmentDirections.actionLoginMediatorFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }
    }
}