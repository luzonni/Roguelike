package com.retronova.game.objects.entities;

import com.retronova.engine.Engine;
import com.retronova.game.Game;
import com.retronova.game.objects.GameObject;
import com.retronova.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zombie extends Entity {

    private final BufferedImage[][] sprite;
    private int countAnim;
    private int indexState;
    private int indexAnim;

    private int cooldown;

    public Zombie(int ID, double x, double y) {
        super(ID, x, y, 0.6);
        sprite = new BufferedImage[][] {getSprite("zombie", 0), getSprite("zombie", 1)};

        // Zumbi não deve ter resistencia a nada não eu acho, que eles são lascado já né.
    }

    @Override
    public BufferedImage getSprite() {
        return sprite[indexState][indexAnim];
    }

    public void tick() {
        moveIA();
        countAnim++;
        if(countAnim > 10) {
            countAnim = 0;
            indexAnim++;
            if(indexAnim >= sprite[indexState].length) {
                indexAnim = 0;
            }
        }
        Player player = Game.getPlayer();
        //Função de dar dano ao jogador.
        cooldown++;
        if(player.getBounds().intersects(this.getBounds()) && cooldown > 45) {
            cooldown = 0;
            player.strike(AttackTypes.Melee, 2);
            player.getPhysical().addForce(4.5d, getPhysical().getAngleForce());
        }
    }

    private void moveIA() {
        Player player = Game.getPlayer();
        if(this.getDistance(player) < GameObject.SIZE()*7) {
            double radians = Math.atan2(player.getY() - getY(), player.getX() - getX());
            getPhysical().addForce(0.86d, radians);
        }
        this.indexState = getPhysical().isMoving() ? 1 : 0;
    }

    @Override
    public void render(Graphics2D g) {
        int orientation = getPhysical().getOrientation()[0] * -1;
        if (orientation == 0)
            orientation = -1;
        BufferedImage sprite = SpriteSheet.flip(getSprite(), 1, orientation);
        renderSprite(sprite, g);
    }

    @Override
    public void dispose() {

    }

}
