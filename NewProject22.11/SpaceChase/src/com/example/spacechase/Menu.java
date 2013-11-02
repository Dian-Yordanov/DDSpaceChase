package com.example.spacechase;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;

import org.andengine.entity.scene.background.ParallaxBackgroundWithRotation;
import org.andengine.entity.scene.background.ParallaxBackgroundWithRotation.ParallaxEntityWithRotation;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.opengl.GLES20;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 01:30:15 - 02.04.2010
 */
public class Menu extends SimpleBaseGameActivity implements IOnMenuItemClickListener {
	// ===========================================================
	// Constants
	// ===========================================================

	static int CAMERA_WIDTH = 1280;
    static int CAMERA_HEIGHT = 720;//To do reverse it

	protected static final int MENU_RESET = 0;
	protected static final int MENU_QUIT = MENU_RESET + 1;
	protected static final int MENU_START = MENU_QUIT +1;
	// ===========================================================
	// Fields
	// ===========================================================

	protected Camera mCamera;

	static Scene mMainScene;

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private ITextureRegion mFaceTextureRegion;

	protected MenuScene mMenuScene;

	private BitmapTextureAtlas mMenuTexture;
	private BitmapTextureAtlas m2MenuTexture;
	private BitmapTextureAtlas m3MenuTexture;
	private BitmapTextureAtlas m4MenuTexture;
	private BitmapTextureAtlas mBackgroundDinamicTexture;
	
	protected ITextureRegion mMenuResetTextureRegion;
	protected ITextureRegion mMenuQuitTextureRegion;
	protected TouchEvent event;
	
	protected ITextureRegion mSpriteTextureRegion;
	protected ITextureRegion m2SpriteTextureRegion;
	protected ITextureRegion m3SpriteTextureRegion;
	protected ITextureRegion m4SpriteTextureRegion;
	protected ITextureRegion mBackgroundDinamicSpriteTextureRegion;
	
	private TextureRegion mStartGameTextureRegion;
	private Texture mStartGameTexture;
	private BitmapTextureAtlas mAutoParallaxBackgroundTexture;
	private BitmapTextureAtlas m2AutoParallaxBackgroundTexture;
	private TextureRegion mParallaxDinamicBackground;
	private TextureRegion mParallaxStaticBackground;
	private int RotationConstant =0;
	
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
		final Display display = getWindowManager().getDefaultDisplay();
    	int cameraWidth = display.getWidth();
        int cameraHeight = display.getHeight();
        CAMERA_WIDTH=cameraWidth;
        CAMERA_HEIGHT=cameraHeight;
        //ScreenScalerX=CAMERA_WIDTH;
        //ScreenScalerY=CAMERA_HEIGHT;
        this.mCamera = new Camera(0, 0, cameraWidth, cameraHeight);

        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		//this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 64, 64, TextureOptions.BILINEAR);
		//this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "face_box_menu.png", 0, 0);
		 
		//this.mBitmapTextureAtlas.load();
		
		//this.mStartGameTexture = new Texture(this.getTextureManager(),256,256,TextureOptions.BILINEAR);
		//this.mStartGameTextureRegion = TextureRegionFactory.createFromAsset(this.mStartGameTexture, this, "gfx/menu_start.png",0,0);
		//this.mEngine.getTextureManager().loadTexture(this.mStartGameTexture);
		this.mBackgroundDinamicTexture = new BitmapTextureAtlas(this.getTextureManager(), 1820,1024, TextureOptions.BILINEAR);
		this.mBackgroundDinamicSpriteTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBackgroundDinamicTexture, this, "BackgroundMenuDinamic1280x800.png", 0, 0);
		
		//this.mMenuResetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "menu_reset.png", 0, 0);
		//this.mMenuQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "menu_quit.png", 0, 50);
		this.mMenuTexture = new BitmapTextureAtlas(this.getTextureManager(), 512, 256, TextureOptions.BILINEAR);
		this.mSpriteTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mMenuTexture, this, "ExitButtonConcep2t.png", 0, 0);
		
		this.m2MenuTexture = new BitmapTextureAtlas(this.getTextureManager(), 512, 256, TextureOptions.BILINEAR);
		this.m2SpriteTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.m2MenuTexture, this, "StartButtonConcept2.png", 0, 0);
		
		this.m3MenuTexture = new BitmapTextureAtlas(this.getTextureManager(), 512, 128, TextureOptions.BILINEAR);
		this.m3SpriteTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.m3MenuTexture, this, "HelpButtonConcept2.png", 0, 0);
		
		this.m4MenuTexture = new BitmapTextureAtlas(this.getTextureManager(), 512, 128, TextureOptions.BILINEAR);
		this.m4SpriteTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.m4MenuTexture, this, "OptionsButtonConcept2.png", 0, 0);
		
		this.mMenuTexture.load();this.m2MenuTexture.load();this.mBackgroundDinamicTexture.load();this.m3MenuTexture.load();this.m4MenuTexture.load();
		
		
		 this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(),1820,1024 , TextureOptions.BILINEAR_PREMULTIPLYALPHA);	        
	     this.mParallaxStaticBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture,this, "BackgroundMenuStatic1280x800.png",0,0);
	     //this.mParallaxDinamicBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture,this, "480x720White.png",0,0);
	    // this.m2AutoParallaxBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(),1820,1024 , TextureOptions.BILINEAR_PREMULTIPLYALPHA);	        
	    // this.mParallaxDinamicBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.m2AutoParallaxBackgroundTexture,this, "BackgroundMenuDinamic1280x800.png",0,0);
	     mAutoParallaxBackgroundTexture.load();//m2AutoParallaxBackgroundTexture.load();
	}
