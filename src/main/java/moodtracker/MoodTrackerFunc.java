package moodtracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MoodTrackerFunc {

    // Día → lista de registros
    private Map<String, List<String>> moodByDay;

    public MoodTrackerFunc() {
        moodByDay = new LinkedHashMap<>();
    }

    public void addMood(String mood) {
        LocalDateTime now = LocalDateTime.now();

        String day = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm"));

        String entry = time + " - " + mood;

        moodByDay.putIfAbsent(day, new ArrayList<>());
        moodByDay.get(day).add(entry);
    }

    public Map<String, List<String>> getMoodByDay() {
        return moodByDay;
    }
}