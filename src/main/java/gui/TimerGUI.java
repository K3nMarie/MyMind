package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import timer.TimerController;

public class TimerGUI extends JPanel {
/////////////
	
private JLabel label;
private JButton startButton;
private JTextField horasInput;

private TimerController controller;

private CircularProgressBar progressBar;
private int tiempoInicial;

private JButton backButton; // Boton para regresar

// Callback para regresar al panel principal
private Runnable onBack;

public TimerGUI(Runnable onBack) {
    this.onBack = onBack;

    //Estilos
    JPanel panel = this;
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(new EmptyBorder(20, 20, 20, 20));

    progressBar = new CircularProgressBar();
    progressBar.setPreferredSize(new Dimension(150, 150));
    progressBar.setMaximumSize(new Dimension(150, 150));
    progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

    label = new JLabel("00:00");
    label.setFont(new Font("Segoe UI", Font.BOLD, 40));
    label.setAlignmentX(Component.CENTER_ALIGNMENT);

    horasInput = new JTextField(5);
    horasInput.setMaximumSize(new Dimension(100, 30));
    horasInput.setText("1");
    horasInput.setHorizontalAlignment(JTextField.CENTER);

    startButton = new JButton("Iniciar");
    startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel inputLabel = new JLabel("Horas de estudio:");
    inputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Boton para regresar al panel principal
    backButton = new JButton("Regresar");
    backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    backButton.addActionListener(e -> {
        if (onBack != null) onBack.run();
    });

    panel.add(progressBar);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(0, 20)));
    panel.add(inputLabel);
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(horasInput);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));
    panel.add(startButton);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));
    panel.add(backButton);

    // Crear controlador
    controller = new TimerController(e -> actualizarVista());

    startButton.addActionListener(e -> iniciarTimer());
}

private void iniciarTimer() {
    int horas = Integer.parseInt(horasInput.getText());
    controller.iniciar(horas);

    tiempoInicial = horas * 3600;
}

private void actualizarVista() {
    int tiempo = controller.getTiempoRestante();

    int minutos = tiempo / 60;
    int segundos = tiempo % 60;

    label.setText(String.format("%02d:%02d", minutos, segundos));

    // Eventos visuales
    if (tiempoInicial > 0) {
        int progreso = (int) ((1 - (tiempo / (double) tiempoInicial)) * 100);
        progressBar.setProgress(progreso);
    }

    if (controller.isEnDescanso()) {
        JOptionPane.showMessageDialog(this,
                "Descansa 5 minutos");
    }

    if (!controller.isEnDescanso() && tiempo == 30 * 60) {
        JOptionPane.showMessageDialog(this,
                "Vuelve a estudiar");
    }

    if (controller.isTerminado()) {
        JOptionPane.showMessageDialog(this,
                "Has terminado tu tiempo de estudio");
    }
}

class CircularProgressBar extends JPanel {
    private int progress = 0;

    public void setProgress(int progress) {
        this.progress = progress;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight()) - 20;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        // Background
        g2.setColor(new Color(220, 220, 220));
        g2.setStroke(new BasicStroke(10));
        g2.drawOval(x, y, size, size);

        // Progress
        g2.setColor(new Color(100, 150, 255));
        int angle = (int) (360 * (progress / 100.0));
        g2.drawArc(x, y, size, size, 90, -angle);
    }
}
}