// establishes the frame of the game (window) and creates panels to display inside the frame

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

  GamePanel panel;
  


  public GameFrame(){

    panel = new GamePanel(); //run GamePanel constructor
    this.setPreferredSize(new Dimension(616, 439));
    this.setLayout(new BorderLayout());
    this.add(panel, BorderLayout.CENTER);
    this.setResizable(false); //frame can't change size
    this.setTitle("Greater Dog"); //set title for frame 
    this.setBackground(Color.BLACK);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //X button will stop program execution
    this.pack();//makes components fit in window - don't need to set JFrame size, as it will adjust accordingly
    this.setVisible(true); //makes window visible to user
    this.setLocationRelativeTo(null);//set window in middle of screen

  }
  
}