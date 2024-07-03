package Projekt;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import jserver.XSendAdapter;
import jserver.Board;

/**
 * Die Klasse Spielfeld verwaltet die Hauptlogik des Spiels 2048.
 * Sie beinhaltet Methoden zum Initialisieren des Spielfelds, Bewegen der Kacheln
 * und Überprüfen von Gewinn- und Verlustbedingungen
 */

public class Spielfeld implements KeyListener {
    public Board board = new Board();
    public XSendAdapter xsend = new XSendAdapter(board);
    private int groesse = 4;  
    private Kacheln[][] kacheln = new Kacheln[groesse][groesse]; // Array zum Speichern der Kacheln
    private GUI gui;

    public void start() {
        baueSpielfeld();
        //setzeTestSpielstand();  //Dies ist ein Spielstand der zu Testzwecken kurz vor dem Erreichen von 2048 ist. 
    }

    public void setGUI(GUI gui) { 
        this.gui = gui;
    }

    private void baueSpielfeld() {
        board.getPlotter().addKeyListener(this);
        board.getPlotter().setFocusable(true);
        board.getPlotter().requestFocusInWindow();
        xsend.groesse(groesse, groesse); 
        gui.setzeGUI();
        initialisiereKacheln();
    }

    
    
    // Initialisiert das Spielfeld, indem alle Kacheln auf 0 gesetzt werden
    // und fügt dann zwei zufällige Kacheln hinzu.
    
