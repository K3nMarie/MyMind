package database;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import moodtracker.MoodTrackerFunc;

/* MANEJA LAS FUNCIONES DE LA BASE DE DATOS (CRUD de tasks y registro de moods) */
public class DatabaseManager {

    //
    //
    // TASKS

    //Inserta una nueva tarea en la base de datos
    public void addTask(String name, String desc, String status, String type, LocalDate dueDate) {

        String sql = "INSERT INTO tasks (task_name, task_description, task_status, task_type, due_date) " +
                     "VALUES (?, ?, ?, ?, ?)";

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

    //Actualiza todos los campos de una tarea existente
    public void updateTask(int id, String name, String desc, String status, String type, LocalDate dueDate) {

        String sql = "UPDATE tasks SET task_name = ?, task_description = ?, task_status = ?, task_type = ?, due_date = ? " +
                     "WHERE id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, desc);
            stmt.setString(3, status);
            stmt.setString(4, type);
            stmt.setDate(5, Date.valueOf(dueDate));
            stmt.setInt(6, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Carga todas las tareas y las agrega a la tabla (DefaultTableModel)
    public void loadTasks(DefaultTableModel model) {

        String sql = "SELECT * FROM tasks";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            //Recorre los resultados y arma cada fila
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

    //
    //
    // MOODTRACKER

    //Guarda un nuevo estado de animo con timestamp actual
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

    //Obtiene todos los moods ordenados por fecha
    public List<MoodTrackerFunc.MoodEntry> getMoods() {

        List<MoodTrackerFunc.MoodEntry> list = new ArrayList<>();

        String sql = "SELECT id, mood, mood_time FROM moodtracker ORDER BY mood_time ASC";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            //Convierte cada fila en un objeto MoodEntry
            while (rs.next()) {

                int id = rs.getInt("id");
                String mood = rs.getString("mood");
                LocalDateTime time = rs.getTimestamp("mood_time").toLocalDateTime();

                list.add(new MoodTrackerFunc.MoodEntry(id, time, mood));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    //
    //
    //Journal
    public static void logEntry(String entryTitle,String entryContent,LocalDate entryDate) {
    	
    	String sql = "INSERT INTO journal (title, content, entry_date) VALUES (?, ?, ?)";
    	
    	try (Connection conn = DatabaseConnector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

    		java.util.Date utilDate = new java.util.Date();
    		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
    		
               stmt.setString(1, entryTitle);
               stmt.setString(2, entryContent);
               stmt.setDate(3, sqlDate);

               stmt.executeUpdate();

           } catch (SQLException e) {
               e.printStackTrace();
           }
    	
    }
}