package com.retronova.game.objects.tiles;

import com.retronova.exceptions.TileNotFound;
import com.retronova.game.objects.GameObject;

public abstract class Tile extends GameObject {

    private boolean solid;

    public static Tile build(int ID, int x, int y) {
        TileIDs mapping = TileIDs.values()[ID];
        x *= GameObject.SIZE();
        y *= GameObject.SIZE();
        switch (mapping) {
            case Brick -> {
                return new Bricks(ID, x, y);
            }
            case Stone -> {
                return new Stone(ID, x, y);
            }
        }
        throw new TileNotFound("Tile not found");
    }

    Tile(int ID, int x, int y, boolean solid) {
        super(ID);
        setX(x);
        setY(y);
        setSolid(solid);
    }

    private void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isSolid() {
        return this.solid;
    }

}
