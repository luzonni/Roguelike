package com.retronova.game.objects.particles;

import com.retronova.engine.Engine;
import com.retronova.engine.graphics.Alpha;
import com.retronova.game.Game;

import java.awt.*;

public class Walking extends Particle {

    private final double dir;
    private double r;
    private int count;

    Walking(int ID, double x, double y, double seconds, double dir) {
        super(ID, x, y, seconds);
        loadSprites("teste_particula");
        this.dir = dir;
        if(Engine.RAND.nextBoolean())
            getSheet().plusIndex();
    }

    @Override
    public void tick() {
        r += Math.toRadians(2);
//        setX(getX() + Math.cos(dir) * 0.5d * Configs.SCALE);
//        setY(getY() + Math.sin(dir) * 0.5d * Configs.SCALE);
        count++;
        if(count >= getSeconds()) {
            Game.getMap().remove(this);
        }
    }

    @Override
    public void render(Graphics2D g) {
        float t = 1f - count/(float)getSeconds();
        int x = (int)getX() - Game.C.getX();
        int y = (int)getY() - Game.C.getY();
        Alpha.draw(getSprite(), x, y, t, g);
    }
}
