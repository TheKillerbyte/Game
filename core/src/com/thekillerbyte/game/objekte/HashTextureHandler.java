package com.thekillerbyte.game.objekte;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.HashMap;

/**
 *
 * @author Alexander
 */
public class HashTextureHandler {

   private static HashMap<String, TextureRegion> textures;

   public static void init() {
      textures = new HashMap<String, TextureRegion>();
   }

   public static TextureRegion getTexture(String name) {
      if (textures.containsKey(name)) {
         return textures.get(name);
      }else{
         throw new NullPointerException("Failed to load" + name);
      }
   }

   public static boolean addTexture(String name, TextureRegion texture) {
      if (textures.containsKey(name)) {
         return false;
      } else {
         textures.put(name, texture);
         return true;
      }
   }

}
