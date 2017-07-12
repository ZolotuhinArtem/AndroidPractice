package com.zolotukhin.picturegame.state;

import com.zolotukhin.picturegame.gameobject.FallingItem;
import com.zolotukhin.picturegame.gameobject.Floor;
import com.zolotukhin.picturegame.gameobject.GameObject;
import com.zolotukhin.picturegame.gameobject.Player;
import com.zolotukhin.picturegame.gameobject.SuperPictureFallingItem;

/**
 * Created by Artem Zolotukhin on 7/12/17.
 */

public class GameStateCollisionListener implements GameObject.CollisionListener {

    private Player player;

    private State state;

    public GameStateCollisionListener(Player player, State state) {
        this.player = player;
        this.state = state;
    }

    @Override
    public void onCollision(GameObject object, GameObject cause) {
        if (object instanceof FallingItem && !(object instanceof SuperPictureFallingItem)) {
            if (cause instanceof Player) {
                player.addPoints(((FallingItem) object).getCost());
            } else {
                if (cause instanceof Floor) {
                    player.subLives(1);
                }
            }

        } else {
            if (object instanceof SuperPictureFallingItem) {
                if (cause instanceof Floor) {
                    player.subLives(1);
                } else {
                    if (cause instanceof Player) {
                        player.addPoints(((FallingItem) object).getCost());
                        //TODO SCREEN PICTURE
                    }
                }


            }
        }



        object.destroy();
    }
}
