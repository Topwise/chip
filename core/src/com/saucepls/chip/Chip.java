package com.saucepls.chip;

/* Revision Changes
 * Altered hero image to sprite from texture
 * changed rectangle to be used in the player object instead of the main class
 * 
 */


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.saucepls.chip.Player;

public class Chip implements ApplicationListener {
	SpriteBatch batch;
	private OrthographicCamera camera;
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer otmr;
	Player player;
	float unitScale = 32;
	
	//-- new variables------------------------------------
	int startX, startY, previousX, previousY, speed;
	//Rectangle playerRect;
	MapLayer colLayer;
	MapObjects objects;
	// strings for input
	String levelName = "socket1.tmx";
	String collisionLayer = "collisions";
	String playerImg = "Hero.png";
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 50 * unitScale/2, 50 * unitScale/2);
		
		batch = new SpriteBatch();
		
		tiledMap = new TmxMapLoader().load(levelName);
		
		
	    otmr = new OrthogonalTiledMapRenderer(tiledMap);
	    
	    // new code-------------------------------------------
    
		//set one variable for character starting x,y location
		startX = 50; // starting X location
		startY = 50; //  Starting Y coordinate
		//initial value for previous locations set to starting x,y
		previousX = startX;
		previousY = startY;
		//speed at which the character moves
		speed = 80; // 320 would be about 1 block
		
		player = new Player(startX, startY, new Sprite(new Texture(playerImg)) );
			    
		//Create a variable to hold a layer.  extract the collision layer into that variable
		colLayer = tiledMap.getLayers().get("collisions");
		// create a variable for objects, extract all objects in the collision layer to that variable
		objects = colLayer.getObjects();
	    
	}
	
	@Override
	public void render () {
		//clearscreen
	    Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    camera.update();
	    
	    // tell the SpriteBatch to render in the
	    // coordinate system specified by the camera.
	    batch.setProjectionMatrix(camera.combined);
	    otmr.setView(camera);
	    otmr.render();
	    
		// begin a new batch and draw the player in it, other textures can be included.	    
	    otmr.getBatch().begin();
	    player.draw(otmr.getBatch());
	    otmr.getBatch().end();
	    
	    //------------- End Rendering, Start Game Logic -------------------------------------
	    //Save the previous player location coords at the beginning
  		// this is so values can be reset if collision is detected.
  		previousX = player.x;
  		previousY = player.y;
	  	   
  		//user input from keyboard
  	    if(Gdx.input.isKeyPressed(Keys.RIGHT)) player.x += speed / unitScale;// * Gdx.graphics.getDeltaTime();
  	    if(Gdx.input.isKeyPressed(Keys.LEFT)) player.x -= speed / unitScale;// * Gdx.graphics.getDeltaTime();
  	    if(Gdx.input.isKeyPressed(Keys.UP)) player.y += speed / unitScale;// * Gdx.graphics.getDeltaTime();
  	    if(Gdx.input.isKeyPressed(Keys.DOWN)) player.y -= speed / unitScale;// * Gdx.graphics.getDeltaTime();
	    
	    for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)){
	    	
	    	Rectangle rectangle = rectangleObject.getRectangle();
	    	
			player.pRectangle.setX(player.x); //set the player rect to whatever the player coords currently are
	    	player.pRectangle.setY(player.y);	    	
	    	if (Intersector.overlaps(rectangle, player.getRectangle() )) {
	    		player.x = previousX;  
		    	player.y = previousY;
		    	//System.out.println("collision");
	    	}// end if statement
	    }// End for loop	
	    
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		otmr.dispose();
		tiledMap.dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
}
