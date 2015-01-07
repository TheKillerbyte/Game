package com.thekillerbyte.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.thekillerbyte.game.objekte.HashTextureHandler;
import com.thekillerbyte.game.objekte.TerrainRegister;
import com.thekillerbyte.game.objekte.Terraintype;
import com.thekillerbyte.game.states.*;

/**
 *
 * @author Alexander
 */
public class TestStarter extends Game {

   public static Spiel spiel;
   public static HauptMenu menu;
   public static Splash splash;

   public Viewport view;
   public Camera cam;

   @Override
   public void create() {

      cam = new OrthographicCamera(1920, 1080);
      view = new ScreenViewport(cam);
      view.update(1920, 1080);
      view.apply();
      cam.update();

      splash = new Splash(this);
      spiel = new Spiel(this);
      menu = new HauptMenu(this);

      setScreen(splash);
   }

   public void preInit() {
      HashTextureHandler.init();
      TerrainRegister.init();
   }

   public void init() {
      {
         TerrainRegister.register(Terraintype.WATER);
         TerrainRegister.register(Terraintype.DIRT);
         TerrainRegister.register(Terraintype.STONE);
         TerrainRegister.register(Terraintype.SAND);

      }
   }

   public void postInit() {
      {
         Texture text = new Texture(Gdx.files.internal("Textures/Spritesheet_01.png"));
         HashTextureHandler.addTexture(Terraintype.WATER.name, new TextureRegion(text, 0, 0, 16, 16));
         HashTextureHandler.addTexture(Terraintype.DIRT.name, new TextureRegion(text, 16, 0, 16, 16));
         HashTextureHandler.addTexture(Terraintype.STONE.name, new TextureRegion(text, 32, 0, 16, 16));
         HashTextureHandler.addTexture(Terraintype.SAND.name, new TextureRegion(text, 48, 0, 16, 16));
      }
      {
         Texture gebaeube = new Texture(Gdx.files.internal("Textures/Spritesheet_03.png"));
         HashTextureHandler.addTexture("Haus_01", new TextureRegion(gebaeube, 0, 0, 64, 64));
         HashTextureHandler.addTexture("null", new TextureRegion(gebaeube, 0, 0, 0, 0));
      }
   }

}
