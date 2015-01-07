package com.thekillerbyte.game.objekte.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thekillerbyte.game.objekte.HashTextureHandler;

/**
 *
 * @author Alexander
 */
public class Haus extends Building {

   static int width = 64, height = 64;

   public Haus(int type, int x, int y) {
      super(type, x, y);
   }

   @Override
   public void render(SpriteBatch batch) {
      batch.draw(HashTextureHandler.getTexture("Haus_1"), x, y, null);
   }

}
