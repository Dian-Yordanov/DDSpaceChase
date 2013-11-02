//from http://perle-development.com/tutorials/andengine-tutorial-03-player-movement-01/ ||| implements movemtn
//START 9
package com.example.spacechase;

import java.util.ArrayList;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import java.lang.String;

import android.util.Log;
import android.widget.Toast;

import com.example.spacechase.MainActivity;

public class Player extends GameObject {

	// ===========================================================
	// Constants
	// ===========================================================
	private int RotationConstant =0;
	final int DEFAULT_VELOCITY = 200;
	private int RandomNumberReseter = 0;
	// ===========================================================
	// Fields
	// ===========================================================

	boolean jumping = false;
	public static boolean mToggleBox=false;
	public static boolean PlayerCollideWithPlatform=false;
	public static ArrayList<Platform> mPlatforms;

	// ===========================================================
	// Constructors
	// ===========================================================

	  public Player(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
	        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public static ArrayList<Platform> getmPlatforms() {
		return mPlatforms;
	}

	public void setmPlatforms(ArrayList<Platform> mPlatforms) {
		this.mPlatforms = mPlatforms;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void move() {
		RotationConstant++;
		this.mPhysicsHandler.setVelocityX(-AccelerometerHelper.TILT * DEFAULT_VELOCITY);
		setRotation(RotationConstant);
		OutOfScreenX();
		explode();
	//	Jumping();

		for (Platform Platform : getmPlatforms()) {
			if (this.collidesWith(Platform)) {
				Log.v("objects.Player", "just collided with platform ;)");
				//Toast.makeText(MainActivity.ContentPasser, "Touch the screen to update (redraw) an existing BitmapTextureAtlas with every touch!", Toast.LENGTH_LONG).show();
				//toggle();
				PlayerCollideWithPlatform=true;mToggleBox=true;IsCollided=true;
				Player1.animationForY=MainActivity.CAMERA_HEIGHT-128-176;
				mY=-150;Player1.StopTranslation=1;
				MainActivity.instance1++;
				MainActivity.instance2++;
				//Log.w("objects.Player","mToggleBox "+mToggleBox);
				//if(Platform.collidesWith(Platform)){
				//	Log.e("objects.Platform", "just collided with platform ;)");}//PlayerCollideWithPlatform=true;}
				//  if(Player.mToggleBox=true){Toast.makeText(MainActivity.getApplicationContext(), "Touch and hold the scene and the camera will smoothly zoom in.\nRelease the scene it to zoom out again.", Toast.LENGTH_LONG).show();}
			}
		}
		
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void Jumping() {
		if (jumping) {
			Jump();
		} else {
			Fall();
		}
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
		if (mX > MainActivity.CAMERA_WIDTH-128-30) { // OutOfScreenX (right)
			mX = MainActivity.CAMERA_WIDTH-128-30;
		} else if (mX < 0+30) { // OutOfScreenX (left)
			mX = 0+30;
		}
	}
	/*
	public void toggle() {
		MainActivity.mPlayerBitmapTextureAtlas.clearTextureAtlasSources();
		MainActivity.mToggleBox = !MainActivity.mToggleBox;
		BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(MainActivity.mPlayerBitmapTextureAtlas, this, MainActivity.mToggleBox ? "MainObject.png" : "new explosion x1024 copytransperant.png", 0, 0, 2, 1);
	}
	*/

	@Override
	public void explode() {
		for (Platform Platform : getmPlatforms()) {
			if (this.collidesWith(Platform)) {
				MainActivity.mToggleBox=true;
				
				//MainActivity.mMainScene.detachChild(oPlayer);
			}
			}
	}
	
}