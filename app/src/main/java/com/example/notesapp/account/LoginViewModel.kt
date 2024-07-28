package com.example.notesapp.account

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.notesapp.di.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val repository: NotesRepository
) :
    ViewModel() {
    val loggedIn = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val isEmpty = MutableLiveData<Boolean>()
    val alreadyLoggedIn = MutableLiveData<Boolean>()

    fun login(email: String, password: String) {
        isLoading.value = true
        viewModelScope.launch {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                try {
                    val response = repository.loginUser(email, password)
                    response.user?.email?.let {
                        putSharedPref()
                        loggedIn.value = true
                        isLoading.value = false
                    } ?: run {
                        Log.e("message", "is empty...")
                    }
                } catch (e: Exception) {
                    loggedIn.value = false
                    isLoading.value = false
                    Log.e("Error", e.localizedMessage.toString())
                }
            } else {
                isEmpty.value = true
                isLoading.value = false
            }
        }
    }

    private fun putSharedPref() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoged", true)
        editor.apply()
    }

    fun getSharedPref() {
        val login = sharedPreferences.getBoolean("isLoged", false)
        if (login) {
            alreadyLoggedIn.value = true
        }
    }

}