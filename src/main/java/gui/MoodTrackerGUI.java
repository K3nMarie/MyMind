package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.time.LocalDate;
import java.util.*;

import moodtracker.MoodTrackerFunc;

public class MoodTrackerGUI extends JPanel {

    private JTextField moodInput;
    private JButton saveButton;
    private JButton backButton;

    private JPanel cardsPanel;

    private MoodTrackerFunc moodFunc;
    private Runnable onBack;

    //Constructor (inicializa la logica y carga los datos)
    public MoodTrackerGUI(Runnable onBack) {

        this.onBack = onBack;
        this.moodFunc = new MoodTrackerFunc();

        setLayout(new BorderLayout());
        createUI();
        refreshCards(); //carga los datos desde la DB al iniciar
    }

    //Crea toda la interfaz del mood tracker
    private void createUI() {

        //TOP PANEL (input + botones)
        JPanel topPanel = new JPanel(new FlowLayout());

        moodInput = new JTextField(15);
        saveButton = new JButton("Guardar mood");
        backButton = new JButton("Volver");

        topPanel.add(new JLabel("Mood:"));
        topPanel.add(moodInput);
        topPanel.add(saveButton);
        topPanel.add(backButton);

        add(topPanel, BorderLayout.NORTH);

        //CARDS PANEL (lista vertical de dias)
        cardsPanel = new JPanel();
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(cardsPanel);
        add(scroll, BorderLayout.CENTER);

        //EVENTOS
        saveButton.addActionListener(e -> {

            String mood = moodInput.getText();

            //Evita guardar strings vacios
            if (!mood.trim().isEmpty()) {
                moodFunc.addMood(mood);
                moodInput.setText("");
                moodInput.requestFocus();
                refreshCards(); //actualiza la UI
            }
        });

        backButton.addActionListener(e -> {
            if (onBack != null) onBack.run();
        });
    }

    //REFRESH UI FROM DB (reconstruye las cards)
    private void refreshCards() {

        cardsPanel.removeAll();

        //Obtiene todos los moods
        List<MoodTrackerFunc.MoodEntry> data = moodFunc.getMoods();

        //Lista para evitar repetir dias
        List<LocalDate> shownDays = new ArrayList<>();

        //Recorre en orden inverso (mas recientes primero)
        for (int i = data.size() - 1; i >= 0; i--) {

            MoodTrackerFunc.MoodEntry entry = data.get(i);
            LocalDate date = entry.getTimestamp().toLocalDate();

            if (!shownDays.contains(date)) {

                shownDays.add(date);

                List<MoodTrackerFunc.MoodEntry> dayMoods = new ArrayList<>();

                //Agrupa todos los moods del mismo dia
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

    //CREATE CARD PER DAY (una tarjeta por fecha)
    private JPanel createDayCard(LocalDate date, List<MoodTrackerFunc.MoodEntry> moods) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel title = new JLabel(date.toString());
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        java.time.format.DateTimeFormatter formatter =
                java.time.format.DateTimeFormatter.ofPattern("HH:mm");

        //Lista de moods con hora
        for (MoodTrackerFunc.MoodEntry entry : moods) {

            String time = entry.getTimestamp().format(formatter);
            list.add(new JLabel("• " + time + " - " + entry.getMood()));
        }

        card.add(title, BorderLayout.NORTH);
        card.add(list, BorderLayout.CENTER);

        return card;
    }
}
