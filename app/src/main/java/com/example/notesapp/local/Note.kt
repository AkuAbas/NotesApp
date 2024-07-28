package com.example.notesapp.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("note_id")
    val id: Int = 0,
    @ColumnInfo("note_title")
    val title: String,
    @ColumnInfo("note_body")
    val body: String
): Parcelable
