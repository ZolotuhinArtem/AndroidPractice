package com.zolotukhin.picturegame.builder;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zolotukhin.picturegame.utils.TextureUtils;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class AnimationBuilder {


    public Animation<TextureRegion> from(Texture texture, float frameDuration, int rows, int columns) {
        TextureUtils textureUtils = new TextureUtils();
        TextureRegion[] textures = textureUtils.split(texture, rows, columns);
        Animation<TextureRegion> animation = new Animation<>(frameDuration, textures);
        return animation;
    }
}
