package com.todoroom

sealed interface TodoEvent{
    object SaveTodo: TodoEvent
    data class SetTitle(val title: String): TodoEvent
    data class Check(val id: Int, val Checked: Boolean): TodoEvent
    data class DeleteTodos(val todos: List<Todo>): TodoEvent
    data class DeleteTodo(val todo: Todo): TodoEvent
    object ShowDialog: TodoEvent
    object HideDialog: TodoEvent
}

