package org.xbasoft.tong;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Locale;

public class GameAssets {

    private static final String kRegularFont = "regular-font.ttf";
    private static final String kRegularBoldFont = "regular-bold-font.ttf";
    private static final String kRegularBoldBorderedFont = "regular-bold-bordered-font.ttf";
    private static final String kSmallFont = "small-font.ttf";

    private static final String FONT_CHARS = "абвгдежзийклмнопрстуфхцчшщъыьэюяaіїєbcdefghijklmnopqrstuvwxyzАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯІЇЄABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";

    private AssetManager mAssetsManager = new AssetManager();
    private boolean mIsSkinLoading;

    public  Skin skin;
    public  I18NBundle i18n;

    public  void load() {
        mIsSkinLoading = false;

        loadFonts();

        mAssetsManager.load("i18n/Strings", I18NBundle.class,
                new I18NBundleLoader.I18NBundleParameter(Locale.getDefault()));
    }

    public boolean update() {
        if (!mIsSkinLoading &&
                mAssetsManager.isLoaded(kRegularFont) &&
                mAssetsManager.isLoaded(kSmallFont) &&
                mAssetsManager.isLoaded(kRegularBoldFont) &&
                mAssetsManager.isLoaded(kRegularBoldBorderedFont))
        {

            mIsSkinLoading = true;
            SkinLoader.SkinParameter skinLoaderParam = new SkinLoader.SkinParameter("atlas/game.atlas",
                    generateFonts());
            mAssetsManager.load("uiskin.json", Skin.class, skinLoaderParam);
        }

        return mAssetsManager.update();
    }

    public void initAssets() {
        i18n = mAssetsManager.get("i18n/Strings", I18NBundle.class);
        skin = mAssetsManager.get("uiskin.json", Skin.class);
    }

    public  void dispose() {
        try {
            skin.dispose();
        } catch (Exception ex) {
        }
    }

    private void loadFonts() {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        mAssetsManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        mAssetsManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreeTypeFontLoaderParameter regularFontParams = defaultFontParams("fonts/monof55.ttf",
                Color.WHITE, 48);
        mAssetsManager.load(kRegularFont, BitmapFont.class, regularFontParams);

        FreeTypeFontLoaderParameter smallFontParams = defaultFontParams("fonts/monof55.ttf",
                Color.WHITE, 28);
        mAssetsManager.load(kSmallFont, BitmapFont.class, smallFontParams);

        FreeTypeFontLoaderParameter regularBoldFontParams = defaultFontParams("fonts/Roboto-Bold.ttf",
                Color.WHITE, 46);
        mAssetsManager.load(kRegularBoldFont, BitmapFont.class, regularBoldFontParams);

        FreeTypeFontLoaderParameter regularBoldBorderedFontParams = defaultFontParams("fonts/Roboto-Bold.ttf",
                Color.WHITE, 46);
        regularBoldBorderedFontParams.fontParameters.borderColor = Color.BLACK;
        regularBoldBorderedFontParams.fontParameters.borderWidth = 1;
        mAssetsManager.load(kRegularBoldBorderedFont, BitmapFont.class, regularBoldBorderedFontParams);
    }

    private ObjectMap<String,Object> generateFonts() {
        ObjectMap<String,Object> fontsParameter = new ObjectMap<String,Object>();

        fontsParameter.put("regular_font", mAssetsManager.get(kRegularFont, BitmapFont.class));
        fontsParameter.put("small_font", mAssetsManager.get(kSmallFont, BitmapFont.class));
        fontsParameter.put("regular_bold_font", mAssetsManager.get(kRegularBoldFont, BitmapFont.class));
        fontsParameter.put("regular_bold_bordered_font",
                mAssetsManager.get(kRegularBoldBorderedFont, BitmapFont.class));

        return fontsParameter;
    }

    private FreeTypeFontLoaderParameter defaultFontParams(String aFontFile, Color aColor, int aSize) {
        FreeTypeFontLoaderParameter fontParams = new FreeTypeFontLoaderParameter();
        fontParams.fontFileName = aFontFile;
        fontParams.fontParameters.color = aColor;
        fontParams.fontParameters.size = aSize;
        fontParams.fontParameters.characters = FONT_CHARS;
        fontParams.fontParameters.minFilter = Texture.TextureFilter.Linear;
        fontParams.fontParameters.magFilter = Texture.TextureFilter.Linear;

        return fontParams;
    }

}
