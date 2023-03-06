package com.example.bk_xsports_app_v2.ui.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bk_xsports_app_v2.R
import com.example.bk_xsports_app_v2.model.UserViewModel

class LoginMainFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginWithoutUserInput()
        return inflater.inflate(R.layout.fragment_login_main, container, false)
    }

    private fun loginWithoutUserInput() {
        val sharedPreference = activity?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val email = sharedPreference?.getString("email", null)
        val password = sharedPreference?.getString("password", null)

//        if(email != null && password != null) {
//            loginCall(email, password)
//        } else {
//            val action = LoginMainFragmentDirections.actionLoginMainFragmentToLoginMediatorFragment()
//            findNavController().navigate(action)
//        }

        val action = LoginMainFragmentDirections.actionLoginMainFragmentToLoginMediatorFragment()
        findNavController().navigate(action)
    }

    private fun loginCall(email: String, password: String) {
        userViewModel.login(email, password)
        userViewModel.status.observe(viewLifecycleOwner) {
            if(userViewModel.status.value == true) {
                val action = LoginMainFragmentDirections.actionLoginMainFragmentToMainActivity(userViewModel.token.value.toString())
                findNavController().navigate(action)
            } else {
                val action = LoginMainFragmentDirections.actionLoginMainFragmentToLoginMediatorFragment()
                findNavController().navigate(action)
            }
        }
    }
}