package com.thekillerbyte.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.thekillerbyte.game.objekte.Spielfeld;
import com.thekillerbyte.game.TestStarter;
import com.thekillerbyte.game.objekte.Fields;
import com.thekillerbyte.game.objekte.HashTextureHandler;
import com.thekillerbyte.game.objekte.buildings.Building;
import com.thekillerbyte.game.objekte.buildings.Haus;
import java.util.ArrayList;

/**
 *
 * @author Alexander
 */
public class Spiel implements Screen {

   private SpriteBatch batch;
   private TextureRegion splash;
   private TestStarter game;
   private Stage ui;
   private Spielfeld feld;
   boolean state;
   ArrayList<Building> gebaeude = new ArrayList();
   byte lastbuild = 0;
   float cursorX, cursorY;
   Label schreib;

   public Spiel(TestStarter game) {
	this.game = game;
   }

   @Override
   public void show() {
	feld = new Spielfeld(128, 128, true, 10, 2);
	ui = new Stage();
	state = false;
	ChangeListener temp = new ChangeListener() {
	   @Override
	   public void changed(ChangeListener.ChangeEvent event, Actor actor) {
		if (actor.getName() != null && actor.getName().equals("Bau_Haus_01")) {
		   if (actor.getName().equals("Bau_Haus_01") && !state && lastbuild == 0) {
			state = true;
			lastbuild = 10;
		   } else if (state && lastbuild == 0) {
			state = false;
		   }
		}
	   }
	};
	Gdx.input.setInputProcessor(ui);

	Texture guiElements = new Texture(Gdx.files.internal("Textures/Spritesheet_02v2.png"));
	Actor ablage_1 = new Image(new TextureRegion(guiElements, 0, 832, 64, 64));

	ablage_1.setPosition(0, 0);
	ablage_1.addListener(temp);
	Actor ablage_2 = new Image(new TextureRegion(guiElements, 0, 832, 64, 64));

	ablage_2.setPosition(64, 0);

	Actor ablage_3 = new Image(new TextureRegion(guiElements, 0, 832, 64, 64));

	ablage_3.setPosition(128, 0);

	Button bau = new Button(new TextureRegionDrawable(HashTextureHandler.getTexture("Haus_01")));

	bau.setPosition(10, 10);
	bau.setName("Bau_Haus_01");
	bau.addListener(temp);
	Actor balken = new Image(new TextureRegion(guiElements, 0, 832, 64, 64));

	balken.setScaleX(1000);
	balken.setPosition(192, 0);
	Image titel = new Image(new TextureRegionDrawable(new TextureRegion(guiElements, 0, 0, 512, 64)));

	titel.setPosition(180, 180);

	schreib = new Label("", new Label.LabelStyle(new BitmapFont(), Color.RED));
	schreib.setPosition(schreib.getWidth(), 20);

	ui.addActor(ablage_1);
	ui.addActor(ablage_2);
	ui.addActor(ablage_3);
	bau.addListener(temp);
	ui.addActor(bau);

	ui.addActor(balken);
	ui.addListener(temp);
	ui.addActor(schreib);
	ui.addListener(new ClickListener() {
	   @Override
	   public void clicked(InputEvent event, float x, float y) {
		if (state == true && lastbuild == 0) {
		   Vector2 test = new Vector2(1, 1);
		   test = getSelector(cursorX, cursorY, game.cam, Fields.currentSize, new Haus(0, 0, 0));
		   gebaeude.add(new Haus(0, (int) (game.cam.position.x + cursorX - ((cursorX + 16) % 32) - 32), (int) (game.cam.position.y + cursorY - ((cursorY + 16) % 32) - 32)));
		   state = false;
		   lastbuild = 10;
		}
	   }

	   @Override
	   public boolean mouseMoved(InputEvent event, float x, float y) {
		cursorX = x;
		cursorY = y;
		return false;
	   }

	});
	batch = new SpriteBatch();
	splash = new TextureRegion(new Texture(Gdx.files.internal("Textures/Spritesheet_01.png")), 0, 0, 16, 16);

	game.cam.position.set(400, 300, 0);
	game.cam.update();
   }

   @Override
   public void render(float delta) {

	updateCamera();
	updateOthers();

	if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {

	   game.setScreen(TestStarter.menu);

	}

	draw(delta);

   }

   @Override
   public void resize(int width, int height) {

	game.view.update(width, height);
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

   }

   private void updateCamera() {
	if (Gdx.input.isKeyPressed(Input.Keys.A)) {
	   if (game.cam.position.x - 10 > -20) {
		game.cam.translate(-10, 0, 0);
	   }
	}
	if (Gdx.input.isKeyPressed(Input.Keys.D)) {
	   if (game.cam.position.x + game.cam.viewportWidth - 10 < 999999 /* (feld.getSizeX()) * Fields.currentSize */) {
		game.cam.translate(10, 0, 0);
	   }
	}
	if (Gdx.input.isKeyPressed(Input.Keys.W)) {
	   if (game.cam.position.y + game.cam.viewportHeight - 10 < 999999/* (feld.getSizeY()) * Fields.currentSize */) {
		game.cam.translate(0, 10, 0);
	   }
	}
	if (Gdx.input.isKeyPressed(Input.Keys.S)) {
	   if (game.cam.position.y - 10 > -120) {
		game.cam.translate(0, -10, 0);
	   }
	}
	game.cam.update();
   }

   private void draw(float delta) {
	Gdx.gl.glClearColor(0, 0, 0, 1);
	Gdx.gl.glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT);
	batch.setProjectionMatrix(game.cam.combined);
	batch.begin();
	feld.draw(game.cam.position.x - game.view.getScreenWidth() / 2, game.cam.position.y - game.view.getScreenHeight() / 2, game.view.getScreenWidth(), game.view.getScreenHeight(), batch);
	for (Building gebaeude1 : gebaeude) {
	   if (gebaeude1 instanceof Haus) {
		batch.draw(HashTextureHandler.getTexture("Haus_01"), gebaeude1.getX(), gebaeude1.getY());
	   }
	}
	if (state) {
	   batch.draw(HashTextureHandler.getTexture("Haus_01"), cursorX - ((cursorX + Fields.currentSize / 2) % 32) - 32, cursorY - ((cursorY + 16) % 32) - 32);
	}
	batch.end();
	ui.act(delta);
	ui.draw();
   }

   private void updateOthers() {
	schreib.setText(Gdx.graphics.getFramesPerSecond() + "\n" + gebaeude.size() + "\nBaumodus" + state + "\nKamera x: " + game.cam.position.x + " y: " + game.cam.position.y + "\nCursor x:" + cursorX + " y: " + cursorY + "\nbauverzögerung" + lastbuild);
	schreib.setPosition(game.view.getScreenWidth() - 180, 70);

	if (lastbuild > 0) {
	   lastbuild--;
	}
   }

   public static Vector2 getSelector(float cursorX, float cursorY, Camera cam, float currentSize, Building gebaude) {
	// x  cursorX - ((cursorX + Fields.currentSize / 2) % 32) - 32
	// y cursorY - ((cursorY + (half the size of a field)) % 32) - (Half the size of a field)
	return new Vector2(cursorX - ((cursorX + Fields.currentSize / 2) % (gebaude.getWidth() / 2)) - (gebaude.getWidth() / 2), cursorY - ((cursorY + Fields.currentSize / 2) % (gebaude.getWidth() / 2)) - (gebaude.getWidth() / 2));

   }
}
