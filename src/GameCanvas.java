import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameCanvas extends Canvas implements KeyListener {
    static final long serialVersionUID = 1L;

    private BufferStrategy backBuffer;
    private Image shipGreen;
    private Image shipGrey;
    private Image bg;

    private Dimension dimension = new Dimension(1024, 720);
    private GameUpdate gm;
    private boolean gameLoop = true;
    private HashMap<Integer, Boolean> keyDownMap = new HashMap<Integer, Boolean>();
    public List<Bullet> firedBullets = Collections.synchronizedList(new ArrayList<Bullet>());

    public GameCanvas() {
        this.gm = new GameUpdate();
        createWindow();
        addKeyListener(this);
        this.createBufferStrategy(2);
        backBuffer = this.getBufferStrategy();
        shipGreen = new ImageIcon(getClass().getResource("/shipGreen.png")).getImage();
        shipGrey = new ImageIcon(getClass().getResource("/shipGrey.png")).getImage();
        bg = new ImageIcon(getClass().getResource("/bg.png")).getImage();
        gameLoop();
    }

    public void createWindow() {
        JFrame window = new JFrame("Dog fight");
        setPreferredSize(dimension);
        window.add(this);
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.requestFocus();
    }

    public void gameLoop() {
        while (gameLoop) {
            for (Bullet bullet : firedBullets) {
                bullet.moveInDirection();
            }
            update();
            render();
            try {
                Thread.sleep(20);
            } catch (Exception e) {
            }
        }
    }

    public void update() {
        if (keyDownMap.containsKey(KeyEvent.VK_LEFT))
            gm.leftKey();
        gm.colission(firedBullets);

        if (keyDownMap.containsKey(KeyEvent.VK_RIGHT))
            gm.rightKey();
        gm.colission(firedBullets);

        if (keyDownMap.containsKey(KeyEvent.VK_UP))
            gm.upKey();
        gm.colission(firedBullets);

        if (keyDownMap.containsKey(KeyEvent.VK_DOWN))
            gm.downKey();
        gm.colission(firedBullets);

        if (keyDownMap.containsKey(KeyEvent.VK_S))
            gm.sKey();
        gm.colission(firedBullets);

        if (keyDownMap.containsKey(KeyEvent.VK_W))
            gm.wKey();
        gm.colission(firedBullets);

        if (keyDownMap.containsKey(KeyEvent.VK_A))
            gm.aKey();
        gm.colission(firedBullets);

        if (keyDownMap.containsKey(KeyEvent.VK_D))
            gm.dKey();
        gm.colission(firedBullets);

        if (keyDownMap.containsKey(KeyEvent.VK_SPACE)) {
        }

        if (keyDownMap.containsKey(KeyEvent.VK_Q)) {
        }

        if (keyDownMap.containsKey(KeyEvent.VK_ESCAPE)) {
            gameLoop = false;
            System.exit(0);
        }
    }

    public void render() {
        Graphics2D g = (Graphics2D) backBuffer.getDrawGraphics();
        g.drawImage(bg, 0, 0, dimension.width, dimension.height, null);
        g.drawImage(shipGrey, gm.greyX, gm.greyY, null);
        g.drawImage(shipGreen, gm.greenX, gm.greenY, null);

        List<Bullet> toRemove = new ArrayList<Bullet>();
        synchronized (firedBullets) {
            for (Bullet bullet : firedBullets) {
                g.drawImage(bullet.bulletImg, bullet.x, bullet.y, null);
                if (bullet.hit) {
                    bullet.stopMove();
                }
                if (bullet.remove) {
                    toRemove.add(bullet);
                }
            }
            firedBullets.removeAll(toRemove);
        }

        String text = "";
        Font font = new Font("Times New Roman", Font.PLAIN, 40);
        if ((gm.greenScore > 9) || (gm.greyScore > 9)) {
            if (gm.greenScore < gm.greyScore) {
                text = "Grey wins!";
            } else {
                text = "Green wins!";
            }
            text = text + " Restart with enter";
            font = new Font("Times New Roman", Font.PLAIN, 80);
        } else {
            text = "Grey: " + gm.greyScore + "    " + "Green: " + gm.greenScore;
        }

        g.setFont(font);
        g.setColor(Color.white);
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        g.drawString(text, x, dimension.height - 10);
        g.dispose();
        backBuffer.show();
    }

    public void keyPressed(KeyEvent e) {
        keyDownMap.put(e.getKeyCode(), true);

        Bullet bullet = null;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                bullet = new Bullet(gm.greenX, gm.greenY);
                firedBullets.add(bullet);
                bullet.launchBullet(-15);
                break;
            case KeyEvent.VK_Q:
                bullet = new Bullet(gm.greyX, gm.greyY);
                firedBullets.add(bullet);
                bullet.launchBullet(15);
                break;
            case KeyEvent.VK_ENTER:
                gm.greenScore = 0;
                gm.greyScore = 0;
                gm.greenX = 900;
                gm.greenY = 200;
                gm.greyX = 10;
                gm.greyY = 200;
        }
    }

    public void keyReleased(KeyEvent e) {
        keyDownMap.remove(e.getKeyCode());
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        new GameCanvas();
    }
}
