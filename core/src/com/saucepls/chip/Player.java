package com.saucepls.chip;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	public int x;
	public int y;
	public Sprite img;
	public Rectangle pRectangle;  // changed to public 8/3
	
	//constructor, receive input from player and set values
	public Player(int coordX, int coordY, Sprite imag) {
		// TODO Auto-generated constructor stub
		this.x = coordX;
		this.y = coordY;
		this.img = imag;
		//Collision box based on images width and height.
		//will have to be adjusted if image includes other frames for animation.
		this.pRectangle = img.getBoundingRectangle();
	}

	
	//function called in tileTest main app to draw the player into the tilemap 
	public void draw(Batch batch) {
		//batch.draw(playerTexture, getPosX(), getPosY(), playerTexture.getWidth(), playerTexture.getHeight());
		batch.draw(img, this.x, this.y, img.getWidth(), img.getHeight());
		
	}

	public Rectangle getRectangle() {
		// TODO Auto-generated method stub
		return pRectangle;
	}
	
	
	
	
}
