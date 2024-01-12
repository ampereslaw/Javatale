// dog class is responsible for creating the little dog that sits at the bottom of the screen barking during the attacking turn. this dog will still do damage if it touches the heart!

public class Dog extends Sprite {

    public Dog(int x, int y) {
        super(x, y);

        createDog();
    }

    private void createDog() {
        loadImage("SleepingDoggo.png");
        getImageDimensions();
    }

}