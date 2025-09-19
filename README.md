# DogFight

**DogFight** is a 2D game I created as the final project for **Programming 1**, where two players control their own planes and try to shoot each other down.

## Features

* Two players (green and gray) control separate planes and can move freely in all directions on the screen.
* Shoot bullets to try and hit your opponent.
* Collisions between planes decrease both players' scores.
* Bullets can collide with each other and disappear upon impact.
* The game keeps track of each player’s score, and a winner is declared when someone reaches 10 points.

## Installation and Running the Game

1. Clone this repository to your computer:

   ```bash
   git clone https://github.com/your-username/DogFight.git
   ```

2. Open the project in your Java IDE (e.g., IntelliJ IDEA or Eclipse).

3. Build and run the project by executing the `GameCanvas` class, which contains the main game loop and keyboard handling.

4. Alternatively, you can compile and run the game from the terminal:

   ```bash
   javac GameCanvas.java
   java GameCanvas
   ```

## Controls

**Green Player:**

* Up: `Arrow UP`
* Down: `Arrow DOWN`
* Left: `Arrow LEFT`
* Right: `Arrow RIGHT`
* Shoot: `SPACE`

**Gray Player:**

* Up: `W`
* Down: `S`
* Left: `A`
* Right: `D`
* Shoot: `Q`

**Other Keys:**

* Restart the game: `ENTER`
* Quit the game: `ESC`

## Game Rules

* If a player is hit by a bullet, the opponent scores one point.
* If the planes collide, both players lose one point and their positions are reset.
* Bullets that collide with each other are destroyed.
* The first player to reach 10 points wins the game.
