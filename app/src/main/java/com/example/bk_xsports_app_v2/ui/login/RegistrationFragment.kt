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
import kotlinx.android.synthetic.main.fragment_login_mediator.*
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        register_button.setOnClickListener {
            if (!checkEmail(register_email.text.toString())) {
                register_email.error = "Incorrect email"
            } else if (!checkPasswords(
                    register_first_password_text.text.toString(),
                    register_second_password_text.text.toString()
            )) {
                register_second_password_text.error = "Doesn't match first password."
            } else {
                registerCall(
                    register_name.text.toString(),
                    register_surname.text.toString(),
                    register_username.text.toString(),
                    register_email.text.toString(),
                    register_first_password_text.text.toString()
                )
            }
        }
    }

    private fun registerCall(name: String, surname: String, username: String, email: String, password: String) {
        userViewModel.register(name, surname, username, email, password)
        userViewModel.status.observe(viewLifecycleOwner) {
            if (userViewModel.status.value == true) {
                saveUserCredentials(email, password)
                val action = RegistrationFragmentDirections.actionRegistrationFragmentToMainActivity(userViewModel.token.value.toString())
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

    private fun checkEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun checkPasswords(password: String, repeated: String) : Boolean {
        return password == repeated
    }
}