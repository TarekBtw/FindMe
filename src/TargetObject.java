import greenfoot.*;

public class TargetObject extends Actor {
    private boolean isVisible;
    public TargetObject() {
        setImage("Golden_Key(1).png");
    }

    @Override
    protected void addedToWorld(World world) {
        spawnSafeLocation();
    }

    public void act() {
        updateVisibility();

        if (Greenfoot.mouseMoved(this) && isVisible) {
            setImage("Golden_Key(2).png");
        } else if (isVisible) {
            setImage("Golden_Key(1).png");
        }

        if (Greenfoot.mouseClicked(this) && isVisible) {
            GreenfootSound win_sound = new GreenfootSound("win_sound.wav");
            win_sound.setVolume(100);
            GameWorld gameWorld = (GameWorld) getWorld();
            gameWorld.removeObject(this);
            Player player = (Player) gameWorld.getObjects(Player.class).get(0);
            player.stopWalkingSound();
            gameWorld.YouWin();
            win_sound.play();
            Greenfoot.delay(500);
            Greenfoot.setWorld(new MainMenu());
        }
    }


    private void updateVisibility() {
        World world = getWorld();
        Overlay overlay = world.getObjects(Overlay.class).get(0);
        Player player = overlay.getPlayer();
        int flashlightRadius = overlay.getRadius();

        int dx = getX() - player.getX();
        int dy = getY() - player.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= flashlightRadius) {
            getImage().setTransparency(255);
            isVisible = true;
        } else {
            getImage().setTransparency(0);
            isVisible = false;
        }
    }

    private void spawnSafeLocation() {
        GameWorld world = (GameWorld) getWorld();
        int maxAttempts = 500;

        for (int i = 0; i < maxAttempts; i++) {
            int tileX = Greenfoot.getRandomNumber(3000 / Tile.SIZE);
            int tileY = Greenfoot.getRandomNumber(3000 / Tile.SIZE);
            int x = tileX * Tile.SIZE + Tile.SIZE / 2;
            int y = tileY * Tile.SIZE + Tile.SIZE / 2;

            if (!isWall(x, y) && (!isWall(x + 10, y + 10)) && (!isWall(x - 10, y - 10)) && isGround(x, y)) {
                setLocation(x, y);
                break;
            }
        }
    }

    private boolean isWall(int x, int y) {
        java.util.List<Tile> tiles = getWorld().getObjectsAt(x, y, Tile.class);
        boolean isWall = !tiles.isEmpty() && tiles.get(0).isWall();
        return isWall;
    }

    private boolean isGround(int x, int y) {
        java.util.List<Tile> tiles = getWorld().getObjectsAt(x, y, Tile.class);
        boolean isGround = !tiles.isEmpty() && tiles.get(0).isGround();
        return isGround;
    }
}