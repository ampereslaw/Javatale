// this class is responsible for creating the red soul that the player controls. actual movement code is written in the gamepanelfight class

public class Heart extends Sprite {

    public int yVelocity;
    public int xVelocity;
    public final int SPEED = 3;
    public static final int GAME_WIDTH = 300;
    public static final int GAME_HEIGHT = 300;

    public Heart(int x, int y) {
        super(x, y);

        createHeart();
    }

    private void createHeart() {

        loadImage("soul.png");
        getImageDimensions();
    }

  // moves the heart and also ensures it stays within bounds 
  public void move(){
    x = x + xVelocity;
    y = y + yVelocity;
    
    if(y<= 0){
      y = 0;
    }
    if(y >= GAME_HEIGHT - 22){
      y = GAME_HEIGHT- 22;
    }
    if(x <= 0){
      x = 0;
    }
    if(x + 22 >= GAME_WIDTH){
      x = GAME_WIDTH - 22;
    }
  }

    public void setYDirection(int yDirection){
      yVelocity = yDirection;
  }

    public void setXDirection(int xDirection){
      xVelocity = xDirection;
  }

}