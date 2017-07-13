package com.zolotukhin.picturegame.state;

import com.zolotukhin.picturegame.GameManager;

/**
 * Created by Artem Zolotukhin on 7/13/17.
 */

public class PictureViewInfoState extends State {


    public static final String PARAM_PICTURE = PictureViewInfoState.class.getName() + ":param_picture";
    public static final String PARAM_PAINTER = PictureViewInfoState.class.getName() + ":param_painter";
    public static final String PARAM_RIGHT = PictureViewInfoState.class.getName() + ":param_right";

    public PictureViewInfoState(GameManager gameManager) {
        super(gameManager);
    }
}
