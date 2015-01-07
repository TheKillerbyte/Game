package com.thekillerbyte.game.objekte.buildings;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Alexander
 */
public abstract class Building {

   protected int type;
   protected int x, y;
   protected int width, height;

   public abstract void render(SpriteBatch batch);

   public Building() {
	this(0, 0, 0, 0, 0);
   }

   public Building(int type, int x, int y, int width, int height) {
	this.type = type;
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
   }

   public Building(int type, int x, int y) {
	this(type, x, y, 32, 32);
   }

   public int getType() {
	return type;
   }

   public void setType(int type) {
	this.type = type;
   }

   public void setX(int x) {
	this.x = x;
   }

   public void setY(int y) {
	this.y = y;
   }

   public void setHeight(int height) {
	this.height = height;
   }

   public void setWidth(int width) {
	this.width = width;
   }

   public int getX() {
	return x;
   }

   public int getY() {
	return y;
   }

   public int getWidth() {
	return width;
   }

   public int getHeight() {
	return height;
   }

   public void setPosition(int x, int y) {
	this.x = x;
	this.y = y;
   }
}
