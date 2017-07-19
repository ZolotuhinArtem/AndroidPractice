package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.resource.ButtonTexture;

/**
 * Created by Nurislam on 13.07.2017.
 */

public class MenuState extends State {

    public static final float BUTTON_DEFAULT_WIDTH = 1f;
    public static final float BUTTON_FONT_SIZE = 0.07f;
    public static final float BUTTON_MARGIN = 0.05f;
    public static final float LABEL_FONT_SIZE = 0.08f;

    private BitmapFont font;
    private BitmapFont buttonFont;
    private Stage stage;


    public MenuState(GameManager gsm) {
        super(gsm);

        ButtonTexture buttonTexture = gameManager.getResourceManager().getDefaultButtonTexture();

        float buttonWidth = BUTTON_DEFAULT_WIDTH;
        float buttonHeight = (float) buttonTexture.getUp().getRegionHeight() / (float) buttonTexture.getUp().getRegionWidth()
                * buttonWidth;
        float buttonFontSize = BUTTON_FONT_SIZE;
        float buttonMargin = BUTTON_MARGIN;
        float labelFontSize = LABEL_FONT_SIZE;

        font = gameManager.getResourceManager()
                .getNewInstanceOfDefaultFont(labelFontSize * getUnit(), Color.WHITE);
        buttonFont = gameManager.getResourceManager()
                .getNewInstanceOfDefaultFont(buttonFontSize * getUnit(), Color.WHITE);


        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        Label label = new Label("PICTURE CATCH!", labelStyle);
        label.setAlignment(Align.center);


        btnStyle.up = new TextureRegionDrawable(buttonTexture.getUp());
        btnStyle.down = new TextureRegionDrawable(buttonTexture.getDown());
        btnStyle.font = buttonFont;

        TextButton btnNewGame = new TextButton("New game", btnStyle);
        btnNewGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.pushState(new PainterChoiceState(gameManager));
            }
        });
        TextButton btnGallery = new TextButton("Gallery", btnStyle);
        btnGallery.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.pushState(new GalleryState(gameManager));
            }
        });
        TextButton btnExit = new TextButton("Exit", btnStyle);
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });

        Table menu = new Table();
        menu.center();
        stage = new Stage();

        menu.add(label)
                .expandX()
                .padBottom(buttonMargin * getUnit());
        menu.row();
        menu.add(btnNewGame)
                .width(buttonWidth * getUnit())
                .height(buttonHeight * getUnit())
                .padTop(buttonMargin * getUnit());
        menu.row();
        menu.add(btnGallery)
                .width(buttonWidth * getUnit())
                .height(buttonHeight * getUnit())
                .padTop(buttonMargin * getUnit());
        menu.row();
        menu.add(btnExit)
                .width(buttonWidth * getUnit())
                .height(buttonHeight * getUnit())
                .padTop(buttonMargin * getUnit());
        menu.setFillParent(true);
        stage.addActor(menu);
    }

    @Override
    public void onShow() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void onResize(int width, int height) {
        stage.getViewport().update(gameManager.getScreenWidth(), gameManager.getScreenHeight());
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
    public void onDispose() {
        buttonFont.dispose();
        font.dispose();
        stage.dispose();
    }
}
