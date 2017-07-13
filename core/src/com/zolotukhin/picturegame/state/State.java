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
    protected GameManager gameManager;

    private int unit;

    private float pointX, pointY;

    private boolean isTouched;

    public State(GameManager gameManager) {
        this.gameManager = gameManager;
        pointY = 0;
        pointX = 0;
        isTouched = false;
        vector = new Vector3(0, 0, 0);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, gameManager.getScreenWidth(), gameManager.getScreenHeight());
        unit = gameManager.getScreenWidth();
    }


    public void onResize(int width, int height) {
    }

    public void onUpdate(float delta) {
    }

    public void onRender(SpriteBatch batch) {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onDispose() {
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

    public GameManager getGameManager() {
        return gameManager;
    }

    public State setGameManager(GameManager gsm) {
        this.gameManager = gsm;
        return this;
    }
}
