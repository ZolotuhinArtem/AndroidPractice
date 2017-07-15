package com.zolotukhin.picturegame.resource;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Artem Zolotukhin on 7/15/17.
 */

public interface ResourceManager extends Disposable {

    ButtonTexture getDefaultButtonTexture();

    BitmapFont getNewInstanceOfDefaultFont(float sizePx, Color color);

}
