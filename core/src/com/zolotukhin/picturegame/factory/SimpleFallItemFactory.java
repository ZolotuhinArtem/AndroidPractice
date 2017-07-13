package com.zolotukhin.picturegame.factory;

import com.badlogic.gdx.math.MathUtils;
import com.zolotukhin.picturegame.gameobject.FallingItem;
import com.zolotukhin.picturegame.gameobject.Floor;
import com.zolotukhin.picturegame.gameobject.GameObject;
import com.zolotukhin.picturegame.gameobject.Player;
import com.zolotukhin.picturegame.gameobject.SuperPictureFallingItem;

/**
 * Created by Artem Zolotukhin on 7/10/17.
 */

public class SimpleFallItemFactory implements FallItemFactory {

    public static final int MIN_INTERVAL_SUPER_SPAWN = 7;
    public static final int MAX_INTERVAL_SUPER_SPAWN = 15;

    private int screenWidth, screenHeight;

    private Player player;

    private Floor floor;

    private int counter;
    private int spawn;


    private GameObject.CollisionListener listener;


    public SimpleFallItemFactory(int screenWidth, int screenHeight, Player player, Floor floor, GameObject.CollisionListener listener) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.player = player;
        this.floor = floor;
        this.listener = listener;
        counter = 0;
        spawn = MathUtils.random(MIN_INTERVAL_SUPER_SPAWN, MAX_INTERVAL_SUPER_SPAWN);
    }

    @Override
    public FallingItem getItem() {
        counter++;

        FallingItem fallingItem;

        if (counter == spawn) {
            fallingItem = new SuperPictureFallingItem(0, screenHeight, screenWidth);
            counter = 0;
            spawn = MathUtils.random(MIN_INTERVAL_SUPER_SPAWN, MAX_INTERVAL_SUPER_SPAWN);
        } else {
            fallingItem = new FallingItem(0, screenHeight, screenWidth);
        }


        fallingItem.addCollisionObject(player);
        fallingItem.addCollisionObject(floor);
        fallingItem.addCollisionListener(listener);

        int posX = MathUtils.random(0, screenWidth - (int) fallingItem.getWidth());
        fallingItem.setX(posX);

        return fallingItem;
    }
}
