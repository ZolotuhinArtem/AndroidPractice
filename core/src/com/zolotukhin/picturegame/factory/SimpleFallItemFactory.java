package com.zolotukhin.picturegame.factory;

import com.badlogic.gdx.math.MathUtils;
import com.zolotukhin.picturegame.gameobject.FallingItem;
import com.zolotukhin.picturegame.gameobject.Player;

/**
 * Created by Artem Zolotukhin on 7/10/17.
 */

public class SimpleFallItemFactory implements FallItemFactory {

    private int screenWidth, screenHeight;

    private Player player;

    public SimpleFallItemFactory(int screenWidth, int screenHeight, Player player) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.player = player;
    }

    @Override
    public FallingItem getItem() {
        FallingItem fallingItem = new FallingItem(0, screenHeight, screenWidth, player);
        int posX = MathUtils.random(0, screenWidth - (int) fallingItem.getWidth());
        fallingItem.setX(posX);
        return fallingItem;
    }
}
