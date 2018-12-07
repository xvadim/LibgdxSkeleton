package org.xbasoft.tong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TongGame extends Game {

	public static float AD_BANNER_HEIGHT;

    public static final Viewport viewport = new ExtendViewport(
            GameConsts.SCREEN_WIDTH,
            GameConsts.SCREEN_HEIGHT,
            new OrthographicCamera());

    public GameAssets mAssetsManager;

    private Stage mSplashStage;
    private GameScreen mGameScreen;

    private SystemServivesInterface mSystemServivesInterface;

    public TongGame(SystemServivesInterface anInterface) {
        mSystemServivesInterface = anInterface;
    }

	@Override
	public void create () {
        AD_BANNER_HEIGHT = 50.f * Gdx.graphics.getDensity();

        mSplashStage = new SplashStage(viewport);

        mAssetsManager = new GameAssets();
        mAssetsManager.load();
	}

	@Override
	public void render () {
        if (!mAssetsManager.update()) {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            mSplashStage.act();
            mSplashStage.draw();
            return;
        }

        if (mGameScreen == null) {
            mAssetsManager.initAssets();

            mGameScreen = new GameScreen(this);
            setScreen(mGameScreen);

            mSplashStage.dispose();
            mSplashStage = null;
        }

        super.render();
	}
	
	@Override
	public void dispose () {
        mAssetsManager.dispose();
	}

    public void showAds(boolean isShow) {
        mSystemServivesInterface.showAds(isShow);
    }

    public void sendEmail() {
        mSystemServivesInterface.sendEmail();
    }
}
