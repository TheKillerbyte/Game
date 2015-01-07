package com.thekillerbyte.game.objekte;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexander
 */
public class Spielfeld {

   private Fields[][] feld;

   public Spielfeld() {
      feld = new Fields[0][0];
   }

   public Spielfeld(int x, int y, boolean randomGenerated) {
      feld = new Fields[x][y];
      if (randomGenerated) {
         recreate(10, 2);
      }
   }

   public Spielfeld(int x, int y, boolean randomGenerated, int detail, int lod) {
      feld = new Fields[x][y];
      if (randomGenerated) {
         recreate(detail, lod);
      }
   }

   public void update() {

   }

   public void draw(double offsetX, double offsetY, int windowWidth, int windowHeight, SpriteBatch batch) {
      // offsetx >= x + xWidth   --  offsetx + windowWidth <= x
      // offsety >= y + yHeight  --  offsety + windowHeight <= y

      int fieldx = offsetX < 0 ? 0 : (int) Math.floor(offsetX / Fields.currentSize);
      int fieldy = offsetY < 0 ? 0 : (int) Math.floor(offsetY / Fields.currentSize);
      int fieldX_Max = (int) Math.ceil((offsetX + windowWidth) / Fields.currentSize);
      int fieldY_Max = (int) Math.ceil((offsetY + windowHeight) / Fields.currentSize);

      //fieldx = fieldx < 0 ? 0 : fieldx;
      //fieldy = fieldy < 0 ? 0 : fieldy;
      for (int i = fieldx; i < fieldX_Max; i++) {
         for (int j = fieldy; j < fieldY_Max; j++) {
            if (feld.length > i && feld[i].length > j && feld[i][j] != null) {
               batch.draw(HashTextureHandler.getTexture(TerrainRegister.getName(feld[i][j].terraintypeID)), i * Fields.currentSize, j * Fields.currentSize, Fields.currentSize, Fields.currentSize);
            }
         }
      }
   }

   public final void recreate(int detail, int rau) {
      // 14
      FractalTerrain test = new FractalTerrain(detail, rau);
      StringBuilder sb = new StringBuilder();
      for (int x1 = 0; x1 < feld.length; x1++) {
         for (int y1 = 0; y1 < feld[x1].length; y1++) {
            if (x1 <= 2 || x1 >= feld.length - 2 || y1 <= 2 || y1 >= feld[x1].length - 2) {
               feld[x1][y1] = new Fields((byte) 0);
            } else {
               double altitude = test.getAltitude((double) x1 / (1 << detail), (double) y1 / (1 << detail));
               sb.append(altitude);
               sb.append(", ");
               if (altitude < 0.3) {
                  feld[x1][y1] = new Fields((byte) 0);
               } else if (altitude < 0.4) {
                  feld[x1][y1] = new Fields((byte) 3);
               } else if (altitude < 0.8) {
                  feld[x1][y1] = new Fields((byte) 1);
               } else {
                  feld[x1][y1] = new Fields((byte) 2);
               }
            }
         }
         sb.append("\n");
      }
      try {
         System.out.println(sb);
         test.finalize();
      } catch (Throwable ex) {
         Logger.getLogger(Spielfeld.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   public int getSizeX() {
      return feld.length;
   }

   public int getSizeY() {
      return feld[0].length;
   }
}
