import processing.core.PApplet;

/**
 * Description: This class creates a sketch that displays snowflakes falling from the top of the screen.
 * The player can move left and right to avoid the snowflakes. The player has 3 lives and loses a life
 * when a snowflake hits the player. The player can also change the speed of the snowflakes by pressing
 * the up and down arrow keys. The player can hide snowflakes by clicking on them with the mouse.
 * @author: Austin L
 */
public class Sketch extends PApplet {

  int intNumSnowflakes = 100;
  float[] fltSnowX = new float[intNumSnowflakes];
  float[] fltSnowY = new float[intNumSnowflakes];
  float[] fltSnowSpeed = new float[intNumSnowflakes];
  boolean[] blnBallHideStatus = new boolean[intNumSnowflakes];
  long longStartTime;
  float fltPlayerX, fltPlayerY, fltPlayerSize;
  int intPlayerLives = 3;

  /**
   * Sets the size of the window.
   */
  public void settings() {
    size(800, 600);
  }

  /**
   * Initializes the sketch with background color and snowflakes' positions and speeds.
   */
  public void setup() {
    background(210, 255, 173);
    for (int i = 0; i < intNumSnowflakes; i++) {
      fltSnowX[i] = random(width);
      fltSnowY[i] = random(-height, 0);
      fltSnowSpeed[i] = random(1, 3);
      blnBallHideStatus[i] = false;
      longStartTime = millis();
    }

    fltPlayerX = width / 2;
    fltPlayerY = height - 30;
    fltPlayerSize = 20;
  }

  /**
   * Draws the snowflakes, player, and handles the game logic.
   */
  public void draw() {
    background(0);

    // Draw snowflakes
    for (int i = 0; i < intNumSnowflakes; i++) {
      if (!blnBallHideStatus[i]) {
        fill(255);
        ellipse(fltSnowX[i], fltSnowY[i], 20, 20);
        fltSnowY[i] += fltSnowSpeed[i];

        if (fltSnowY[i] > height) {
          fltSnowY[i] = random(-height, 0);
          fltSnowX[i] = random(width);
        }

        // Check for collision with player
        if (dist(fltSnowX[i], fltSnowY[i], fltPlayerX, fltPlayerY) < fltPlayerSize / 2 + 5) {
          intPlayerLives--;
          fltSnowY[i] = random(-height, 0);
          fltSnowX[i] = random(width);
        }
      }
    }

    // Draw player
    fill(0, 0, 255);
    ellipse(fltPlayerX, fltPlayerY, fltPlayerSize, fltPlayerSize);

    // Draw player lives
    fill(255, 0, 0);
    for (int i = 0; i < intPlayerLives; i++) {
      rect(width - (i + 1) * 30, 10, 20, 20);
    }

    // Check for game over
    if (intPlayerLives <= 0) {
      background(255);
      fill(0);
      textSize(32);
      textAlign(CENTER, CENTER);
      text("Game Over", width / 2, height / 2);
      noLoop();
    }

    // Calculates elapsed time
    int elapsedTime = (int) (millis() - longStartTime);
    int milliseconds = elapsedTime % 1000;
    int seconds = (elapsedTime / 1000) % 60;
    int minutes = (elapsedTime / (1000 * 60)) % 60;
    int hours = (elapsedTime / (1000 * 60 * 60)) % 24;

    // Display the timer at the top left of the screen
    fill(255);
    textSize(32);
    text(hours + ":" + minutes + ":" + seconds + ":" + milliseconds, 10, 50);
  }

  /**
   * Description: Handles key presses to move the player and change snowflake speed.
   * @param: None
   * @return: None
   */
  public void keyPressed() {
    if (keyCode == DOWN) {
      for (int i = 0; i < intNumSnowflakes; i++) {
        fltSnowSpeed[i] += 0.5;
      }
    } else if (keyCode == UP) {
      for (int i = 0; i < intNumSnowflakes; i++) {
        fltSnowSpeed[i] -= 0.5;
        if (fltSnowSpeed[i] < 1) {
          fltSnowSpeed[i] = 1;
        }
      }
    } else if (key == 'a') {
      fltPlayerX -= 20;
    } else if (key == 'd') {
      fltPlayerX += 20;
    } else if (key == 'w') {
      fltPlayerY -= 20;
    } else if (key == 's') {
      fltPlayerY += 20;
    }
  }

  /**
   * Description: Handles mouse press to hide snowflakes.
   * @param: None
   * @return: None
   */
  public void mousePressed() {
    for (int i = 0; i < intNumSnowflakes; i++) {
      if (dist(mouseX, mouseY, fltSnowX[i], fltSnowY[i]) < 7) {
        blnBallHideStatus[i] = true;
      }
    }
  }
}