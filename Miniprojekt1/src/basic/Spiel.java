package basic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;

import jserver.Board;
import jserver.XSendAdapter;
import plotter.Graphic;

public class Spiel implements KeyListener {
    private Board board = new Board();
    private XSendAdapter xsend = new XSendAdapter(board);
    private int groesse = 4;  
    private Kacheln[][] kacheln = new Kacheln[groesse][groesse]; // Array zum Speichern der Kacheln
	private Graphic graphic;
    

    public static void main(String[] args) {
        new Spiel().start();
    }

    private void start() {
    	
    		
        	baueSpielfeld();
        	
    		
        }
  
      

	
	    private void baueSpielfeld() {
        board.getPlotter().addKeyListener(this);
        board.getPlotter().setFocusable(true);
        board.getPlotter().requestFocusInWindow();
        xsend.flaeche(0xbbada0); 
        xsend.groesse(groesse, groesse); 
        xsend.symbolGroessen(0.46);
        xsend.statusText("2048");
        board.receiveMessage(">>fontsize 45");
        board.receiveMessage(">>fonttype Arial Bold");
        initialisiereKacheln();
        
    }
    

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
        java.util.List<int[]> emptySpaces = new java.util.ArrayList<>();
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                if (kacheln[i][j].wert == 0) {
                    emptySpaces.add(new int[]{i, j});
                }
            }
        }

        if (!emptySpaces.isEmpty()) {
            int[] position = emptySpaces.get((int) (Math.random() * emptySpaces.size()));
            int value = Math.random() < 0.9 ? 2 : 4; //Wahrscheinlichkeiten: 90% für 2, 10% für 4 
            kacheln[position[0]][position[1]] = new Kacheln(position[0], position[1], value);
            setzeKacheln(kacheln[position[0]][position[1]]);
        }
    }

    private void setzeKacheln(Kacheln tile) {
        tile.setFarben();
        xsend.farbe2(tile.x, tile.y, tile.farbe);
        xsend.text2(tile.x, tile.y, tile.getText());
        xsend.textFarbe2(tile.x, tile.y, tile.textFarbe);
        xsend.form2(tile.x, tile.y, tile.form);
    }
    
    private void updateSpielfeld() {
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                setzeKacheln(kacheln[i][j]);
            }
        }
    }
    
    private boolean gewonnen(){
    	boolean sieg = false;
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
            	if (kacheln[i][j].wert == 2048) {
            		sieg = true;
            	}
            }
        }
        return sieg;
    }
        
    private boolean verloren() {
    	boolean kachelFrei = true;
    	boolean vertKombi = true;
    	boolean horzKombi = true;
    	boolean gameOver = false;
        for (int i = 0; i < groesse; i++) {
            for (int j = 0; j < groesse; j++) {
                if (kacheln[i][j].wert == 0) {
                    kachelFrei = false; // Es gibt noch leere Felder
                }
                if (i < groesse - 1 && kacheln[i][j].wert == kacheln[i + 1][j].wert) {
                    vertKombi = false; // Es gibt noch mögliche vertikale Kombinationen
                }
                if (j < groesse - 1 && kacheln[i][j].wert == kacheln[i][j + 1].wert) {
                    horzKombi = false; // Es gibt noch mögliche horizontale Kombinationen
                }
            }
            
        }
        if(kachelFrei == true && vertKombi == true && horzKombi == true ) {
        	gameOver = true;
        }
   
        return gameOver; // Kein Zug mehr möglich
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            	bewegeLinks();
                break;
            case KeyEvent.VK_RIGHT:
            	bewegeRechts();
                break;
            case KeyEvent.VK_UP:
            	bewegeHoch();                
                break;
            case KeyEvent.VK_DOWN:                
                bewegeRunter();
                break;
        }
        addRandomKachel(); // Füge nach jedem Zug eine neue Kachel hinzu
        updateSpielfeld();
        if (gewonnen()) {
        	xsend.statusText("Gewonnen");
        	board.getPlotter().setFocusable(false);
        }
        if (verloren()) {
        	xsend.statusText("Verloren");
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}
