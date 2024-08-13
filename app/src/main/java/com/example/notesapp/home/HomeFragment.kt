package com.example.notesapp.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.databinding.CustomAlertDeleteBinding
import com.example.notesapp.databinding.CustomAlertLogoutBinding
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.utils.BaseFragment
import com.example.notesapp.utils.gone
import com.example.notesapp.local.Note
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel by viewModels<HomeViewModel>()
    private val noteAdapter = NoteAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvHome.adapter = noteAdapter
        observeData()
        viewModel.getAllNotes()

        setupClicks()
    }


    private fun setupClicks() {
        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNotesFragment())
        }
        noteAdapter.onClick = {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNotesFragment(it))
        }
        noteAdapter.onDelete = {
            createAlertDialogDelete(it)
        }
        binding.buttonLogOut.setOnClickListener {
            createAlertDialogLogout()
        }
    }


    private fun observeData() {
        viewModel.noteList.observe(viewLifecycleOwner) {
            noteAdapter.updateList(it)
            if (it.isNotEmpty()) binding.textViewHome.gone()
        }
        viewModel.isLogedOut.observe(viewLifecycleOwner) {
            if (it) findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment2())
        }
    }


    private fun createAlertDialogDelete(note: Note) {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        val alertBinding = CustomAlertDeleteBinding.inflate(layoutInflater)
        alertDialog.setView(alertBinding.root)
        alertDialog.show()

        alertBinding.buttonAlertDelete.setOnClickListener {
            viewModel.deleteNote(note)
            alertDialog.dismiss()
        }
        alertBinding.buttonAlertCancel.setOnClickListener { alertDialog.dismiss() }
    }

    private fun createAlertDialogLogout() {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        val alertBinding = CustomAlertLogoutBinding.inflate(layoutInflater)
        alertDialog.setView(alertBinding.root)
        alertDialog.show()

        alertBinding.buttonAlertLogout.setOnClickListener {
            viewModel.logOut()
            Toast.makeText(context, "You have been logged out successfully", Toast.LENGTH_SHORT)
                .show()
            alertDialog.dismiss()
        }
        alertBinding.buttonAlertCancel.setOnClickListener { alertDialog.dismiss() }
    }


}