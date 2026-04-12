package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

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

        // Eventos
        saveButton.addActionListener(e -> {
            String mood = moodInput.getText();

            if (!mood.isEmpty()) {
                moodFunc.addMood(mood);
                moodInput.setText("");
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

        Map<String, List<String>> data = moodFunc.getMoodByDay();

        for (String day : data.keySet()) {
            cardsPanel.add(createDayCard(day, data.get(day)));
        }

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    // 🧾 Crear carta por día
    private JPanel createDayCard(String day, List<String> moods) {

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JLabel title = new JLabel(day);
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel moodList = new JPanel();
        moodList.setLayout(new BoxLayout(moodList, BoxLayout.Y_AXIS));

        for (String m : moods) {
            moodList.add(new JLabel("• " + m));
        }

        card.add(title, BorderLayout.NORTH);
        card.add(moodList, BorderLayout.CENTER);

        return card;
    }
}