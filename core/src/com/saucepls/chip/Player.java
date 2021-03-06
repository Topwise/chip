package com.saucepls.chip;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Player {
	public float x;
	public float y;
	public Sprite img;
	public Rectangle pRectangle;  // changed to public 8/3
	public Rectangle actRectangle;
	
	//constructor, receive input from player and set values
	public Player(float startX, float startY, Sprite imag) {
		// TODO Auto-generated constructor stub
		this.x = startX;
		this.y = startY;
		this.img = imag;
		
		//Collision box based on images width and height.
		//will have to be adjusted if image includes other frames for animation.
		this.pRectangle = img.getBoundingRectangle();
		this.actRectangle = new Rectangle( pRectangle.getX() - 2 , pRectangle.y - 2 , pRectangle.getWidth() + 3 , pRectangle.getHeight() + 3 ) ;
	}

	
	//function called in tileTest main app to draw the player into the tilemap 
	public void draw(Batch batch) {
		//batch.draw(playerTexture, getPosX(), getPosY(), playerTexture.getWidth(), playerTexture.getHeight());
		batch.draw(img, this.x, this.y, img.getWidth(), img.getHeight());
		
	}

	
	
}
