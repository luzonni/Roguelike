package com.retronova.game.objects.tiles;

import com.retronova.engine.Engine;
import com.retronova.game.objects.entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Stone extends Tile {

    private static BufferedImage[] sprites;
    private int index = 0;

    Stone(int id, int x, int y) {
        super(id, x, y, false);
        if(Stone.sprites == null)
            Stone.sprites = this.loadSprite("stone", 1);
        index = Engine.RAND.nextInt(Stone.sprites.length);
    }

    @Override
    public BufferedImage getSprite() {
        return sprites[0];
    }

    @Override
    public void effect(Entity e) {

    }

    @Override
    public void render(Graphics2D g) {
        renderSprite(sprites[index], g);
    }

    @Override
    public void dispose() {
        Stone.sprites = null;
    }

}
