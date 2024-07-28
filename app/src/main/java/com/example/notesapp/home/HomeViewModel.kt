package com.example.notesapp.home

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.di.NotesRepository
import com.example.notesapp.local.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NotesRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val noteList = MutableLiveData<List<Note>>()
    val isLogedOut = MutableLiveData<Boolean>()


    fun getAllNotes() {
        viewModelScope.launch {
            repository.getNotes().collectLatest {
                noteList.value = it
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
            getAllNotes()
        }
    }

    fun logOut() {
        handleSharedPref()
    }


    private fun handleSharedPref() {
        val login = sharedPreferences.getBoolean("isLoged", false)
        Log.e("login", login.toString())
        if (login) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("isLoged", false)
            editor.apply()
            isLogedOut.value = true
        }else {
            isLogedOut.value = false
        }

    }


}