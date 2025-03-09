package com.retronova.game.objects.entities;

import com.retronova.engine.sound.Sound;
import com.retronova.engine.sound.Sounds;
import com.retronova.game.Game;
import com.retronova.engine.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Zombie extends Entity {

    private int countAnim;
    private int cooldown;

    Zombie(int ID, double x, double y) {
        super(ID, x, y, 0.5);
        loadSprites("mousezombie");
        setSolid();
        setAlive();
    }

    public void tick() {
        moveIA();
        countAnim++;
        if(countAnim > 10) {
            countAnim = 0;
            getSheet().plusIndex();
        }
        Player player = Game.getPlayer();
        //Função de dar dano ao jogador.
        cooldown++;
        if(player.getBounds().intersects(this.getBounds()) && cooldown > 45) {
            cooldown = 0;
            player.strike(AttackTypes.Melee, 2);
            Sound.play(Sounds.Zombie);
            player.getPhysical().addForce("knockback_zombie", 0.82d, getPhysical().getAngleForce());
        }
    }

    private void moveIA() {
        Player player = Game.getPlayer();
        double radians = Math.atan2(player.getY() - getY(), player.getX() - getX());
        getPhysical().addForce("move", getSpeed(), radians);
    }

    @Override
    public void render(Graphics2D g) {
        int orientation = getPhysical().getOrientation()[0] * -1;
        if (orientation == 0)
            orientation = -1;
        BufferedImage sprite = SpriteSheet.flip(getSprite(), 1, orientation);
        renderSprite(sprite, g);
    }

}
