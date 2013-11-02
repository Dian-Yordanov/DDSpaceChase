/**
//from http://perle-development.com/tutorials/andengine-tutorial-03-player-movement-01/ ||| implements movemtn
//START 9
package com.example.spacechase;

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.example.spacechase.MainActivity;

public class Player extends GameObject {

   // ===========================================================
   // Constants
   // ===========================================================

   // ===========================================================
   // Fields
   // ===========================================================

   // ===========================================================
   // Constructors
   // ===========================================================

   public Player(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
       super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
   }

   // ===========================================================
   // Methods for/from SuperClass/Interfaces
   // ===========================================================

   @Override
   public void move() {
       this.mPhysicsHandler.setVelocityY(-AccelerometerHelper.TILT * 10);
	   //setVelocityX(-AccelerometerHelper.TILT * 100);
       setRotation(-AccelerometerHelper.TILT * 7);
	   
	   
   //from http://perle-development.com/tutorials/andengine-tutorial-03-player-movement-01/ ||| calls the method that restart the postion of the ship
   //START 12
       OutOfScreenY();
   //END 12
   }

   // ===========================================================
   // Methods
   // ===========================================================
   //from http://perle-development.com/tutorials/andengine-tutorial-03-player-movement-01/ ||| make it go back if it goes off screen
   //START 11
   private void OutOfScreenY() {
	    if (mY > MainActivity.CAMERA_HEIGHT ) { // OutOfScreenX (right)
	        mY = MainActivity.CAMERA_HEIGHT;
	        		
	    } else if (mY < 0) { // OutOfScreenX (left)
	        mY = MainActivity.CAMERA_HEIGHT;
	    }
	}
   //END 11
}
//END 9
*/