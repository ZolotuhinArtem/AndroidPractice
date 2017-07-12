package com.zolotukhin.picturegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.zolotukhin.picturegame.state.GameState;

public class PictureGame extends ApplicationAdapter implements AbstractGame {
	private int screenWidth;
	private int screenHeight;

	private GameManager gsm;
	private SpriteBatch batch;


	public PictureGame(int screenWidth, int screenHeight) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;

	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		gsm = new GameManager(this);
		Gdx.gl.glClearColor(0, 0, 0, 1);
//		Gdx.gl.glClearColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
		gsm.pushState(new GameState(gsm));
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void resize(int width, int height) {
		gsm.resize(width, height);
	}

	@Override
    public void pause() {
        gsm.pause();
    }

    @Override
    public void resume() {
        gsm.resume();
    }

    @Override
	public void dispose() {
		batch.dispose();
        gsm.dispose();
	}

	@Override
	public int getScreenWidth() {
		return screenWidth;
	}

	@Override
	public int getScreenHeight() {
		return screenHeight;
	}
}
