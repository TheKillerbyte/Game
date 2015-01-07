package com.thekillerbyte.game.objekte;

import java.util.ArrayList;

/**
 *
 * @author Alexander
 */
public class TerrainRegister {

    public static ArrayList<Terraintype> registrierte;

    public static void init() {
        registrierte = new ArrayList<Terraintype>();
    }

    public static void register(Terraintype zuregistrieren) {
        registrierte.add(zuregistrieren);
    }

    public static String getName(int id) {
        return registrierte.get(id).name;
    }

    
    
}
