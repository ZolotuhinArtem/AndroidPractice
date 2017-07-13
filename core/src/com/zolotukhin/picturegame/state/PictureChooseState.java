package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zolotukhin.picturegame.GameManager;

/**
 * Created by Artem Zolotukhin on 7/13/17.
 */

public class PictureChooseState extends State {

    public static final String PARAM_PAINTER = PictureChooseState.class.getName() + ":param_painter";

    public PictureChooseState(GameManager gsm) {
        super(gsm);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
