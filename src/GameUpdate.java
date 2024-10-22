import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GameUpdate {
    public int greenX = 900;
    public int greenY = 200;
    public int greyX = 10;
    public int greyY = 200;

    int planeWidth = 27;
    int planeHeight = 110;

    public Integer greenScore = 0;
    public Integer greyScore = 0;

    public void colission(List<Bullet> firedBullets) {
        if (Math.abs(greenX - greyX) <= 100 && Math.abs(greenY - greyY) <= 30) {
            greenX = 900;
            greenY = 200;
            greyX = 10;
            greyY = 200;
            greenScore--;
            greyScore--;
        }

        synchronized (firedBullets) {
            for (Bullet bullet : firedBullets) {
                int direction = bullet.getDirection();
                if (direction < 0) {
                    if (Math.abs(greyX - bullet.x) <= planeHeight / 2 && Math.abs(greyY - bullet.y) <= 10) {
                        if (!bullet.hit) {
                            greenScore++;
                        }
                        bullet.boom();
                    }
                } else {
                    if (Math.abs(greenX - bullet.x) <= planeHeight / 2 && Math.abs(greenY - bullet.y) <= 10) {
                        if (!bullet.hit) {
                            greyScore++;
                        }
                        bullet.boom();
                    }
                }
            }

            List<Bullet> toRemove = new ArrayList<Bullet>();

            for (Bullet bullet1 : firedBullets) {
                for (Bullet bullet2 : firedBullets) {
                    if (!bullet1.equals(bullet2)) {
                        if (bullet1.y == bullet2.y) {
                            if (Math.abs(bullet1.x - bullet2.x) < 15) {
                                toRemove.add(bullet1);
                                toRemove.add(bullet2);
                            }
                        }
                    }
                }
            }
            firedBullets.removeAll(toRemove);
        }
    }

    public void displayScore() {
        System.out.println("Green Score: " + greenScore);
        System.out.println("Grey Score: " + greyScore);
    }

    public void leftKey() {
        if (greenX > 0) {
            greenX -= 10;
        }
    }

    public void rightKey() {
        if (greenX < 910) {
            greenX += 10;
        }
    }

    public void upKey() {
        if (greenY > 0) {
            greenY -= 10;
        }
    }

    public void downKey() {
        if (greenY < 620) {
            greenY += 10;
        }
    }

    public void aKey() {
        if (greyX > 0) {
            greyX -= 10;
        }
    }

    public void dKey() {
        if (greyX < 910) {
            greyX += 10;
        }
    }

    public void wKey() {
        if (greyY > 0) {
            greyY -= 10;
        }
    }

    public void sKey() {
        if (greyY < 620) {
            greyY += 10;
        }
    }
}
