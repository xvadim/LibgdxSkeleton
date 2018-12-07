package org.xbasoft.tong;

import com.badlogic.gdx.Gdx;

public class Utils {

    private static String sTAG = "GAME1";

    public static void L(Object...args) {
        String message = "";
        for(Object a: args) {
            message += a + " ";
        }
        Gdx.app.log(sTAG, message);
    }
}
