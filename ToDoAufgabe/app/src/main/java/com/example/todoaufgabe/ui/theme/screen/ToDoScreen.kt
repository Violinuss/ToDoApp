package com.example.todoaufgabe.ui.theme.screen


import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todoaufgabe.database.controller.ToDoController
import com.example.todoaufgabe.database.dataclass.ToDoDataClass
import com.example.todoaufgabe.ui.theme.Cerise
import com.example.todoaufgabe.ui.theme.Pinkbeauty
import com.example.todoaufgabe.ui.theme.Pinki
import com.example.todoaufgabe.ui.theme.screen.ToDoCard

@Composable
fun ToDoScreen(navController: NavHostController) {
    val context = LocalContext.current
    val todoController = ToDoController(context)

    // Zustände
    var todos by remember { mutableStateOf(todoController.getAllToDos()) }
    var selectedFilter by remember { mutableStateOf("Alle") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedToDo by remember { mutableStateOf<ToDoDataClass?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Header mit Dashboard und FilterDropdown
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Pinkbeauty)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Dashboard",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                    FilterDropdown(
                        selectedFilter = selectedFilter,
                        onFilterChange = { newFilter ->
                            selectedFilter = newFilter
                            todos = when (newFilter) {
                                "Offen" -> todoController.getAllToDos(status = false)
                                "Erledigt" -> todoController.getAllToDos(status = true)
                                else -> todoController.getAllToDos()
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ToDo-Liste
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(todos) { todo ->
                    ToDoCard(
                        todo = todo,
                        onEditClick = {
                            selectedToDo = todo
                            showDialog = true
                        },
                        onDeleteClick = {
                            todoController.deleteToDo(todo.id)
                            todos = todoController.getAllToDos(status = when (selectedFilter) {
                                "Offen" -> false
                                "Erledigt" -> true
                                else -> null
                            })
                        }
                    )
                }
            }
        }

        // FloatingActionButton unten rechts
        FloatingActionButton(
            onClick = {
                selectedToDo = null
                showDialog = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = Pinki
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Neues ToDo hinzufügen",
                tint = Color.White
            )
        }
    }

    // Dialog für ToDo erstellen/bearbeiten
    if (showDialog) {
        ToDoDialog(
            todo = selectedToDo,
            onSave = { todo ->
                if (todo.id == 0) {
                    todoController.insertToDo(todo)
                } else {
                    todoController.updateToDo(todo)
                }
                if(!todo.status.equals("Offen"))
                todos = todoController.getAllToDos(status = when (selectedFilter) {
                    "Erledigt" -> true
                    else -> null
                })
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }
}
