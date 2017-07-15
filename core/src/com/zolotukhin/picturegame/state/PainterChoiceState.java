package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.model.JsonPictureRepository;
import com.zolotukhin.picturegame.model.Painter;
import com.zolotukhin.picturegame.state.gamestate.GameState;

import java.util.List;

/**
 * Created by Nurislam on 14.07.2017.
 */

public class PainterChoiceState extends State {


    public static final float BUTTON_DEFAULT_WIDTH = 0.9f;
    public static final float BUTTON_DEFAULT_HEIGHT = 0.27f;
    public static final float BUTTON_MARGIN = 0.05f;

    public static final float LABEL_FONT_SIZE = 0.055f;

    private TextButton btnExit;
    private BitmapFont font;
    private Table menu;
    private Stage stage;
    private List<Painter> allPainter;

    private Texture btnTextureUp;
    private Texture btnTextureDown;

    public PainterChoiceState(GameManager gsm) {
        super(gsm);
        allPainter = new JsonPictureRepository().getAllPainters();
        btnTextureUp = new Texture("btn_simple.png");
        btnTextureDown = new Texture("btn_pressed.png");

        font = gameManager.getDefaultFont(LABEL_FONT_SIZE * getUnit(), Color.BLACK);
        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.up = new TextureRegionDrawable(new TextureRegion(btnTextureUp));
        btnStyle.down = new TextureRegionDrawable(new TextureRegion(btnTextureDown));
        btnStyle.font = font;

        menu = new Table();
        menu.bottom();
        stage = new Stage();

        boolean isFirst = true;

        int i = 0;
        for (Painter painter : allPainter) {
            Button btnPainter = new TextButton(painter.getNames().get("en"), btnStyle);
            final Painter finalPainter = painter;
            btnPainter.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameManager.putParcel(GameState.PARAM_PAINTER, finalPainter);
                    gameManager.setState(new GameState(gameManager));
                }
            });
            menu.add(btnPainter)
                    .width(BUTTON_DEFAULT_WIDTH * getUnit())
                    .height(BUTTON_DEFAULT_HEIGHT * getUnit())
                    .padTop(!isFirst ? BUTTON_MARGIN * getUnit() : 0);
            menu.row();
            isFirst = false;
        }
        btnExit = new TextButton("Back", btnStyle);
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.popState();
            }
        });
        menu.add(btnExit)
                .width(BUTTON_DEFAULT_WIDTH * getUnit())
                .height(BUTTON_DEFAULT_HEIGHT * getUnit())
                .padTop(BUTTON_MARGIN * getUnit());
        menu.setFillParent(true);

        ScrollPane pane = new ScrollPane(menu);
        pane.setScrollingDisabled(true, false);
        pane.setFillParent(true);

        stage.addActor(pane);
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
        font.dispose();
        stage.dispose();
    }
}
