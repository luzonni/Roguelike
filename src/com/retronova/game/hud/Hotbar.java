package com.retronova.game.hud;

import com.retronova.engine.Configs;
import com.retronova.engine.Engine;
import com.retronova.game.items.Item;
import com.retronova.game.objects.entities.Player;
import com.retronova.graphics.SpriteSheet;
import com.retronova.inputs.mouse.Mouse;

import java.awt.*;
import java.awt.image.BufferedImage;

class Hotbar {

    private BufferedImage[] sprites;
    private Rectangle[] bounds;
    private final Player player;
    private int index;

    public Hotbar(Player player) {
        this.player = player;
        SpriteSheet sheet = new SpriteSheet("ui", "hotbar", Configs.HUDSCALE);
        int sheetSize = sheet.getWidth()/16;
        this.sprites = new BufferedImage[sheetSize];
        for(int i = 0; i < sheetSize; i++) {
            this.sprites[i] = sheet.getSprite(i, 0);
        }
        this.bounds = new Rectangle[5];
        int hotbarWidth = this.sprites[0].getWidth() * 5;
        int x = Engine.window.getWidth()/2 - hotbarWidth/2;
        int y = Engine.window.getHeight() - this.sprites[0].getHeight() - Configs.MARGIN;
        for(int i = 0; i < 5; i++) {
            this.bounds[i] = new Rectangle(x + i * sprites[0].getWidth(), y, sprites[0].getWidth(), sprites[0].getHeight());
        }
    }


    public void tick() {
        int length = player.getInventory().getHotbarSize();
        int scroll = Mouse.Scroll();
        if(scroll > 0) {
            index++;
            if(index > length-1) {
                index = 0;
            }
        }else if(scroll < 0) {
            index--;
            if(index < 0) {
                index = length-1;
            }
        }
        Item itemHand = player.getInventory().getHotbar()[index];
        player.getInventory().setItemHand(itemHand);
    }

    public void render(Graphics2D g) {
        Item[] items = player.getInventory().getHotbar();
        int length = player.getInventory().getHotbarSize();
        int w = bounds[0].width * bounds.length;
        int ww = bounds[0].width * length;
        int difX = (w - ww)/2;
        for(int i = 0; i < length; i++) {
            BufferedImage sprite = index == i ? sprites[1] : sprites[0];
            g.drawImage(sprite, bounds[i].x + difX, bounds[i].y, null);
            if(items[i] != null) {
                g.drawImage(items[i].getSprite(), bounds[i].x + difX, bounds[i].y, null);
            }
        }
    }

}
