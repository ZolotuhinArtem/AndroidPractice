package com.zolotukhin.picturegame.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Artem Zolotukhin on 7/15/17.
 */

public class SimpleResourceManager implements ResourceManager {

    private Texture btnUp, btnDown;

    private String fontName;

    public SimpleResourceManager(String btnUp, String btnDown, String fontName) {

        this.fontName = fontName;

        this.btnUp = new Texture(btnUp);
        this.btnDown = new Texture(btnDown);
    }



    @Override
    public ButtonTexture getDefaultButtonTexture() {

        return new ButtonTexture(new TextureRegion(btnUp), new TextureRegion(btnDown));

    }

    @Override
    public BitmapFont getNewInstanceOfDefaultFont(float sizePx, Color color) {
        BitmapFont bitmapFont;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Math.round(sizePx);
        parameter.color = color;

        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        return bitmapFont;
    }

    @Override
    public void dispose() {
        btnUp.dispose();
        btnDown.dispose();
    }
}
