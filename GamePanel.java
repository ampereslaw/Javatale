//main Panel, extends JLayeredPane so components can be layered on it. 
//includes KeyListener to register key inputs and Runnable for gameThread to continuously check for booleans 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
  
public class GamePanel extends JLayeredPane implements Runnable, KeyListener{
  private JPanel outerPanel = new JPanel();
  private JPanel innerPanel = new JPanel();
  private Thread gameThread = new Thread(this);
  public Menu menu = new Menu(innerPanel);
  public GamePanelFight fight = new GamePanelFight();
  public boolean displayMenu = false;
  public JLabel greaterDog = new JLabel();
  public JLabel health = new JLabel();


  public GamePanel() {
    //setting main background panels
    this.setBounds(0, 0, 600, 400);
    outerPanel.setBackground(Color.BLACK);
    outerPanel.setBounds(0, 0, 600, 400);
    innerPanel.setBackground(Color.BLACK);
    innerPanel.setBounds(50, 200, 500, 130);
    innerPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
    this.add(outerPanel, 0, 0);
    this.add(innerPanel, 2, 0);
    //adding menu on top
    this.add(menu, 1, 0);
    //Greater Dog sprite
    greaterDog.setIcon(new ImageIcon ("GreaterDog.png"));
    this.add(greaterDog, 3, 0);
    greaterDog.setBounds(200, 10, 300, 200);
    //Fighting panel
    this.add(fight, 4, 0);
    fight.setBounds(150,20,300,300);
    this.setOpaque(true);
    this.setBackground(Color.BLACK);
    this.setPreferredSize(new Dimension(300, 300));
    this.setVisible(true);
    this.addKeyListener(this);
    this.setFocusable(true);

    menu.setBoxText("   It's the Greater Dog :  ) !!!!!!");
    fight.setVisible(false);

    //hp points
    health.setText("HP:" + fight.hp);
    health.setFont(new Font("Comic Sans", Font.BOLD, 18));
    health.setBounds(20, 20, 300, 200);
    health.setForeground(Color.WHITE);
    health.setVisible(true);
    this.add(health, 6, 0);

    gameThread = new Thread(this);
    gameThread.start();
  }

  public void keyPressed(KeyEvent e){
  	int keyCode = e.getKeyCode();
    char keyChar = e.getKeyChar();
    menu.keyPressed(keyCode, keyChar);
    fight.keyPressed(keyChar);
  }

  public void keyReleased(KeyEvent e){
    char keyChar = e.getKeyChar();
    fight.keyReleased(keyChar);
  }

  public void keyTyped(KeyEvent e){
  }
  
  //switches screens
  public void transition() {
    if (displayMenu) {
      greaterDog.setVisible(false);
      innerPanel.setVisible(false);
      fight.setVisible(true);
    }
    else if (!displayMenu) {
      greaterDog.setVisible(true);
      innerPanel.setVisible(true);
      fight.setVisible(false);
    }
  }

 public void run(){
    long lastTime = System.nanoTime();
    double amountOfTicks = 60;
    double ns = 1000000000/amountOfTicks;
    double delta = 0;
    long now;

    while(true){ 
      now = System.nanoTime();
      delta = delta + (now-lastTime)/ns;
      lastTime = now;

      if(delta >= 1){
        fight.move();
        fight.checkCollision();
        health.setText("HP: " + fight.hp);
        repaint();
        delta--;
        if (menu.dead == true) {
          greaterDog.setIcon(new ImageIcon("GreaterDogDead.png"));
          GamePanelFight.wait(10000);
          System.exit(0);
        }
        if (menu.spared == true) {
          GamePanelFight.wait(5000);
          System.exit(0);
        }
        if (menu.finish && displayMenu) {
          GamePanelFight.wait(4000);
          transition();
          displayMenu = false;
          fight.isTurn = true;
          menu.setBoxText("");
        }

        else if (!fight.isTurn && !displayMenu) {
          GamePanelFight.wait(4000);
          transition();
          displayMenu = true;
          menu.finish = false;

        }
      }

    }
  }
}