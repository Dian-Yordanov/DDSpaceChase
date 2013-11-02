//from http://perle-development.com/tutorials/andengine-tutorial-01-creating-a-scene/ ||| build simple android application
//1 START
package com.example.spacechase;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.CameraScene;
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
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import android.content.Context;
import com.example.spacechase.Player;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.Toast;
 
public class MainActivity extends SimpleBaseGameActivity {
    // ===========================================================
    // Constants
    // ===========================================================
    static int CAMERA_WIDTH = 1280;
    static int CAMERA_HEIGHT = 720;//To do reverse it
    //a variable that should scale the screen on different devices. it can do that be either looking at different screen sizes and scaling the place of objects or by using some AntEngine method
    public double ScreenScalerX = 0.0; //may have to convert them to integers or use MathRound in code
    public double  ScreenScalerY = 0.0; //why not make something like CAMERA_HEIGHT/OBJECT_HEIGTH
    //the code may not need such a variable at all for now
    int CameraWidthChanger =0;
    int CameraHeightChanger=0;
    Random rand = new Random();
 //   static int aNumber = (int) (Math.random() * 50 + (int) (Math.random() * 10));
//    static int bNumber = (int) (Math.random() * 50 + 50); //maybe they should add with each other or something like that
//    static int cNumber = (int) (Math.random() * 50 + 100);//http://www.andengine.org/forums/gles2/random-time-before-sprite-appearance-t9936.html another solution
//    static int dNumber = (int) (Math.random() * 50 + 100);
    private int randomNumber1 = (int) (Math.random()*CAMERA_WIDTH-100);
    private int randomNumber2 = (int) (Math.random()*CAMERA_WIDTH-100);
    private int randomNumber3 = (int) (Math.random()*CAMERA_WIDTH-100);
    // ===========================================================
    // Fields
    // ===========================================================
  //  private int RandomNumberReseter = 0;
    private CameraScene mPauseScene;
    private Camera mCamera;
    static Scene mMainScene;
    private BitmapTextureAtlas myBackgroundTextureAtlas;
    private IBitmapTextureAtlasSource myIBackgroundTextureAtlas;
	private TextureRegion myBackgroundTextureRegion;	
	public static TextureRegion myBackground1TextureRegion;
	private BitmapTextureAtlas myBackground1TextureAtlas;
	private TiledTextureRegion mPlayerTiledTextureRegion;
	private TiledTextureRegion mPlayerTiled2TextureRegion;
    private BitmapTextureAtlas mBitmapTextureAtlas;
    public static TiledTextureRegion mPlayerTextureRegion;
    private TiledTextureRegion mFaceTextureRegion;
    private BitmapTextureAtlas mAutoParallaxBackgroundTexture;
    private TextureRegion mParallaxLayerBack;
    private TextureRegion mParallaxLayerMid;
    private TextureRegion mParallaxLayerFront;
    private TextureRegion mParallaxDinamicBackground;
    private TextureRegion mParallaxDinamic1Background;
    private TextureRegion mParallaxDinamic2Background;
    private TextureRegion mParallaxStaticBackground;
    private TextureRegion mParallaxStatic1Background;
    //pause image
    private BitmapTextureAtlas mBitmapTextureAtlasForPause;
    private ITextureRegion mPausedTextureRegion;
    
    public static boolean mToggleBox = false;
    public static int instance1 =1;
    public static int instance2 =1;
    //private Camera mCamera;
	private AccelerometerHelper mAccelerometerHelper;
	//private Scene mMainScene;
	//public static Context ContentPasser=getApplicationContext();
	public   Context method()
	{
		
		return getApplicationContext();
	}
	private ArrayList<Platform> mPlatforms = new ArrayList<Platform>();

	static BitmapTextureAtlas mPlayerBitmapTextureAtlas;
	//private TextureRegion mPlayerTextureRegion;

	private BitmapTextureAtlas mPlatformBitmapTextureAtlas;
	private TiledTextureRegion mPlatformTextureRegion;
    // ===========================================================
    // Constructors
    // ===========================================================
	private BitmapTextureAtlas mPlayer2BitmapTextureAtlas;
	private TiledTextureRegion mPlayerTiled22TextureRegion;
 
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
        //ScreenScalerX=CAMERA_WIDTH;
        //ScreenScalerY=CAMERA_HEIGHT;
        this.mCamera = new Camera(0, 0, cameraWidth, cameraHeight);
        
       
        
