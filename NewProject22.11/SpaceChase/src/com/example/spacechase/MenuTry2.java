package com.example.spacechase;

import java.io.IOException;
import java.io.InputStream;
 
import javax.microedition.khronos.opengles.GL10;
 
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.animator.SlideMenuAnimator;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;
 
import android.view.KeyEvent;
 
public class MenuTry2 extends SimpleBaseGameActivity implements IOnMenuItemClickListener {
 
        // ===========================================================
        // Constants
        // ===========================================================
        private final int LEFT_EDGE = 0;
        private final int CAMERA_WIDTH = 720; // right edge of screen
        private final int TOP_OF_SCREEN = 0;
        private final int CAMERA_HEIGHT = 480; // bottom of screen
       
        protected static final int MENU_ABOUT = 0;
        protected static final int MENU_QUIT = MENU_ABOUT + 1;
        protected static final int MENU_PLAY = 100;
        protected static final int MENU_SCORES = MENU_PLAY + 1;
        protected static final int MENU_OPTIONS = MENU_SCORES + 1;
        protected static final int MENU_HELP = MENU_OPTIONS + 1;
        // ===========================================================
        // Fields
        // ===========================================================
        protected Camera mCamera;
        protected Scene mMainScene;
 
        private TextureRegion mMenuBackTextureRegion;
        protected MenuScene mStaticMenuScene;
        protected MenuScene mPopUpMenuScene;
 
        private Font mFont;
        protected TextureRegion mPopUpAboutTextureRegion;
        protected TextureRegion mPopupQuitTextureRegion;
        protected TextureRegion mMenuPlayTextureRegion;
 
        protected TextureRegion mMenuScoresTextureRegion;
        protected TextureRegion mMenuOptionsTextureRegion;
        protected TextureRegion mMenuHelpTextureRegion;
       
        private TextureRegion mTextureRegion;
 
        private boolean popupDisplayed;
       
        private Color selectedColor;
        private Color unselectedColor;
 
        // ===========================================================
        // Constructors
        // ===========================================================
        // ===========================================================
        // Getter and Setter
        // ===========================================================
        // ===========================================================
        // Methods for/from SuperClass/Interfaces
        // ===========================================================
 
        public EngineOptions onCreateEngineOptions() {
                this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
 
                final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
 
                return engineOptions;
        }
 
        @Override
        public void onCreateResources() {
               
                selectedColor = new Color(0,0,1);
                unselectedColor = new Color(1,1,1);
                 
                try {
                        setFontTexture();
                        setMenuTexture();
                setPopupTexture();
                setPopupQuitTexture();
                }
                catch (IOException e) {
                        e.printStackTrace();
                }
 
                popupDisplayed = false;
        }
 
        private void setFontTexture() throws IOException {
               
                FontFactory.setAssetBasePath("font/");
 
                final ITexture fontTexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
                this.mFont = FontFactory.createFromAsset(this.getFontManager(), fontTexture, this.getAssets(), "Classica-Bold.ttf", 48, true, android.graphics.Color.WHITE);
                this.mFont.load();
               
        }      
       
        private void setMenuTexture() throws IOException {
 
                ITexture texture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
 
                        public InputStream open() throws IOException {
                                return getAssets().open("gfx/menu/bg.png");
                        }
                });
 
                texture.load();
                this.mMenuBackTextureRegion = TextureRegionFactory.extractFromTexture(texture);
 
        }
 
        private void setPopupTexture() throws IOException {
               
                ITexture texture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
 
                        public InputStream open() throws IOException {
                                return getAssets().open("gfx/menu/About_button.png");
                        }
                });
 
                texture.load();
        this.mPopUpAboutTextureRegion = TextureRegionFactory.extractFromTexture(texture);
 
        }
       
        private void setPopupQuitTexture() throws IOException {
 
                ITexture texture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
 
                        public InputStream open() throws IOException {
                                return getAssets().open("gfx/menu/Quit_button.png");
                        }
                });
 
                texture.load();
                this.mPopupQuitTextureRegion = TextureRegionFactory.extractFromTexture(texture);
 
        }
       
        @Override
        public Scene onCreateScene() {
 
                this.mEngine.registerUpdateHandler(new FPSLogger());
 
                this.createStaticMenuScene();
                this.createPopUpMenuScene();
 
                // Center the background on the camera.
                final float centerX = ( CAMERA_WIDTH - this.mMenuBackTextureRegion.getWidth()) / 2;
                final float centerY = (CAMERA_HEIGHT - this.mMenuBackTextureRegion.getHeight()) /       2;
                this.mMainScene = new Scene();
               
                // Add the background and static menu
                final Sprite menuBackground = new Sprite(centerX,       centerY, this.mMenuBackTextureRegion, this.getVertexBufferObjectManager());
                //Debug.d("onCreateScene(): mMainScene: " + mMainScene.getUserData() + "\t:" + mMainScene.getChildCount());
                mMainScene.attachChild(menuBackground);
                //Debug.d("onCreateScene(): mMainScene: " + mMainScene.getUserData() + "\t:" + mMainScene.getChildCount());
                //mMainScene.getLastChild().attachChild(menuBackground);
                mMainScene.setChildScene(mStaticMenuScene);
                return this.mMainScene;
        }
 
        @Override
        public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent) {
 
                if(pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        if(popupDisplayed) {
                                // Remove the menu and reset it.
                                this.mPopUpMenuScene.back();
                                mMainScene.setChildScene(mStaticMenuScene);
                                popupDisplayed = false;
                        }
                        else {
 
                                // Attach the menu.
                                this.mMainScene.setChildScene(
                                                this.mPopUpMenuScene, false, true, true);
                                popupDisplayed = true;
                        }
                        return true;
                }
                else {
                        return super.onKeyDown(pKeyCode, pEvent);
                }
        }
       
        public boolean onMenuItemClicked(final MenuScene pMenuScene, final IMenuItem pMenuItem, final float pMenuItemLocalX, final float pMenuItemLocalY) {
                switch(pMenuItem.getID()) {
                case MENU_ABOUT:
        //              case MENU_RESET:
                                /* Restart the animation. */
                                this.mMainScene.reset();
 
                                /* Remove the menu and reset it. */
                                this.mMainScene.clearChildScene();
                                this.mPopUpMenuScene.reset();
                                return true;
                        case MENU_QUIT:
                                /* End Activity. */
                                this.finish();
                                return true;
                        default:
                                return false;
                }
        }
       
