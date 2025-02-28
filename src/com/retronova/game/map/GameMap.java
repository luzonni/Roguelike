package com.retronova.game.map;

import com.retronova.game.objects.GameObject;
import com.retronova.game.objects.entities.Entity;
import com.retronova.game.objects.entities.Player;
import com.retronova.game.objects.tiles.TileIDs;
import com.retronova.game.objects.tiles.Tile;
import com.retronova.graphics.SpriteSheet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GameMap {

    private int length;
    private Rectangle bounds;

    private List<Entity> entities;
    private final Tile[] map;

    private Waves waves;

    public GameMap() {
        this.map = loadMap();
        this.entities = new ArrayList<>();
        this.waves = new Waves(this);
    }

    public Player addPlayer(Player player) {
        getEntities().add(player);
        player.setX(getBounds().getWidth()/2);
        player.setY(getBounds().getHeight()/2);
        return player;
    }

    private Tile[] loadMap() {
        BufferedImage mapImage = new SpriteSheet("maps", "easy", 1).getSHEET();
        int width = mapImage.getWidth();
        int height = mapImage.getHeight();
        this.bounds = new Rectangle(width * GameObject.SIZE(), height * GameObject.SIZE());
        int[] rgb = mapImage.getRGB(0, 0, width, height, null, 0, width);
        this.length = width;
        return convertMap(rgb, width, height);
    }

    private Tile[] convertMap(int[] rgb, int width, int height) {
        Tile[] map = new Tile[width * height];
        TileIDs[] values = TileIDs.values();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = x + y * width;
                for (TileIDs value : values) {
                    if (Color.decode(value.getColor()).getRGB() == rgb[index]) {
                        map[index] = Tile.build(value.ordinal(), x, y);
                    }
                }
            }
        }
        return map;
    }

    public Tile[] getMap() {
        return this.map;
    }

    public Waves getWaves() {
        return waves;
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 || x >= length || y >= map.length / length) {
            return null; // Evita erro
        }
        return getMap()[x + y * length];
    }


    public List<Entity> getEntities() {
        if (entities == null) {
            entities = new ArrayList<>(); // evita erro de ponteiro
        }
        return entities;
    }


    public Rectangle getBounds() {
        return this.bounds;
    }
}
