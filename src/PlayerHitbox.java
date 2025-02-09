import greenfoot.*;

import java.util.List;

public class PlayerHitbox extends Actor {
    public PlayerHitbox(int width, int height) {
        GreenfootImage hitboxImage = new GreenfootImage(width, height);
        hitboxImage.setColor(new Color(255, 0, 0, 100));
        hitboxImage.fill();
        hitboxImage.setTransparency(0);
        setImage(hitboxImage);
    }

    public List<Tile> getIntersectingTiles() {
        return super.getIntersectingObjects(Tile.class);
    }
}
