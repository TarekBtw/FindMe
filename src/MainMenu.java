import greenfoot.*;


public class MainMenu extends World {

    public MainMenu () {
        super(1680, 1080, 1);
        addObject(new FPSCounter(), 50,20);
        setBackground("images/BackGround.png");
        addButtons();
        addLogo();
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
}
