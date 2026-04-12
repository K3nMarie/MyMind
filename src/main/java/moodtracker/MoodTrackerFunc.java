package moodtracker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MoodTrackerFunc {

    // 🔹 Inner class (represents a DB row)
    public static class MoodEntry {
        private int id;
        private LocalDateTime timestamp;
        private String mood;

        public MoodEntry(int id, LocalDateTime timestamp, String mood) {
            this.id = id;
            this.timestamp = timestamp;
            this.mood = mood;
        }

        public int getId() { return id; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public String getMood() { return mood; }
    }

    // 🔹 Store entries properly (not Map<String, List<String>> anymore)
    private List<MoodEntry> moods;
    private int nextId;

    public MoodTrackerFunc() {
        moods = new ArrayList<>();
        nextId = 1;
    }

    // ✅ Add mood (structured, not String formatting)
    public void addMood(String mood) {
        MoodEntry entry = new MoodEntry(
            nextId++,
            LocalDateTime.now(),
            mood
        );

        moods.add(entry);
    }

    // ✅ Return structured data
    public List<MoodEntry> getMoods() {
        return moods;
    }
}