/**
package com.example.spacechase;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.Intent;
import android.opengl.GLES20;
import android.view.KeyEvent;
import android.view.View;


public class Menu extends SimpleBaseGameActivity implements IOnMenuItemClickListener {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	protected static final int MENU_RESET = 0;
	protected static final int MENU_QUIT = MENU_RESET + 1;
	//protected static sprite MENU_QUIT = MENU_RESET + 1;
	

	// ===========================================================
	// Fields
	// ===========================================================

	protected Camera mCamera;

	protected Scene mMainScene;

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private ITextureRegion mFaceTextureRegion;

	protected MenuScene mMenuScene;

	private BitmapTextureAtlas mMenuTexture;
	protected ITextureRegion mMenuResetTextureRegion;
	protected ITextureRegion mMenuQuitTextureRegion;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
	}

	public void onLoadResources() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "face_box_menu.png", 0, 0);
		this.mBitmapTextureAtlas.load();

		this.mMenuTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		this.mMenuResetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "menu_reset.png", 0, 0);
		this.mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "menu_quit.png", 0, 0);
		this.mMenuTexture.load();
	}

	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		this.createMenuScene();

		
		this.mMainScene = new Scene();
		this.mMainScene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		final Sprite exit = new Sprite(0,0,this.mMenuQuitTextureRegion, this.getVertexBufferObjectManager());
		final Sprite face = new Sprite(0, 0, this.mFaceTextureRegion, this.getVertexBufferObjectManager());
		face.registerEntityModifier(new MoveModifier(30, 0, CAMERA_WIDTH - face.getWidth(), 0, CAMERA_HEIGHT - face.getHeight()));
		//exit.onAreaTouched(pSceneTouchEvent, exit.getX(), exit.getY());
		//exit.(startActivity(new Intent(this, MainActivity.class)), exit.getX(), exit.getY());
		//Intent i = new Intent(this.getBaseContext(), MainActivity.class);
		//startActivity(i);
		//MENU_QUIT=exit;
		//scene.registerTouchArea(face);
		
//		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) 
//		{
//			this.setPosition(pSceneTouchEvent.getX() - MainActivity.CAMERA_HEIGHT / 2, pSceneTouchEvent.getY() - MainActivity.CAMERA_WIDTH / 2);
//			return true;
//		}
		mMainScene.setOnAreaTouchListener(new IOnAreaTouchListener() {
		    @Override
		    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, ITouchArea pTouchArea, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		        if (pTouchArea.equals(exit)) {
		        	finish();
		            System.exit(0);	
		            return true;
		        }
		        return false;
		    }
		});
		        
		//mMainScene.setTouchAreaBindingOnActionDownEnabled(true);
		mMainScene.registerTouchArea(exit);
		this.mMainScene.attachChild(face);
		this.mMainScene.attachChild(exit);
		return this.mMainScene;
	}

	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if(pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if(this.mMainScene.hasChildScene()) {
				//Intent i = new Intent(this.getBaseContext(), MainActivity.class);
				//startActivity(i);
				
				
				this.mMenuScene.back();
			} else {
				
				this.mMainScene.setChildScene(this.mMenuScene, false, true, true);
				
			}
			return true;
		} else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}

	@Override
	public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
		switch(pMenuItem.getID()) {
			case MENU_RESET:
				
				this.mMainScene.reset();
				//Intent i = new Intent(getBaseContext(), MainActivity.class);
				//startActivity(i);
				
				this.mMainScene.clearChildScene();
				//this.mMenuScene.reset();
				return true;
			case MENU_QUIT:
				
				this.finish();
				//startActivity(new Intent(this, MainActivity.class));
				return true;
				 
			//case MENU_	
			default:
				return true;
				
		}
	}
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		 if (v.getId() == MENU_RESET)
		 {
			 Intent i = new Intent(v.getContext(), MainActivity.class);
			 startActivity(i);
			 //startActivity(new Intent(this, EventActivity.class)); 
		 }
		 //MENU BUTTON QUIT
		 if (v.getId() == MENU_QUIT)
		 {
			 finish();
	            System.exit(0);	
	        	 
		 }
		//MENU BUTTON QUIT
	}
	// ===========================================================
	// Methods
	// ===========================================================
	
	protected void createMenuScene() {
		this.mMenuScene = new MenuScene(this.mCamera);

		final SpriteMenuItem resetMenuItem = new SpriteMenuItem(MENU_RESET, this.mMenuResetTextureRegion, this.getVertexBufferObjectManager());
		resetMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.mMenuScene.addMenuItem(resetMenuItem);
		//this.mMenuScene.addItem(resetMenuItem);
		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT, this.mMenuQuitTextureRegion, this.getVertexBufferObjectManager());
		quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.mMenuScene.addMenuItem(quitMenuItem);

		this.mMenuScene.buildAnimations();

		this.mMenuScene.setBackgroundEnabled(false);

		this.mMenuScene.setOnMenuItemClickListener(this);
		
	}


	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
*/