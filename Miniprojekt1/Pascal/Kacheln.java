package basic;

//import jserver.XSendAdapter;


public class Kacheln {
	public int x;
    public int y;
    public int farbe;
    public int textFarbe;
    public String form = "s"; // Alle Kacheln als Quadrat
    public int wert;
    public static final int LEERE_FARBE = 0xcdc1b4;

    public Kacheln(int x, int y, int wert) {
        this.x = x;
        this.y = y;
        this.wert = wert;
        setFarben();
    }
    
    private void setFarben() {
        switch (wert) {
            case 0:
                this.farbe = LEERE_FARBE;
                this.textFarbe = LEERE_FARBE; // Textfarbe gleich Hintergrundfarbe
                break;
            case 2:
                this.farbe = 0xeee4da;
                this.textFarbe = 0x776e65;
                break;
            case 4:
                this.farbe = 0xede0c8;
                this.textFarbe = 0x776e65;
                break;
            case 8:
                this.farbe = 0xf2b179;
                this.textFarbe = 0xf9f6f2;
                break;
            case 16:
                this.farbe = 0xf59563;
                this.textFarbe = 0xf9f6f2;
                break;
            case 32:
                this.farbe = 0xf67c5f;
                this.textFarbe = 0xf9f6f2;
                break;
            case 64:
                this.farbe = 0xf65e3b;
                this.textFarbe = 0xf9f6f2;
                break;
            case 128:
                this.farbe = 0xedcf72;
                this.textFarbe = 0xf9f6f2;
                break;
            case 256:
                this.farbe = 0xedcc61;
                this.textFarbe = 0xf9f6f2;
                break;
            case 512:
                this.farbe = 0xeac64e;
                this.textFarbe = 0xf9f6f2;
                break;
            case 1024:
                this.farbe = 0xecc441;
                this.textFarbe = 0xf9f6f2;
                break;
            case 2048:
                this.farbe = 0xeec22e;
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
