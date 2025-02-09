import greenfoot.*;

import java.util.ArrayList;
import java.util.List;

public class LevelMenu extends World {
    private final List<GreenfootImage> frames;
    private int frameIndex = 0;
    private final int frameDelay = 5;
    private int delayCounter = 0;
    public LevelMenu() {
        super(1680, 1080, 1);
        addObject(new FPSCounter(), 50,20);
        frames = new ArrayList<>();
        loadFrames();
        addLogo();
        addLevelUI();
        addButtons();
    }

    public void act() {

        if (delayCounter++ >= frameDelay) {
            delayCounter = 0;
            frameIndex = (frameIndex + 1) % frames.size();
            setBackground(frames.get(frameIndex));
        }
    }

    private void addLogo() {
        Actor logo = new LevelMenu.Logo();
        addObject(logo, getWidth()/2, 150);
    }

    static class Logo extends Actor {
        public Logo() {
            setImage("select_level1.png");
        }
    }

    private void addLevelUI() {
        Actor levelUI = new LevelMenu.levelUI();
        addObject(levelUI, getWidth()/2, 500);
    }

    static class levelUI extends Actor {
        public levelUI() {
            setImage("levelUI.png");
        }
    }

    private void addButtons() {
        addObject(new Button("images/buttons/LevelEasy.png", "images/buttons/LevelEasy_Hover.png",
                () -> Greenfoot.setWorld(new GameWorld(180000,150))), getWidth()/ 2 - 225, 500);
        addObject(new Button("images/buttons/LevelMedium.png", "images/buttons/LevelMedium_Hover.png",
                () -> Greenfoot.setWorld(new GameWorld(120000,100))), getWidth()/ 2, 500);
        addObject(new Button("images/buttons/LevelHard.png", "images/buttons/LevelHard_Hover.png",
                () -> Greenfoot.setWorld(new GameWorld(6000 ,50))), getWidth()/ 2 + 225, 500);
    }

    private void loadFrames() {
        for (int i = 1; i <= 167; i++) {
            String fileName = String.format("images/frames/ezgif-frame-%03d.png", i);
            GreenfootImage frame = new GreenfootImage(fileName);
            frames.add(frame);
        }
    }
}
