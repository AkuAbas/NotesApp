package com.example.notesapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.notesapp.local.NoteDAO
import com.example.notesapp.local.NoteDataBase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("mySharedPref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun createRoom(@ApplicationContext context: Context): NoteDataBase {
        return Room.databaseBuilder(context, NoteDataBase::class.java, "local_db").build()
    }

    @Provides
    @Singleton
    fun provideDao(noteDataBase: NoteDataBase): NoteDAO {
        return noteDataBase.getDao()
    }
}