package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.zolotukhin.picturegame.GameManager;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class GameOverState extends State {

    public static final float FONT_SIZE = 0.1f;

    private BitmapFont font;


    public GameOverState(GameManager gsm) {
        super(gsm);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixel-font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Math.round(FONT_SIZE * getUnit());
        font = generator.generateFont(parameter);
        generator.dispose();


    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
        font.draw(batch, "GAME OVER!", 0, gsm.getScreenHeight() / 2,
                gsm.getScreenWidth(), Align.center, false);
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
