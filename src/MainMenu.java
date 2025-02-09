import greenfoot.*;
import java.util.List;
import java.util.ArrayList;

public class MainMenu extends World {
    private final List<GreenfootImage> frames;
    private int frameIndex = 0;
    private final int frameDelay = 5;
    private int delayCounter = 0;

    public MainMenu () {
        super(1680, 1080, 1);
        addObject(new FPSCounter(), 50,20);
        frames = new ArrayList<>();
        loadFrames();
        setBackground(frames.get(0));
        addButtons();
        addLogo();
    }

    public void act() {
        if (delayCounter++ >= frameDelay) {
            delayCounter = 0;
            frameIndex = (frameIndex + 1) % frames.size();
            setBackground(frames.get(frameIndex));
        }
    }

    private void addLogo() {
        Actor logo = new Logo();
        addObject(logo, getWidth()/2, getHeight() - getHeight() + 200);
    }

    static class Logo extends Actor {
        public Logo() {
            setImage("FindMeLogo.png");
        }
    }

    private void addButtons() {
        addObject(new Button("images/buttons/start_normal.png", "images/buttons/start_hover.png",
                () -> Greenfoot.setWorld(new LevelMenu())), getWidth()/2, getHeight() - getHeight() + 500);
        
        addObject(new Button("images/buttons/exit_normal.png", "images/buttons/exit_hover.png",
                () -> System.exit(0)), getWidth()/2, getHeight() - getHeight() + 575);
    }

    private void loadFrames() {
        for (int i = 1; i <= 167; i++) {
            String fileName = String.format("images/frames/ezgif-frame-%03d.png", i);
            GreenfootImage frame = new GreenfootImage(fileName);
            frames.add(frame);
        }

    }
}
