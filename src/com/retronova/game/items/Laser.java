package com.retronova.game.items;

import com.retronova.engine.graphics.Rotate;
import com.retronova.game.Game;
import com.retronova.game.objects.entities.enemies.Enemy;
import com.retronova.game.objects.entities.Entity;
import com.retronova.game.objects.entities.Player;

import java.awt.*;

public class Laser extends Item {

    private double angle = 0;
    private int count;
    private int countShot;
    private Entity currentTarget;
    private double itemOffsetX = 30;
    private double itemOffsetY = 25;
    private double laserOffsetX = 10;
    private double laserOffsetY = 0;
    private double laserDistance = 30;

    Laser(int id) {
        super(id, "Laser", "laser");
        addSpecifications("Laser add burn", "player damage", "shot instant");
    }
    /*

        Lógica: Crei uma variavel Target;
        essa variavel target será o inimigo mais perto, e, quando sua vida zerar, o target será setada null;

        a lógica de encontrat targets é que, quando o target for null, voce usa o getNearest para encontrar outro target

        para renderizar o lazer, pegue a posição do target e da ponta do lazer e use o drawLine e coloque os pontos.

        a parte do cudaoa é com vc!

     */
    @Override
    public void tick() {
        Player player = Game.getPlayer();
        Entity nearest = player.getNearest(player.getRange(), Enemy.class);

        if (currentTarget != null && !Game.getMap().getEntities(Enemy.class).contains(currentTarget)) {
            currentTarget = null;
        }

        if (nearest != null) {
            currentTarget = nearest;
            angle = nearest.getAngle(player);
            count++;
            if (count > (player.getAttackSpeed() * 3.25d) / 5) {
                count = 0;
                countShot++;
                this.plusIndexSprite();
            }
            if (countShot >= 5) {
                countShot = 0;
                shot(player);
            }
        } else {
            resetIndexSprite();
        }
    }

    private void shot(Player shooter) {
        double centerX = shooter.getX() + shooter.getWidth() / 2.0;
        double centerY = shooter.getY() + shooter.getHeight() / 2.0;

        double laserX = centerX;
        double laserY = centerY;

        double constantDamage = 10.0;
        //Local onde estava a adição do objeto
    }

    @Override
    public void render(Graphics2D g) {
        Player player = Game.getPlayer();
        int x = (int) player.getX() + player.getWidth() / 2 - Game.C.getX();
        int y = (int) player.getY() + player.getHeight() / 2 - Game.C.getY();
        double xx = x - getSprite().getWidth() / 2d + itemOffsetX;
        double yy = y - getSprite().getHeight() / 2d + itemOffsetY;
        Rotate.draw(getSprite(), (int) xx, (int) yy, angle + Math.PI/4, null, g);
    }

    public double getX() {
        Player player = Game.getPlayer();
        double centerX = player.getX() + player.getWidth() / 2.0;
        return centerX + Math.cos(angle) * laserDistance + Math.cos(angle) * laserOffsetX - Math.sin(angle) * laserOffsetY;
    }

    public double getY() {
        Player player = Game.getPlayer();
        double centerY = player.getY() + player.getHeight() / 2.0;
        return centerY + Math.sin(angle) * laserDistance + Math.sin(angle) * laserOffsetX + Math.cos(angle) * laserOffsetY;
    }

    public double getAngle() {
        return angle;
    }
}