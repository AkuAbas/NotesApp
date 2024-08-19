package com.example.notesapp.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.NoteItemBinding
import com.example.notesapp.local.Note

class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteViewHolder>((NoteDiffCallback())) {

    lateinit var onClick: (Note) -> Unit
    lateinit var onDelete: (Note) -> Unit

    inner class NoteViewHolder(val noteItemBinding: NoteItemBinding) :
        RecyclerView.ViewHolder(noteItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val data = getItem(position)
        holder.noteItemBinding.note = data
        holder.noteItemBinding.noteItem.setOnClickListener {
            onClick(data)
        }
        holder.noteItemBinding.buttonDelete.setOnClickListener {
            onDelete(data)
        }
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

    }
}