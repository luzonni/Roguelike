package com.retronova.game.objects.entities.utilities;

import com.retronova.game.Game;
import com.retronova.game.objects.GameObject;
import com.retronova.game.objects.entities.Entity;
import com.retronova.game.objects.entities.Player;

public class Xp extends Utility {

    private int count;
    private double weight;
    private double speed;

    public Xp(double x, double y) {
        super(-1, x, y, 0.5d);
        loadSprites("xp");
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }

    @Override
    public void tick() {
        count++;
        if(count > 15) {
            count = 0;
            getSheet().plusIndex();
        }
        Player player = Game.getPlayer();
        if(player.getDistance(this) < GameObject.SIZE()/3d) {
            player.plusXp(getWeight());
            disappear();
        }
        if(player.getDistance(this) < GameObject.SIZE()*5) {
            if(speed < 15d) {
                speed += 0.15d;
            }
            getPhysical().addForce("follow", speed, player.getAngle(this));
        }
    }

}
