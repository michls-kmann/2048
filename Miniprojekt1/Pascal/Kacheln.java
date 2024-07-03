package Projekt;

/**
 * Diese Klasse repräsentiert eine einzelne Kachel auf dem Spielfeld.
 * Jede Kachel hat Attribute wie Position (x,y), Form, Zahlenwert, Farben für den Hintergrund und Text.
 */
 

public class Kacheln {
    public int x;
    public int y;
    public int farbe;
    public int textFarbe;
    public String form = "s"; 
    public int wert;
    public static int LEERE_FARBE = 0xDED1C3;
    public int[] farben = {0xF5EBE1, 0xF2E4C4, 0xf2b179, 0xf59563, 0xf67c5f, 0xf65e3b, 0xedcf72, 0xedcc61, 0xeac64e, 0xefc53f, 0xe0b110};

    
    
    public Kacheln(int x, int y, int wert) {
        this.x = x;
        this.y = y;
        this.wert = wert;
        setFarben(farben);
    }

    public void setFarben(int[] farben) {
        switch (wert) {
            case 0:
                this.farbe = LEERE_FARBE;
                this.textFarbe = LEERE_FARBE; 
                break;
            case 2:
                this.farbe = farben[0];
                this.textFarbe = 0x776e65;
                break;
            case 4:
                this.farbe = farben[1];
                this.textFarbe = 0x776e65;
                break;
            case 8:
                this.farbe = farben[2];
                this.textFarbe = 0xf9f6f2;
                break;
            case 16:
                this.farbe = farben[3];
                this.textFarbe = 0xf9f6f2;
                break;
            case 32:
                this.farbe = farben[4];
                this.textFarbe = 0xf9f6f2;
                break;
            case 64:
                this.farbe = farben[5];
                this.textFarbe = 0xf9f6f2;
                break;
            case 128:
                this.farbe = farben[6];
                this.textFarbe = 0xf9f6f2;
                break;
            case 256:
                this.farbe = farben[7];
                this.textFarbe = 0xf9f6f2;
                break;
            case 512:
                this.farbe = farben[8];
                this.textFarbe = 0xf9f6f2; 
                break;
            case 1024:
                this.farbe = farben[9];
                this.textFarbe = 0xf9f6f2;
                break;
            case 2048:
                this.farbe = farben[10];
                this.textFarbe = 0xf9f6f2;
                break;
            default:
                this.farbe = LEERE_FARBE;
                this.textFarbe = LEERE_FARBE;
        }
    }

    public String getText() {
        return wert > 0 ? String.valueOf(wert) : "";
    }
    
    
    
  
}
