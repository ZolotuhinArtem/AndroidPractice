package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.builder.ButtonBuilder;
import com.zolotukhin.picturegame.gameobject.Button;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class PauseState extends State implements Button.ButtonEventListener {

    public static final float BUTTON_FONT_SIZE = 0.05f;
    public static final float BUTTON_WIDTH = 0.4f;
    public static final float BUTTON_HEIGHT = 0.2f;

    private Button btnContinue;

    public PauseState(GameManager gsm) {
        super(gsm);
        btnContinue = new ButtonBuilder()
                .state(this)
                .width(gsm.getScreenWidth() * BUTTON_WIDTH)
                .height(gsm.getScreenWidth() * BUTTON_HEIGHT)
                .fontColor(Color.BLACK)
                .fontFromAssets("pixel-font.otf", true)
                .fontSize(Math.round(BUTTON_FONT_SIZE * gsm.getScreenWidth()))
                .text("Resume")
                .addEventListener(this)
                .build();
        btnContinue.setXY(gsm.getScreenWidth() / 2 - btnContinue.getWidth() / 2,
                gsm.getScreenHeight() / 2 - btnContinue.getHeight() / 2);
        btnContinue.setTextureSimple(new Texture("btn_simple.png"), true);
        btnContinue.setTexturePressed(new Texture("btn_pressed.png"), true);

    }

    @Override
    public void update(float delta) {

        btnContinue.update(delta);
        
    }

    @Override
    public void render(SpriteBatch batch) {
        camera.update();

        batch.begin();
        btnContinue.renderWithoutBeginEnd(batch);
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
        btnContinue.dispose();
    }

    @Override
    public void onEvent(Button button, Button.Event event) {
        if (event == Button.Event.RELEASED) {
            gsm.popState();
        }
    }
}
