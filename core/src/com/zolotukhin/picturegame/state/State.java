package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.zolotukhin.picturegame.GameManager;

/**
 * Created by Artem Zolotukhin on 7/9/17.
 */

public abstract class State {

    protected OrthographicCamera camera;
    protected Vector3 vector;
    protected GameManager gsm;

    private int unit;

    private float pointX, pointY;

    private boolean isTouched;

    public State(GameManager gsm) {
        this.gsm = gsm;
        pointY = 0;
        pointX = 0;
        isTouched = false;
        vector = new Vector3(0, 0, 0);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, gsm.getScreenWidth(), gsm.getScreenHeight());
        unit = gsm.getScreenWidth();
    }

    public void handleInput() {
        if (Gdx.input.isTouched()) {
            vector.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            vector = camera.unproject(vector);
            pointX = vector.x;
            pointY = vector.y;
            isTouched = true;
        } else {
            isTouched = false;
        }
    }

    public void resize(int width, int height){}

    public abstract void update(float delta);

    public abstract void render(SpriteBatch batch);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();

    public float getPointX() {
        return pointX;
    }

    public float getPointY() {
        return pointY;
    }

    public boolean isTouched() {
        return isTouched;
    }

    public int getUnit() {
        return unit;
    }
}
