//Menu class that controls the menu functions. has keyListener methods and when turn is finished, sets boolean finish as true in which no key functions do anything. Ends system if Greater Dog is successfully killed or spared (met spare criteria or kills it to 0hp)
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;
import java.lang.Math;

public class Menu extends JPanel{
  //JLabels for menu buttons 
  private JLabel fight = new JLabel();
  private JLabel act = new JLabel();
  private JLabel item = new JLabel();
  private JLabel mercy = new JLabel();

  //JLabels for sub menu buttons
  private JLabel [] labels = new JLabel[5];

  //to tell what the choice is
  private int index = 0;
  private int subIndex = 1;

  //to tell how many options there are
  private int [] subMenuOptions = new int [] {2, 6, 4, 2};

  //to tell if the menu or submenu has been selected
  private boolean indexSelected = false;
  private boolean subIndexSelected = true;

  private String [] inventory = new String [] {"stick", "knife", "treats"};
  public int monsterHp = 30;
  public boolean dead = false;
  public boolean spared = false;

  //these are all criteria for action dialogue and spare criteria
  private boolean close = false;
  private int petCounter = 0;
  private int ignoreCounter = 0;
  private boolean hasPlay = false;


  public boolean finish = true;

  public Menu (JPanel innerPanel){
    this.setBounds(20, 326, 600, 74);
    this.setBackground(Color.BLACK);
    this.setLayout(new GridLayout(1, 4, 5, 5));
      this.add(fight);
      this.add(act);
      this.add(item);
      this.add(mercy);
    

    for (int i = 0;i<5;i++) {
        labels[i] = new JLabel("");
        labels[i].setForeground(Color.WHITE);
        labels[i].setFont(new Font("Comic Sans", Font.BOLD, 18));
        innerPanel.add(labels[i]);
    }
    innerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 70, 4));
    subMenuSet(true);
    menuSet();

    
  }

  public void keyPressed(int keyCode, char keyChar) {
        if (!finish) {
        if (keyCode == KeyEvent.VK_RIGHT && !indexSelected)
          ++index;
        else if (keyCode == KeyEvent.VK_LEFT && !indexSelected)
          --index;
        else if (keyCode == KeyEvent.VK_UP && !subIndexSelected)
          --subIndex;
        else if (keyCode == KeyEvent.VK_DOWN && !subIndexSelected)
          ++subIndex;
        if (!indexSelected) 
          menuLight();
        if (!subIndexSelected)
          subMenuLight();
        if (keyChar == 'z') {
          if(!indexSelected) {
          menuSet();
          choice();
          indexSelected = true;
          subIndexSelected = false;
          subIndex = 1;
          subMenuSet(false);
          subMenuLight();
          }
          else if (!subIndexSelected){
              if (index == 1)
                fight();
              else if (index == 2) 
                actChoice();
              else if (index == 3)
                itemChoice();
              else if (index == 4) 
                spare();
          subIndexSelected = true;
          indexSelected = false;
          index = 0;
          subIndex = 1;

          finish = true;

          }
        }
        else if (keyChar == 'x' && !subIndexSelected) {
          setBoxText("");
          indexSelected = false;
          index = 1;
          menuLight();
        }
			}
  }

  //'lights up' specific menu button
  private void menuLight() {
    menuSet();

    if (index == 5) 
    index = 1;
    else if (index == 0) 
    index = 4;
    if (index == 1) 
    fight.setIcon(new ImageIcon("fight2.png"));
    else if (index == 2)
    act.setIcon(new ImageIcon("act2.png"));
    else if(index == 3)
    item.setIcon(new ImageIcon("item2.png"));
    else if (index == 4)
    mercy.setIcon (new ImageIcon("mercy2.png"));
  }

  //changes current subIndex to red
  private void subMenuLight(){
    subMenuSet(false);
    if(subIndex == subMenuOptions[index-1])
      subIndex = 1;
    else if (subIndex == 0) 
      subIndex = subMenuOptions[index-1]-1;
    for (int i = 0; i<subMenuOptions[index-1]; i++) {
      if (subIndex == i +1) 
      labels[i].setForeground(Color.RED);
    }
  }

  //if true, the labels all disappear, if false, the labels turn white
  private void subMenuSet(boolean disappear) {
    for (int i = 0; i<5; i++) {
      if (disappear)
      labels[i].setVisible(false);
      else 
      labels[i].setForeground(Color.WHITE);
    }
  }

  //sets all menu options to default sprites
  private void menuSet() {
    fight.setIcon(new ImageIcon("FIGHT_sprite_button.png"));
		act.setIcon(new ImageIcon("ACT_sprite_button.png"));
		item.setIcon(new ImageIcon("ITEM_sprite_button.png"));
		mercy.setIcon(new ImageIcon("MERCY_sprite_button.png"));
  }

  //to set which menu options show as what (blank for empty)
  private void choice() {
    if (index == 1) {
      setChoices("Greater Dog", "", "","", "");
    }
    else if (index == 2) 
      setChoices("Check", "Beckon", "Ignore", "Pet", "Play");
    else if (index == 3) 
      setChoices(inventory[0], inventory[1], inventory[2], "", "");
    else if (index == 4) 
      setChoices("Spare", "", "", "", "");
    
    indexSelected = false;
  }

  //sets menu options and displays them
  private void setChoices(String op1, String op2, String op3, String op4, String op5) {
    String ops [] = new String[] {op1, op2, op3, op4, op5};
    for (int i=1; i<subMenuOptions[index -1]; i++) {
      labels[i-1].setText("*" + ops[i-1]);
      labels[i-1].setVisible(true);
    }
  }

  //random number generates, subtracts from monsters current hp and checks for death
  private void fight () {
    int random = (int)(Math.random() * 10) + 3;
    monsterHp = monsterHp - random;
    setBoxText("<html>  You dealt " + random + " damage! <br/> It now has " + monsterHp + " health</html>");
    if (monsterHp <= 0) {
      setBoxText("You've killed the Greater Dog :  )");
      dead = true;
    }
  }

  //checks which dialogue option based on booleans and subIndex
  private void actChoice () {
    labels[0].setVisible(false);
    labels[0].setForeground(Color.WHITE);
    if (subIndex == 1) {
      setBoxText("<html>It's so excited that it thinks <br/> fighting is just play!!</html>");
    }
    else if (subIndex == 2 && !close) {
      setBoxText("<html>You call the Greater Dog.<br/> It bounds toward you, flecking slobber <br/> into your face.</html>");
      close = true;
    }
    else if (subIndex == 2 && close) {
      setBoxText("<html>Greater Dog's ears perk up. <br/> Nothing else happens. </html>");
    }
    else if (subIndex == 3 && ignoreCounter<=2) {
      setBoxText("<html>Greater Dog inches closer.</html>");
      ignoreCounter++;
      close = true;
    }
    else if (subIndex == 3 && ignoreCounter>=4) {
      setBoxText("<html>Greater Dog decides you are too boring.</html>");
    }
    else if (subIndex == 3 && ignoreCounter>=3) {
      setBoxText("<html>Greater Dog is making puppy-dog eyes. </html>");
    }
    else if (subIndex == 4 && !close) {
      setBoxText("<html>Greater Dog is too far away to pet. <br/> You just pet the air.</html>");
    }
    else if (subIndex == 4 && close && petCounter == 0) {
      setBoxText("<html>Greater Dog curls up in your lap as <br/> it is pet by you. It gets so comfortable <br/> it falls asleep... Zzzzz... ... <br/>Then it wakes up! It's so excited!</html>");
      petCounter++;
    }
    else if (subIndex == 4 && !hasPlay) {
      setBoxText("<html>Greater Dog's excitement is creating a power <br/>field that prevents petting.</html>");
    }
    else if (subIndex == 4 && hasPlay && petCounter == 1) {
      setBoxText("<html>As you pet the dog, it sinks<br/> its entire weight into you... <br/>Your movements slow. <br/>But, you still haven't pet enough...</html>");
      petCounter++;
    }
    else if (subIndex == 4 && hasPlay && petCounter == 2) {
      setBoxText("<html>You pet decisively. <br/>Pet capacity reaches 100 percent. <br/>The dog flops over with its legs <br/>hanging in the air.</html>");
      petCounter++;
    }
    else if (subIndex == 4 && hasPlay && petCounter == 3) {
      setBoxText("<html>Tummy rubs are forbidden.</html>");
    }
    else if (subIndex == 5 && petCounter>1) {
      setBoxText("<html>Greater Dog is not excited enough to play with.</html>");
    }
    else if (subIndex == 5 && hasPlay) {
      setBoxText("<html>Greater Dog is too tired to play.</html>");
    }
    else if (subIndex == 5 && petCounter>=1) {
      setBoxText("<html>You make a snowball and throw it <br/>for the dog to fetch. It splats on the ground. <br/>Greater Dog picks up all the snow in the area<br/> and brings it to you. Now dog is very tired... <br/>It rests its head on you...</html>");
      hasPlay = true;
    }

  }

  //uses item and sets its place to empty
  private void itemChoice () {
    if (subIndex == 1 && (Arrays.asList(inventory).contains("stick"))) {
      setBoxText("<html>You threw the stick and the dog <br/> ran to get it. You played fetch <br/> for a while.</html>");
      inventory[0] = "empty";
    }
    else if (subIndex == 2 && (Arrays.asList(inventory).contains("knife"))) {
      monsterHp = monsterHp - 15;
      setBoxText("<html>You stabbed the Greater Dog <br/> (You monster!) It lost 15 hp<br/>It now has " + monsterHp + " hp</html>");
      inventory[1] = "empty";

    }
    else if (subIndex == 3 && (Arrays.asList(inventory).contains("treats"))) {
      monsterHp = monsterHp + 8;
      setBoxText("<html>You fed the Greater Dog some treats.<br/>  It restored 8 hp, <br/> It now has " + monsterHp + " hp.</html>");
      inventory[2] = "empty";
    }
    else {
      setBoxText("<html>You've already used it.</html>");
    }

  }

  //checks if one of the spare criteria are met
  private void spare() {
    if (petCounter == 3 || ignoreCounter == 4 || !(Arrays.asList(inventory).contains("stick"))) {
    setBoxText("SPARED!");
    GamePanelFight.wait(4000);
    spared = true;
    }
    else 
    setBoxText("You cannot spare the Greater Dog.");
  }

  //sets box text to String text
  public void setBoxText(String text) {
    labels[0].setForeground(Color.WHITE);
    subMenuSet(true);
    labels[0].setText(text);
    labels[0].setVisible(true);

  }


}
