package basic;

import jserver.XSendAdapter;


public class Kacheln {
	public int x;
    public int y;
    public int farbe;
    public String form;
    public int wert;
    public int punkte;
    


    public static Kacheln getKachel() {
    	Kacheln kachel = new Kacheln();
    	kachel.farbe = XSendAdapter.RED;
    	kachel.x = 2;
    	kachel.y = 2;
    	kachel.form = "s";
	
    	return kachel;
	}

    public void setzeKachel() {
    	
    	getKachel();
    }
}
