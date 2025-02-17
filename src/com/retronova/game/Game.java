package com.retronova.game;

import com.retronova.engine.Activity;
import com.retronova.engine.Engine;
import com.retronova.game.map.GameMap;
import com.retronova.game.objects.entities.Entity;
import com.retronova.game.objects.entities.Player;
import com.retronova.game.objects.tiles.Tile;

import java.awt.*;
import java.util.List;

public class Game implements Activity {
    private Player player;
    public static Camera C;
    private GameMap gameMap;

    //Teste
    public Game(Player player, GameMap map) {
        this.player = player;
        this.gameMap = map;
        this.gameMap.getEntities().add(player);

        Game.C = new Camera(gameMap.getBounds(), 0.25d);
        Game.C.setFollowed(player);
    }

    @Override
    public void tick() {
        //tick logic
        List<Entity> entities = gameMap.getEntities();
        /**
         * entities.sort(Entity.Depth) ->
         * Este metodo organizará a lista de objetos de acordo com sua profundidade do eixo Y.
         */
        entities.sort(Entity.Depth);
        for(int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.tick();
        }
        Tile[] map = gameMap.getMap();
        for(int i = 0; i < map.length; i++) {
            Tile tile = map[i];
            tile.tick();
        }

        //Atualização da camera, sempre no final!
        C.tick();
    }

    public GameMap getMap() {
        return this.gameMap;
    }

    @Override
    public void render(Graphics2D g) {
        //Render logic
        renderMap(g);
        renderEntities(g);
    }

    private void renderMap(Graphics2D g) {
        Tile[] map = gameMap.getMap();
        for(int i = 0; i < map.length; i++) {
            map[i].render(g);
        }
    }

    private void renderEntities(Graphics2D g) {
        List<Entity> entities = gameMap.getEntities();
        for(int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            entity.render(g);
        }
    }

    @Override
    public void dispose() {
        //limpar memoria
        System.out.println("Dispose Game");
    }
}