        if(CAMERA_WIDTH>960 && CAMERA_WIDTH<=1280){ScreenScalerX=10.0;}
        if(CAMERA_WIDTH>800 && CAMERA_WIDTH<=960){ScreenScalerX=7.5;}
        if(CAMERA_WIDTH>480 && CAMERA_WIDTH<=800){ScreenScalerX=6.25;}
        if(CAMERA_WIDTH>320 && CAMERA_WIDTH<=480){ScreenScalerX=3.75;}
        if(CAMERA_WIDTH<=320){ScreenScalerX=2.5;}
        
        if(CAMERA_HEIGHT>540 && CAMERA_HEIGHT<=800){ScreenScalerY=6.25;}
        if(CAMERA_HEIGHT>480 && CAMERA_HEIGHT<=540){ScreenScalerY=4.2;}
        if(CAMERA_HEIGHT>320 && CAMERA_HEIGHT<=480){ScreenScalerY=3.75;}
        if(CAMERA_HEIGHT>240 && CAMERA_HEIGHT<=320){ScreenScalerY=2.5;}
        if(CAMERA_HEIGHT<=240){ScreenScalerY=1.875;}
        
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
       // this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
       // return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
    }
    
    //from http://andengine.wikidot.com/detect-screen-resolution ||| Detect Screen Resolution
    public Engine onLoadEngine() {
        final Display display = getWindowManager().getDefaultDisplay();
        int cameraWidth = display.getWidth();
        int cameraHeight = display.getHeight();
     
        String deb = String.format("Screen: %d / %d",cameraWidth,cameraHeight);
        Log.d("Debug:", deb);
        
       
     //   RandomNumberReseter++;
        
		randomNumber1 = rand.nextInt(10)*MainActivity.CAMERA_WIDTH/10;
		
		randomNumber2 = rand.nextInt(10)*MainActivity.CAMERA_WIDTH/10;
		randomNumber3 = rand.nextInt(10)*MainActivity.CAMERA_WIDTH/10;
		if(randomNumber1>MainActivity.CAMERA_WIDTH){randomNumber1-=256;}
		if(randomNumber1<128){randomNumber1+=128;}
		if(randomNumber2>MainActivity.CAMERA_WIDTH){randomNumber2-=256;}
		if(randomNumber2<128){randomNumber2+=128;}
		if(randomNumber3>MainActivity.CAMERA_WIDTH){randomNumber3-=256;}
		if(randomNumber3<128){randomNumber3+=128;}
        
        this.mCamera = new Camera(0, 0, cameraWidth,cameraHeight);
        return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
            new RatioResolutionPolicy(cameraWidth, cameraHeight), this.mCamera));
    }
   
    @Override
    public void onCreateResources() { 
    	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    	myBackgroundTextureAtlas  = new BitmapTextureAtlas(this.getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT);
    	myBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.myBackgroundTextureAtlas, this, "MainObject.png",128,128); 
    	this.mPlayerTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.myBackgroundTextureAtlas, this, "MainObject.png", 128, 128, 1,1 );
    	
    	this.mPlayer2BitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024, 1024);
    	this.mPlayerTiled2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mPlayer2BitmapTextureAtlas, this, "new explosion x1024 copytransperant.png", 0, 0, 4,4 );
    	this.mPlayerTiled22TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mPlayer2BitmapTextureAtlas, this, "new explosion x1024 copytransperant.png", 0, 0, 1,2 );
    	this.mPlayer2BitmapTextureAtlas.load();
    	
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "player.png", 0, 0, 3, 4);
        this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, "new explosion x1024 copytransperant.png", 0, 0, 4, 4);
        
        this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(),1820,1024 , TextureOptions.BILINEAR_PREMULTIPLYALPHA);

        
        this.mParallaxStaticBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture,this, "1280x800MainActivityStaticBackground.png",480,0);
        this.mParallaxDinamic2Background = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture,this, "480x720White.png",0,0);
        this.mParallaxDinamic1Background = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture,this, "480x720White.png",0,0);
      
        this.mParallaxDinamicBackground = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture,this, "480x720White.png",0,0);
        /* Texture for Player */
		this.mPlayerBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 128, 128);
		this.mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mPlayerBitmapTextureAtlas, this, "MainObject.png", 0, 0, 1, 1);
		this.mPlayerBitmapTextureAtlas.load();
		
		  /* Texture for pause */
		this.mBitmapTextureAtlasForPause = new BitmapTextureAtlas(this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		this.mPausedTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlasForPause, this, "paused.png", 0, 0);
		this.mBitmapTextureAtlasForPause.load();

		
		/* Texture for platform */
		this.mPlatformBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 128, 128);
		this.mPlatformTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mPlatformBitmapTextureAtlas, this, "meteorite.png", 0, 0,1,1);
		this.mPlatformBitmapTextureAtlas.load();
        mBitmapTextureAtlas.load();
        mAutoParallaxBackgroundTexture.load();

    	myBackgroundTextureAtlas.load();
    	
    	}
