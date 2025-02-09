import greenfoot.*;

public class Tile extends Actor {
    public static final int SIZE = 40;
    private boolean isWall;
    private boolean isGround;

    public Tile(boolean isWall, boolean isGround) {
        this.isWall = isWall;
        this.isGround = isGround;
        updateImage();
    }

    private void updateImage() {
        GreenfootImage img = new GreenfootImage(SIZE, SIZE);
        if (isWall) {
            img.setColor(Color.BLACK);
        } else {
            img.setColor(Color.WHITE);
        }
        img.fill();
        setImage(img);
    }

    public boolean isWall() {
        return isWall;
    }

    public boolean isGround() {
        return isGround;
    }
}