//	public static Context getAppContext()
//    {return Menu.getAppContext();}
	@Override
	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		//this.createMenuScene();

		/* Just a simple scene with an animated face flying around. */
		this.mMainScene = new Scene();
		this.mMainScene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		final Sprite BackgroundDinamic = new Sprite(0,0,this.mBackgroundDinamicSpriteTextureRegion, this.getVertexBufferObjectManager());
	//	final Sprite face = new Sprite(0, 0, this.mFaceTextureRegion, this.getVertexBufferObjectManager());
		final Sprite my3Sprite = new Sprite(CAMERA_WIDTH-318, 200, this.m3SpriteTextureRegion, this.getVertexBufferObjectManager());my3Sprite.registerEntityModifier(new MoveModifier(2, CAMERA_WIDTH-318, CAMERA_WIDTH-368, 200, 200));
		final Sprite my4Sprite = new Sprite(0, 450, this.m4SpriteTextureRegion, this.getVertexBufferObjectManager());my4Sprite.registerEntityModifier(new MoveModifier(2, 0, 50, 450, 450));
		
		
		final Sprite my2Sprite = new Sprite(0, 100, this.m2SpriteTextureRegion, this.getVertexBufferObjectManager()) {
			
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			//	this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2); //the bug is not from that line
		            //    int eventaction = event.getAction(); 
		               // int eventaction = 2;
		                Intent intent1 = new Intent();int i=0;
		       //         float X = event.getX();
		       //         float Y = event.getY();
	//
		                //my2Sprite.setTouchAreaBindingOnActionDownEnabled(false);
		                switch (0) {
		           //    case TouchEvent.ACTION_DOWN: 
			       //            intent1.setClass(getApplicationContext(), MainActivity.class);i--;
			       //            if(i==0){startActivity(intent1);}
			      //             finish();
			      //             break;
		          //      case TouchEvent.ACTION_MOVE: 
			      //             intent1.setClass(getApplicationContext(), MainActivity.class);i--;
			     //             if(i==0){startActivity(intent1);}
			     ////              finish();
			     //              break;
		                   case TouchEvent.ACTION_DOWN: 
		                   intent1.setClass(getApplicationContext(), MainActivity.class);
		                   if(i==0){startActivity(intent1);i--;}
		                   finish(); System.exit(0);
		                   break;
		                   //case TouchEvent.ACTION_UP: intent1.setClass(getApplicationContext(), MainActivity.class);
		                   //startActivity(intent1);
		                   //finish();
	                       // break;
		                   
		                }
				return true;
			}};mMainScene.registerTouchArea(my2Sprite);mMainScene.setTouchAreaBindingOnActionMoveEnabled(false);mMainScene.setTouchAreaBindingOnActionDownEnabled(false);my2Sprite.registerEntityModifier(new MoveModifier(2, 0, 100, 100, 100));
		//	mMainScene.isOnSceneTouchListenerBindingOnActionDownEnabled(my2Sprite) =false;
			 
			// mMainScene.setTouchAreaBindingOnActionDownEnabled(true);
		
		
		
		final Sprite mySprite = new Sprite(CAMERA_WIDTH-318, 550, this.mSpriteTextureRegion, this.getVertexBufferObjectManager()) {
		
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2); //the bug is not from that line
	               // int eventaction = event.getAction(); 
	                int eventaction = 1;
//
	       //         float X = event.getX();
	       //         float Y = event.getY();
