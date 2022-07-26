package com.example.movieapp.ui.actors

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieapp.ui.genres.Genre

@Entity(tableName = "actors")
data class Actor (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "profile_path")
    var profile_path: String?,
    @ColumnInfo(name = "isSelected")
    var isSelected: Boolean)

{
    override fun equals(other: Any?) = (other is Actor) && id == other.id

    override fun toString(): String {
        return "Actor(id=$id, name='$name', profile_path='$profile_path', isSelected=$isSelected)"
    }
}
