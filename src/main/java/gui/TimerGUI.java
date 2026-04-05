package gui;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import timer.TimerController;

public class TimerGUI extends JFrame {
/////////////

private JLabel label;
    private JButton startButton;
    private JTextField horasInput;

    private TimerController controller;

    public TimerGUI() {
        setTitle("Timer de Estudio");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        label = new JLabel("00:00");
        label.setFont(new Font("Arial", Font.BOLD, 30));

        horasInput = new JTextField(5);
        horasInput.setText("1");

        startButton = new JButton("Iniciar");

        add(new JLabel("Horas de estudio:"));
        add(horasInput);
        add(startButton);
        add(label);

        // Crear controlador
        controller = new TimerController(e -> actualizarVista());

        startButton.addActionListener(e -> iniciarTimer());
    }

    private void iniciarTimer() {
        int horas = Integer.parseInt(horasInput.getText());
        controller.iniciar(horas);
    }

    private void actualizarVista() {
        int tiempo = controller.getTiempoRestante();

        int minutos = tiempo / 60;
        int segundos = tiempo % 60;

        label.setText(String.format("%02d:%02d", minutos, segundos));

        // Eventos visuales
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

    public static void main(String[] args) {
        new TimerGUI().setVisible(true);
    }
    
}