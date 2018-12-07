package org.xbasoft.tong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by vadimkhohlov on 10/7/17.
 */

public class AboutDialog extends Window {

    public AboutDialog(final GameAssets pAssetsManager) {
        super("", pAssetsManager.skin);

        setModal(true);

        defaults().pad(10, GameConsts.DIALOG_PAD_LEFT, 10, GameConsts.DIALOG_PAD_RIGHT);

        row();
        Label l = new Label(pAssetsManager.i18n.get("full_title.key"), pAssetsManager.skin, "dialog_bold_text");
        add(l).colspan(2).expandX();

        row();
        l = new Label(pAssetsManager.i18n.get("copyright.key"), pAssetsManager.skin, "copyright_text");
        add(l).colspan(2).expandX();

        row();
        l = new Label(pAssetsManager.i18n.get("privacy_policy.key"), pAssetsManager.skin, "privacy_text");
        l.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.net.openURI("https://xvadim.github.io/xbasoft/xbasoft_privacy_policy.html");
            }
        });
        add(l).colspan(2).expand();

        row();
        //
        TextButton b = new TextButton(pAssetsManager.i18n.get("more_apps.key"),
                pAssetsManager.skin.get("ok", TextButton.TextButtonStyle.class));
        b.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                remove();
                Gdx.net.openURI("https://xvadim.github.io/xbasoft/");
            }
        });
        add(b).pad(0, GameConsts.DIALOG_PAD_LEFT, GameConsts.DIALOG_PAD_BOTTOM, 5);

        b = new TextButton("Ok",
                pAssetsManager.skin.get("ok", TextButton.TextButtonStyle.class));
        b.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                remove();
            }
        });

        add(b).pad(0, 5, GameConsts.DIALOG_PAD_BOTTOM, GameConsts.DIALOG_PAD_RIGHT);
    }

    @Override
    public float getPrefWidth () {
        return Math.max(super.getPrefWidth(), GameConsts.SCREEN_WIDTH * 3 / 4 );
    }

    public void show(Stage stage) {
        pack();

        stage.addActor(this);
        stage.setKeyboardFocus(this);
        stage.setScrollFocus(this);

        addAction(sequence(Actions.alpha(0), Actions.fadeIn(0.4f, Interpolation.fade)));

        setPosition(Math.round((stage.getWidth() - getWidth()) / 2),
                Math.round((stage.getHeight() - getHeight()) / 2));
    }
}
