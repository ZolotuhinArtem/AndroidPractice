package com.zolotukhin.picturegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.zolotukhin.picturegame.state.State;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by Artem Zolotukhin on 7/9/17.
 */

public class GameManager implements Disposable {

    private Stack<State> states;

    private AbstractGame abstractGame;

    private HashMap<String, Object> parcels;

    public GameManager(AbstractGame abstractGame) {
        states = new Stack<State>();
        this.abstractGame = abstractGame;
    }

    public void pushState(State state) {
        states.push(state);
    }

    public void popState() {
        states.pop().dispose();
    }

    public void setState(State state) {
        states.pop().dispose();
        states.push(state);
    }

    public void update(float delta){
        states.peek().handleInput();
        states.peek().update(delta);

    }

    public void render(SpriteBatch batch) {
        states.peek().render(batch);
    }

    public int getScreenWidth(){
        return abstractGame.getScreenWidth();
    }

    public int getScreenHeight(){
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

    public void pause(){
        states.peek().pause();
    }

    public void resume(){
        states.peek().resume();
    }

    @Override
    public void dispose() {
        while (states.size() > 0) {
            popState();
        }
    }
}
