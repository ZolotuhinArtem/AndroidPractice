package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.zolotukhin.picturegame.GameManager;

/**
 * Created by Nurislam on 13.07.2017.
 */

public class MenuState extends State {

    public static final float BUTTON_DEFAULT_WIDTH = 0.9f;
    public static final float BUTTON_DEFAULT_HEIGHT = 0.27f;
    public static final float BUTTON_FONT_SIZE = 0.07f;
    public static final float BUTTON_MARGIN = 0.05f;
    public static final float LABEL_FONT_SIZE = 0.08f;

    private TextButton btnNewGame, btnGallery, btnExit;
    private BitmapFont font;
    private BitmapFont buttonFont;
    private Table menu;
    private Stage stage;

    private Texture btnTextureUp;
    private Texture btnTextureDown;


    public MenuState(GameManager gsm) {
        super(gsm);

        font = gameManager.getDefaultFont(LABEL_FONT_SIZE * getUnit(), Color.WHITE);
        buttonFont = gameManager.getDefaultFont(BUTTON_FONT_SIZE * getUnit(), Color.BLACK);

        btnTextureUp = new Texture("btn_simple.png");
        btnTextureDown = new Texture("btn_pressed.png");

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = Color.WHITE;
        Label label = new Label("GAME NAME!", labelStyle);
        label.setAlignment(Align.center);


        btnStyle.up = new TextureRegionDrawable(new TextureRegion(btnTextureUp));
        btnStyle.down = new TextureRegionDrawable(new TextureRegion(btnTextureDown));
        btnStyle.font = buttonFont;

        btnNewGame = new TextButton("New game", btnStyle);
        btnNewGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.pushState(new PainterChoiceState(gameManager));
            }
        });
        btnGallery = new TextButton("Gallery", btnStyle);
        btnGallery.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.pushState(new GalleryState(gameManager));
            }
        });
        btnExit = new TextButton("Exit", btnStyle);
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });

        menu = new Table();
        menu.center();
        stage = new Stage();

        menu.add(label)
                .expandX();
        menu.row();
        menu.add(btnNewGame)
                .width(BUTTON_DEFAULT_WIDTH * getUnit())
                .height(BUTTON_DEFAULT_HEIGHT * getUnit())
                .padTop(BUTTON_MARGIN * getUnit());
        menu.row();
        menu.add(btnGallery)
                .width(BUTTON_DEFAULT_WIDTH * getUnit())
                .height(BUTTON_DEFAULT_HEIGHT * getUnit())
                .padTop(BUTTON_MARGIN * getUnit());
        menu.row();
        menu.add(btnExit)
                .width(BUTTON_DEFAULT_WIDTH * getUnit())
                .height(BUTTON_DEFAULT_HEIGHT * getUnit())
                .padTop(BUTTON_MARGIN * getUnit());
        menu.setFillParent(true);
        stage.addActor(menu);
    }

    @Override
    public void onShow() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void onUpdate(float delta) {
        stage.getViewport().update(gameManager.getScreenWidth(), gameManager.getScreenHeight(), true);
    }

    @Override
    public void onRender(SpriteBatch batch) {
        batch.begin();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.end();
    }

    @Override
    public void onDispose() {
        btnTextureUp.dispose();
        btnTextureDown.dispose();
        buttonFont.dispose();
        font.dispose();
        stage.dispose();
    }
}
