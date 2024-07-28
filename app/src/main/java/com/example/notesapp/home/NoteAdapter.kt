package com.example.notesapp.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.NoteItemBinding
import com.example.notesapp.local.Note

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    val noteList = arrayListOf<Note>()
    lateinit var onClick: (Note) -> Unit
    lateinit var onDelete: (Note)-> Unit

    inner class NoteViewHolder(val noteItemBinding: NoteItemBinding) :
        RecyclerView.ViewHolder(noteItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val data = noteList[position]
        holder.noteItemBinding.note = data
        holder.noteItemBinding.noteItem.setOnClickListener {
            onClick(data)
        }
        holder.noteItemBinding.buttonDelete.setOnClickListener {
            onDelete(data)
        }
    }

    fun updateList(newList: List<Note>) {
        noteList.clear()
        noteList.addAll(newList)
        notifyDataSetChanged()
    }
}