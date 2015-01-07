package com.thekillerbyte.game.objekte;

/**
 *
 * @author Alexander
 */
public enum Terraintype {

    /**
     *
     */
    WATER(0, false, "Wasser"),
    DIRT(1, true, "Erde"),
    STONE(2, true, "Stein"),
    SAND(3,true,"Sand");

    public final int textureID;
    public final boolean isBoden;
    public final String name;

    Terraintype(int textureID, boolean ground, String name) {
        this.textureID = textureID;
        this.isBoden = ground;
        this.name = name;
    }
}
