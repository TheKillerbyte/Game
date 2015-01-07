package com.thekillerbyte.game.objekte;

/**
 *
 * @author Alexander
 */
public abstract class Drawable {

   int x, y;
   int width, height;

   public abstract void render();

   public int getX() {
      return x;
   }

   public int getY() {
      return y;
   }

   public int getHeight() {
      return height;
   }

   public int getWidth() {
      return width;
   }

}
