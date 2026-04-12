package gui;

import javax.swing.*;
import java.awt.*;

import moodtracker.MoodTrackerFunc;

public class MoodTrackerGUI extends JPanel {

    private JTextField moodInput;
    private JButton saveButton;
    private JButton backButton;

    private JPanel cardsPanel;

    private MoodTrackerFunc moodFunc;
    private Runnable onBack;

    public MoodTrackerGUI(Runnable onBack) {
        this.onBack = onBack;
        this.moodFunc = new MoodTrackerFunc();

        setLayout(new BorderLayout());
        createUI();
    }

    private void createUI() {

        // 🔝 Panel superior (input)
        JPanel topPanel = new JPanel(new FlowLayout());

        moodInput = new JTextField(15);
        saveButton = new JButton("Guardar mood");
        backButton = new JButton("Volver");

        topPanel.add(new JLabel("Mood:"));
        topPanel.add(moodInput);
        topPanel.add(saveButton);
        topPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);

        // 📦 Panel de cartas
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(cardsPanel);
        add(scroll, BorderLayout.CENTER);

        // 🔘 Eventos
        saveButton.addActionListener(e -> {
            String mood = moodInput.getText();

            if (!mood.trim().isEmpty()) {
                moodFunc.addMood(mood);
                moodInput.setText("");
                moodInput.requestFocus(); // UX improvement
                refreshCards();
            }
        });

        backButton.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });
    }

    // 🔄 Refrescar UI
    private void refreshCards() {
        cardsPanel.removeAll();

        java.util.List<MoodTrackerFunc.MoodEntry> data = moodFunc.getMoods();

        // Lista de días ya mostrados
        java.util.List<java.time.LocalDate> shownDays = new java.util.ArrayList<>();

        // Mostrar desde el más reciente
        for (int i = data.size() - 1; i >= 0; i--) {
            MoodTrackerFunc.MoodEntry entry = data.get(i);

            java.time.LocalDate date = entry.getTimestamp().toLocalDate();

            // Si el día no se ha mostrado aún
            if (!shownDays.contains(date)) {
                shownDays.add(date);

                // Crear lista de moods para ese día
                java.util.List<MoodTrackerFunc.MoodEntry> dayMoods = new java.util.ArrayList<>();

                for (int j = data.size() - 1; j >= 0; j--) {
                    MoodTrackerFunc.MoodEntry e = data.get(j);

                    if (e.getTimestamp().toLocalDate().equals(date)) {
                        dayMoods.add(e);
                    }
                }

                cardsPanel.add(createDayCard(date, dayMoods));
            }
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    // 🧾 Crear carta por día
    private JPanel createDayCard(
            java.time.LocalDate date,
            java.util.List<MoodTrackerFunc.MoodEntry> moods
    ) {

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JLabel title = new JLabel(date.toString());
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel moodList = new JPanel();
        moodList.setLayout(new BoxLayout(moodList, BoxLayout.Y_AXIS));

        java.time.format.DateTimeFormatter formatter =
                java.time.format.DateTimeFormatter.ofPattern("HH:mm");

        for (MoodTrackerFunc.MoodEntry entry : moods) {
            String time = entry.getTimestamp().format(formatter);
            moodList.add(new JLabel("• " + time + " - " + entry.getMood()));
        }

        card.add(title, BorderLayout.NORTH);
        card.add(moodList, BorderLayout.CENTER);

        return card;
    }
}