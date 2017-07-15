package com.zolotukhin.picturegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;
import com.zolotukhin.picturegame.resource.ResourceManager;
import com.zolotukhin.picturegame.resource.SimpleResourceManager;
import com.zolotukhin.picturegame.state.State;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Artem Zolotukhin on 7/9/17.
 */

public class GameManager implements Disposable, ResourceManagerProvider {

    private Stack<State> states;

    private AbstractGame abstractGame;

    private HashMap<String, Object> parcels;

    private int lastWidth, lastHeight;

    private ResourceManager resourceManager;

    public GameManager(AbstractGame abstractGame) {
        states = new Stack<State>();
        this.abstractGame = abstractGame;
        parcels = new HashMap<>();
        lastWidth = abstractGame.getScreenWidth();
        lastHeight = abstractGame.getScreenHeight();
        resourceManager = new SimpleResourceManager("btn_simple.png", "btn_pressed.png", "pixel-font.ttf");
    }

    public void pushState(State state) {
        if (states.size() > 0) {
            states.peek().onHide();
        }
        states.push(state);
        state.onResize(lastWidth, lastHeight);
        state.onShow();
    }

    public void popState() {
        states.pop().onDispose();
        if (states.size() > 0) {
            states.peek().onResize(lastWidth, lastHeight);
            states.peek().onShow();
        }
    }

    public void setState(State state) {
        if (states.size() > 0) {
            states.pop().onDispose();
        }
        states.push(state);
        state.onResize(lastWidth, lastHeight);
        state.onShow();
    }

    public void update(float delta) {
        if (states.size() > 0) {
            states.peek().handleInput();
            states.peek().onUpdate(delta);
        }
    }

    public void resize(int width, int height) {
        this.lastWidth = width;
        this.lastHeight = height;
        if (states.size() > 0) {
            states.peek().onResize(lastWidth, lastHeight);
        }
    }

    public void render(SpriteBatch batch) {
        if (states.size() > 0) {
            states.peek().onRender(batch);
        }
    }

    public int getScreenWidth() {
        return abstractGame.getScreenWidth();
    }

    public int getScreenHeight() {
        return abstractGame.getScreenHeight();
    }

    public void putParcel(String key, Object value) {
        parcels.put(key, value);
    }

    public Object getParcel(String key) {
        return parcels.get(key);
    }

    public void removeParcel(String key) {
        parcels.remove(key);
    }

    public void pause() {
        if (states.size() > 0) {
            states.peek().onPause();
        }
    }

    public void resume() {
        if (states.size() > 0) {
            states.peek().onResume();
        }
    }

    public void clearStates(){
        while (states.size() > 0) {
            popState();
        }
    }

    @Override
    public void dispose() {
        clearStates();
        resourceManager.dispose();
    }


    @Override
    public ResourceManager getResourceManager() {
        return resourceManager;
    }
}
