import greenfoot.*;

public class Player extends Actor {
    private final int speed = 2;
    private final int animation_delay_walking = 5;
    private final int animation_delay_idle = 50;
    private final int animation_delay_dead = 10;
    private GreenfootImage[] idleRight, walkRight;
    private GreenfootImage[] idleLeft, walkLeft;
    private GreenfootImage[] dead;
    private String facing = "right";
    private boolean isMoving = false;
    private boolean isDead = false;
    private int animationFrame = 0;
    private int deathAnimationFrame = 0;
    private int animationCounter = 0;
    private PlayerHitbox hitbox;
    private GreenfootSound walking_sound = new GreenfootSound("walking_sound.wav");


    public Player() {
        loadAnimations();
        setImage(idleRight[0]);
    }

    @Override
    protected void addedToWorld(World world) {
        spawnSafeLocation();
        int hitboxWidth = getImage().getWidth() / 2;
        int hitboxHeight = getImage().getHeight() / 2;
        hitbox = new PlayerHitbox(hitboxWidth, hitboxHeight);
        world.addObject(hitbox, getX(), getY());
    }

    public void act() {
        hitbox.setLocation(getX(), getY()+10);

        if (!isDead) {
            handleMovement();
            handleAnimation();
            checkTimer();
        } else {
            deathAnimation();
        }
    }



    private void loadAnimations() {
        idleRight = loadFrames("images/player/idle/idle", 11);
        walkRight = loadFrames("images/player/walk/walk", 10);
        dead = loadFrames("images/player/dead/dead", 5);

        idleLeft = mirrorImages(idleRight);
        walkLeft = mirrorImages(walkRight);
        dead = mirrorImages(dead);
    }

    private GreenfootImage[] loadFrames(String baseName, int count) {
        GreenfootImage[] frames = new GreenfootImage[count];
        for(int i = 0; i < count; i++) {
            try {
                frames[i] = new GreenfootImage(baseName + (i + 1) + ".png");
            } catch(Exception e) {
                System.err.println("Missing frame: " + baseName + (i + 1) + ".png");
            }
        }
        return frames;
    }

    private GreenfootImage[] mirrorImages(GreenfootImage[] originals) {
        GreenfootImage[] mirrored = new GreenfootImage[originals.length];
        for(int i = 0; i < originals.length; i++) {
            mirrored[i] = new GreenfootImage(originals[i]);
            mirrored[i].mirrorHorizontally();
        }
        return mirrored;
    }

    private void handleMovement() {
        int dx = 0;
        int dy = 0;
        isMoving = false;

        boolean left = Greenfoot.isKeyDown("a");
        boolean right = Greenfoot.isKeyDown("d");
        boolean up = Greenfoot.isKeyDown("w");
        boolean down = Greenfoot.isKeyDown("s");

        if (left) {
            dx -= speed;
            facing = "left";
            isMoving = true;
        }
        if (right) {
            dx += speed;
            facing = "right";
            isMoving = true;
        }
        if (up) {
            dy -= speed;
            isMoving = true;
        }
        if (down) {
            dy += speed;
            isMoving = true;
        }

        if (dx != 0 && dy != 0) {
            dx = (int) (dx * Math.sqrt(0.5));
            dy = (int) (dy * Math.sqrt(0.5));
        }

        boolean canMoveX = !isCollidingWithWall(getX() + dx, getY());
        boolean canMoveY = !isCollidingWithWall(getX(), getY() + dy);

        if (!canMoveX && canMoveY) {
            dx = 0;
        } else if (canMoveX && !canMoveY) {
            dy = 0;
        } else if (!canMoveX && !canMoveY) {
            dx = 0;
            dy = 0;
        }
        setLocation(getX() + dx, getY() + dy);

        if (isMoving) {
            if (!walking_sound.isPlaying()) {
                walking_sound.playLoop();
            }
        } else {
            walking_sound.stop();
        }
    }

    public void stopWalkingSound() {
        walking_sound.stop();
    }


    private void handleAnimation() {
        int currentDelay;
        if (isMoving) {
            currentDelay = animation_delay_walking;
        } else {
            currentDelay = animation_delay_idle;
        }

        if (animationCounter++ < currentDelay) return;
        animationCounter = 0;

        GreenfootImage[] currentAnimation;
        if (isMoving) {
            if (facing.equals("left")) {
                currentAnimation = walkLeft;
            } else {
                currentAnimation = walkRight;
            }
        } else {
            if (facing.equals("left")) {
                currentAnimation = idleLeft;
            } else {
                currentAnimation = idleRight;
            }
        }

        animationFrame = (animationFrame + 1) % currentAnimation.length;
        setImage(currentAnimation[animationFrame]);
    }

    private void deathAnimation() {
        if (animationCounter++ < animation_delay_dead) return;
        animationCounter = 0;

        if (deathAnimationFrame < dead.length) {
            setImage(dead[deathAnimationFrame]);
            deathAnimationFrame++;
        } else {
            GreenfootSound lose_sound = new GreenfootSound("lose_sound.wav");
            lose_sound.setVolume(100);
            GameWorld gameWorld = (GameWorld) getWorld();
            walking_sound.stop();
            gameWorld.gameOver();
            lose_sound.play();
            Greenfoot.delay(500);
            Greenfoot.setWorld(new MainMenu());
        }
    }

    private void checkTimer() {
        Timer timer = getWorld().getObjects(Timer.class).get(0);
        if (timer != null && timer.isGameOver()) {
            isDead = true;
            deathAnimationFrame = 0;
            animationCounter = 0;
        }
    }

    private boolean isCollidingWithWall(int x, int y) {
        int newHitboxX = x;
        int newHitboxY = y + 10;
        int oldHitboxX = hitbox.getX();
        int oldHitboxY = hitbox.getY();
        hitbox.setLocation(newHitboxX, newHitboxY);
        boolean collision = hitbox.getIntersectingTiles().stream().anyMatch(Tile::isWall);
        hitbox.setLocation(oldHitboxX, oldHitboxY);
        return collision;
    }


    private boolean isHitboxSafe(int x, int y) {
        int hitboxY = y + 10;
        java.util.List<Tile> tiles = getWorld().getObjectsAt(x, hitboxY, Tile.class);
        return tiles.isEmpty() || !tiles.get(0).isWall();
    }


    private void spawnSafeLocation() {
        GameWorld world = (GameWorld) getWorld();
        int maxAttempts = 500;

        for (int i = 0; i < maxAttempts; i++) {
            int tileX = Greenfoot.getRandomNumber(world.getWidth() / Tile.SIZE);
            int tileY = Greenfoot.getRandomNumber(world.getHeight() / Tile.SIZE);
            int x = tileX * Tile.SIZE + Tile.SIZE / 2;
            int y = tileY * Tile.SIZE + Tile.SIZE / 2;

            if (!isWall(x, y) &&
                    !isWall(x + 40, y + 40) &&
                    !isWall(x - 40, y - 40) &&
                    !isWall(x + 90, y + 90) &&
                    !isWall(x - 90, y - 90) &&
                    isGround(x, y) &&
                    isHitboxSafe(x, y)) {
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