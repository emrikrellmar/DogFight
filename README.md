# DogFight 

**DogFight** är ett 2D spel jag gjorde som slutprojekt i programmering 1 där två spelare styr var sitt plan och försöker skjuta ner varandra med skott.

## Funktioner

- Två spelare (grön och grå) styr varsitt skepp och kan röra sig i alla riktningar på skärmen.
- Skjut skott för att försöka träffa motståndaren.
- Kollisioner mellan skeppen minskar poängen för båda spelarna.
- Kulor kan kollidera med varandra och försvinner då.
- Spelet håller reda på poängen för varje spelare, och en vinnare koras när någon når 10 poäng.

## Installation och körning

1. Klona detta repository till din dator:

   ```bash
   git clone https://github.com/ditt-användarnamn/DogFight.git
   ```

2. Öppna projektet i din Java IDE (t.ex. IntelliJ IDEA eller Eclipse).

3. Bygg och kör projektet genom att exekvera `GameCanvas`-klassen, som innehåller spelets huvudloop och hantering av tangentbord.

4. Alternativt kan du kompilera och köra spelet via terminalen:

   ```bash
   javac GameCanvas.java
   java GameCanvas
   ```

## Spelkontroller

**Grön spelare:**
- Uppåt: `Arrow UP`
- Nedåt: `Arrow DOWN`
- Vänster: `Arrow LEFT`
- Höger: `Arrow RIGHT`
- Skjut: `SPACE`

**Grå spelare:**
- Uppåt: `W`
- Nedåt: `S`
- Vänster: `A`
- Höger: `D`
- Skjut: `Q`

**Övriga tangenter:**
- Starta om spelet: `ENTER`
- Avsluta spelet: `ESC`

## Spelets regler

- Om en spelare träffas av en kula, får motståndaren en poäng.
- Om skeppen krockar, återställs deras positioner och båda spelarna förlorar en poäng.
- Kulor som kolliderar med varandra försvinner.
- Den spelare som når 10 poäng först vinner spelet.

## Skärmdumpar från spelet
