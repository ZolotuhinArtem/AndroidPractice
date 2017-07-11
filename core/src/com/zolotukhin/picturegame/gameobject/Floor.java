package com.zolotukhin.picturegame.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class Floor extends GameObject{


    public Floor(float x, float y, float width, float height) {
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
