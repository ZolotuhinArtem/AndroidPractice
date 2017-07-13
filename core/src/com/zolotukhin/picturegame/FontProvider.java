package com.zolotukhin.picturegame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Artem Zolotukhin on 7/13/17.
 */

public interface FontProvider {

    BitmapFont getDefaultFont(int sizePx, Color color);

    BitmapFont getDefaultFont(float sizePx, Color color);
}
