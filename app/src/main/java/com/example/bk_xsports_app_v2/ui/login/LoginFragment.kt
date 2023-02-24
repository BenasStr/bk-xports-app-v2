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
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login_mediator.*
import kotlinx.android.synthetic.main.fragment_login_mediator.login_button

class LoginFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_button.setOnClickListener {
            loginCall(email_field.text.toString(), password_field.text.toString())
        }
    }

    private fun loginCall(email: String, password: String) {
        userViewModel.login(email, password)
        userViewModel.status.observe(viewLifecycleOwner) {
            if(userViewModel.status.value == true) {
                saveUserCredentials(userViewModel.email.value.toString(), userViewModel.password.value.toString())
                val action = LoginFragmentDirections.actionLoginFragmentToMainActivity(userViewModel.token.value.toString())
                findNavController().navigate(action)
            }
        }
    }

    private fun saveUserCredentials(email: String, password: String) {
        val sharedPreference = activity?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val editor = sharedPreference?.edit()
        editor?.putString("email", email)
        editor?.putString("password", password)
        editor?.apply()
    }
}