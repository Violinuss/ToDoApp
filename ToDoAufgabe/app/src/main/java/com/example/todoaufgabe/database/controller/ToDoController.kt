package com.example.todoaufgabe.database.controller

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.todoaufgabe.database.DbHelper
import com.example.todoaufgabe.database.dataclass.ToDoDataClass


class ToDoController(context: Context) {
    private val dbHelper = DbHelper(context)

    /**
     * Fügt ein neues ToDo in die Datenbank ein.
     */
    fun insertToDo(todo: ToDoDataClass): Boolean {
        val db = dbHelper.writableDatabase
        return try {
            // Eingabedaten validieren
            if (todo.name.isBlank() || todo.description.isBlank() || todo.endTime.isBlank()) {
                Log.e("ToDoController", "Ungültige Eingabedaten: Name, Beschreibung oder Datum leer")
                return false
            }

            val values = ContentValues().apply {
                put("name", todo.name)
                put("description", todo.description)
                put("priority", todo.priority)
                put("enddate", todo.endTime) // Muss im Format YYYY-MM-DD HH:MM:SS sein
                put("state", if (todo.status) 1 else 0)
            }

            val result = db.insert("task", null, values)
            if (result == -1L) {
                Log.e("ToDoController", "Fehler beim Einfügen: Insert result = -1")
                return false
            }
            Log.d("ToDoController", "Insert erfolgreich: ID = $result")
            true
        } catch (e: Exception) {
            Log.e("ToDoController", "Fehler beim Einfügen: ${e.message}", e)
            false
        } finally {
            db.close()
        }
    }


    /**
     * Aktualisiert ein bestehendes ToDo in der Datenbank.
     */
    fun updateToDo(todo: ToDoDataClass): Boolean {
        val db = dbHelper.writableDatabase
        return try {
            // Eingabedaten validieren
            if (todo.name.isBlank() || todo.description.isBlank() || todo.endTime.isBlank()) {
                Log.e("ToDoController", "Ungültige Eingabedaten: Name, Beschreibung oder Datum leer")
                return false
            }

            val values = ContentValues().apply {
                put("name", todo.name) // Korrigiert: Name wird jetzt korrekt aktualisiert
                put("description", todo.description)
                put("priority", todo.priority)
                put("enddate", todo.endTime) // Muss im Format YYYY-MM-DD HH:MM:SS sein
                put("state", if (todo.status) 1 else 0)
            }

            val result = db.update("task", values, "id = ?", arrayOf(todo.id.toString()))
            if (result == 0) {
                Log.e("ToDoController", "Update fehlgeschlagen: Keine Zeilen betroffen")
                return false
            }
            Log.d("ToDoController", "Update erfolgreich: $result Zeilen betroffen")
            true
        } catch (e: Exception) {
            Log.e("ToDoController", "Fehler beim Aktualisieren: ${e.message}", e)
            false
        } finally {
            db.close()
        }
    }


    /**
     * Löscht ein ToDo anhand seiner ID.
     */
    fun deleteToDo(id: Int): Boolean {
        val db = dbHelper.writableDatabase
        return try {
            db.delete("task", "id = ?", arrayOf(id.toString())) > 0
        } catch (e: Exception) {
            Log.e("ToDoController", "Fehler beim Löschen: ${e.message}", e)
            false
        } finally {
            db.close()
        }
    }

    /**
     * Ruft alle ToDos aus der Datenbank ab
     */
    fun getAllToDos(status: Boolean? = null): List<ToDoDataClass> {
        val db = dbHelper.readableDatabase
        val todos = mutableListOf<ToDoDataClass>()
        val query = buildString {
            append("SELECT * FROM task")
            if (status != null) {
                append(" WHERE state = ${if (status) 1 else 0}")
            }
        }
        val cursor: Cursor? = db.rawQuery(query, null)

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    todos.add(
                        ToDoDataClass(
                            id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                            name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                            description = cursor.getString(cursor.getColumnIndexOrThrow("description")),
                            priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority")),
                            endTime = cursor.getString(cursor.getColumnIndexOrThrow("enddate")),
                            status = cursor.getInt(cursor.getColumnIndexOrThrow("state")) == 1
                        )
                    )
                } while (cursor.moveToNext())
            }
            Log.d("ToDoController", "Geladene ToDos: ${todos.size}")
        } catch (e: Exception) {
            Log.e("ToDoController", "Fehler beim Abrufen: ${e.message}", e)
        } finally {
            cursor?.close()
            db.close()
        }

        return todos
    }
}
