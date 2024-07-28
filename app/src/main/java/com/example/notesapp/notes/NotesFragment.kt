package com.example.notesapp.notes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.databinding.FragmentNotesBinding
import com.example.notesapp.utils.BaseFragment
import com.example.notesapp.local.Note
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : BaseFragment<FragmentNotesBinding>(FragmentNotesBinding::inflate) {
    private val viewModel by viewModels<NotesViewModel>()
    private val args: NotesFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.note?.let {
            binding.editTextTitle.setText(it.title)
            binding.editTextNote.setText(it.body)
        }

        binding.buttonBack.setOnClickListener {
            if (args.note !== null) {
                updateNote()
            } else addNote()
            findNavController().popBackStack()
        }
    }


    private fun updateNote() {
        val title = binding.editTextTitle.text.toString().trim()
        val body = binding.editTextNote.text.toString().trim()
        args.note?.let {
            viewModel.addNewNote(it.copy(title = title, body = body))
        }
    }

    private fun addNote() {
        val title = binding.editTextTitle.text.toString().trim()
        val body = binding.editTextNote.text.toString().trim()
        if (title.isNotEmpty() || body.isNotEmpty()) {
            viewModel.addNewNote(Note(title = title, body = body))
        }

    }


}