package org.xbasoft.tong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuStage extends BaseGameStage {

    public static final int MENU_CONTINUE = 0;
    public static final int MENU_NEW = MENU_CONTINUE + 1;
    public static final int MENU_ABOUT = MENU_NEW + 1;
    public static final int MENU_FB = MENU_ABOUT + 1;
    public static final int MENU_FEEDBACK = MENU_FB + 1;


    public interface MenuItemListener {
        void onMenuItem(int itemId);
    }

    private MenuItemListener mMenuItemListener;
    private Preferences mPreferences;

    public MenuStage(TongGame aGame, GameScreen aScreen, MenuItemListener aListener) {
        super(aGame, aScreen);

        mMenuItemListener = aListener;
        mPreferences = Gdx.app.getPreferences(GameConsts.PREFS_FILE_NAME);

        createMenu();
    }

    private void createMenu() {
        createMenuItem("continue.key", "menu_button", MENU_CONTINUE);
        createMenuItem("new.key", "menu_button", MENU_NEW);
        createMenuItem("about.key", "menu_button", MENU_ABOUT);

        mRootTable.row();

        final CheckBox cb = new CheckBox("", mGame.mAssetsManager.skin, "sound");
        mRootTable.add(cb);
        cb.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean isEnabled = cb.isChecked();
                mPreferences.putBoolean(GameConsts.KEY_SOUND, isEnabled);
                mPreferences.flush();
                mGameScreen.soundManager.enableSound(isEnabled);
            }
        });
        boolean isEnabled = mPreferences.getBoolean(GameConsts.KEY_SOUND, false);
        cb.setChecked(isEnabled);
        mGameScreen.soundManager.enableSound(isEnabled);

        //TODO: create a special class
        final CheckBox mcb = new CheckBox("", mGame.mAssetsManager.skin, "music");
        mRootTable.add(mcb);
        mcb.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean isEnabled = mcb.isChecked();
                mPreferences.putBoolean(GameConsts.KEY_MUSIC, isEnabled);
                mPreferences.flush();
                SoundManager.enableMusic(isEnabled);
            }
        });
        isEnabled = mPreferences.getBoolean(GameConsts.KEY_MUSIC, false);
        SoundManager.enableMusic(isEnabled);
        mcb.setChecked(isEnabled);

        mRootTable.add();

        Image imgButton;
        imgButton = new Image(mGame.mAssetsManager.skin.getDrawable("feedback"));
        imgButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mMenuItemListener.onMenuItem(MENU_FEEDBACK);
            }
        });
        mRootTable.add(imgButton);

        imgButton = new Image(mGame.mAssetsManager.skin.getDrawable("fb"));
        imgButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mMenuItemListener.onMenuItem(MENU_FB);
            }
        });
        mRootTable.add(imgButton);

    }

    private void createMenuItem(String pItemKey, String pStyleName, final int pMenuId) {
        TextButton item = new TextButton(mGame.mAssetsManager.i18n.get(pItemKey),
                mGame.mAssetsManager.skin, pStyleName);
        item.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mMenuItemListener.onMenuItem(pMenuId);
            }
        });

        mRootTable.row();
        mRootTable.add(item).colspan(5).fillX();
    }
}
