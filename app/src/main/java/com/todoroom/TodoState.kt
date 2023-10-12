package com.todoroom
// used for UI states
data class TodoState(
    val todos: List<Todo> = emptyList(),
    val title: String = "",
    val isAddingTodo: Boolean = false,
    val checked: Boolean = false
)
