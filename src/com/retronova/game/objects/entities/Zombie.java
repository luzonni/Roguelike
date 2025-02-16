package com.retronova.game.objects.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zombie extends Entity {

    private BufferedImage[] sprite;

    public Zombie(int ID, double x, double y) {
        super(ID, x, y);
        sprite = getSprite("zombie");
    }

    @Override
    public void render(Graphics2D g) {
        renderSprite(sprite[0], g);
    }

    @Override
    public void dispose() {
    }
}
