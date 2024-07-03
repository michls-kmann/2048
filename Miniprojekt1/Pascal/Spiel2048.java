/**
 * SWE2-Mini-Projekt: "2048" (Computerspiel)
 *
 * Beschreibung: 
 * 2048 ist ein beliebtes Puzzlespiel, bei dem das Ziel darin besteht, Kacheln mit der gleichen Zahl 
 * zu kombinieren, um die Kachel mit dem Wert 2048 zu erreichen.
 * 
 * Autoren: Pascal Bastiné & Michael Kaufmann 
 * Datum: 02.07.2024
 * Version: 1.0
 */

package Projekt;  

/**
 * Hauptklasse für das 2048 Spiel. 
 * Initialisiert das Spielfeld und die GUI und startet das Spiel.
 */
public class Spiel2048 {
    public static void main(String[] args) {
        Spielfeld spielfeld = new Spielfeld();
        GUI gui = new GUI(spielfeld);
        spielfeld.setGUI(gui);
        gui.setzeBoardDesign();
        spielfeld.start();
    }
}
