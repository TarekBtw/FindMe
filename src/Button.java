import greenfoot.*;

public class Button extends Actor {
    private GreenfootImage normalImage;
    private GreenfootImage hoverImage;
    private Runnable onClick;

    public Button(String normalImagePath, String hoverImagePath, Runnable onClick) {
        this.normalImage = new GreenfootImage(normalImagePath);
        this.hoverImage = new GreenfootImage(hoverImagePath);
        this.onClick = onClick;
        setImage(normalImage);
    }



    public void act() {

        if (Greenfoot.mouseMoved(this)) {
            setImage(hoverImage);
        } else if (Greenfoot.mouseMoved(null) && !Greenfoot.mouseMoved(this)) {
            setImage(normalImage);
        }


        if (Greenfoot.mouseClicked(this)) {
            onClick.run();
        }
    }
}