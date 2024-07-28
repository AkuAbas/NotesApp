package com.example.notesapp.di

import com.example.notesapp.local.Note
import com.example.notesapp.local.NoteDAO
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NotesRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val dao: NoteDAO
) {

    suspend fun loginUser(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password).await()

    suspend fun registerUser(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password).await()

    fun addNote(note: Note) = dao.addNote(note)
    fun getNotes() = dao.getAllNotes()
    suspend fun deleteNote(note: Note) = dao.deleteNote(note)
}