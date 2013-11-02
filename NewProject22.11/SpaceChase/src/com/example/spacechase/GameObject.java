//from http://perle-development.com/tutorials/andengine-tutorial-03-player-movement-01/ ||| implement movement
//START 8
package com.example.spacechase;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;
import android.widget.Toast;
 
public abstract class GameObject extends AnimatedSprite {
 
    // ===========================================================
    // Constants
    // ===========================================================
 
    // ===========================================================
    // Fields
    // ===========================================================
 public static boolean IsCollided=false;
    public PhysicsHandler mPhysicsHandler;
 
    // ===========================================================
    // Constructors
    // ===========================================================
 
    public GameObject(final float pX, final float pY, final ITiledTextureRegion pTiledTextureRegion, final VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
        this.mPhysicsHandler = new PhysicsHandler(this);
        this.registerUpdateHandler(this.mPhysicsHandler);
    }
 
    // ===========================================================
    // Getter & Setter
    // ===========================================================
 
    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================
 
    @Override
    protected void onManagedUpdate(float pSecondsElapsed) {
        move();
        explode();
       // if(Player.mToggleBox==true){Log.w("objects.Player","mToggleBox "+Player.mToggleBox);IsCollided=true;}
       
        //if(GameObject.IsCollided==true){MainActivity.mMainScene.detachChild(MainActivity.mMainScene);}
        //if(Player.mToggleBox==true){Toast.makeText(MainActivity.method(), "Touch and hold the scene and the camera will smoothly zoom in.\nRelease the scene it to zoom out again.", Toast.LENGTH_LONG).show();}
        super.onManagedUpdate(pSecondsElapsed);
        //if(Player.PlayerCollideWithPlatform==true){MainActivity.mMainScene.detachChild(MainActivity.mPlayerTextureRegion);}Player.PlayerCollideWithPlatform=false;
      //  Player.mToggleBox=false;IsCollided=false;
        
    }
 
    // ===========================================================
    // Methods
    // ===========================================================
 
  

	public abstract void explode();
		
		
	

	public abstract void move();
}
//END 8