import greenfoot.*;

public class Timer extends Actor {
    private long startTime = System.currentTimeMillis();
    private int totalTime;
    private boolean isGameOver = false;

    public Timer(int totalTime) {
        this.totalTime = totalTime;
    }

    public void act() {
        updateImage();
    }

    private void updateImage() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        long remainingTime = totalTime - elapsedTime;

        if (remainingTime < 0) {
            remainingTime = 0;
            isGameOver = true;
        }


        int seconds = (int) (remainingTime / 1000) % 60;
        int minutes = (int) (remainingTime / 60000);
        String timeString = String.format("Time Remaining: %02d:%02d", minutes, seconds);
        setImage(new GreenfootImage(timeString, 24, Color.WHITE, new Color(0, 0, 0, 0)));
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}