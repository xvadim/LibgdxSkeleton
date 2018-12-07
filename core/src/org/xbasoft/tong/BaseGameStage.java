package org.xbasoft.tong;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class BaseGameStage extends Stage {

    protected final TongGame mGame;
    protected final GameScreen mGameScreen;
    protected final Table mRootTable = new Table();

    public BaseGameStage(TongGame aGame, GameScreen aScreen) {
        super(TongGame.viewport);

        mGame = aGame;
        mGameScreen = aScreen;

        mRootTable.setFillParent(true);
        mRootTable.defaults().pad(GameConsts.PADDING);
        mRootTable.addAction(Actions.fadeIn(GameConsts.DURATION));
        //<a href="http://www.freepik.com">Designed by kues1 / Freepik</a>
        mRootTable.setBackground(mGame.mAssetsManager.skin.getTiledDrawable("screen_bg"));
        addActor(mRootTable);
    }

    public void pause() {
    }

    public void resize(int width, int height) {
    }
}
