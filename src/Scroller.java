import greenfoot.*;

public class Scroller {
    private World world;
    private GreenfootImage scrollImage;

    public Scroller(World world) {
        this(world, null);
    }

    public Scroller(World world, GreenfootImage image) {
        this.world = world;
        this.scrollImage = image;
        if (scrollImage != null) {
            world.setBackground(scrollImage);
        }
    }

        public World getWorld() {
            return this.world;
        }


    public void scroll(int dx, int dy) {
        for (Object obj : world.getObjects(null)) {
            Actor actor = (Actor) obj;
            if (actor instanceof FPSCounter) {
                continue;
            }
            if (actor instanceof Timer) {
                continue;
            }
            actor.setLocation(actor.getX() - dx, actor.getY() - dy);
        }
    }
}