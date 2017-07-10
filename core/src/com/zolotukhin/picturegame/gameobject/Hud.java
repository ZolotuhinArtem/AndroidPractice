package com.zolotukhin.picturegame.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Artem Zolotukhin on 7/10/17.
 */

public class Hud extends GameObject {

    private BitmapFont font;

    private int points;

    private int lives;

    public Hud(float x, float y, int fontSizePixels) {
        super(x, y);
        points = 0;
        lives = 0;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixel-font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = fontSizePixels;
        font = generator.generateFont(parameter);
        generator.dispose();

    }


    @Override
    public void update(float delta) {

    }

    @Override
    public void renderWithoutBeginEnd(SpriteBatch batch) {
        font.draw(batch, "Lives: " + lives + "\nPoints: " + points, getX(), getY());
    }

    @Override
    public void dispose() {

    }

    public int getPoints() {
        return points;
    }

    public Hud setPoints(int points) {
        this.points = points;
        return this;
    }

    public BitmapFont getFont() {
        return font;
    }

    public Hud setFont(BitmapFont font) {
        this.font = font;
        return this;
    }

    public int getLives() {
        return lives;
    }

    public Hud setLives(int lives) {
        this.lives = lives;
        return this;
    }
}
