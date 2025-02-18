package com.retronova.game.objects.entities;

import com.retronova.exceptions.EntityNotFound;
import com.retronova.game.objects.GameObject;
import com.retronova.game.objects.Physical;

public abstract class Entity extends GameObject {

    private Physical physical;

    public static Entity build(int ID, double x, double y) {
        IDs entityId = IDs.values()[ID];
        x *= GameObject.SIZE();
        y *= GameObject.SIZE();
        switch (entityId) {
            case Zombie -> {
                return new Zombie(ID, x, y);
            }
        }
        throw new EntityNotFound("Entity not found");
    }

    public Entity(int ID, double x, double y, double friction) {
        super(ID);
        setX(x);
        setY(y);
        this.physical = new Physical(this, friction);

    }

    @Override
    public void tick() {
        setDepth();
        getPhysical().moment();
    }


    public Physical getPhysical() {
        return this.physical;
    }
}