//static Context actvitiyContext = MainActivity.getApplicationContext();

    @Override
    protected Scene onCreateScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger()); // logs the frame rate
        this.mMainScene = new Scene();

        this.mEngine.getTextureManager().loadTexture(this.myBackgroundTextureAtlas);

       

      
        final int centerX = (CAMERA_WIDTH - myBackgroundTextureAtlas.getWidth()) / 2;
        final int centerY = (CAMERA_HEIGHT - myBackgroundTextureAtlas.getHeight()) / 2;
        
       // final float centerX = (CAMERA_WIDTH - this.mPausedTextureRegion.getWidth()) / 2;
       //final float centerY = (CAMERA_HEIGHT - this.mPausedTextureRegion.getHeight()) / 2;
	//	final Sprite pausedSprite = new Sprite(0, 0, this.mPausedTextureRegion, this.getVertexBufferObjectManager());
	//	this.mMainScene.attachChild(pausedSprite);
        
        //final Player oPlayer = new Player(CAMERA_WIDTH/2-128/2, CAMERA_HEIGHT-128, this.mPlayerTiledTextureRegion, this.getVertexBufferObjectManager());
        //this.mMainScene.attachChild(oPlayer);
        /* Create the sprite and add it to the scene. */
		final Player oPlayer = new Player(CAMERA_WIDTH/2, CAMERA_HEIGHT-128-100, this.mPlayerTextureRegion, this.getVertexBufferObjectManager());
		//final Player oPlayer1 = new Player(CAMERA_WIDTH/2, CAMERA_HEIGHT-128-100, this.mPlayerTextureRegion, this.getVertexBufferObjectManager());
		this.mMainScene.attachChild(oPlayer);
		//if(GameObject.IsCollided==true){this.mMainScene.detachChild(oPlayer);GameObject.IsCollided=false;}
		//if(MainActivity.mToggleBox==false){this.mMainScene.detachChild(oPlayer);}
		//if(mToggleBox==true){this.mMainScene.attachChild(oPlayer1);}
		
		final Player1 oPlayer1 = new Player1(CAMERA_WIDTH/2-56, CAMERA_HEIGHT, this.mPlayerTiled2TextureRegion, this.getVertexBufferObjectManager());oPlayer1.animate(100);
		this.mMainScene.attachChild(oPlayer1);
		/*
		if(Player1.StopTranslation==1)
		{
			if(this.mEngine.isRunning()) {
			//	this.mMainScene.setChildScene(this.mPauseScene, false, true, true);
				this.mEngine.stop();
			} else {
				this.mMainScene.clearChildScene();
				this.mEngine.start();
			}
		}
		*/
		//final AnimatedSprite face = new AnimatedSprite(centerX, centerY, this.mFaceTextureRegion, this.getVertexBufferObjectManager());
		//face.animate(100);
		//mMainScene.attachChild(face);
		//;MainActivity.instance1++;
		//;MainActivity.instance2++;
		//if(mToggleBox==true)
		//{MainActivity.this.toggle();}
		//mMainScene.reset();
		//mToggleBox=true;
		mPlatforms.add(new Platform(randomNumber1, 0, this.mPlatformTextureRegion, this.getVertexBufferObjectManager()));
		mPlatforms.add(new Platform(randomNumber2, 0, this.mPlatformTextureRegion, this.getVertexBufferObjectManager()));
		mPlatforms.add(new Platform(randomNumber3, 0, this.mPlatformTextureRegion, this.getVertexBufferObjectManager()));
		mPlatforms.add(new Platform(randomNumber1+50, 100, this.mPlatformTextureRegion, this.getVertexBufferObjectManager()));
		mPlatforms.add(new Platform(randomNumber2+50, 200, this.mPlatformTextureRegion, this.getVertexBufferObjectManager()));
		mPlatforms.add(new Platform(randomNumber3+50, 150, this.mPlatformTextureRegion, this.getVertexBufferObjectManager()));
		oPlayer.setmPlatforms(mPlatforms); // Adds platforms, so we can work with them (Player.java)

		for (Platform Platform : mPlatforms) {
			this.mMainScene.attachChild(Platform);
		}
		//Player.PlayerCollideWithPlatform=false;
       // final Player1 oPlayer1 = new Player1(centerX+128 , centerY+256, this.mPlayerTiled2TextureRegion, this.getVertexBufferObjectManager());
       // this.mMainScene.attachChild(oPlayer1);
		
		//if(MainActivity.mToggleBox==true){
		//	this.mMainScene.detachChild(oPlayer);
	    	//toggle();
		//}
       final AutoParallaxBackground autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
       
      autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(CAMERA_WIDTH - (CAMERA_WIDTH-1), CAMERA_HEIGHT - (CAMERA_HEIGHT-1), this.mParallaxStaticBackground, this.getVertexBufferObjectManager())));
      autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(25.0f, new Sprite(480, centerY, this.mParallaxDinamic1Background, this.getVertexBufferObjectManager())));
      autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(25.0f, new Sprite(0, centerY, this.mParallaxDinamicBackground, this.getVertexBufferObjectManager())));
      autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(25.0f, new Sprite(960, centerY, this.mParallaxDinamic2Background, this.getVertexBufferObjectManager())));

       this.mMainScene.setBackground(autoParallaxBackground);      
     //  if(RandomNumberReseter >0){randomNumber1 = (int)(Math.random()*CAMERA_WIDTH);}
     //  if(RandomNumberReseter >0){randomNumber2 = (int)(Math.random()*CAMERA_WIDTH);}
     //  if(RandomNumberReseter >0){randomNumber3 = (int)(Math.random()*CAMERA_WIDTH);}
     //  RandomNumberReseter=0;
       
      
        return this.mMainScene;//Player.mToggleBox=false;
    }
    
    @Override                                                                
    public boolean onKeyDown(int keyCode, KeyEvent event) {                  
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            	finish();
            	System.exit(0);
                    return true;                                                      
                                                                             
            }                                                                    
            return super.onKeyDown(keyCode, event);                              
    }    
    
    //public void explode() {
    	
		/*RotationConstant++;
		this.mPhysicsHandler.setVelocityX(-AccelerometerHelper.TILT * DEFAULT_VELOCITY);
		setRotation(RotationConstant);
		OutOfScreenX();

	//	Jumping();

		for (Platform Platform : getmPlatforms()) {
			if (this.collidesWith(Platform)) {
				Log.v("objects.Player", "just collided with platform ;)");
				//Toast.makeText(MainActivity.ContentPasser, "Touch the screen to update (redraw) an existing BitmapTextureAtlas with every touch!", Toast.LENGTH_LONG).show();
				//toggle();
				PlayerCollideWithPlatform=true;mToggleBox=true;//Log.w("objects.Player","mToggleBox "+mToggleBox);
				if(Platform.collidesWith(Platform)){
					Log.e("objects.Platform", "just collided with platform ;)");}//PlayerCollideWithPlatform=true;}
				//  if(Player.mToggleBox=true){Toast.makeText(MainActivity.getApplicationContext(), "Touch and hold the scene and the camera will smoothly zoom in.\nRelease the scene it to zoom out again.", Toast.LENGTH_LONG).show();}
			}
		}
		*/
	//}
    
    private void toggle() {
		this.mBitmapTextureAtlas.clearTextureAtlasSources();
		//this.mToggleBox = !this.mToggleBox;
		BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, this.mToggleBox ? "face_box_tiled.png" : "face_circle_tiled.png", 0, 0, 1, 2);
	}
}

