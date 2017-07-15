package com.zolotukhin.picturegame.state.gamestate;

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

    private SuperItemCatchListener superItemCatchListener;

    public GameStateCollisionListener(Player player, SuperItemCatchListener superItemCatchListener) {
        this.player = player;
        this.superItemCatchListener = superItemCatchListener;
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
                        if (superItemCatchListener != null) {
                            superItemCatchListener.onCatch((SuperPictureFallingItem) object);
                        }
                    }
                }
            }
        }

        object.destroy();
    }

    public SuperItemCatchListener getSuperItemCatchListener() {
        return superItemCatchListener;
    }

    public GameStateCollisionListener setSuperItemCatchListener(SuperItemCatchListener superItemCatchListener) {
        this.superItemCatchListener = superItemCatchListener;
        return this;
    }
}
