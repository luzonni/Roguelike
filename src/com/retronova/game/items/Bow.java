package com.retronova.game.items;

import com.retronova.game.Game;
import com.retronova.game.objects.entities.Arrow;
import com.retronova.game.objects.entities.Entity;
import com.retronova.game.objects.entities.Player;

import java.awt.*;

public class Bow extends Item {

    private double angle = 0;
    private int count;

    Bow(int id) {
        super(id, "Bow", "bow");
    }

    @Override
    public void tick() {
        Player player = Game.getPlayer();
        Entity nearest = player.getNearest(player.getRange());
        if(nearest != null){
            angle = nearest.getAngle(player);
            count++;
            if(count >= player.getAttackSpeed()) {
                count = 0;
                double x = player.getX() + player.getWidth() * Math.cos(angle);
                double y = player.getY() + player.getHeight() * Math.sin(angle);
                Arrow arrow = new Arrow(x, y, player.getRangeDamage(), angle);
                Game.getMap().getEntities().add(arrow);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        Player player = Game.getPlayer();
        int x = (int) player.getX() - Game.C.getX() + player.getWidth() / 2;
        int y = (int) player.getY() - Game.C.getY() + player.getHeight() / 2;
        g.rotate((angle + Math.PI/4), x, y);
        double xx = x - getSprite().getWidth()/2d;
        double yy = y - getSprite().getHeight()/2d;
        g.drawImage(getSprite(), (int)xx, (int)yy, null);
        g.rotate(-(angle + Math.PI/4), x, y);
    }
}
