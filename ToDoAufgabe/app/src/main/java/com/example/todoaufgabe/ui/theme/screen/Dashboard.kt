package com.example.todoaufgabe.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoaufgabe.ui.theme.screen.ToDoScreen
import com.example.todoaufgabe.database.controller.ToDoController

/**
 * Hauptnavigationsansicht der App und verwaltet die Navigationen zwischen den zwei Bildschirmen ("ToDoScreen", "Dashboard")
 */
@Composable
fun Dashboard() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "todo_screen") {
        composable("todo_screen") {
            ToDoScreen(navController = navController)
        }
    }
}
