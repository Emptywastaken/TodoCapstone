package com.todoroom

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoViewModel(
    private val dao: TodoDao
): ViewModel() {

    private val _state = MutableStateFlow(TodoState())
    private val _todos = dao.getTodos().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    // combine merges flows
    // if any of combined flows emits a value the code below is going to be executed
    // inState to pass down immutable state to composables
    val state = combine(_state, _todos) { state, todos ->
        state.copy(
            todos = todos,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TodoState())

    fun onEvent(event:TodoEvent) {
        when(event) {
            is TodoEvent.Check -> {
                viewModelScope.launch{
                    dao.updateChecked(event.id, event.Checked)
                }
            }

            is TodoEvent.DeleteTodos -> {
                viewModelScope.launch{
                    dao.deleteTodos(event.todos)
                }
            }
            is TodoEvent.DeleteTodo -> {
                viewModelScope.launch {
                    dao.deleteTodo(event.todo)
                }
            }

            TodoEvent.HideDialog -> {
                _state.update { it.copy( isAddingTodo = false) }}

            TodoEvent.ShowDialog -> {
                    _state.update {it.copy(isAddingTodo = true)}
            }

            is TodoEvent.SetTitle -> {
                _state.update{ it.copy(title = event.title)}
            }
            TodoEvent.SaveTodo -> {
                val title = state.value.title

                if(title.isBlank()){
                    return
                }

                val todo = Todo(
                    title = title,
                    checked = false
                )

                viewModelScope.launch {
                    dao.upsertTodo(todo)
                }
                // hide the dialogue window
                // and reset input fields
                _state.update { it.copy(
                    isAddingTodo = false,
                    title = "",
                ) }
            }

        }
    }
}