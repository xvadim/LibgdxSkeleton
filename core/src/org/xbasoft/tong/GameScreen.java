package org.xbasoft.tong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Stack;

import static org.xbasoft.tong.MenuStage.MENU_ABOUT;
import static org.xbasoft.tong.MenuStage.MENU_CONTINUE;
import static org.xbasoft.tong.MenuStage.MENU_FB;
import static org.xbasoft.tong.MenuStage.MENU_FEEDBACK;
import static org.xbasoft.tong.MenuStage.MENU_NEW;
import static org.xbasoft.tong.Utils.L;

public class GameScreen extends ScreenAdapter
                        implements MenuStage.MenuItemListener
{
    public SoundManager soundManager;

    protected final TongGame mGame;
    private BaseGameStage currentStage;


    private InputMultiplexer mInputProcessor;

    java.util.Stack<BaseGameStage> mStagesStack = new Stack<BaseGameStage>();

    public GameScreen(final TongGame aTongGame) {
        mGame = aTongGame;

        soundManager = new SoundManager();

        Gdx.input.setCatchBackKey(true);

        mInputProcessor = new InputMultiplexer();

        mInputProcessor.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int pKeyCode) {
                if(pKeyCode == Input.Keys.BACK) {
                    popStage();
                }
                return false;
            }
        });
        Gdx.input.setInputProcessor(mInputProcessor);

        pushStage(new MenuStage(mGame, this, this));
    }

    private void setStage(BaseGameStage stage) {
        hide();
        currentStage = stage;
        show();
    }

    public Stage getStage() {
        return currentStage;
    }

    @Override
    public void hide() {
        if (mInputProcessor.size() > 1) {
            mInputProcessor.removeProcessor(1);
        }
    }

    @Override
    public void show() {
        mInputProcessor.addProcessor(1, currentStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        currentStage.act(delta);
        currentStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        TongGame.viewport.update(width, height);
        currentStage.resize(width, height);
    }

    @Override
    public void pause () {
        currentStage.pause();
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose() {
        //TODO: clean mStagesStack ??
        currentStage.dispose();
    }

    @Override
    public void onMenuItem(int itemId) {
        soundManager.play(SoundManager.SOUND_CLICK);
        switch (itemId) {
            case MENU_CONTINUE:
            case MENU_NEW:
                mGame.showAds(true);
                pushStage(new GameStage(mGame, this));
                break;
            case MENU_ABOUT:
                new AboutDialog(mGame.mAssetsManager).show(currentStage);
                break;
            case MENU_FB:
                Gdx.net.openURI("https://www.facebook.com/XBASoft");
                break;
            case MENU_FEEDBACK:
                mGame.sendEmail();
                break;
        }
    }

    // Private methods
    private void pushStage(BaseGameStage pStage) {
        mStagesStack.push(pStage);
        setStage(pStage);
    }

    public void popStage() {
        mGame.showAds(false);
        if (mStagesStack.size() <= 0) {
            Gdx.app.exit();
        } else {
            BaseGameStage stage = mStagesStack.pop();
            stage.dispose();
            if (mStagesStack.size() <= 0) {
                Gdx.app.exit();
            } else {
                BaseGameStage newStage = mStagesStack.peek();
                setStage(newStage);
            }
        }
    }


}
