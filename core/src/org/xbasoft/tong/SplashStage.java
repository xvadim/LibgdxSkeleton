package org.xbasoft.tong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SplashStage extends Stage {
    public SplashStage(Viewport viewport) {
        super(viewport);

        Image bg = new Image(new Texture("splash/splash.png"));

        bg.setPosition(Gdx.graphics.getWidth()/2 - bg.getWidth()/2,
                Gdx.graphics.getHeight()/2 - bg.getHeight()/2);

        addActor(bg);
    }
}
