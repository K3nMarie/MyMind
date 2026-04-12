package database;

import java.sql.*;
import java.util.*;

/* MANEJA LAS FUNCIONES DE LA BASE DE DATO*/

public class DatabaseManager {

    // =========================
    // TASKS
    // =========================

    public static List<String> getTasksByDate(String fecha) {
        List<String> tareas = new ArrayList<>();

        String sql = "SELECT titulo FROM tareas WHERE due_date = ?";

        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, fecha);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tareas.add(rs.getString("titulo"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tareas;
    }

    public static List<Object[]> getAllTasks() {
        List<Object[]> list = new ArrayList<>();

        try (Connection con = DatabaseConnector.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM tareas")) {

            while (rs.next()) {
                list.add(new Object[] {
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descripcion"),
                    rs.getString("due_date"),
                    rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void deleteTask(int id) {
        String sql = "DELETE FROM tareas WHERE id = ?";

        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // MOOD TRACKER
    // =========================

    public static void saveMood(String mood, String date) {
        String sql = "INSERT INTO moodtracker (mood, date) VALUES (?, ?)";

        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mood);
            ps.setString(2, date);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getMoodByDate(String date) {
        String sql = "SELECT mood FROM moodtracker WHERE date = ?";

        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, date);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("mood");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    // =========================
    // JOURNAL
    // =========================

    public static void saveJournal(String title, String content, String date) {
        String sql = "INSERT INTO journal (title, content, date) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, content);
            ps.setString(3, date);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getJournalByDate(String date) {
        String sql = "SELECT content FROM journal WHERE date = ?";

        try (Connection con = DatabaseConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, date);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("content");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}