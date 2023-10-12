package com.todoroom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Dao
//all functions that are avalible for a specific table
interface TodoDao {

    @Upsert
    suspend fun upsertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodos(todos: List<Todo>)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo ORDER BY id")
    fun getTodos(): Flow<List<Todo>>

    @Query("UPDATE todo SET checked = :Checked WHERE id == :eventId")
    suspend fun updateChecked(eventId: Int, Checked: Boolean)
}