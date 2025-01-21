package com.example.todoaufgabe.ui.theme.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoaufgabe.database.dataclass.ToDoDataClass
import com.example.todoaufgabe.ui.theme.Cerise
import com.example.todoaufgabe.ui.theme.Green
import com.example.todoaufgabe.ui.theme.Lightgreen
import com.example.todoaufgabe.ui.theme.Pink80
import com.example.todoaufgabe.ui.theme.Pinki
import com.example.todoaufgabe.ui.theme.Rouge

/**
 * Card, die alle den Namen, der Priorität, dem Enddatum und dem Status abbildet, sowie
 * zwei Buttons am Ende der Card zum Bearbeiten oder Löschen der Aufgabe
 * Dabei werden erledigte Aufgaben Hellgrün angezeigt und ihr Status in grün
 */
@Composable
fun ToDoCard(
    todo: ToDoDataClass,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val backgroundColor = if (todo.status) Lightgreen else Pink80
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = todo.name,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Priorität: ${getPriorityLevel(todo.priority)}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Fällig bis: ${todo.endTime}",
                style = MaterialTheme.typography.bodyMedium
            )
            if (todo.status) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Status: Erledigt",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Green
                )
            } else{
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Status: Offen",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = onEditClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Cerise,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Bearbeiten")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onDeleteClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Cerise,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Löschen")
                }
            }
        }
    }
}

/**
 * Funktion, um die Priorität der Aufgabe zu ermitteln
 */
fun getPriorityLevel(priority: Int): String {
    return when (priority) {
        1 -> "Niedrig"
        2 -> "Mittel"
        3 -> "Hoch"
        else -> "Unbekannt"
    }
}

/**
 * DropDownMenu, für die Auswahl der Prioritöt der Aufgabe beim Erstellen oder Bearbeiten einer neuen Aufgabe
 */
@Composable
fun PriorityDropdownMenu(
    priority: Int,
    onPriorityChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Cerise,
                contentColor = Color.White
            )
        ){
            Text(text = "Priorität: ${getPriorityLevel(priority)}")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Niedrig") },
                onClick = {
                    onPriorityChange(1)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Mittel") },
                onClick = {
                    onPriorityChange(2)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Hoch") },
                onClick = {
                    onPriorityChange(3)
                    expanded = false
                }
            )
        }
    }
}


/**
 * Dialog, wenn man eine Aufgabe erstellt oder bearbeitet.
 * Man muss den Namen, Beschreibung und Enddatum eingeben.
 * Wenn man nichts für Priorität oder Status auswählt, werden die Defaultwerte "Niedrig" und "Offen" ausgewählt.
 */
@Composable
fun ToDoDialog(
    todo: ToDoDataClass?,
    onDismiss: () -> Unit,
    onSave: (ToDoDataClass) -> Unit
) {
    var name by remember { mutableStateOf(todo?.name ?: "") }
    var priority by remember { mutableStateOf(todo?.priority ?: 1) }
    var endTime by remember { mutableStateOf(todo?.endTime ?: "") }
    var description by remember { mutableStateOf(todo?.description ?: "") }
    var status by remember { mutableStateOf(todo?.status ?: false) }

    AlertDialog(
        containerColor = Pinki,
        onDismissRequest = onDismiss,
        title = {
            Text(text = if (todo == null) "Neues ToDo erstellen" else "ToDo bearbeiten", color = Color.White)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(text = "Beschreibung") },
                    modifier = Modifier.fillMaxWidth()
                )
                PriorityDropdownMenu(
                    priority = priority,
                    onPriorityChange = { newPriority -> priority = newPriority }
                )
                TextField(
                    value = endTime,
                    onValueChange = { endTime = it },
                    label = { Text(text = "Fälligkeitsdatum (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = status,
                        onCheckedChange = { status = it }
                    )
                    Text(text = "Erledigt", color = Color.White)
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isBlank() || endTime.isBlank() || description.isBlank()) {
                    Log.e("ToDoDialog", "Name, Beschreibung oder Enddatum ist leer.")
                    return@Button
                }
                if (!endTime.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
                    Log.e("ToDoDialog", "Ungültiges Fälligkeitsdatum.")
                    return@Button
                }
                val updatedToDo = ToDoDataClass(
                    id = todo?.id ?: 0,
                    name = name,
                    priority = priority,
                    endTime = endTime,
                    description = description,
                    status = status
                )
                onSave(updatedToDo)
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Cerise,
                    contentColor = Color.White)
            ) {
                Text(text = "Speichern")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Cerise,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Abbrechen")
            }
        }
    )
}

/**
 * Funktion, die die Filterung von Aufgaben, je nach Status regelt.
 * Es gibt "Alle", "Offen" und "Erledigt".
 * Bei den zwei letzteren Stufen werden nur Aufgaben angezeigt, die offen bzw erledigt sind.
 */
@Composable
fun FilterDropdown(
    selectedFilter: String,
    onFilterChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Rouge
                )
            ) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = "Filter anwenden",
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Alle") },
                onClick = {
                    onFilterChange("Alle")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Offen") },
                onClick = {
                    onFilterChange("Offen")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Erledigt") },
                onClick = {
                    onFilterChange("Erledigt")
                    expanded = false
                }
            )
        }
    }
}
