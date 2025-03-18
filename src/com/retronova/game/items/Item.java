package com.retronova.game.items;

import com.retronova.engine.Configs;
import com.retronova.engine.exceptions.NotFound;
import com.retronova.engine.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public abstract class Item {

    private static final Map<String, BufferedImage[]> sheet;
    
    static {
        sheet = new HashMap<>();
    }

    public static Item build(int id, Object... values) {
        ItemIDs type = ItemIDs.values()[id];
        switch (type) {
            case Silk -> {
                return new ItemSilk(id);
            }
            case Sword -> {
                return new Sword(id);
            }
            case Bow -> {
                return new Bow(id);
            }
            case Bomb -> {
                return new ItemBomb(id);
            }
            case Feed -> {
                int stack = (int)values[0];
                Consumable consumable = new Feed(id);
                consumable.setStack(stack);
                return consumable;
            }
            case Catnip -> {
                int stack = (int)values[0];
                Consumable consumable = new Catnip(id);
                consumable.setStack(stack);
                return consumable;
            }
            case Laser -> {
                return new Laser(id);
            }
            case Acorn -> {
                int stack = (int)values[0];
                Consumable consumable = new Acorn(id);
                consumable.setStack(stack);
                return consumable;
            }
            case Watermelon -> {
                int stack = (int)values[0];
                Consumable consumable = new Watermelon(id);
                consumable.setStack(stack);
                return consumable;
            }
        }
        throw new NotFound("Item not found");
    }

    private final int id;
    private final String name;
    private int indexSprite;

    private final BufferedImage[] sprite;
    private String[] specifications;

     Item(int id, String name, String sprite) {
        this.id = id;
        this.name = name;
        if(!sheet.containsKey(sprite)) {
            SpriteSheet sheet = new SpriteSheet("items", sprite, Configs.GameScale());
            int length = sheet.getWidth() / 16;
            this.sprite = new BufferedImage[length];
            for (int i = 0; i < length; i++) {
                this.sprite[i] = sheet.getSpriteWithIndex(i, 0);
            }
        }else {
            this.sprite = sheet.get(sprite);
        }
        addSpecifications("espec");
    }

    protected void addSpecifications(String... specifications) {
         this.specifications = specifications;
    }

    public String[] getSpecifications() {
         return this.specifications;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj instanceof Item item) {
            return this.getID() == item.getID();
        }
        return false;
    }

    public BufferedImage getSprite() {
        return this.sprite[indexSprite];
    }

    protected void plusIndexSprite() {
        this.indexSprite++;
        if(indexSprite > sprite.length-1) {
            indexSprite = 0;
        }
    }

    protected void resetIndexSprite() {
        this.indexSprite = 0;
    }

    public abstract void tick();

    public abstract void render(Graphics2D g);

}
