@file:OptIn(ExperimentalMaterial3Api::class)

package com.todoroom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddTodoDialog(
    state: TodoState,
    onEvent: (TodoEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(modifier = modifier,onDismissRequest = { onEvent(TodoEvent.HideDialog) },
        title = { Text(text = "Add new task")},
        text = {
            TextField(
                value = state.title,
                onValueChange = {onEvent(TodoEvent.SetTitle(it))},
                placeholder = { Text(
                    text = "Describe this task"
        )})
        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                Button(onClick = { onEvent(TodoEvent.SaveTodo) }) {Text(text = "Add")
                }
            }
        }
    )
}
