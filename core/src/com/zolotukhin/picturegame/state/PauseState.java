package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.resource.ButtonTexture;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class PauseState extends State {

    public static final float BUTTON_FONT_SIZE = 0.07f;
    public static final float BUTTON_MARGIN = 0.05f;
    public static final float BUTTON_WIDTH = 1f;


    private Stage stage;

    private BitmapFont font;

    public PauseState(GameManager gsm) {
        super(gsm);

        ButtonTexture buttonTexture = gameManager.getResourceManager().getDefaultButtonTexture();

        float buttonWidth = BUTTON_WIDTH;
        float buttonHeight = (float) buttonTexture.getUp().getRegionHeight() / (float) buttonTexture.getUp().getRegionWidth()
                * buttonWidth;
        float buttonFontSize = BUTTON_FONT_SIZE;
        float buttonMargin = BUTTON_MARGIN;

        font = gameManager.getResourceManager()
                .getNewInstanceOfDefaultFont(buttonFontSize * getUnit(), Color.WHITE);

        stage = new Stage(new ScreenViewport());
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(buttonTexture.getUp());
        textButtonStyle.down = new TextureRegionDrawable(buttonTexture.getDown());
        textButtonStyle.font = font;

        TextButton buttonResume = new TextButton("Resume", textButtonStyle);
        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.popState();
            }
        });

        TextButton buttonExit = new TextButton("Exit", textButtonStyle);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.clearStates();
                gameManager.setState(new MenuState(gameManager));
            }
        });

        table.add(buttonResume)
                .width(buttonWidth * getUnit())
                .height(buttonHeight * getUnit())
                .padBottom(buttonMargin * getUnit());
        table.row();

        table.add(buttonExit)
                .width(buttonWidth * getUnit())
                .height(buttonHeight * getUnit());
    }

    @Override
    public void onResize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void onUpdate(float delta) {
        stage.act(delta);
    }

    @Override
    public void onRender(SpriteBatch batch) {
        super.onRender(batch);
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void onShow() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void onDispose() {
        font.dispose();
        stage.dispose();
    }
}
