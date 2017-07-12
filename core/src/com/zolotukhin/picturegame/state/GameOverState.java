package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Align;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.builder.ButtonBuilder;
import com.zolotukhin.picturegame.gameobject.Button;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class GameOverState extends State implements Button.ButtonEventListener {

    public static final float FONT_SIZE = 0.1f;
    public static final float REFRESH_BUTTON_FONT_SIZE = 0.05f;
    public static final String POINTS_KEY = "points key";

    public static final float REFRESH_HEIGHT = 0.15f;
    public static final float REFRESH_WIDTH = 0.15f;
    public static final float EXIT_HEIGHT = 0.15f;
    public static final float EXIT_WIDTH = 0.15f;

    private int quantityPoints;
    private Button btnRefresh;
    private Button btnExit;

    private BitmapFont font;


    public GameOverState(GameManager gsm) {
        super(gsm);
//        quantityPoints = (int) gsm.getParcel(POINTS_KEY);
        quantityPoints = 0;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("pixel-font.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Math.round(FONT_SIZE * getUnit());
        font = generator.generateFont(parameter);
        generator.dispose();

        btnRefresh = new ButtonBuilder()
                .state(this)
                .width(REFRESH_WIDTH * gsm.getScreenWidth())
                .height(REFRESH_HEIGHT * gsm.getScreenWidth())
                .addEventListener(this)
                .build();

        btnRefresh.setXY(gsm.getScreenWidth() / 2 - btnRefresh.getWidth() / 2,
                gsm.getScreenHeight() / 2 - btnRefresh.getHeight() / 2);
        btnRefresh.setTextureSimple(new Texture("refresh.png"), true);
        btnRefresh.setTexturePressed(new Texture("refresh.png"), true);

        btnExit = new ButtonBuilder()
                .state(this)
                .width(EXIT_WIDTH * gsm.getScreenWidth())
                .height(EXIT_HEIGHT * gsm.getScreenWidth())
                .fontColor(Color.BLACK)
                .fontFromAssets("pixel-font.otf", true)
                .fontSize(Math.round(REFRESH_BUTTON_FONT_SIZE * gsm.getScreenWidth()))
                .text("Exit")
                .addEventListener(this)
                .build();

        btnExit.setXY(gsm.getScreenWidth() / 2 - btnRefresh.getWidth() / 2,
                gsm.getScreenHeight() / 2 - btnRefresh.getHeight() - btnExit.getWidth() / 2 - btnExit.getWidth() / 8);
        btnExit.setTextureSimple(new Texture("btn_simple.png"), true);
        btnExit.setTexturePressed(new Texture("btn_pressed.png"), true);
    }

    @Override
    public void update(float delta) {
        btnRefresh.update(delta);
        btnExit.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();
//        quantityPoints = (int) gsm.getParcel(POINTS_KEY);
        btnRefresh.renderWithoutBeginEnd(batch);
        btnExit.renderWithoutBeginEnd(batch);
        font.draw(batch, "GAME OVER! " +
                        "\nPoints: " + quantityPoints, 0, gsm.getScreenHeight() / 4 * 3,
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

    @Override
    public void onEvent(Button button, Button.Event event) {
        if (event == Button.Event.RELEASED) {
            if (button == btnRefresh) {
                gsm.setState(new com.zolotukhin.picturegame.state.gamestate.GameState(gsm));
            } else if (button == btnExit) {
                System.exit(0);
            }
        }
    }
}
