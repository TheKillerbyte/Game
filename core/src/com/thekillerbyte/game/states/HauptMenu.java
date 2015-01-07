package com.thekillerbyte.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.thekillerbyte.game.TestStarter;

/**
 *
 * @author Alexander
 */
public class HauptMenu implements Screen {

   private SpriteBatch batch;
   private Sprite test;
   private TestStarter game;
   private Stage stage;
   private TextureRegion[] texts;

   public HauptMenu(TestStarter game) {
      this.game = game;
   }

   @Override
   public void show() {
      {
         Pixmap pmap = new Pixmap(800, 600, Pixmap.Format.RGB565);
         pmap.setColor(255, 0, 0, 0);
         test = new Sprite(new Texture(pmap));

      }
      {
         Texture text = new Texture(Gdx.files.internal("Textures/Spritesheet_02v2.png"));

         texts = new TextureRegion[5];
         texts[0] = new TextureRegion(text, 0, 0, 512, 64);
         texts[1] = new TextureRegion(text, 0, 896, 128, 64);
         texts[2] = new TextureRegion(text, 0, 960, 128, 64);
         texts[3] = new TextureRegion(text, 128, 896, 128, 64);
         texts[4] = new TextureRegion(text, 0, 0, 512, 64);
      }
      stage = new Stage();

      TextButton[] buttons = new TextButton[4];

      TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
      style.up = new TextureRegionDrawable(texts[1]);
      style.checked = new TextureRegionDrawable(texts[2]);
      style.down = new TextureRegionDrawable(texts[2]);
      style.font = new BitmapFont();
      style.font.setColor(Color.CLEAR);

      String[] names = {"Ende", "Option", "Laden", "Start"};
      ChangeListener list = new ChangeListener() {
         @Override
         public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            System.out.println(actor.getName());
            if (actor.getName().equals("Start")) {
               game.setScreen(TestStarter.spiel);
            } else if (actor.getName().equals("Ende")) {
               game.dispose();
               System.exit(0);
            } else if (actor.getName().equals("Option") | actor.getName().equals("Laden")) {
               System.out.println("Leider noch nicht unterstützt!");
            }
         }

      };

      for (int i = 0;
           i < buttons.length;
           i++) {
         buttons[i] = new TextButton(names[i], style);
         buttons[i].setName(names[i]);
         buttons[i].setPosition(100, 100 + i * 80);
         buttons[i].addListener(list);
         buttons[i].setSize(128, 64);

         stage.addActor(buttons[i]);
      }
      Image title = new Image(new TextureRegionDrawable(new TextureRegion(texts[0])));

      title.setPosition(50, 500);

      stage.addActor(title);
      stage.addListener(list);

      Gdx.input.setInputProcessor(stage);
      System.out.println(game.cam.view);
      game.cam.update();
      batch = new SpriteBatch(4);
   }

   @Override
   public void render(float delta) {
      Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);

      batch.begin();
      test.setPosition(0, 0);
      test.draw(batch);
      batch.end();

      stage.act();
      stage.draw();
      // game.setScreen(TestStarter.spiel);
   }
   /**
    * TODO many things
    * @param width
    * @param height 
    */
   @Override
   public void resize(int width, int height) {
      stage.getViewport().update(width, height);
      game.cam.update();
   }

   @Override
   public void pause() {

   }

   @Override
   public void resume() {

   }

   @Override
   public void hide() {

   }

   @Override
   public void dispose() {
      stage.dispose();
      Gdx.input.setInputProcessor(null);

   }
}
