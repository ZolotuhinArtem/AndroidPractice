package com.zolotukhin.picturegame.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class TextureUtils {


    public TextureRegion[] split(Texture src, int rowsCount, int columnsCount) {

        TextureRegion[][] tmp = TextureRegion.split(src, src.getWidth() / columnsCount, src.getHeight() / rowsCount);

        TextureRegion[] textureRegions = new TextureRegion[rowsCount * columnsCount];

        int index = 0;

        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                textureRegions[index++] = tmp[i][j];
            }
        }

        return textureRegions;
    }

    public TextureRegion getSquareThumbnail(Texture src) {

        TextureRegion textureRegion;

        if (src.getWidth() > src.getHeight()) {
            int delta = (src.getWidth() - src.getHeight()) / 2;
            textureRegion = new TextureRegion(src, delta, 0, src.getHeight(), src.getHeight());
        } else {
            int delta = (src.getHeight() - src.getWidth()) / 2;
            textureRegion = new TextureRegion(src, 0, delta, src.getWidth(), src.getWidth());
        }
        return textureRegion;

    }
}
