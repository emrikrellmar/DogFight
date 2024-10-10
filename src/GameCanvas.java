import java.awt.Canvas;  // Importera Canvas-klassen från java.awt paketet
import java.awt.Color;  // Importera Color-klassen från java.awt paketet
import java.awt.Dimension;  // Importera Dimension-klassen från java.awt paketet
import java.awt.Font;  // Importera Font-klassen från java.awt paketet
import java.awt.FontMetrics;  // Importera FontMetrics-klassen från java.awt paketet
import java.awt.Graphics2D;  // Importera Graphics2D-klassen från java.awt paketet
import java.awt.Image;  // Importera Image-klassen från java.awt paketet
import java.awt.event.KeyEvent;  // Importera KeyEvent-klassen från java.awt.event paketet
import java.awt.event.KeyListener;  // Importera KeyListener-gränssnittet från java.awt.event paketet
import java.awt.image.BufferStrategy;  // Importera BufferStrategy-klassen från java.awt.image paketet
import java.util.ArrayList;  // Importera ArrayList-klassen från java.util paketet
import java.util.Collections;  // Importera Collections-klassen från java.util paketet
import java.util.HashMap;  // Importera HashMap-klassen från java.util paketet
import java.util.Iterator;  // Importera Iterator-gränssnittet från java.util paketet
import java.util.List;  // Importera List-gränssnittet från java.util paketet

import javax.swing.ImageIcon;  // Importera ImageIcon-klassen från javax.swing paketet
import javax.swing.JFrame;  // Importera JFrame-klassen från javax.swing paketet

public class GameCanvas extends Canvas implements KeyListener {
    static final long serialVersionUID = 1L;

    private BufferStrategy backBuffer;  // Buffer-strategi för dubbelfbuffring

    private Image shipGreen;  // Bild för det gröna skeppet
    private Image shipGrey;  // Bild för det grå skeppet
    private Image bg;  // Bild för bakgrunden

    private Dimension dimension = new Dimension(1024, 720);  // Spelfönstrets dimensioner
    private GameUpdate gm;  // Speluppdateringslogik

    private boolean gameLoop = true;  // Flagga för att köra spelets huvudloop

    private HashMap<Integer, Boolean> keyDownMap = new HashMap<Integer, Boolean>();  // Karta för att hålla koll på nedtryckta tangenter
    public List<Bullet> firedBullets = Collections.synchronizedList(new ArrayList<Bullet>());  // Lista över avfyrade kulor

    // Konstruktor för GameCanvas
    public GameCanvas() {
        this.gm = new GameUpdate();  // Skapa en ny GameUpdate-instans
        createWindow();  // Skapa spelfönstret
        addKeyListener(this);  // Lägg till KeyListener för att hantera tangenttryckningar
        this.createBufferStrategy(2);  // Skapa en buffer-strategi med två buffrar
        backBuffer = this.getBufferStrategy();  // Få referens till buffer-strategin

        // Ladda bilderna för skeppen och bakgrunden
        shipGreen = new ImageIcon(getClass().getResource("/shipGreen.png")).getImage();
        shipGrey = new ImageIcon(getClass().getResource("/shipGrey.png")).getImage();
        bg = new ImageIcon(getClass().getResource("/bg.png")).getImage();

        gameLoop();  // Starta spelets huvudloop
    }

    // Skapa spelfönstret
    public void createWindow() {
        JFrame window = new JFrame("Dog fight");  // Skapa ett nytt JFrame med titeln "Dog fight"

        setPreferredSize(dimension);  // Sätt önskad storlek på Canvas

        window.add(this);  // Lägg till Canvas i fönstret
        window.pack();  // Packa fönstret så att det passar Canvas
        window.setResizable(false);  // Gör fönstret icke-resizable
        window.setVisible(true);  // Gör fönstret synligt

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Stäng programmet när fönstret stängs

        this.requestFocus();  // Sätt fokus på Canvas

        // Kod för att skapa ett hjälpfönster (avkommenterat)
        /*
        JFrame windowHelp = new JFrame("Instruktioner");

        setPreferredSize(dimension);

        window.add(this);
        window.pack();
        window.setResizable(false);
        window.setVisible(true);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.requestFocus();
        */
    }

    // Spelets huvudloop
    public void gameLoop() {
        while (gameLoop) {
            // Flytta varje kula i riktningen den är avfyrad
            for (Bullet bullet : firedBullets) {
                bullet.moveInDirection();
                // Möjliga tillägg: Kontrollera kulans gränser och kollisioner
            }
            update();  // Uppdatera spelets tillstånd
            render();  // Rendera spelet
            try {
                Thread.sleep(20);  // Pausa loopen i 20 millisekunder
            } catch (Exception e) {
            }
        }
    }

