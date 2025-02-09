import greenfoot.*;

public class FPSCounter extends Actor {
    private long lastTime = System.currentTimeMillis();
    private int frames = 0;
    private int fps = 0;

    public void act() {
        frames++;
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastTime >= 1000) {
            fps = frames;
            frames = 0;
            lastTime = currentTime;
        }

        setImage(new GreenfootImage("FPS: " + fps, 24, Color.WHITE, new Color(0, 0, 0, 0)));
    }
}
