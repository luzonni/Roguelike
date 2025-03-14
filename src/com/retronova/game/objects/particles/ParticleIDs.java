package com.retronova.game.objects.particles;

public enum ParticleIDs {

    Smoke(4), Spark(3);

    private final int args;

    ParticleIDs(int args) {
        this.args = args;
    }

    public int getArgs() {
        return this.args;
    }

}
