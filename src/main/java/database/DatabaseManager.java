package database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import moodtracker.MoodTrackerFunc;

/* MANEJA LAS FUNCIONES DE LA BASE DE DATOS */
public class DatabaseManager {

    //
    //
    // TASKS

    //Inserta una nueva tarea
    public void addTask(String name, String desc, String status, String type, LocalDate dueDate) {

        String sql = "INSERT INTO tasks (task_name, task_description, task_status, task_type, due_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, desc);
            stmt.setString(3, status);
            stmt.setString(4, type);
            stmt.setDate(5, Date.valueOf(dueDate));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Carga todas las tareas
    public void loadTasks(DefaultTableModel model) {

        String sql = "SELECT * FROM tasks";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("task_name"),
                    rs.getString("task_description"),
                    rs.getDate("due_date"),
                    rs.getString("task_status"),
                    rs.getString("task_type")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
  //Elimina una tarea por su ID
    public void deleteTask(int id) {

        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //
    //
    // MOODTRACKER

    //Guarda un estado de ánimo
    public void logMood(String mood) {

        String sql = "INSERT INTO moodtracker (mood_time, mood) VALUES (?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(2, mood);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Obtiene lista de moods
    public List<MoodTrackerFunc.MoodEntry> getMoods() {

        List<MoodTrackerFunc.MoodEntry> list = new ArrayList<>();

        String sql = "SELECT id, mood, mood_time FROM moodtracker ORDER BY mood_time ASC";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new MoodTrackerFunc.MoodEntry(
                    rs.getInt("id"),
                    rs.getTimestamp("mood_time").toLocalDateTime(),
                    rs.getString("mood")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    //
    //
    // JOURNAL

    //Inserta una nueva entrada
    public static void logEntry(String title, String content, LocalDate date) {

        String sql = "INSERT INTO journal (title, content, entry_date) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.setDate(3, Date.valueOf(date));

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Actualiza una entrada existente
    public static void updateEntry(int id, String title, String content) {

        String sql = "UPDATE journal SET title = ?, content = ? WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.setInt(3, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}