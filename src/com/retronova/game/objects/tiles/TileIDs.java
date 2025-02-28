package com.retronova.game.objects.tiles;

public enum TileIDs {
    // 0x_2xRed_2xGreen_2xBlue
    // 00 -> 0
    // ff -> 255
    Brick("0x000000"),
    Stone("0xffffff");

    private final String color;

    TileIDs(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

}
