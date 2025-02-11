package com.retronova.game.objects.entities;

import com.retronova.exceptions.EntityNotFound;
import com.retronova.game.objects.GameObject;
import com.retronova.graphics.SpriteSheet;

import java.awt.image.BufferedImage;

public abstract class Entity extends GameObject {

    public static Entity build(int ID, int x, int y) {
        Entity entity;
        throw new EntityNotFound("Entity not found");
    }

    public Entity(int ID, int x, int y) {
        super(ID);
        setX(x);
        setY(y);
    }

}