//      @Override
//      public boolean onMenuItemClicked(final MenuScene pMenuScene,
//                      final IMenuItem pMenuItem,
//                      final float pMenuItemLocalX,
//                      final float pMenuItemLocalY) {
//             
//              switch(pMenuItem.getID()) {
//             
//              case MENU_ABOUT&#058;
//                      Toast.makeText(MainActivity.this,
//                                      "About selected",
//                                      Toast.LENGTH_SHORT).show();
//                      return true;
//              case MENU_QUIT:
//                      /* End Activity. */
//                      this.finish();
//                      return true;
//              case MENU_PLAY:
//                      Toast.makeText(MainActivity.this, "Play selected", Toast.LENGTH_SHORT).show();
//                      return true;
//              case MENU_SCORES:
//                      Toast.makeText(MainActivity.this, "Scores selected", Toast.LENGTH_SHORT).show();
//                      return true;
//              case MENU_OPTIONS:
//                      Toast.makeText(MainActivity.this, "Options selected", Toast.LENGTH_SHORT).show();
//                      return true;
//              case MENU_HELP:
//                      Toast.makeText(MainActivity.this, "Help selected", Toast.LENGTH_SHORT).show();
//                      return true;
//              default:
//                      return false;
//              }
//      }
 
        // ===========================================================
        // Methods
        // ===========================================================
        protected void createStaticMenuScene() {
               
                this.mStaticMenuScene = new MenuScene(this.mCamera);
 
                // play
                final IMenuItem playMenuItem = new ColorMenuItemDecorator(
                                new TextMenuItem(MENU_PLAY, mFont, "Play Game", this.getVertexBufferObjectManager()), selectedColor, unselectedColor);
 
                playMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
 
                this.mStaticMenuScene.addMenuItem(playMenuItem);
 
                // score
                final IMenuItem scoresMenuItem = new ColorMenuItemDecorator(
                                new TextMenuItem(MENU_SCORES, mFont, "Scores", this.getVertexBufferObjectManager()), selectedColor, unselectedColor);
 
                scoresMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
 
                this.mStaticMenuScene.addMenuItem(scoresMenuItem);
 
                // options
                final IMenuItem optionsMenuItem =       new ColorMenuItemDecorator(
                                                new TextMenuItem(MENU_OPTIONS, mFont, "Options", this.getVertexBufferObjectManager()), selectedColor, unselectedColor);
 
                optionsMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA,     GL10.GL_ONE_MINUS_SRC_ALPHA);
 
                this.mStaticMenuScene.addMenuItem(optionsMenuItem);
 
                // help
                final IMenuItem helpMenuItem = new ColorMenuItemDecorator(
                                new TextMenuItem(MENU_HELP, mFont, "Help", this.getVertexBufferObjectManager()), selectedColor, unselectedColor);
 
                helpMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
 
                this.mStaticMenuScene.addMenuItem(helpMenuItem);
 
                //
                this.mStaticMenuScene.buildAnimations();
                this.mStaticMenuScene.setBackgroundEnabled(false);
                this.mStaticMenuScene.setOnMenuItemClickListener(this);
        }
 
        protected void createPopUpMenuScene() {
               
                this.mPopUpMenuScene = new MenuScene(this.mCamera);
                final SpriteMenuItem aboutMenuItem = new SpriteMenuItem(MENU_ABOUT, this.mPopUpAboutTextureRegion, this.getVertexBufferObjectManager());
 
                aboutMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
                this.mPopUpMenuScene.addMenuItem(aboutMenuItem);
                final SpriteMenuItem quitMenuItem = new SpriteMenuItem(MENU_QUIT, this.mPopupQuitTextureRegion, this.getVertexBufferObjectManager());
                quitMenuItem.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
                this.mPopUpMenuScene.addMenuItem(quitMenuItem);
                this.mPopUpMenuScene.setMenuAnimator(new SlideMenuAnimator());
                this.mPopUpMenuScene.buildAnimations();
                this.mPopUpMenuScene.setBackgroundEnabled(false);
                this.mPopUpMenuScene.setOnMenuItemClickListener(this);
        }
        // ===========================================================
        // Inner and Anonymous Classes
        // ===========================================================
 
}
 
 