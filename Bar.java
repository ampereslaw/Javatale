// bar class is responsible for creating the vertical bar that moves across the screen during the attack turn. 

import java.awt.*;

public class Bar extends Rectangle{

  public int speed = 5;
  public boolean visible = true;

  public Bar(int x, int y) {
    super(x, y, 20, 300);
  }

  public void move(){
    x = x - speed;
  }

  public boolean isVisible() {
    return visible;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
  }

  public Rectangle getBounds() {
    return new Rectangle(x, y, width, height);
  }

  public void setX(int xNew){
    x = xNew;
  }

  public void setY(int yNew){
    y = yNew;
  }


}