//
	                switch (eventaction) {
	                   case TouchEvent.ACTION_DOWN: System.exit(0);
	        		break;
	            //       case TouchEvent.ACTION_MOVE: System.exit(0);{
	            //	        this.setPosition(X, Y);
	           	//        break;}
	                   case TouchEvent.ACTION_UP: System.exit(0);
	                        break;
	                }
			return true;
		}};mMainScene.registerTouchArea(mySprite);mySprite.registerEntityModifier(new MoveModifier(2, CAMERA_WIDTH-318, CAMERA_WIDTH-418, 550, 550));
		
		
		
		
		
		
	//
	//face.registerEntityModifier(new MoveModifier(30, 0, CAMERA_WIDTH - face.getWidth(), 0, CAMERA_HEIGHT - face.getHeight()));
	
	
	final int centerX = (CAMERA_WIDTH ) / 2;
    final int centerY = (CAMERA_HEIGHT ) / 2;
	final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
    
    autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, 0, this.mParallaxStaticBackground, this.getVertexBufferObjectManager())));
    //autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, 0, this.mParallaxDinamicBackground, this.getVertexBufferObjectManager())));
    //mParallaxDinamicBackground.setRotationX(10);
    //setRotation(RotationConstant);
    //mParallaxDinamicBackground.isRotated();
    //this.mMainScene.attachChild(face);
    //RotationConstant+=10;
    //BackgroundDinamic.setRotationCenterX(RotationConstant);
   	
    
     this.mMainScene.setBackground(autoParallaxBackground);      
     BackgroundDinamic.registerEntityModifier(new RotationModifier(6000,36000,0));
     this.mMainScene.attachChild(BackgroundDinamic);
		
		
		this.mMainScene.attachChild(mySprite);
		this.mMainScene.attachChild(my2Sprite);
		this.mMainScene.attachChild(my3Sprite);
		this.mMainScene.attachChild(my4Sprite);
		
		mMainScene.setTouchAreaBindingOnActionDownEnabled(false);
		mMainScene.setTouchAreaBindingOnActionMoveEnabled(false);
		//this.mMainScene.setTouchAreaBindingOnActionUpEnabled(false);
		return this.mMainScene;
	//	mMainScene.registerTouchArea(mySecondSprite);
	//	
	}
	//public boolean onTouchEvent(MotionEvent event) {
   //     int myEventAction = event.getAction(); 
//
    //    float X = event.getX();
    //    float Y = event.getY();

    //    switch (myEventAction) {
    //       case MotionEvent.ACTION_DOWN: this.finish();System.exit(0);
     //   	break; 
    //       case MotionEvent.ACTION_MOVE: this.finish();System.exit(0);{
   //     	    mySprite.setPosition(X, Y);
    //        	break;}
    //       case MotionEvent.ACTION_UP:System.exit(0);
   //             break;
  //      }
  //      return true;
 //   }
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
		if(pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
			if(this.mMainScene.hasChildScene()) {
				/* Remove the menu and reset it. */
				this.mMenuScene.back();
			} else {
				/* Attach the menu. */
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
				/* Restart the animation. */
				this.mMainScene.reset();
			 case MENU_START:
			  //    Intent intent = new Intent();
			 //     intent.setClass(getApplicationContext(), MainActivity.class);
			 //     startActivity(intent);
			      this.finish();
			      return true;
				/* Remove the menu and reset it. */
				//this.mMainScene.clearChildScene();
				//this.mMenuScene.reset();
				//return true;
			case MENU_QUIT:
				/* End Activity. */
				this.finish();
				return true;
			default:
				return false;
		}
	}
	
	
	// ===========================================================
	// Methods
	// ===========================================================

//	protected void createMenuScene() {
//		this.mMenuScene = new MenuScene(this.mCamera);
//
		//this.mMenuScene.addMenuItem(new SpriteMenuItem(MENU_START, this.mStartGameTextureRegion));
		
//		final SpriteMenuItem resetMenuItem = new SpriteMenuItem(MENU_RESET, this.mMenuResetTextureRegion, this.getVertexBufferObjectManager());
//		resetMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
//		this.mMenuScene.addMenuItem(resetMenuItem);

//		final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT, this.mMenuQuitTextureRegion, this.getVertexBufferObjectManager());
//		quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
//		this.mMenuScene.addMenuItem(quitMenuItem);

//		this.mMenuScene.buildAnimations();

//		this.mMenuScene.setBackgroundEnabled(false);

//		this.mMenuScene.setOnMenuItemClickListener(this);
//	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}