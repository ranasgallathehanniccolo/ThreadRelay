/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package threadrelay;

/**
 *
 * @author Windows
 */
public enum Velocita {
    LENTO(120, "Lento"),
    NORMALE(60, "Normale"),
    VELOCE(20, "Veloce");

    private final int delayMs;
    private final String label;

    Velocita(int delayMs,String label) {
        this.delayMs = delayMs;
        this.label = label;
    }

    public int getDelayMs() {
        return delayMs;
    }

    @Override
    public String toString() {
        return label;
    }
}
