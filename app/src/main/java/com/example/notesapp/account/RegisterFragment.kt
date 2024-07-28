package com.example.notesapp.account

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapp.databinding.FragmentRegisterBinding
import com.example.notesapp.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding.buttonRegister.setOnClickListener {
            regitser()
        }
        binding.loginLink.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun regitser() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModel.register(email, password)
        } else Toast.makeText(context, "All inputs must be filled to continue.", Toast.LENGTH_SHORT)
            .show()
    }

    private fun observeData() {
        viewModel.registeredIn.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    context,
                    "Your account has been successfully created. Welcome!",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToHomeFragment())
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

}