    public void initialisiereKacheln() {
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                kacheln[i][j] = new Kacheln(i, j, 0);
                setzeKacheln(kacheln[i][j]);
            }
        }
        addRandomKachel();
        addRandomKachel(); 
    }

    private void addRandomKachel() {
        List<int[]> emptySpaces = new ArrayList<>();
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                if (kacheln[i][j].wert == 0) {
                    emptySpaces.add(new int[]{i, j});
                }
            }
        }

        if (!emptySpaces.isEmpty()) {
            int[] position = emptySpaces.get((int) (Math.random() * emptySpaces.size()));
            int value = Math.random() < 0.9 ? 2 : 4; // Wahrscheinlichkeiten: 90% für 2, 10% für 4 
            kacheln[position[0]][position[1]] = new Kacheln(position[0], position[1], value);
            setzeKacheln(kacheln[position[0]][position[1]]);
        }
    }

    private void setzeKacheln(Kacheln tile) {
        tile.setFarben(tile.farben);
        xsend.farbe2(tile.x, tile.y, tile.farbe);
        xsend.text2(tile.x, tile.y, tile.getText());
        xsend.textFarbe2(tile.x, tile.y, tile.textFarbe);
        xsend.form2(tile.x, tile.y, tile.form);
    }

    public void updateSpielfeld() {
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                setzeKacheln(kacheln[i][j]);
            }
        }
    }

    private boolean gewonnen() {
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                if (kacheln[i][j].wert == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean verloren() {
        boolean kachelFrei = true;
        boolean vertKombi = true;
        boolean horzKombi = true;
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                if (kacheln[i][j].wert == 0) {
                    kachelFrei = false;
                }
                if (i < groesse - 1 && kacheln[i][j].wert == kacheln[i + 1][j].wert) {
                    vertKombi = false;
                }
                if (j < groesse - 1 && kacheln[i][j].wert == kacheln[i][j + 1].wert) {
                    horzKombi = false;
                }
            }
        }
        return kachelFrei && vertKombi && horzKombi;
    }

    private boolean hatBewegungStattgefunden(Kacheln[][] vorher, Kacheln[][] nachher) {
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                if (vorher[i][j].wert != nachher[i][j].wert) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    //Testspielstand kurz vor dem Gewinnen
    public void setzeTestSpielstand() {
        int[][] testWerte = {
            {0, 0, 0, 0},
            {4, 2, 2, 0},
            {8, 16, 32, 64},
            {1024, 512, 256, 128}
        };
        
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                kacheln[i][j] = new Kacheln(i, j, testWerte[i][j]);
                setzeKacheln(kacheln[i][j]);
            }
        }
    }
    
    

    @Override
    public void keyPressed(KeyEvent e) {
        Kacheln[][] vorher = new Kacheln[groesse][groesse];
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                vorher[i][j] = new Kacheln(i, j, kacheln[i][j].wert);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            bewegeLinks();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            bewegeRechts();
        } else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            bewegeHoch();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            bewegeRunter();
        }
        if (hatBewegungStattgefunden(vorher, kacheln)) {
            addRandomKachel();
        }
        updateSpielfeld();
        if (gewonnen()) {
            xsend.statusText("Gewonnen!");
            board.getPlotter().setFocusable(false);
        }
        if (verloren()) {
            xsend.statusText("Verloren!");
            board.getPlotter().setFocusable(false);
        }
    }

    private void bewegeRunter() {
        for (int i = 0; i < groesse; i++) {
            int[] neueZeile = new int[groesse];
            int index = 0;
            boolean merged = false;

            for (int j = 0; j < groesse; j++) {
                if (kacheln[i][j].wert != 0) {
                    if (index > 0 && neueZeile[index - 1] == kacheln[i][j].wert && !merged) {
                        neueZeile[index - 1] *= 2;
                        merged = true;
                    } else {
                        neueZeile[index] = kacheln[i][j].wert;
                        index++;
                        merged = false;
                    }
                }
            }

            for (int j = 0; j < groesse; j++) {
                kacheln[i][j].wert = neueZeile[j];
            }
        }
    }

    private void bewegeHoch() {
        for (int i = 0; i < groesse; i++) {
            int[] neueZeile = new int[groesse];
            int index = groesse - 1;
            boolean merged = false;

            for (int j = groesse - 1; j >= 0; j--) {
                if (kacheln[i][j].wert != 0) {
                    if (index < groesse - 1 && neueZeile[index + 1] == kacheln[i][j].wert && !merged) {
                        neueZeile[index + 1] *= 2;
                        merged = true;
                    } else {
                        neueZeile[index] = kacheln[i][j].wert;
                        index--;
                        merged = false;
                    }
                }
            }

            for (int j = 0; j < groesse; j++) {
                kacheln[i][j].wert = neueZeile[j];
            }
        }
    }

    private void bewegeLinks() {
        for (int j = 0; j < groesse; j++) {
            int[] neueSpalte = new int[groesse];
            int index = 0;
            boolean merged = false;

            for (int i = 0; i < groesse; i++) {
                if (kacheln[i][j].wert != 0) {
                	if (index > 0 && neueSpalte[index - 1] == kacheln[i][j].wert && !merged) {
                        neueSpalte[index - 1] *= 2;
                        merged = true;
                    } else {
                        neueSpalte[index] = kacheln[i][j].wert;
                        index++;
                        merged = false;
                    }
                }
            }

            for (int i = 0; i < groesse; i++) {
                kacheln[i][j].wert = neueSpalte[i];
            }
        }
    }

    private void bewegeRechts() {
        for (int j = 0; j < groesse; j++) {
            int[] neueSpalte = new int[groesse];
            int index = groesse - 1;
            boolean merged = false;

            for (int i = groesse - 1; i >= 0; i--) {
                if (kacheln[i][j].wert != 0) {
                    if (index < groesse - 1 && neueSpalte[index + 1] == kacheln[i][j].wert && !merged) {
                        neueSpalte[index + 1] *= 2;
                        merged = true;
                    } else {
                        neueSpalte[index] = kacheln[i][j].wert;
                        index--;
                        merged = false;
                    }
                }
            }

            for (int i = 0; i < groesse; i++) {
                kacheln[i][j].wert = neueSpalte[i];
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Nicht genutzt
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Nicht genutzt
    }
}

                