    // Uppdatera spelets tillstånd baserat på nedtryckta tangenter
    public void update() {
        if (keyDownMap.containsKey(KeyEvent.VK_LEFT))
            gm.leftKey();
        gm.colission(firedBullets);  // Kontrollera kollisioner

        if (keyDownMap.containsKey(KeyEvent.VK_RIGHT))
            gm.rightKey();
        gm.colission(firedBullets);  // Kontrollera kollisioner

        if (keyDownMap.containsKey(KeyEvent.VK_UP))
            gm.upKey();
        gm.colission(firedBullets);  // Kontrollera kollisioner

        if (keyDownMap.containsKey(KeyEvent.VK_DOWN))
            gm.downKey();
        gm.colission(firedBullets);  // Kontrollera kollisioner

        if (keyDownMap.containsKey(KeyEvent.VK_S))
            gm.sKey();
        gm.colission(firedBullets);  // Kontrollera kollisioner

        if (keyDownMap.containsKey(KeyEvent.VK_W))
            gm.wKey();
        gm.colission(firedBullets);  // Kontrollera kollisioner

        if (keyDownMap.containsKey(KeyEvent.VK_A))
            gm.aKey();
        gm.colission(firedBullets);  // Kontrollera kollisioner

        if (keyDownMap.containsKey(KeyEvent.VK_D))
            gm.dKey();
        gm.colission(firedBullets);  // Kontrollera kollisioner

        if (keyDownMap.containsKey(KeyEvent.VK_SPACE)) {
            // Hantera mellanslag (skjutknapp)
        }

        if (keyDownMap.containsKey(KeyEvent.VK_Q)) {
            // Hantera Q-knappen (skjutknapp för andra skeppet)
        }

        if (keyDownMap.containsKey(KeyEvent.VK_ESCAPE)) {
            gameLoop = false;  // Avsluta spelets huvudloop
            System.exit(0);  // Avsluta programmet
        }
    }

    // Rendera spelet på skärmen
    public void render() {
        Graphics2D g = (Graphics2D) backBuffer.getDrawGraphics();  // Få grafikobjektet från buffer-strategin

        g.drawImage(bg, 0, 0, dimension.width, dimension.height, null);  // Rita bakgrunden

        g.drawImage(shipGrey, gm.greyX, gm.greyY, null);  // Rita det grå skeppet
        g.drawImage(shipGreen, gm.greenX, gm.greenY, null);  // Rita det gröna skeppet

        List<Bullet> toRemove = new ArrayList<Bullet>();  // Lista över kulor som ska tas bort
        synchronized (firedBullets) {
            for (Bullet bullet : firedBullets) {
                g.drawImage(bullet.bulletImg, bullet.x, bullet.y, null);  // Rita varje kula
                if (bullet.hit) {
                    bullet.stopMove();  // Stoppa kulans rörelse om den har träffat något
                }
                if (bullet.remove) {
                    toRemove.add(bullet);  // Lägg till kulan i borttagningslistan om den ska tas bort
                }
            }
            firedBullets.removeAll(toRemove);  // Ta bort alla kulor som ska tas bort från listan
        }

        String text = "";

        Font font = new Font("Times New Roman", Font.PLAIN, 40); // font Times New Roman, 40 storlek
        if ((gm.greenScore > 9) || (gm.greyScore > 9)) {
            if (gm.greenScore < gm.greyScore) {
                text = "Grey wins!";
            } else {
                text = "Green wins!";
            }
            text = text + " Restart with enter"; // Lägger till text efter texten

            font = new Font("Times New Roman", Font.PLAIN, 80); // font Times New Roman, 80 storlek
        } else {
            text = "Grey: " + gm.greyScore + "    " + "Green: " + gm.greenScore;
        }

        g.setFont(font);  // Sätt fonten
        g.setColor(Color.white);  // Sätt färgen till vit
        FontMetrics fm = g.getFontMetrics();  // Få fontmetrik för den valda fonten
        int x = (getWidth() - fm.stringWidth(text)) / 2;  // Centrera texten horisontellt
        g.drawString(text, x, dimension.height - 10);  // Rita texten på skärmen
        g.dispose();  // Frigör grafikobjektet
        backBuffer.show();  // Visa den uppdaterade buffern
    }

    // Hantera nedtryckningar av tangenter
    public void keyPressed(KeyEvent e) {
        keyDownMap.put(e.getKeyCode(), true);  // Lägg till tangenten i keyDownMap

        Bullet bullet = null;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                bullet = new Bullet(gm.greenX, gm.greenY);  // Skapa en ny kula vid det gröna skeppet
                firedBullets.add(bullet);  // Lägg till kulan i listan över avfyrade kulor
                bullet.launchBullet(-15);  // Avfyra kulan i en viss riktning
                break;
            case KeyEvent.VK_Q:
                bullet = new Bullet(gm.greyX, gm.greyY);  // Skapa en ny kula vid det grå skeppet
                firedBullets.add(bullet);  // Lägg till kulan i listan över avfyrade kulor
                bullet.launchBullet(15);  // Avfyra kulan i en viss riktning
                break;
            case KeyEvent.VK_ENTER:
                gm.greenScore = 0;  // Återställ poängen för gröna laget
                gm.greyScore = 0;  // Återställ poängen för grå laget
                gm.greenX = 900;  // Återställ gröna skeppets position
                gm.greenY = 200;  // Återställ gröna skeppets position
                gm.greyX = 10;  // Återställ grå skeppets position
                gm.greyY = 200;  // Återställ grå skeppets position
        }
    }

    // Hantera när en tangent släpps
    public void keyReleased(KeyEvent e) {
        keyDownMap.remove(e.getKeyCode());  // Ta bort tangenten från keyDownMap
    }

    // Oanvänd metod, krävs av KeyListener-gränssnittet
    public void keyTyped(KeyEvent e) {
    }

    // Huvudmetoden för att starta spelet
    public static void main(String[] args) {
        new GameCanvas();  // Skapa en ny instans av GameCanvas och starta spelet
    }
}
