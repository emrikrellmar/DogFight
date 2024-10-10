import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GameUpdate {
    // Koordinater för de gröna och grå spelfigurerna
    public int greenX = 900;
    public int greenY = 200;
    public int greyX = 10;
    public int greyY = 200;

    // Planernas storlek
    int planeWidth = 27;
    int planeHeight = 110;

    // Poäng för varje spelare
    public Integer greenScore = 0;
    public Integer greyScore = 0;

    // Metod för att hantera kollisioner mellan kulor och spelare
    public void colission(List<Bullet> firedBullets) {

        /*
         * Regler:
         * - Kollision med vägg ger minuspoäng.
         * - Träff med skott ger pluspoäng.
         * - Skott mot skott tar ut varandra.
         * - Krock mellan spelarna återställer deras positioner och poängen minskar.
         */

        // Kontrollera kollision mellan spelarna
        if (Math.abs(greenX - greyX) <= 100 && Math.abs(greenY - greyY) <= 30) {
            greenX = 900;
            greenY = 200;
            greyX = 10;
            greyY = 200;
            greenScore--;  // Minska poängen för gröna spelaren
            greyScore--;  // Minska poängen för grå spelaren
        }

        // Lås listan med kulor eftersom den kan modifieras samtidigt på andra ställen i programmet
        synchronized (firedBullets) {
            for (Bullet bullet : firedBullets) {
                int direction = bullet.getDirection();  // Hämta kulans riktning
                // Om direction < 0 är det en grön kula, annars är det en grå kula

                // Kontrollera om grått skepp träffas av grön kula
                if (direction < 0) {
                    if (Math.abs(greyX - bullet.x) <= planeHeight / 2 && Math.abs(greyY - bullet.y) <= 10) {
                        if (!bullet.hit) {
                            greenScore++;  // Öka poängen för gröna spelaren
                        }
                        bullet.boom();  // Skapa en explosion för kulan
                    }
                } else {
                    // Kontrollera om grönt skepp träffas av grå kula
                    if (Math.abs(greenX - bullet.x) <= planeHeight / 2 && Math.abs(greenY - bullet.y) <= 10) {
                        if (!bullet.hit) {
                            greyScore++;  // Öka poängen för grå spelaren
                        }
                        bullet.boom();  // Skapa en explosion för kulan
                    }
                }
            }

            // Lista för att hålla kulor som ska tas bort
            List<Bullet> toRemove = new ArrayList<Bullet>();

            // Kontrollera kollision mellan kulor
            for (Bullet bullet1 : firedBullets) {
                for (Bullet bullet2 : firedBullets) {
                    if (!bullet1.equals(bullet2)) {  // Kontrollera att det inte är samma kula
                        if (bullet1.y == bullet2.y) {  // Kontrollera om kulorna är på samma y-koordinat
                            if (Math.abs(bullet1.x - bullet2.x) < 15) {
                                toRemove.add(bullet1);  // Lägg till båda kulorna för borttagning
                                toRemove.add(bullet2);
                            }
                        }
                    }
                }
            }
            firedBullets.removeAll(toRemove);  // Ta bort kulorna från listan
        }
    }

    // Metod för att visa poängen på konsolen
    public void displayScore() {
        System.out.println("Green Score: " + greenScore);
        System.out.println("Grey Score: " + greyScore);
    }

    /*
     * Metod som anropas när vänster piltangent trycks ner
     */
    public void leftKey() {
        if (greenX == 0) {
            greenX += 0;  // Om gröna skeppet är vid vänstra kanten, gör inget
        } else {
            greenX -= 10;  // Annars, flytta skeppet 10 pixlar åt vänster
        }
    }

    /**
     * Metod som anropas när höger piltangent trycks ner
     */
    public void rightKey() {
        if (greenX == 910) {
            greenX = 910;  // Om gröna skeppet är vid högra kanten, gör inget
        } else {
            greenX += 10;  // Annars, flytta skeppet 10 pixlar åt höger
        }
    }

    /**
     * Metod som anropas när upp piltangent trycks ner
     */
    public void upKey() {
        if (greenY == 0) {
            greenY = 0;  // Om gröna skeppet är vid övre kanten, gör inget
        } else {
            greenY -= 10;  // Annars, flytta skeppet 10 pixlar uppåt
        }
    }

    /**
     * Metod som anropas när ner piltangent trycks ner
     */
    public void downKey() {
        if (greenY == 620) {
            greenY = 620;  // Om gröna skeppet är vid nedre kanten, gör inget
        } else {
            greenY += 10;  // Annars, flytta skeppet 10 pixlar nedåt
        }
    }

    /**
     * Metod som anropas när A-tangenten trycks ner
     */
    public void aKey() {
        if (greyX == 0) {
            greyX = 0;  // Om gråa skeppet är vid vänstra kanten, gör inget
        } else {
            greyX -= 10;  // Annars, flytta skeppet 10 pixlar åt vänster
        }
    }

    /**
     * Metod som anropas när D-tangenten trycks ner
     */
    public void dKey() {
        if (greyX == 910) {
            greyX = 910;  // Om gråa skeppet är vid högra kanten, gör inget
        } else {
            greyX += 10;  // Annars, flytta skeppet 10 pixlar åt höger
        }
    }

    /**
     * Metod som anropas när W-tangenten trycks ner
     */
    public void wKey() {
        if (greyY == 0) {
            greyY = 0;  // Om gråa skeppet är vid övre kanten, gör inget
        } else {
            greyY -= 10;  // Annars, flytta skeppet 10 pixlar uppåt
        }
    }

    /**
     * Metod som anropas när S-tangenten trycks ner
     */
    public void sKey() {
        if (greyY == 620) {
            greyY = 620;  // Om gråa skeppet är vid nedre kanten, gör inget
        } else {
            greyY += 10;  // Annars, flytta skeppet 10 pixlar nedåt
        }
    }
}
