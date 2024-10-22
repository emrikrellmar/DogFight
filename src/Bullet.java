import java.awt.Image;
import javax.swing.ImageIcon;

public class Bullet {
    private int direction;
    public int x;
    public int y;
    public Image bulletImg;
    public boolean hit = false;
    public boolean remove = false;
    private boolean m_stopmove = false;
    private int m_hitcounter = 50;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void launchBullet(int direction) {
        this.setDirection(direction);
        if (direction < 1) {
            bulletImg = new ImageIcon(getClass().getResource("/bullet2.png")).getImage();
        } else {
            bulletImg = new ImageIcon(getClass().getResource("/bullet3.png")).getImage();
        }
    }

    public void boom() {
        bulletImg = new ImageIcon(getClass().getResource("/EXPLOSIONPNG.png")).getImage();
        hit = true;
    }

    public void moveInDirection() {
        if (!m_stopmove) {
            x = x + getDirection();
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void stopMove() {
        m_hitcounter--;
        if (m_hitcounter == 0) {
            remove = true;
        }
        m_stopmove = true;
    }
}
