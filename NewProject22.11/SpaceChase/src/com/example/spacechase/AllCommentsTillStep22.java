/**
//from http://perle-development.com/tutorials/andengine-tutorial-01-creating-a-scene/ ||| build simple android application
//1 START
package com.example.spacechase;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.util.Log;
import android.view.Display;
 
public class MainActivity extends SimpleBaseGameActivity {
    // ===========================================================
    // Constants
    // ===========================================================
    static int CAMERA_WIDTH = 1280;
    static int CAMERA_HEIGHT = 720;//To do reverse it
    int CameraWidthChanger =0;
    int CameraHeightChanger=0;
 
    // ===========================================================
    // Fields
    // ===========================================================
 
    private Camera mCamera;
    private Scene mMainScene;
    //from tutorial videos ||| define BitmapTextureAtlas and TextureRegion
    //3 START
    private BitmapTextureAtlas myBackgroundTextureAtlas;
    private IBitmapTextureAtlasSource myIBackgroundTextureAtlas;
	private TextureRegion myBackgroundTextureRegion;	
	private TextureRegion myBackground1TextureRegion;
	private BitmapTextureAtlas myBackground1TextureAtlas;
	//3 END
	//from http://perle-development.com/tutorials/andengine-tutorial-03-player-movement-01/ |||need it to run the motion programm
	//START 10
	private TiledTextureRegion mPlayerTiledTextureRegion;
	private TiledTextureRegion mPlayerTiled2TextureRegion;
	
	 // private Camera mCamera;

      private BitmapTextureAtlas mBitmapTextureAtlas;
      private TiledTextureRegion mPlayerTextureRegion;
      private TiledTextureRegion mEnemyTextureRegion;

      private BitmapTextureAtlas mAutoParallaxBackgroundTexture;

      private TextureRegion mParallaxLayerBack;
      private TextureRegion mParallaxLayerMid;
      private TextureRegion mParallaxLayerFront;
      //18
      private TextureRegion mParallaxDinamicBackground;
      private TextureRegion mParallaxDinamic1Background;
      private TextureRegion mParallaxDinamic2Background;
      private TextureRegion mParallaxStaticBackground;
      private TextureRegion mParallaxStatic1Background;
      
      //18
	//END 10
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
    	mAccelerometerHelper = new AccelerometerHelper(this);
    	final Display display = getWindowManager().getDefaultDisplay();
    	int cameraWidth = display.getWidth();
        int cameraHeight = display.getHeight();
        CAMERA_WIDTH=cameraWidth;
        CAMERA_HEIGHT=cameraHeight;
        CameraWidthChanger=CAMERA_WIDTH;
        CameraHeightChanger=CAMERA_HEIGHT;
        this.mCamera = new Camera(0, 0, cameraWidth, cameraHeight);
 
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
       // this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
       // return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
    }
    
    //from http://andengine.wikidot.com/detect-screen-resolution ||| Detect Screen Resolution
    //2 START
    public Engine onLoadEngine() {
        final Display display = getWindowManager().getDefaultDisplay();
        int cameraWidth = display.getWidth();
        int cameraHeight = display.getHeight();
     
        String deb = String.format("Screen: %d / %d",cameraWidth,cameraHeight);
        Log.d("Debug:", deb);
     
        this.mCamera = new Camera(0, 0, cameraWidth,cameraHeight);
        return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
            new RatioResolutionPolicy(cameraWidth, cameraHeight), this.mCamera));
    //	this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
     //   return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
    }
    //2 END
    //16
    public void onLoadResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "player.png", 0, 0, 3, 4);
        this.mEnemyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "enemy.png", 73, 0, 3, 4);

        this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(),1024, 1024, TextureOptions.DEFAULT);
        this.mParallaxLayerFront = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "parallax_background_layer_front.png", 0, 0);
        this.mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "parallax_background_layer_back.png", 0, 188);
        this.mParallaxLayerMid = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "parallax_background_layer_mid.png", 0, 669);

      //  this.mEngine.getTextureManager().loadTextures(this.mBitmapTextureAtlas, this.mAutoParallaxBackgroundTexture);
        mBitmapTextureAtlas.load();
        mAutoParallaxBackgroundTexture.load();
}
//16
    //from tutorial videos ||| define the properties of myBackgroundTextureAtla and myBackgroundTextureRegion
    //4 START
    @Override
    public void onCreateResources() { 
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	myBackgroundTextureAtlas  = new BitmapTextureAtlas(this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT);
    	myBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.myBackgroundTextureAtlas, this, "MainObject.png",128,128); 
    	this.mPlayerTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.myBackgroundTextureAtlas, this, "MainObject.png", 128, 128, 1,1 );
    	//myIBackgroundTextureAtlas = new IBitmapTextureAtlasSource(1280, 720);
    	
    	
    	//myBackground1TextureAtlas  = new BitmapTextureAtlas(this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT);	
    	myBackground1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.myBackgroundTextureAtlas, this, "meteorite.png",0,0); 
    	this.mPlayerTiled2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.myBackgroundTextureAtlas, this, "meteorite.png", 0, 0, 1,1 );
    	//16
//BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	//17 try to reverse the directions and did not workout
       // CAMERA_WIDTH=CameraHeightChanger;
        //CAMERA_HEIGHT=CameraWidthChanger;
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "player.png", 0, 0, 3, 4);
        this.mEnemyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "enemy.png", 73, 0, 3, 4);
        
        this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(),1820,1024 , TextureOptions.BILINEAR_PREMULTIPLYALPHA);
   //     this.mParallaxLayerFront = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "parallax_background_layer_front.png", 0, 0);
   //     this.mParallaxLayerBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "parallax_background_layer_back.png", 0, 188);
   //     this.mParallaxLayerMid = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "parallax_background_layer_mid.png", 0, 669);
        //18 some error here       
       // 
        
        this.mParallaxStaticBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture,this, "1280x720outer_space_trip_08_by_brujo.png",480,0);
        this.mParallaxDinamic2Background = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture,this, "480x720White.png",0,0);
        this.mParallaxDinamic1Background = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture,this, "480x720White.png",0,0);
       // this.mParallaxStatic1Background = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture,this, "1280x720White1.png",0,0);
        this.mParallaxDinamicBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture,this, "480x720White.png",0,0);
        
        //18
        //CAMERA_WIDTH=CameraWidthChanger;
        //CAMERA_HEIGHT=CameraHeightChanger;
        //17 
      //  this.mEngine.getTextureManager().loadTextures(this.mBitmapTextureAtlas, this.mAutoParallaxBackgroundTexture);
        mBitmapTextureAtlas.load();
        mAutoParallaxBackgroundTexture.load();
    	//16
    	myBackgroundTextureAtlas.load();
    	//from http://perle-development.com/tutorials/andengine-tutorial-02-textures-and-sprites/ |||
    	// Load all the textures this game needs. w
    	//7 START
    		//mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 32, 32);
        	//mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, "face_box.png", 0, 0);
        	//mBitmapTextureAtlas.load();
    	//7 END
    	}
    //4 END

    @Override
    protected Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger()); // logs the frame rate
        this.mMainScene = new Scene();
        //final Scene scene = new Scene();
        //because of 7
                
        //from http://andengineguides.wordpress.com/ ||| load the textures into the engine
        //5 START
        this.mEngine.getTextureManager().loadTexture(this.myBackgroundTextureAtlas);
        //5 END
      //from andengine tutorials ||| Show background in the app
        //6 START
        //17
  //      mMainScene.setBackground(new SpriteBackground(new Sprite(0,0,myBackgroundTextureRegion, getVertexBufferObjectManager())));
        //17
      //6 END
       
       
        // from http://perle-development.com/tutorials/andengine-tutorial-02-textures-and-sprites/ ||| should load and show background
        //7 START
        //11:52 15.11 removed ||| this only sets background color
        //11 START
        //this.mMainScene.setBackground(new Background(1, 1, 1));
        //11 END
        // Centre the player on the camera.
        final int centerX = (CAMERA_WIDTH - myBackgroundTextureAtlas.getWidth()) / 2;
        final int centerY = (CAMERA_HEIGHT - myBackgroundTextureAtlas.getHeight()) / 2;
     //   final int centeriX = (CAMERA_HEIGHT) / 2;
     //   final int centeriY = (CAMERA_WIDTH) / 2;
     
       //  Create the sprite and add it to the scene. 
        //final RepeatingSpriteBackground oBackground = new RepeatingSpriteBackground(centerX, centerY, this.getTextureManager(),this.myIBackgroundTextureAtlas, this.getVertexBufferObjectManager());
        //hidden because of parallax background experiments
        //17
        final Player oPlayer = new Player(CAMERA_WIDTH/2-128/2, CAMERA_HEIGHT-128, this.mPlayerTiledTextureRegion, this.getVertexBufferObjectManager());
        this.mMainScene.attachChild(oPlayer);
        //7 END
        final Player1 oPlayer1 = new Player1(centerX+128 , centerY+256, this.mPlayerTiled2TextureRegion, this.getVertexBufferObjectManager());
        this.mMainScene.attachChild(oPlayer1);
        //17
        //call OutOfScreen;
        
       // public final move() { 
        //16
        
       final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
       //18 
       autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(CAMERA_WIDTH - (CAMERA_WIDTH-1), CAMERA_HEIGHT - (CAMERA_HEIGHT-1), this.mParallaxStaticBackground, this.getVertexBufferObjectManager())));
      autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(25.0f, new Sprite(480, centerY, this.mParallaxDinamic1Background, this.getVertexBufferObjectManager())));
      autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(25.0f, new Sprite(0, centerY, this.mParallaxDinamicBackground, this.getVertexBufferObjectManager())));
      autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(25.0f, new Sprite(960, centerY, this.mParallaxDinamic2Background, this.getVertexBufferObjectManager())));
  //    autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(25.0f, new Sprite(centerX, CAMERA_HEIGHT - (CAMERA_HEIGHT-1), this.mParallaxDinamic1Background, this.getVertexBufferObjectManager())));
      // autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(centerX, CAMERA_HEIGHT, this.mParallaxStatic1Background, this.getVertexBufferObjectManager())));
       
   //    autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(25.0f, new Sprite(centerX, centerY, this.mParallaxDinamicBackground, this.getVertexBufferObjectManager())));
       
       
       //18
  //     autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(5.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerBack.getHeight(), this.mParallaxLayerBack, this.getVertexBufferObjectManager())));
 //      autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0, 80, this.mParallaxLayerMid, this.getVertexBufferObjectManager())));
 //      autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerFront.getHeight(), this.mParallaxLayerFront, this.getVertexBufferObjectManager())));
       this.mMainScene.setBackground(autoParallaxBackground);      
       
       // Calculate the coordinates for the face, so its centered on the camera. 
      // final int playerX = (CAMERA_WIDTH - this.mPlayerTextureRegion.getTileWidth()) / 2;
      // final int playerY = CAMERA_HEIGHT - this.mPlayerTextureRegion.getTileHeight() - 5;

       //Create two sprits and add it to the scene. 
      // final AnimatedSprite player = new AnimatedSprite(centerX, centerY, this.mPlayerTextureRegion, this.getVertexBufferObjectManager());
      // player.setScaleCenterY(this.mPlayerTextureRegion.getHeight());
     //  player.setScale(2);
     //  player.animate(new long[]{200, 200, 200}, 3, 5, true);

  //     final AnimatedSprite enemy = new AnimatedSprite(centerX - 80, centerY, this.mEnemyTextureRegion, this.getVertexBufferObjectManager());
  //     enemy.setScaleCenterY(this.mEnemyTextureRegion.getHeight());
  //     enemy.setScale(2);
  //     enemy.animate(new long[]{200, 200, 200}, 3, 5, true);

     //  this.mMainScene.attachChild(player);
  //     this.mMainScene.attachChild(enemy);
       
       //16
      
        return this.mMainScene;
    }
   
}

//1 END
*/