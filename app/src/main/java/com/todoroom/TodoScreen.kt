package com.todoroom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    state: TodoState,
    onEvent: (TodoEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {onEvent(TodoEvent.ShowDialog)}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)

                }
            },
        modifier = Modifier.padding(16.dp)
    ) { padding ->
        if (state.isAddingTodo){
            AddTodoDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(contentPadding = padding,
            modifier = Modifier.fillMaxSize().padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
            )
        {
            items(items = state.todos){todo ->
                taskItem(todo.title, todo.checked, onCheck = {onEvent(TodoEvent.Check(todo.id, !todo.checked))}, onClose = {onEvent(TodoEvent.DeleteTodo(todo))})

            }
        }
    }
}


@Composable
fun taskItem(
    title: String,
    checked: Boolean,
    onCheck: (Boolean)-> Unit,
    onClose: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically){
        if(checked) {
            Text(
                text = title,
                fontStyle = FontStyle.Italic,
                style = LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
                    .alpha(0.7f)
            )

        }
        else{
            Text(text = title, fontSize = 16.sp,
                modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)

            )
        }
        Checkbox(checked = checked, onCheckedChange = onCheck)
        IconButton(onClick = onClose) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    }
}