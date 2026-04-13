/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threadrelay;

/**
 *
 * @author Windows
 */
public class Corridore implements Runnable {

    private final int id;
    private final int speed;
    private final Corridore prossimoCorridore;
    private boolean paused = false;
    private boolean stopped = false;
    private boolean canStart = false;
    private boolean finished = false;
    private int count = 0;

    public interface Ascoltatore {

        void Aggiorna(int id, int count);

        void Fine(int id);
    }

    private Ascoltatore ascoltatore;

    public Corridore(int id, int speed, Corridore prossimoCorridore) {
        this.id = id;
        this.speed = speed;
        this.prossimoCorridore = prossimoCorridore;
    }

    public void setAscoltatore(Ascoltatore ascoltatore) {
        this.ascoltatore = ascoltatore;
    }

    public synchronized void allowStart() {
        canStart = true;
        notifyAll();
    }

    public synchronized boolean isFinished() {
        return finished;
    }

    public synchronized void pause() {
        paused = true;
    }

    public synchronized void resume() {
        paused = false;
        notifyAll();
    }

    public synchronized void stop() {
        stopped = true;
        paused = false;
        notifyAll();
    }

    @Override
    public void run() {

        // Aspetta il via
        synchronized (this) {
            while (!canStart) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        // Corsa da 0 a 99
        for (count = 0; count <= 99; count++) {

            if (ascoltatore != null) {
                ascoltatore.Aggiorna(id, count);
            }
            synchronized (this) {
                while (paused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                if (stopped) {
                    return;
                }
            }

            if (count == 90 && prossimoCorridore != null) {
                prossimoCorridore.allowStart();
            }

            synchronized (this) {
                try {
                    wait(speed);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        // Fine corsa
        synchronized (this) {
            finished = true;
            notifyAll();
        }

        if (ascoltatore != null) {
            ascoltatore.Fine(id);
        }
    }
}
