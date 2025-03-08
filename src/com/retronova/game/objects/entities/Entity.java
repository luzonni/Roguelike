package com.retronova.game.objects.entities;

import com.retronova.engine.Configs;
import com.retronova.engine.Engine;
import com.retronova.engine.exceptions.EntityNotFound;
import com.retronova.game.Game;
import com.retronova.game.items.Item;
import com.retronova.game.objects.GameObject;
import com.retronova.game.objects.physical.Physical;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entity extends GameObject {

    private final Physical physical;
    private AttackTypes[] resistanceOf;

    private final Map<Modifiers, Double> modifiers;
    private final List<Effect> effects;

    private double[] life; //este valor é um array de dois valores, o primeiro é a vida original, e o outro a vida atual
    private double range;
    private double speed;
    private double damage;
    private double attackSpeed;

    private boolean alive = false;
    private double xpWeight;

    public static Entity build(int ID, double x, double y) {
        EntityIDs entityId = EntityIDs.values()[ID];
        x *= GameObject.SIZE();
        y *= GameObject.SIZE();
        switch (entityId) {
            case Xp -> {
                return new Xp(ID, x, y);
            }
            case Zombie -> {
                return new Zombie(ID, x, y);
            }
            case Skeleton -> {
                return new Skeleton(ID, x, y);
            }
            case Slime -> {
                return new Slime(ID, x, y);
            }
            case RatExplode -> {
                return new RatExplode(ID, x , y);
            }
            case MouseSquire -> {
                return new MouseSquire(ID, x, y);
            }
            case MouseVampire -> {
                return new MouseVampire(ID, x, y);
            }
            case CatToyBoss -> {
                return new CatToyBoss(ID,x,y);
            }
            case KingCursedCatBoss -> {
                return new KingCursedCatBoss(ID, x,y);
            }
            case MonarkMouse -> {
                return new MonarkMouse(ID, x, y);
            }
        }
        throw new EntityNotFound("Entity not found");
    }

    Entity(int ID, double x, double y, double friction) {
        super(ID);
        setX(x);
        setY(y);
        this.physical = new Physical(this, friction);
        setResistances(null);
        this.modifiers = new HashMap<>();
        this.effects = new ArrayList<>();
        setLife(100d);
        setXpWeight(7.33d);
    }

    public void addModifier(Modifiers modifier, double value) {
        if(!this.modifiers.containsKey(modifier))
            this.modifiers.put(modifier, value);
    }

    public void addEffect(EffectApplicator applicator, int seconds) {
        this.effects.add(new Effect(this, applicator, seconds));
    }

    void removeEffect(Effect effect) {
        this.effects.remove(effect);
    }

    public void tickEffect() {
        for(int i = 0; i < effects.size(); i++) {
            effects.get(i).tick();
        }
    }

    protected void setXpWeight(double weight) {
        this.xpWeight = weight;
    }

    protected double getXpWeight() {
        return this.xpWeight;
    }

    public double getLife() {
        return this.life[1];
    }

    public double getLifeSize() {
        if(this.modifiers.containsKey(Modifiers.Life)) {
            return this.life[0] + this.modifiers.get(Modifiers.Life);
        }
        return this.life[0];
    }

    public void setLifeSize(double lifeSize){ this.life[0] = lifeSize; }

    public void setLife(double life) {
        if(this.life == null) {
            this.life = new double[] {life, life};
            return;
        }
        this.life[1] = life;
    }

    public double getDamage() {
        if(modifiers.containsKey(Modifiers.Damege)) {
            return this.damage + modifiers.get(Modifiers.Damege);
        }
        return this.damage;
    }

    protected void setDamage(double damage) {
        this.damage = damage;
    }

    public double getSpeed() {
        if(modifiers.containsKey(Modifiers.Speed)) {
            return this.speed + modifiers.get(Modifiers.Speed);
        }
        return this.speed;
    }

    protected void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getRange() {
        if(modifiers.containsKey(Modifiers.Range)) {
            return this.range + modifiers.get(Modifiers.Range);
        }
        return this.range;
    }

    protected void setRange(double range) {
        this.range = range;
    }

    public double getAttackSpeed() {
        if(modifiers.containsKey(Modifiers.AttackSpeed)) {
            return this.attackSpeed + modifiers.get(Modifiers.AttackSpeed);
        }
        return this.attackSpeed;
    }

    protected void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive() {
        this.alive = true;
    }

    protected void setResistances(AttackTypes... resistences) {
        this.resistanceOf = resistences;
    }

    public void strike(AttackTypes type, double damage) {
        if(!isAlive()) {
            return;
        }
        if (this.resistanceOf != null && type != null) {
            for (AttackTypes resistance : this.resistanceOf) {
                if (resistance.equals(type)) {
                    setLife(getLife() - damage * (1 - resistance.getResistance()));
                    if (getLife() <= 0) {
                        die();
                    }
                    return;
                }
            }
        }
        setLife(getLife() - damage); // Dano total
        if (getLife() <= 0) {
            die();
        }
    }

    public Entity getNearest(double range){
        List<Entity> entities = Game.getMap().getEntities();
        Entity nearest = null;
        double maxDist = GameObject.SIZE() * range;
        for(int i = 0; i < entities.size(); i ++){
            Entity e = entities.get(i);
            if(e.equals(this) || !e.isAlive()){
                continue;
            }
            double currDist = e.getDistance(this);
            if(currDist < maxDist){
                nearest = e;
                maxDist = currDist;
            }
        }
        return nearest;
    }

    protected boolean colliding(Entity entity) {
        return this.getBounds().intersects(entity.getBounds());
    }

    /**
     * Quando chamado, adiciona partículas no local da morte, aciona um som padrão de morte e remove a entidade do mapa.
     */
    public void die() {
        //TODO adicionar particula de morte
        //TODO adicionar som de morte
        this.disappear();
        this.dropXp();
    }

    private void dropXp() {
        double luck = Game.getPlayer().getLuck();
        Xp e = (Xp)build(EntityIDs.Xp.ordinal(), 0, 0);
        e.setX(getX());
        e.setY(getY());
        e.setWeight(getXpWeight() * (1d + Engine.RAND.nextDouble(luck)));
        Game.getMap().getEntities().add(e);
        e.getPhysical().addForce(7, Math.PI*2);
    }

    /**
     * Esta função serve apenas para retirar uma entidade do mara, sem nenhum tipo de efeito.
     */
    public void disappear() {
        Game.getMap().getEntities().remove(this);
    }

    public void dropLoot(Item loot) {
        Entity drop = new Drop(getX(), getY(), loot);
        Game.getMap().getEntities().add(drop);
        drop.getPhysical().addForce(Engine.RAND.nextInt(10), Engine.RAND.nextDouble(Math.PI*2));
    }

    public Physical getPhysical() {
        return this.physical;
    }

    public void renderLife(Graphics2D g) {
        if(getLife() == getLifeSize() || this instanceof Player || !this.isAlive())
            return;
        int x = (int)getX() - Game.C.getX();
        int y = (int)getY() + getHeight() - Game.C.getY() + Configs.SCALE*2;
        int w = getWidth();
        int h = Configs.SCALE * 3;

        g.setColor(new Color(135, 35, 65));
        g.fillRect(x, y, w, h);
        double lifeSize = w * (getLife() / getLifeSize());
        g.setColor(new Color(190, 49, 68));
        g.fillRect(x, y, (int) lifeSize, h);

        g.setStroke(new BasicStroke(Configs.SCALE));
        g.setColor(new Color(9, 18, 44));
        g.drawRect(x, y, w, h);
    }
}
