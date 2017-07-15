package com.zolotukhin.picturegame.state;

import com.zolotukhin.picturegame.GameManager;

/**
 * Created by Nurislam on 15.07.2017.
 */

public class PictureShowState extends State{
    public static final String PARAM_GALLERY_ENTRY = PictureShowState.class.getName() + ":param_gallery_entry";

    public PictureShowState(GameManager gameManager) {
        super(gameManager);
    }
}
