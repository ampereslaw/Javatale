// bullet class is responsible for creating the "bark" sprites that move across the screen during the attacking turn.

public class Bullet extends Sprite {

  public int speed = 2;

  public Bullet(int x) {
    super(x, 300);

    createBullet();
  }

  private void createBullet() {
    loadImage("bark.png");
    getImageDimensions();
  }

  public void move(){
    y = y - speed;
  }

}