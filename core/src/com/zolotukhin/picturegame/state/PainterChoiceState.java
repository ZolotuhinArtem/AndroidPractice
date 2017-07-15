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
import com.zolotukhin.picturegame.resource.ButtonTexture;
import com.zolotukhin.picturegame.state.gamestate.GameState;

import java.util.List;

/**
 * Created by Nurislam on 14.07.2017.
 */

public class PainterChoiceState extends State {


    public static final float BUTTON_WIDTH = 1f;
    public static final float BUTTON_MARGIN = 0.05f;
    public static final float LABEL_FONT_SIZE = 0.055f;

    private TextButton btnExit;
    private BitmapFont font;
    private Table menu;
    private Stage stage;
    private List<Painter> allPainter;


    public PainterChoiceState(GameManager gsm) {
        super(gsm);

        ButtonTexture buttonTexture = gameManager.getResourceManager().getDefaultButtonTexture();

        float buttonWidth = BUTTON_WIDTH;
        float buttonHeight = (float) buttonTexture.getUp().getRegionHeight() / (float) buttonTexture.getUp().getRegionWidth()
                * buttonWidth;
        float buttonMargin = BUTTON_MARGIN;
        float labelFontSize = LABEL_FONT_SIZE;

        allPainter = new JsonPictureRepository().getAllPainters();

        font = gameManager.getResourceManager()
                .getNewInstanceOfDefaultFont(labelFontSize * getUnit(), Color.WHITE);

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.up = new TextureRegionDrawable(buttonTexture.getUp());
        btnStyle.down = new TextureRegionDrawable(buttonTexture.getDown());
        btnStyle.font = font;

        menu = new Table();
        menu.center();
        stage = new Stage();

        boolean isFirst = true;
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
                    .width(buttonWidth * getUnit())
                    .height(buttonHeight * getUnit())
                    .padTop(!isFirst ? buttonMargin * getUnit() : 0);
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
                .width(buttonWidth * getUnit())
                .height(buttonHeight * getUnit())
                .padTop(buttonMargin * getUnit());

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
    public void onResize(int width, int height) {
        stage.getViewport().update(gameManager.getScreenWidth(), gameManager.getScreenHeight(), true);
    }

    @Override
    public void onUpdate(float delta) {
        stage.act(delta);
    }

    @Override
    public void onRender(SpriteBatch batch) {
        batch.begin();
        stage.draw();
        batch.end();
    }

    @Override
    public void onDispose() {
        font.dispose();
        stage.dispose();
    }
}
