package com.example.notesapp.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.di.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: NotesRepository) :
    ViewModel() {
    val registeredIn = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun register(email: String, password: String) {
        isLoading.value = false
        viewModelScope.launch {
            try {
                val response = repository.registerUser(email, password)
                response.user?.email?.let {
                    registeredIn.value = true
                    isLoading.value = false
                } ?: run {
                    Log.e("message", "is empty...")
                }
            } catch (e: Exception) {
                registeredIn.value = false
                isLoading.value = false
                errorMessage.value = e.localizedMessage.toString()
            }
        }
    }
}