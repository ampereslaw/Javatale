// this class is the parent class of the majority of the various sprites used in this game (namely, the ones that move or do damage). various methods allow the program to retrieve boundaries for collision detection, get dimensions to place it on the screen, and more.

import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Sprite {

    public int x;
    public int y;
    public int width;
    public int height;
    public boolean visible;
    public Image image;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
    }

    public void getImageDimensions() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public Image getImage() {
        return image;
    }

    public void loadImage(String file) {
        ImageIcon imageOne = new ImageIcon(file);
        image = imageOne.getImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    // returns rectangle with dimensions of image. used for collisions 
    public Rectangle getBounds() {
        getImageDimensions();
        return new Rectangle(x, y, width, height);
    }
    
    public void resize(int x, int y){
        image = image.getScaledInstance(x, y, Image.SCALE_DEFAULT);
    }

    public void setX(int xNew){
      x = xNew;
    }

    public void setY(int yNew){
      y = yNew;
    }
}