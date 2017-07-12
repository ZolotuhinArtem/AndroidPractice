package com.zolotukhin.picturegame.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Artem Zolotukhin on 7/12/17.
 */

public class Ready extends GameObject {

    public static final float READY_FONT_SIZE = 0.1f;

    public Ready(float x, float y, float width, float height, float fontSize) {
        super(x, y, width, height);

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void renderWithoutBeginEnd(SpriteBatch batch) {

    }

    @Override
    public void dispose() {

    }
}
