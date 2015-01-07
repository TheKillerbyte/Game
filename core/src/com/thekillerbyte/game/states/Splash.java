package com.thekillerbyte.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thekillerbyte.game.TestStarter;

/**
 *
 * @author Alexander
 */
public class Splash extends ScreenAdapter {

   private static TestStarter game;

   public Splash(TestStarter game) {
      this.game = game;
   }

   @Override
   public void show() {
   }

   @Override
   public void hide() {

   }

   @Override
   public void render(float delta) {
      SpriteBatch batch = new SpriteBatch(4);
      batch.begin();
      batch.draw(new Texture(Gdx.files.internal("Textures/Loading_Screen.png")), 0, 0);
      batch.end();
      game.preInit();
      game.init();
      game.postInit();
      game.setScreen(TestStarter.menu);
   }

}
