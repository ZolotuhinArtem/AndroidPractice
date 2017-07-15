package com.zolotukhin.picturegame.state.gamestate;

import com.zolotukhin.picturegame.gameobject.SuperPictureFallingItem;

/**
 * Created by Artem Zolotukhin on 7/13/17.
 */

public interface SuperItemCatchListener {

    void onCatch(SuperPictureFallingItem item);
}
