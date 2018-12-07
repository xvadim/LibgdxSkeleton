package org.xbasoft.tong.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import org.xbasoft.tong.GameConsts;
import org.xbasoft.tong.SystemServivesInterface;
import org.xbasoft.tong.TongGame;

public class DesktopLauncher {
	public static void main (String[] arg) {

        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;
        TexturePacker.process(settings, "../../images", "atlas", "game");


        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Tong";
        config.width = (int)GameConsts.SCREEN_WIDTH;
        config.height = (int) GameConsts.SCREEN_HEIGHT;
		new LwjglApplication(new TongGame(createInterface()), config);
	}

	private static SystemServivesInterface createInterface() {
	    return new SystemServivesInterface() {
            @Override
            public void showAds(boolean isShow) {
            }

            @Override
            public void sendEmail() {
            }
        };
    }
}
