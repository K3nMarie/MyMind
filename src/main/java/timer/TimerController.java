package timer;


import javax.swing.Timer;
import java.awt.event.ActionListener;

public class TimerController {

    private Timer timer;

    private int tiempoRestante;
    private int tiempoTotal;

    private boolean enDescanso = false;

    private final int BLOQUE_ESTUDIO = 30 * 60; //esta es la parte de el tiempo de estudio, toma un break cada 30 minutos  
    private final int DESCANSO = 5 * 60; // lo mismo de arriba pero aplicado a decanso.

    private ActionListener callback; // callback esta hecha para poder comunicar esto con lo que se encuentra en el gui

    public TimerController(ActionListener callback) {
        this.callback = callback;

        timer = new Timer(1000, e -> tick());
    }

    public void iniciar(int horas) {
        tiempoTotal = horas * 3600; // esta parte es  la cantidad de segundos que tiene una hora, se hace asi por que el timer funciona con segundos 
        tiempoRestante = BLOQUE_ESTUDIO;
        enDescanso = false;

        timer.start();
    }

    private void tick() {
        tiempoRestante--; /// esta parte es la que permite que en el gui el clock valle al revez (no se como explicarlo).
        tiempoTotal--;// lo mismo de arriba

        // Notificar a la GUI
        callback.actionPerformed(null);

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

    public boolean isEnDescanso() {
        return enDescanso;
    }

    public boolean isTerminado() {
        return tiempoTotal <= 0;
    }
}
