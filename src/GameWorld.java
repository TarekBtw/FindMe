import greenfoot.*;
import java.util.Objects;
import java.util.Scanner;
import org.json.*;

public class GameWorld extends World {
    private static final int WORLD_WIDTH = 1440;
    private static final int WORLD_HEIGHT = 880;
    private Player player;
    private Scroller scroller;

    public GameWorld(int totalTime, int radius) {
        super(WORLD_WIDTH, WORLD_HEIGHT, 1, false);
        setPaintOrder(YouWin.class, GameOver.class, FPSCounter.class, Timer.class, PlayerHitbox.class, Player.class, TargetObject.class, Overlay.class, WallBrickDesign.class, Boden.class, Tile.class);
        loadFloorsFromCSV("map4.csv");
        loadWallsFromCSV("map4.csv");
        loadObjectsFromJSON("map4.json");
        addObject(new FPSCounter(), 50, 20);
        addTimer(totalTime);
        scroller = new Scroller(this, null);
        addPlayer();
        addOverlay(radius);
        addObject(new TargetObject(), 30,30);
    }


    public void act() {
        int dx = player.getX() - getWidth()/2;
        int dy = player.getY() - getHeight()/2;
        scroller.scroll(dx, dy);

        if (Greenfoot.isKeyDown("escape")) {
            Greenfoot.setWorld(new MainMenu());
        }
    }

    private void loadFloorsFromCSV(String filename) {
        loadTilesFromCSV(filename, false);
    }

    private void loadWallsFromCSV(String filename) {
        loadTilesFromCSV(filename, true);
    }


    private void loadTilesFromCSV(String filename, boolean loadWalls) {
        try (Scanner scanner = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream("/" + filename)))) {
            int y = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] tiles = line.split(",");
                for (int x = 0; x < tiles.length; x++) {
                    int tileType = Integer.parseInt(tiles[x].trim());
                    boolean isWall = (tileType == 0);
                    boolean isGround = (tileType == 1);
                    if (isWall == loadWalls) {
                        addObject(new Tile(isWall, isGround), x * Tile.SIZE, y * Tile.SIZE);
                    }
                }
                y++;
            }
        } catch (Exception e) {
            System.out.println("Fehler beim Laden der Map: " + e);
        }
    }



    private void addPlayer() {
        player = new Player();
        addObject(player, 200, 200);
    }

    private void addOverlay(int radius) {
        addObject(new Overlay(player, scroller, radius), 0, 0);
    }

    private void addTimer(int totaltime) {
        addObject(new Timer(totaltime), getWidth() / 2, 30);
    }


    private void loadObjectsFromJSON(String filename) {
        try (Scanner scanner = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream("/" + filename)))) {
            StringBuilder jsonText = new StringBuilder();
            while (scanner.hasNextLine()) {
                jsonText.append(scanner.nextLine());
            }

            JSONObject json = new JSONObject(jsonText.toString());
            JSONArray layers = json.getJSONArray("layers");

            for (int i = 0; i < layers.length(); i++) {
                JSONObject layer = layers.getJSONObject(i);
                if (layer.getString("type").equals("objectgroup")) {
                    int layerOffsetX = layer.optInt("offsetx", 0);
                    int layerOffsetY = layer.optInt("offsety", 0);

                    JSONArray objects = layer.getJSONArray("objects");
                    for (int j = 0; j < objects.length(); j++) {
                        JSONObject obj = objects.getJSONObject(j);

                        int x = Math.round(obj.getFloat("x")) + layerOffsetX + 7 * Tile.SIZE;
                        int y = Math.round(obj.getFloat("y")) + layerOffsetY + 8 * Tile.SIZE;

                        int gid = obj.optInt("gid", 0);
                        if (gid == 3) {
                            addObject(new Boden(), x, y);
                        } else {
                            addObject(new WallBrickDesign(), x, y);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading JSON objects: " + e);
        }
    }

    void gameOver() {
        Actor gameOver = new GameWorld.GameOver();
        addObject(gameOver, getWidth() / 2, getHeight() / 2);
    }

    static class GameOver extends Actor {
        public GameOver() {
            setImage("game_over2.png");
        }
    }

    void YouWin() {
        Actor youWin = new GameWorld.YouWin();
        addObject(youWin, getWidth() / 2, getHeight() / 2);
    }

    static class YouWin extends Actor {
        public YouWin() {
            setImage("you_win.png");
        }
    }

}