package Projekt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import jserver.Board;
import jserver.XSendAdapter;
import plotter.Graphic;

/**
* Die GUI-Klasse ist für die Gestaltung Benutzeroberfläche verantwortlich.
* Sie setzt das allgemeine Design des Spielfeldes, 
* fügt Schaltflächen und Menüs für die Spielsteuerung hinzu, 
* ermöglicht die Auswahl verschiedener Farbpaletten und 
* zeigt Spielanleitungen sowie Strategietipps in Dialogfenstern an.
*/

public class GUI {
    private Board board;
    private XSendAdapter xsend;
    private Spielfeld spielfeld;
    private Graphic graphic;

    public GUI(Spielfeld spielfeld) {
        this.spielfeld = spielfeld;
        this.board = spielfeld.board;
        this.xsend = spielfeld.xsend;
        this.graphic = board.getGraphic();  
    }
    
 
    public void setzeBoardDesign() {
    	board.setSize(650, 555); //Breite / Höhe
    	xsend.flaeche(0xbbada0); 
        xsend.rahmen(0x776e65);
        xsend.symbolGroessen(0.45);
        board.receiveMessage("fontsize 37"); //jedoch nur für ALLE Felder im Board möglich, nicht einzeln variabel 
        board.receiveMessage("fonttype Arial Bold");
        xsend.statusText("2048");
        board.receiveMessage("statusfontsize 34");
        //board.receiveMessage("statuscolor 0x776e65");
        board.receiveMessage("graphicBorder 0xe0d9d1");
        board.receiveMessage("borderWidth 5");
    }
    
    public void setzeGUI() {
    	//NEUSTART-Knopf
        JButton resetButton = new JButton("NEUSTART");
        graphic.addSouthComponent(resetButton);

        resetButton.addActionListener(new ActionListener() {
            @Override
            
            public void actionPerformed(ActionEvent e) {
            	board.getPlotter().setFocusable(true);
                board.getPlotter().requestFocusInWindow();
                spielfeld.initialisiereKacheln();
                xsend.statusText("2048");
            }
        });
        
        
        //Farbpaletten
        JLabel label = new JLabel("Farbpalette auswählen:");
        JPanel panel = new JPanel();
        panel.setMaximumSize(new Dimension(150, 30));
        label.setOpaque(false);
        panel.setOpaque(false);
        panel.add(label);
        graphic.addEastComponent(panel);
        

        String[] farbPaletten = {"Standard", "Dunkel", "Pastell", "Retro", "Monochrom", "THM",};
        JComboBox<String> farbPalettenBox = new JComboBox<>(farbPaletten);
        farbPalettenBox.setMaximumSize(new Dimension(150, 30)); 
        
        graphic.addEastComponent(farbPalettenBox);


        farbPalettenBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String auswahl = (String) farbPalettenBox.getSelectedItem();
                switch (auswahl) {
                    case "Standard":
                        xsend.flaeche(0xbbada0);
                        xsend.rahmen(0x776e65);
                        board.receiveMessage("graphicBorder 0xe0d9d1");
                        Kacheln.LEERE_FARBE = 0xcdc1b4;
                        break;
                    case "Dunkel":
                        xsend.flaeche(0x1f2125);
                        xsend.rahmen(0x000000);
                        board.receiveMessage("graphicBorder 0x363947");
                        Kacheln.LEERE_FARBE = 0x111112;
                        break;
                    case "Pastell":
                        xsend.flaeche(0xedabc1);
                        xsend.rahmen(0xb3cceb);
                        board.receiveMessage("graphicBorder 0xa0d9da");
                        Kacheln.LEERE_FARBE = 0xfbd0d0;
                        break;
                    case "Retro":
                        xsend.flaeche(0x5d382f);
                        xsend.rahmen(0xed9f3d);
                        board.receiveMessage("graphicBorder 0xfee7bd");
                        Kacheln.LEERE_FARBE = 0x93d2c2;
                        break;
                    case "Monochrom":
                        xsend.flaeche(0x848494);
                        xsend.rahmen(0x2b2b3b);
                        board.receiveMessage("graphicBorder 0xa0a0ac");
                        Kacheln.LEERE_FARBE = 0xcdc1b4;
                        break;
                    case "THM":
                        xsend.flaeche(0x80ba24);
                        xsend.rahmen(0x4a5c66);
                        board.receiveMessage("graphicBorder 0xbfe77e");
                        Kacheln.LEERE_FARBE = 0xcdc1b4;
                        break;
                        
                }
                spielfeld.updateSpielfeld();
                board.getPlotter().requestFocusInWindow();
            }
        });
        
        
        //Spielanleitung-Menü
        JMenu spielregelnMenue = new JMenu("<html><p style=\"color:#0000FF;\">Spielanleitung</p></html>");
        graphic.addExternMenu(spielregelnMenue);
        

        JMenuItem text = new JMenuItem("<html><p style=\"background-color:#fdfc60;\">Spielanleitung</p></html>");
        spielregelnMenue.add(text);
        
        JMenuItem text2 = new JMenuItem("<html><p style=\"background-color:#FF10F0;\">Strategien & Tipps</p></html>");
        spielregelnMenue.add(text2);
        
       
        text.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                		"<html><body style=\"font-family: Arial Light;\">"   //simples HTML & CSS
                        + "<p>Verwenden Sie die <span style=\"color:#0000FF;\">Pfeiltasten</span>, um die Kacheln zu bewegen.<br> "
                        + "Kacheln mit der gleichen Zahl verschmelzen zu einer, wenn sie sich berühren.<br> "
                        + "Füge sie zusammen, um <span style=\"color:#FF0000;\">2048</span> zu erreichen!</p>"
                        + "<p><span style=\"font-family: Arial Black;\"><u>Viel Erfolg!</u></span> ☺</p>"
                        + "</body></html>",
                        "Spielanleitung", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        

        text2.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        	    String message = "<html><body>"
        	    		+ "<p>***************************************************</p>"	
        	    		+ "<p>Noch in Bearbeitung...</p>" 
        	    		+ "<p>***************************************************</p>"	
        	            + "</body></html>";

        	    Object[] options = {"Alles klar, kann losgehen!"};

        	    JOptionPane.showOptionDialog(null, message, "Wie man 2048 gewinnt - Strategien und Hinweise",
        	            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
        	            null, options, options[0]);
        	}
        });
        
        
    }
    
    
}
