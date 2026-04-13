package moodtracker;

import java.time.LocalDateTime;
import java.util.List;

import database.DatabaseManager; // Clase para interactuar con la base de datos

public class MoodTrackerFunc {

    private DatabaseManager db = new DatabaseManager(); // Conexión a la base de datos

    // Método para registrar un nuevo estado de ánimo en la base de datos
    public void addMood(String mood) {
        db.logMood(mood); // Llama al método de la base de datos para guardar el estado de ánimo
    }

    // Método para obtener todos los estados de ánimo registrados
    public List<MoodEntry> getMoods() {
        return db.getMoods(); // Devuelve la lista de estados de ánimo desde la base de datos
    }

    // Clase interna que representa una entrada de estado de ánimo en la base de datos
    public static class MoodEntry {

        private int id; // Identificador único para cada entrada
        private LocalDateTime timestamp; // Fecha y hora en que se registró el estado de ánimo
        private String mood; // El estado de ánimo en sí (por ejemplo, "feliz", "triste")

        // Constructor para inicializar una entrada de estado de ánimo
        public MoodEntry(int id, LocalDateTime timestamp, String mood) {
            this.id = id;
            this.timestamp = timestamp;
            this.mood = mood;
        }

        public int getId() {
            return id; // Obtiene el id de la entrada
        }

        public LocalDateTime getTimestamp() {
            return timestamp; // Obtiene la fecha y hora de la entrada
        }

        public String getMood() {
            return mood; // Obtiene el estado de ánimo registrado
        }
    }
}
