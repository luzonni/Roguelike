package com.retronova.game.objects.entities;

import com.retronova.game.Game;
import com.retronova.engine.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MouseVampire extends Entity {

    private final BufferedImage[][] sprite;
    private int countAnim;
    private int indexState;
    private int indexAnim;

    MouseVampire(int ID, double x, double y) {
        super(ID,x,y,0.3);
        sprite = new BufferedImage[][] {loadSprite("mousevampire", 0), loadSprite("mousevampire", 1)};
        //adicionar a resistência a algo
        setSolid();
        setAlive();
    }

    @Override
    public BufferedImage getSprite(){
        return sprite[indexState][indexAnim];
    }

    public void tick() {
        moveIA();
        animation();
        //criar função de dano
    }

    public void moveIA() {
        Player player = Game.getPlayer();
        double radians = Math.atan2(player.getY() - getY(), player.getX() - getX());
        getPhysical().addForce(0.50, radians);


    }

    public void animation() {
        countAnim ++;
        if(countAnim > 10) {
            countAnim = 0;
            indexAnim ++;
            if(indexAnim >= sprite[indexState].length) {
                indexAnim = 0;
            }
        }

    }

    public void render(Graphics2D d) {
        int orientation = getPhysical().getOrientation()[0] * -1;
        if (orientation == 0) {
            orientation = -1;
        }
        BufferedImage sprite = SpriteSheet.flip(getSprite(), 1, orientation);
        renderSprite(sprite, d);
    }

    @Override
    public void dispose() {}
}
