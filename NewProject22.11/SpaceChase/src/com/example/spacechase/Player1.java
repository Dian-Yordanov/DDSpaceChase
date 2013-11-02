//from http://perle-development.com/tutorials/andengine-tutorial-03-player-movement-01/ ||| implements movemtn
//START 9
package com.example.spacechase;

import java.util.ArrayList;

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.example.spacechase.MainActivity;

public class Player1 extends GameObject {

   // ===========================================================
   // Constants
   // ===========================================================
	private int RotationConstant =0;
	final int DEFAULT_VELOCITY = 200;
	private int RandomNumberReseter = 0;
	public static int animationForY=1000;
	private int loadingVariable=0;
	public static int StopTranslation=0;
	// ===========================================================
	// Fields
	// ===========================================================

	boolean jumping = false;
	public static boolean mToggleBox=false;
	public static boolean PlayerCollideWithPlatform=false;
	public static ArrayList<Platform> mPlatforms;
   // ===========================================================
   // Fields
   // ===========================================================

   // ===========================================================
   // Constructors
   // ===========================================================

   public Player1(final float pX, final float pY, final TiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
       super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
   }

   // ===========================================================
   // Methods for/from SuperClass/Interfaces
   // ===========================================================

   @Override
   public void move() {
	  // RotationConstant++;
	   if(StopTranslation==0)
	   {
		this.mPhysicsHandler.setVelocityX(-AccelerometerHelper.TILT * DEFAULT_VELOCITY);
		setRotation(RotationConstant);
		OutOfScreenX();
		explode();
		}
		//if(loadingVariable == 0){mY = 1000;loadingVariable++;}
		mY=animationForY;
	//	Jumping();
		if(StopTranslation==1)
		   {
			this.mPhysicsHandler.setVelocityX(0);
		   }
   }

   // ===========================================================
   // Methods
   // ===========================================================
   //from http://perle-development.com/tutorials/andengine-tutorial-03-player-movement-01/ ||| make it go back if it goes off screen
   //START 11
   private void Jumping() {
		if (jumping) {
			Jump();
		} else {
			Fall();
		}
	}
  private void Animate(){
  
  }
	private void Jump() {
		if (mY <= MainActivity.CAMERA_HEIGHT / 2) { // mY <= 400
			jumping = false;
		} else {
			this.mPhysicsHandler.setVelocityY(-DEFAULT_VELOCITY);
		}
	}

	private void Fall() {
		if (mY >= MainActivity.CAMERA_HEIGHT) { // mY >= 800
			jumping = true;
		} else {
			this.mPhysicsHandler.setVelocityY(DEFAULT_VELOCITY);
		}
	}

	private void OutOfScreenX() {
		if (mX > MainActivity.CAMERA_WIDTH-128-30-56) { // OutOfScreenX (right)
			mX = MainActivity.CAMERA_WIDTH-128-30-56;
		} else if (mX < 0+30-56) { // OutOfScreenX (left)
			mX = 0+30-56;
		}
	}

	@Override
	public void explode() {
		// TODO Auto-generated method stub
		
	}}
//END 9