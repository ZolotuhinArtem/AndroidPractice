package com.zolotukhin.picturegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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

    private int lastWidth, lastHeight;

    public GameManager(AbstractGame abstractGame) {
        states = new Stack<State>();
        this.abstractGame = abstractGame;
        lastWidth = abstractGame.getScreenWidth();
        lastHeight = abstractGame.getScreenHeight();
    }

    public void pushState(State state) {
        states.push(state);
        state.resize(lastWidth, lastHeight);
    }

    public void popState() {
        states.pop().dispose();
        states.peek().resize(lastWidth, lastHeight);
    }

    public void setState(State state) {
        states.pop().dispose();
        states.push(state);
        state.resize(lastWidth, lastHeight);
    }

    public void update(float delta){
        states.peek().handleInput();
        states.peek().update(delta);

    }

    public void resize(int width, int height){
        this.lastWidth = width;
        this.lastHeight = height;
        states.peek().resize(lastWidth, lastHeight);
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

    public BitmapFont getStandartFont(int sizePx, Color color) {

        BitmapFont bitmapFont;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixel-font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = sizePx;
        parameter.color = color;

        bitmapFont = generator.generateFont(parameter);
        generator.dispose();

        return bitmapFont;



    }
}
