package com.example.todoaufgabe.database.dataclass

/**
 * Datenklasse, die ein ToDo-Element darstellt
 * @param id Eindeutige ID des ToDos in der Datenbank
 * @param name Name des ToDos
 * @param priority Priorität des ToDos
 * @param endTime Enddatum des ToDos im Format "YYYY-MM-DD"
 * @param description Beschreibung des ToDos
 *  @param status Status des ToDos: true bedeutet erledigt, false bedeutet offen
 *
 */
data class ToDoDataClass(
    val id: Int = 0,
    val name: String,
    val priority: Int,
    val endTime: String,
    val description: String,
    val status: Boolean
)
