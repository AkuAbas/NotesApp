package com.example.notesapp.account

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapp.databinding.FragmentLoginBinding
import com.example.notesapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel by viewModels<LoginViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.getSharedPref()
        binding.buttonLogin.setOnClickListener {
            signIn()
        }
        binding.registerLink.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

    }

    private fun observeData() {
        viewModel.loggedIn.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Loged successfully. Welcome!", Toast.LENGTH_SHORT).show()
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            } else Toast.makeText(context, "Email or password is wrong", Toast.LENGTH_SHORT).show()
        }
        viewModel.alreadyLoggedIn.observe(viewLifecycleOwner){
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar2.isVisible = it
        }
        viewModel.isEmpty.observe(viewLifecycleOwner){
            if (it) Toast.makeText(context, "All inputs must be filled to continue.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun signIn() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        viewModel.login(email, password)
    }


}
