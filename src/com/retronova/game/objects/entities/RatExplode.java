package com.retronova.game.objects.entities;

import com.retronova.game.Game;
import com.retronova.game.objects.GameObject;
import com.retronova.engine.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RatExplode extends Entity {

    private static BufferedImage[][] sprite;
    private int countAnim;
    private int indexState;
    private int indexAnim;


    //private final int raioExplosao;
    private final double danoExplosao;

    // TODO: Entender pq a sprite ta nervosa virando de um lado pro outro sem motivo
    RatExplode(int ID, double x, double y) {
        super(ID,x,y,0.3);
        this.danoExplosao = 5;
        if(sprite == null) {
            sprite = new BufferedImage[][]{loadSprite("ratexplode", 0), loadSprite("ratexplode", 1)};
        }
        setSolid();
        setAlive();
    }

    @Override
    public BufferedImage getSprite() {
        return sprite[indexState][indexAnim];
    }

    public void tick() {
        moveIA();
        animar();
    }

    public void moveIA() {
        Player player = Game.getPlayer();

        if(this.getDistance(player) < GameObject.SIZE()) {
            explodir(player);
            getPhysical().addForce(0,0);
        }else {
            double radians = Math.atan2(player.getY() - getY(), player.getX() - getX());
            getPhysical().addForce(0.86d, radians);
        }

        this.indexState = getPhysical().isMoving() ? 1 : 0;

    }

    private void explodir(Player player) {
        System.out.println("Mouse explodiu causando " + danoExplosao + " de dano");

    }

    private void animar() {
        countAnim ++;
        if (countAnim > 10) {
            countAnim = 0;
            indexAnim ++;
            if (indexAnim >= sprite[indexState].length) {
                indexAnim = 0;
            }
        }
    }


    public void render(Graphics2D d) {
        int orientation = getPhysical().getOrientation()[0] * -1;
        if(orientation == 0)
            orientation = -1;
        BufferedImage sprite = SpriteSheet.flip(getSprite(), 1, orientation);
        renderSprite(sprite, d);
    }

    public void dispose() {
        sprite = null;
    }


}
