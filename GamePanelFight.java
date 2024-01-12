// this class is responsible for bringing all aspects of the attacking turn together. it tells the program whether or not it is the attacking turn, makes objects visible, and allows the user to move the heart (if replit cooperates). it also keeps track of the user's HP and terminates the game if it reaches 0.

import java.awt.*;
import javax.swing.*;
import java.awt.Rectangle;

public class GamePanelFight extends JPanel implements Runnable{

  //dimensions of window
  public static final int GAME_WIDTH = 300;
  public static final int GAME_HEIGHT = 300;
  public final int SPEED = 3;

  public Graphics graphics;
  public Thread gameThread;
  public Dog dog; 
  public Heart heart;
  public Bullet bullet;
  public Bullet bulletTwo;
  public Bar bar;
  public boolean isTurn = false;
  public boolean turnOne = true;
  public boolean barBoy = false;
  public boolean doggo = true;
  public boolean damage = false;

  public int hp = 20;
  public int iteration = 0;
  public int iterationTwo = 0;
  public int damageCounter = 0;
  public int x = 130;
  public int y = 125;

  GamePanelFight(){
    heart = new Heart(x, y);
    heart.resize(20, 20);
    dog = new Dog(110, 265);
    dog.resize(65, 30);
    bullet = new Bullet((int)(Math.random()*GAME_WIDTH-80));
    bullet.resize(60, 30);
    bulletTwo = new Bullet((int)(Math.random()*GAME_WIDTH-80));
    bulletTwo.resize(60, 30);
    bar = new Bar(280, 0);

    this.setFocusable(true);
    this.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
    this.setBackground(Color.black);
    this.setBorder(BorderFactory.createLineBorder(Color.white, 3));
    gameThread = new Thread(this);
    gameThread.start();
  }

  // paints objects if they are supposed to be visible at that time
  public void paint(Graphics g){
    super.paint(g);

    if(dog.isVisible() && doggo == true) {
      g.drawImage(dog.getImage(), dog.getX(), dog.getY(), this);
    }

    if(heart.isVisible()) {
      g.drawImage(heart.getImage(), heart.getX(), heart.getY(), this);
    }

    if(bullet.isVisible() && doggo == true) {
      g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
    }

    if(bulletTwo.isVisible() && doggo == true) {
      g.drawImage(bulletTwo.getImage(), bulletTwo.getX(), bulletTwo.getY(), this);
    }

    if(bar.isVisible() && barBoy == true) {
      g.drawRect((int)bar.getX(), (int)bar.getY(), (int)bar.getWidth(), (int)bar.getHeight());
      if(damage == false) {
        g.setColor(Color.blue);
        }
      else{
        g.setColor(Color.white);
      }
      g.fillRect((int)bar.getX(), (int)bar.getY(), (int)bar.getWidth(), (int)bar.getHeight());
    }

  }

  public void run(){
    long lastTime = System.nanoTime();
    double amountOfTicks = 60;
    double ns = 1000000000/amountOfTicks;
    double delta = 0;
    long now;

    while(true){ //this is the infinite game loop
      now = System.nanoTime();
      delta = delta + (now-lastTime)/ns;
      lastTime = now;

      //only move objects around and update screen if enough time has passed
      if(delta >= 1){
        move();
        repaint();
        checkCollision();
        delta--;

        if(turnOne == false){
          barBoy = true;
          doggo = false;
          bar.setVisible(true);
        }

        if(turnOne == true){
          barBoy = false;
          doggo = true;
          dog.setVisible(true);
          bullet.setVisible(true);
          bulletTwo.setVisible(true);
        }

        if(hp == 0){
          System.exit(0);
        }

      }
    }
  }

  // moves objects around the screen if they are visible 
  public void move(){
    if(heart.isVisible()){
      heart.move();
    }

    if(bullet.isVisible() && doggo == true){
        bullet.move();
        wait(1);
      }
      
    if(bulletTwo.isVisible() && doggo == true){
        bulletTwo.move();
        wait(1);
      }

    if(bar.isVisible() && barBoy == true){
      bar.move();
      wait(1);
      damageCounter++;
      changeDamage();
    }
  }

  // toggles the bar from blue (no damage) to white (yes damage) as it moves across the screen
  public void changeDamage(){
      if(damageCounter%5 == 0){
        damage = !damage;
      }
  }
    
  // checks for collision between heart and enemy objects, as well as collision between bullets (so they don't overlap). also ensures that moving objects will return to the other side of the screen once they reach one end of it, and that the turn will end eventually if the user avoids being hit (and will end automatically if the user is hit). 
  public void checkCollision(){
    
    Rectangle r1 = heart.getBounds();
    Rectangle r2 = dog.getBounds();
    Rectangle r3 = bullet.getBounds();
    Rectangle r4 = bulletTwo.getBounds();
    Rectangle r5 = bar.getBounds();

    if(r1.intersects(r2) || r1.intersects(r3) || r1.intersects(r4)) {
      dog.setVisible(false);
      bullet.setVisible(false);
      bulletTwo.setVisible(false);
      if(isTurn){
        hp--;
      }
      turnOne = false;
      isTurn = false;
    }

    if(r1.intersects(r5) && damage == true) {
      bar.setVisible(false);
      if(isTurn){
        hp--;
      }
      turnOne = true;
      isTurn = false;
    }

    if(r3.intersects(r4)){
      bullet.setX((int)(Math.random()*GAME_WIDTH-80));
      bullet.setY(300);
    }

    if(bullet.getY() < 0){
      bullet.setX((int)(Math.random()*GAME_WIDTH-80));
      bullet.setY(300);
      iteration++;

      if(iteration > 4){
        bullet.setVisible(false);
        bulletTwo.setVisible(false);
        turnOne = false;
        isTurn = false;
      }
    }

    if(bulletTwo.getY() < 0){
      bulletTwo.setX((int)(Math.random()*GAME_WIDTH-80));
      bulletTwo.setY(300);
    }

    if(r5.getX() < 0){
      bar.setX(280);
      iterationTwo++;

      if(iteration > 2){
        bar.setVisible(false);
        turnOne = true;
        isTurn = false;
      }
    }
  }

  // checks for user input regarding heart
  public void keyPressed(char keyChar){
    if(keyChar == 'd'){
      heart.setXDirection(SPEED);
      heart.move();
    }

    if(keyChar == 'a'){
      heart.setXDirection(SPEED*-1);
      heart.move();
    }

    if(keyChar == 'w'){
      heart.setYDirection(SPEED*-1);
      heart.move();
    }

    if(keyChar == 's'){
      heart.setYDirection(SPEED);
      heart.move();
    }
  }

  public void keyReleased(char keyChar){
    if(keyChar == 'd'){
      heart.setXDirection(0);
      heart.move();
    }

    if(keyChar == 'a'){
      heart.setXDirection(0);
      heart.move();
    }

    if(keyChar == 'w'){
      heart.setYDirection(0);
      heart.move();
    }

    if(keyChar == 's'){
      heart.setYDirection(0);
      heart.move();
    }
  }

  // pauses program for set amount of time 
  public static void wait(int ns) {
    try {
      Thread.sleep(ns);
    }

    catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }
  
  }
