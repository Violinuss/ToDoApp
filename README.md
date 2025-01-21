# ToDoApp
Eine Android-App zur Demonstration von Datenpersistenz und SQLite-Datenbankanbindung in Android-Anwendungen. Diese App dient als Abgabeprojekt zur Demonstration von SQLite-Datenbankanbindung in Android. Berabeitet von Violeta B. Die eidesstattliche Erklärung befindet sich im Briefkasten.
## Installation & Set-up
1. Repository klonen:
 git clone https://github.com/derschwabe226/StudentCard.git
3. Projekt(ToDoApp) in Android Studio öffnen (nicht das ganze Repository)
4. Gradle-Sync durchführen
5. Emulator mit mindestens API 26 erstellen oder verwenden
6. App ausführen
Entwickeln Sie eine ToDo-App mit folgender Funktionalität:

## Funktionsweise
Die ToDoApp besteht aus zwei Hauptansichten: Dashboard, ToDoScreen.
Nach App-Start wird das Dashboard angezeigt.
Erledigte ToDos werden grün angezeigt und heben sich somit von den offenen Aufgaben ab.

Die ToDos werden als Cards anzeigt, die die zwei Buttons "Löschen" und "Bearbeiten" verwalten.
Unten rechts im Screen ist ein "+" Button zu finden, der das Hinzufügen von weiteren ToDos ermöglicht.
Oben rechts findet man ein Listen-Icon, der es ermöglicht die ToDos nach Status zu filtern.
Ein ToDo-Element besteht aus folgenden Attributen:
-Name
-Priorität
-Endzeitpunkt (JJJJ-MM-TT)
-Beschreibungstext
-Status (erledigt/offen)

Die Eingabe von der Priorität und dem Status sind anders als bei den restlichen Attributen nicht zwingend erforderlich, da einfach Default-Werte sonst verwendet werden.

## Verwendete Technologien
- Implementierung in Jetpack Compose
- SQLite-Datenbank zur Datenspeicherung
- DBeaver 24.3.2

## bekannte Probleme
1. Ich wusste nicht, wie die App ungefähr visuell dargestellt werden sollte, weshalb ich erledigte ToDos grün gefärbt habe, um diese von offenen ToDos abzuheben.
2. Der Endzeitpunkt muss immer in dem Format JJJJ-MM-TT eingegeben werden.

