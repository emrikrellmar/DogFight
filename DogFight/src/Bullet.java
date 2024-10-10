import java.awt.Image;  // Importera klassen Image från java.awt paketet
import javax.swing.ImageIcon;  // Importera klassen ImageIcon från javax.swing paketet

public class Bullet {
    private int direction;  // Riktningen som kulan rör sig i (positiv eller negativ)
    public int x;  // Kulans x-koordinat
    public int y;  // Kulans y-koordinat
    public Image bulletImg;  // Bilden av kulan
    public boolean hit = false;  // Indikerar om kulan har träffat något
    public boolean remove = false;  // Indikerar om kulan ska tas bort från spelvärlden
    private boolean m_stopmove = false;  // Flagga som stoppar kulans rörelse
    private int m_hitcounter = 50;  // Räknare för hur länge kulan ska visas efter en träff

    // Konstruktor som initierar kulans position
    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Metod för att skjuta kulan i en viss riktning
    public void launchBullet(int direction) {
        this.setDirection(direction);  // Sätter riktningen för kulan
        if (direction < 1) {
            // Om riktningen är mindre än 1, använd en specifik bild för kulan
            bulletImg = new ImageIcon(getClass().getResource("/bullet2.png")).getImage();
        } else {
            // Om riktningen är 1 eller större, använd en annan bild för kulan
            bulletImg = new ImageIcon(getClass().getResource("/bullet3.png")).getImage();
        }
    }

    // Metod för att ändra kulans bild till en explosion och markera den som träffad
    public void boom() {
        bulletImg = new ImageIcon(getClass().getResource("/EXPLOSIONPNG.png")).getImage();  // Ändra bild till explosion
        hit = true;  // Markera kulan som träffad
    }

    // Metod för att flytta kulan i den angivna riktningen
    public void moveInDirection() {
        // Flytta kulan med hastighet i den satta riktningen, om den inte har stoppats
        if (!m_stopmove) {
            x = x + getDirection();  // Uppdatera kulans x-koordinat med dess riktning
        }
    }

    // Getter för riktningen
    public int getDirection() {
        return direction;
    }

    // Setter för riktningen
    public void setDirection(int direction) {
        this.direction = direction;
    }

    // Metod för att stoppa kulans rörelse och räkna ned tills den ska tas bort
    public void stopMove() {
        m_hitcounter--;  // Minska räknaren
        if (m_hitcounter == 0) {
            remove = true;  // Om räknaren når noll, markera kulan för borttagning
        }
        m_stopmove = true;  // Stoppa kulans rörelse
    }
}
