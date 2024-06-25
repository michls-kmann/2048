package basic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import jserver.Board;
import jserver.XSendAdapter;

public class Spiel implements KeyListener {
    private Board board = new Board();
    private XSendAdapter xsend = new XSendAdapter(board);
    private int size = 4;  
    private Kacheln[][] kacheln = new Kacheln[size][size]; // Array zum Speichern der Kacheln

    public static void main(String[] args) {
        new Spiel().start();
    }

    private void start() {
        setUpBoard();
        
    }

    private void setUpBoard() {
        board.getPlotter().addKeyListener(this);
        board.getPlotter().setFocusable(true);
        board.getPlotter().requestFocusInWindow();
        xsend.flaeche(0xbbada0); 
        xsend.groesse(size, size); 
        xsend.symbolGroessen(0.46);
        board.receiveMessage(">>fontsize 45");
        board.receiveMessage(">>fonttype Arial Bold");
        initializeKacheln();
    }

    private void initializeKacheln() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                kacheln[i][j] = new Kacheln(i, j, 0); // Initialisiert alle Felder als leer
                updateKachelBoard(kacheln[i][j]);
            }
        }
        addRandomKachel(); // Füge eine zufällige Kachel am Anfang hinzu
        addRandomKachel(); // Zwei Kacheln zu Beginn
    }

    private void addRandomKachel() {
        java.util.List<int[]> emptySpaces = new java.util.ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (kacheln[i][j].wert == 0) {
                    emptySpaces.add(new int[]{i, j});
                }
            }
        }

        if (!emptySpaces.isEmpty()) {
            int[] position = emptySpaces.get((int) (Math.random() * emptySpaces.size()));
            int value = Math.random() < 0.9 ? 2 : 4; //Wahrscheinlichkeiten: 90% für 2, 10% für 4 
            kacheln[position[0]][position[1]] = new Kacheln(position[0], position[1], value);
            updateKachelBoard(kacheln[position[0]][position[1]]);
        }
    }

    private void updateKachelBoard(Kacheln tile) {
        xsend.farbe2(tile.x, tile.y, tile.farbe);
        xsend.text2(tile.x, tile.y, tile.getText());
        xsend.textFarbe2(tile.x, tile.y, tile.textFarbe);
        xsend.form2(tile.x, tile.y, tile.form);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Nicht genutzt
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
//        switch (e.getKeyCode()) {
//            case KeyEvent.VK_LEFT:
//                moveLeft();
//                break;
//            case KeyEvent.VK_RIGHT:
//                moveRight();
//                break;
//            case KeyEvent.VK_UP:
//                moveUp();
//                break;
//            case KeyEvent.VK_DOWN:
//                moveDown();
//                break;
//        }
    }
    

    
    @Override
    public void keyReleased(KeyEvent e) {
        // Nicht genutzt
    }
  
}


