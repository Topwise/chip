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
	boolean action;     //  Action button ----------------------------
	
	//-- new variables------------------------------------
	float startX, startY, speed;
		
	//Rectangle playerRect;
	MapLayer colLayer;
	MapObjects colObjects;
	// strings for input
	String levelName = "socket2.tmx";
	String collisionLayer = "collisions";
	String playerImg = "Hero.png";
	
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 25 * unitScale, 25 * unitScale);
		
		batch = new SpriteBatch();
		
		tiledMap = new TmxMapLoader().load(levelName);
				
	    otmr = new OrthogonalTiledMapRenderer(tiledMap);
	    
	    // new code-------------------------------------------
    
		//set one variable for character starting x,y location
		startX = 50; // starting X location
		startY = 50; //  Starting Y coordinate
		//initial value for previous locations set to starting x,y
		// speed at which the character moves
		speed = 3; 
		
		player = new Player(startX, startY, new Sprite(new Texture(playerImg)));
		action = false;
		//Create a variable to hold a layer.  extract the collision layer into that variable
		colLayer = tiledMap.getLayers().get("collisions");
		// create a variable for colObjects, extract all objects in the collision layer to that variable
		colObjects = colLayer.getObjects();
	    
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
  		
	  	
	    
  		//Move Right
  	    if(Gdx.input.isKeyPressed(Keys.RIGHT)){
  	    	player.x += speed;// * Gdx.graphics.getDeltaTime();
  	    }
  	    //Move Left
  	    if(Gdx.input.isKeyPressed(Keys.LEFT)){
  	    	player.x -= speed;// * Gdx.graphics.getDeltaTime();
  	    }
  	    //Move Up
  	    if(Gdx.input.isKeyPressed(Keys.UP)){
  	    	player.y += speed;// * Gdx.graphics.getDeltaTime();
  	    }
  	    //Move Down
  	    if(Gdx.input.isKeyPressed(Keys.DOWN)){
  	    	player.y -= speed;// * Gdx.graphics.getDeltaTime();  	    	
  	    }
  	    
  	    //-----------Action button
  	   
  	    if(Gdx.input.isKeyPressed(Keys.SPACE)){
  	    	action = true;
  	    }
  	    
	    
	   
	    /*
	     * This for loop creates takes every rectangle object in the "collisions" layer of the map
	     * Moves through each one "for each RectangleMapObject"
	     * Creates a rectangle around it for measuring collision
	     * 
	     * The if statement, tests that Rectangle to see if it intersects with our players collision rectange pRectangle
	     * if so, the intersection of the two rectangles is tested to see which side of the player its on.
	     * 
	     * Based on the side of the collision the player is then moved next to the rectangle he just hit.
	     */
  	    
  	    //rectangleObject is pulled from colObjects (all the rectangle objects) that were gathered from the collision layer.
	    for (RectangleMapObject rectangleObject : colObjects.getByType(RectangleMapObject.class)){
	    	
	    	//colRectangle is the collision rectangle created from the rectangle object
	    	Rectangle colRectangle = rectangleObject.getRectangle();
	    	
			player.pRectangle.setX(player.x); //set the player rect to whatever the player coords currently are
	    	player.pRectangle.setY(player.y);
	    	player.actRectangle.setX(player.pRectangle.getX() - 1);
	    	player.actRectangle.setY(player.pRectangle.getY() - 1);
	    	Rectangle intersection = new Rectangle();
			
			Intersector.intersectRectangles(player.pRectangle, colRectangle, intersection);
	    	if ( intersection.getHeight() > 0 || intersection.getWidth() > 0 ) {
	    		
	    		
	    		
	    		//because string is a pointer you can't do check = "" have to do check.equals("")
	    		if(rectangleObject.getName() != null && rectangleObject.getName().equals("door") ){
	    			
	    			System.out.println("check " + rectangleObject.getName());
	    			
	    		}else{// if the object name is null, which all walls are. 
	    				    			
	    			//Up, Top Collision    			
	    			if(intersection.y > (player.pRectangle.getHeight()/2 + player.pRectangle.getY()) &&
	    					intersection.getWidth() > intersection.getHeight()){
	    				player.y = colRectangle.getY() - player.pRectangle.getHeight() ;
	    			}
	    			//Down, Bottom Collision
	    			if(intersection.y < (player.pRectangle.getHeight()/2 + player.pRectangle.getY()) &&
	    					intersection.getWidth() > intersection.getHeight()){
	    				player.y = colRectangle.getY() + colRectangle.getHeight() ;
	    			}
	    			//Left Collision
	    			if(intersection.x < (player.pRectangle.getWidth()/2 + player.pRectangle.getX()) &&
	    					intersection.getWidth() < intersection.getHeight()){
	    				player.x = colRectangle.getX() + colRectangle.getWidth() ;  
	    			}
	    			//Right Collision
	    			if(intersection.x > (player.pRectangle.getWidth()/2 + player.pRectangle.getX()) &&
	    					intersection.getWidth() < intersection.getHeight()){
	    				player.x = colRectangle.getX() - player.pRectangle.getWidth();
	    			}
	    			
	    		}//end else 
	      	    
	    	}// end if statement
	     
	    }// End for loop	
	    
	    for (RectangleMapObject rectangleObject : colObjects.getByType(RectangleMapObject.class)){
	    	Rectangle colisRectangle = rectangleObject.getRectangle();
	     	Rectangle intersect = new Rectangle();
			Intersector.intersectRectangles(player.actRectangle, colisRectangle, intersect);
	    	if ( intersect.getHeight() > 0 || intersect.getWidth() > 0 ) {
	    		if(action){
    		    	if(rectangleObject.getName() != null && rectangleObject.getName().equals("closedDoor")){
    					rectangleObject.setName("door");
    					System.out.println(rectangleObject.getName());
    					System.out.println(action);
    					action = false;
    		    	}else if(rectangleObject.getName() != null && rectangleObject.getName().equals("door")) {
    		    		rectangleObject.setName("closedDoor");
    		    		System.out.println("closing door");
    		    		action = false;
    		    	}
    		    }
	    	}
  	    }
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
