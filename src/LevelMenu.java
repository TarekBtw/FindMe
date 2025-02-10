import greenfoot.*;

public class LevelMenu extends World {

    public LevelMenu() {
        super(1680, 1080, 1);
        addObject(new FPSCounter(), 50,20);
        setBackground("images/ezgif-frame-001.png");
        addLogo();
        addLevelUI();
        addButtons();
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
}
