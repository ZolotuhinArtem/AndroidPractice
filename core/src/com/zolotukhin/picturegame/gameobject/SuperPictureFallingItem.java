package com.zolotukhin.picturegame.gameobject;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Artem Zolotukhin on 7/12/17.
 */

public class SuperPictureFallingItem extends FallingItem {

    public SuperPictureFallingItem(float x, float y, float screenWidth) {
        super(x, y, screenWidth);
        getTexture().dispose();
        setTexture(new Texture("super_item.png"));
    }
}
