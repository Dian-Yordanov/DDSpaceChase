package com.example.spacechase;

import java.util.Random;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Platform extends GameObject {

	// ===========================================================
	// Constants
	// ===========================================================
	private int RotationConstant =0;
	Random rand = new Random();
	private int randomNumber1 = rand.nextInt(10)*MainActivity.CAMERA_WIDTH/10;
	private int RandomNumberReseter = 0;
	// ===========================================================
	// Fields
	// ===========================================================
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public Platform(int pX, int pY,final ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void move() {
		// TODO Auto-generated method stub
		if(Player1.StopTranslation==0)
		{
		this.mPhysicsHandler.setVelocityY(100);
		RotationConstant = RotationConstant+1;
		setRotation(RotationConstant);
		OutOfScreenY();OutOfScreenX();
		if(RandomNumberReseter >0){randomNumber1 = rand.nextInt(10)*MainActivity.CAMERA_WIDTH/10;RandomNumberReseter=0;}
		RandomNumberReseter++;
		if(randomNumber1>MainActivity.CAMERA_WIDTH){randomNumber1-=256;}
		if(randomNumber1<128){randomNumber1+=128;}
		}
		if(Player1.StopTranslation==1)
		   {
			this.mPhysicsHandler.setVelocityY(0);
			RotationConstant = RotationConstant+1;
			setRotation(RotationConstant);
		   }
	}

	// ===========================================================
	// Methods
	// ===========================================================
	private void OutOfScreenY() {
		if (mY > MainActivity.CAMERA_WIDTH) { // OutOfScreenX (right)
			mY = 0;//+MainActivity.dNumber; //+ MainActivity.aNumber;
			mX = randomNumber1;
		    //   if(RandomNumberReseter >0){randomNumber2 = (int)(Math.random()*CAMERA_WIDTH);}
		    //   if(RandomNumberReseter >0){randomNumber3 = (int)(Math.random()*CAMERA_WIDTH);}
		       
		} else if (mY < 0) { // OutOfScreenX (left)
			mY = 0;
			mX = randomNumber1;
		}
		
	}
	private void OutOfScreenX() {
		if (mX > MainActivity.CAMERA_WIDTH-128-30) { // OutOfScreenX (right)
			mX = MainActivity.CAMERA_WIDTH-128-30;
		} else if (mX < 0+30) { // OutOfScreenX (left)
			mX = 0+30;
		}
	}

	@Override
	public void explode() {
		// TODO Auto-generated method stub
		
	}
}
