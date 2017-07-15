package com.zolotukhin.picturegame.resource;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Artem Zolotukhin on 7/15/17.
 */

public class ButtonTexture {

    public TextureRegion up, down;

    public ButtonTexture() {
    }

    public ButtonTexture(TextureRegion up, TextureRegion down) {
        this.up = up;
        this.down = down;
    }

    public TextureRegion getUp() {
        return up;
    }

    public ButtonTexture setUp(TextureRegion up) {
        this.up = up;
        return this;
    }

    public TextureRegion getDown() {
        return down;
    }

    public ButtonTexture setDown(TextureRegion down) {
        this.down = down;
        return this;
    }
}
