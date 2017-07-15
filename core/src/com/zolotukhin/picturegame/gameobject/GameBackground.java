package com.zolotukhin.picturegame.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Artem Zolotukhin on 7/14/17.
 */

public class GameBackground extends GameObject {

    private Texture texture;

    public GameBackground(float x, float y, float width, float height, String textureName) {
        super(x, y, width, height);
        texture = new Texture(textureName);
    }

    @Override
    public void renderWithoutBeginEnd(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
