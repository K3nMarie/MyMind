package timer;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TimerController {

    private Timer timer;

    private int tiempoRestante;
    private int tiempoTotal;

    private boolean enDescanso = false;

    private final int BLOQUE_ESTUDIO = 30 * 60; // esta es la parte de el tiempo de estudio, toma un break cada 30 minutos  
    private final int DESCANSO = 5 * 60; // lo mismo de arriba pero aplicado a decanso.

    private ActionListener callback; // callback esta hecha para poder comunicar esto con lo que se encuentra en el gui

    public TimerController(ActionListener callback) {
        this.callback = callback;

        timer = new Timer(1000, e -> tick());
    }

    public void iniciar(int horas) {

        if (timer.isRunning()) {
            timer.stop(); // evita multiples timers
        }

        tiempoTotal = horas * 3600; 
        tiempoRestante = BLOQUE_ESTUDIO;
        enDescanso = false;

        timer.start();
    }

    private void tick() {
        tiempoRestante--; 
        tiempoTotal--;

        // Notificar a la GUI (mejor que null)
        callback.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "tick"));

        if (tiempoRestante <= 0) {

            if (!enDescanso) {
                enDescanso = true;
                tiempoRestante = DESCANSO;

            } else {
                enDescanso = false;

                if (tiempoTotal < BLOQUE_ESTUDIO) {
                    tiempoRestante = tiempoTotal;
                } else {
                    tiempoRestante = BLOQUE_ESTUDIO;
                }
            }
        }

        if (tiempoTotal <= 0) {
            timer.stop();
        }
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public int getTiempoTotal() {
        return tiempoTotal;
    }

    public boolean isEnDescanso() {
        return enDescanso;
    }

    public boolean isTerminado() {
        return tiempoTotal <= 0;
    }
}