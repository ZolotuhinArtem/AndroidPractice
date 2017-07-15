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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.model.JsonPictureRepository;
import com.zolotukhin.picturegame.model.Painter;
import com.zolotukhin.picturegame.state.gamestate.GameState;

import java.util.List;

/**
 * Created by Nurislam on 13.07.2017.
 */

class GalleryState extends State {
    public static final float BUTTON_DEFAULT_WIDTH = 0.15f;
    public static final float BUTTON_DEFAULT_HEIGHT = 0.15f;
    public static final float LABEL_FONT_SIZE = 0.1f;
    public static final float EXIT_FONT_SIZE = 0.05f;

    private TextButton btnNewGame, btnGallery, btnExit;
    private BitmapFont font;
    private Table menu;
    private Stage stage;
    private ScrollPane pane;
    private List<Painter> allPainter;

    public GalleryState(GameManager gsm) {
        super(gsm);

        allPainter = new JsonPictureRepository().getAllPainters();
        Gdx.input.setInputProcessor(stage);

        font = gameManager.getDefaultFont(LABEL_FONT_SIZE * getUnit(), Color.WHITE);
        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture("btn_simple.png")));
        btnStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture("btn_pressed.png")));
        btnStyle.font = gsm.getDefaultFont(32, Color.BLACK);

        menu = new Table();
        menu.center();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        for (Painter painter : allPainter) {
            Button btnPainter = new TextButton(painter.getSystemName(), btnStyle);
            final Painter finalPainter = painter;
            btnPainter.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameManager.setState(new GameState(gameManager));
                    gameManager.putParcel(GameState.PARAM_PAINTER, finalPainter);
                }
            });
            menu.add(btnPainter).width(gsm.getScreenWidth() / 2).height(gsm.getScreenHeight() / 10);
            menu.row();
        }
        btnExit = new TextButton("Exit", btnStyle);
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });
        menu.add(btnExit).width(gsm.getScreenWidth() / 2).height(gsm.getScreenHeight() / 10);
        menu.setFillParent(true);
        ScrollPane pane = new ScrollPane(menu);
        pane.setScrollingDisabled(true, false);
        pane.setFillParent(true);
        stage.addActor(pane);
    }

    @Override
    public void onUpdate(float delta) {
        stage.getViewport().update(gameManager.getScreenWidth(), gameManager.getScreenHeight(), true);
    }

    @Override
    public void onRender(SpriteBatch batch) {
        batch.begin();
        font.draw(batch, "GAME NAME! ", 0, gameManager.getScreenHeight() / 4 * 3,
                gameManager.getScreenWidth(), Align.center, false);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.end();
    }

    @Override
    public void onDispose() {
        font.dispose();
        stage.dispose();
    }

}
