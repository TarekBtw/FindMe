import greenfoot.*;

public class Overlay extends Actor {
    private Player player;
    private Scroller scroller;
    private GreenfootImage overlay;
    private GreenfootImage flashlightMask;
    private int radius;

    public Overlay(Player player, Scroller scroller, int radius) {
        this.player = player;
        this.scroller = scroller;
        this.radius = radius;

        World world = scroller.getWorld();

        overlay = new GreenfootImage(world.getWidth(), world.getHeight());
        flashlightMask = createFlashlightMask(radius);
        updateOverlayImage();
        setImage(overlay);
    }

    public void act() {
        World world = getWorld();
        int viewportCenterX = world.getWidth() / 2;
        int viewportCenterY = world.getHeight() / 2;
        setLocation(viewportCenterX, viewportCenterY);
        updateOverlayImage();
    }


    private void updateOverlayImage() {
        overlay.clear();
        overlay.setColor(new Color(0, 0, 0,150));
        overlay.fill();

        int centerX = scroller.getWorld().getWidth() / 2;
        int centerY = scroller.getWorld().getHeight() / 2;
        overlay.drawImage(flashlightMask, centerX - radius, centerY - radius);
    }

    private GreenfootImage createFlashlightMask(int radius) {
        int diameter = radius * 2;
        GreenfootImage mask = new GreenfootImage(diameter, diameter);
        for (int x = 0; x < diameter; x++) {
            for (int y = 0; y < diameter; y++) {
                int dx = x - radius;
                int dy = y - radius;
                double distance = Math.sqrt(dx * dx + dy * dy);
                if (distance > radius) {
                    mask.setColorAt(x, y, new Color(0, 0, 0, 0));
                } else {
                    int alpha = (int) (180 * (distance / radius));
                    mask.setColorAt(x, y, new Color(0, 0, 0, alpha));
                }
            }
        }
        return mask;
    }

    public Player getPlayer() {
        return player;
    }

    public int getRadius() {
        return radius;
    }

}