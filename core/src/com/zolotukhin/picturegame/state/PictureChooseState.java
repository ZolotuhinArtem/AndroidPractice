package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.model.JsonPictureRepository;
import com.zolotukhin.picturegame.model.Painter;
import com.zolotukhin.picturegame.model.PictureRepository;

/**
 * Created by Artem Zolotukhin on 7/13/17.
 */

public class PictureChooseState extends State {

    public static final int DEFAULT_PICTURE_COUNT = 3;

    public static final String PARAM_PAINTER = PictureChooseState.class.getName() + ":param_painter";

    private PictureRepository pictureRepository;

    private Painter painter;

    private Texture[] pictures;

    private TextureRegion[] thumbnails;

    private ImageButton[] buttons;

    private Stage stage;

    private Label label;

    private BitmapFont bitmapFont;

    public PictureChooseState(GameManager gsm) {
        super(gsm);
        pictureRepository = new JsonPictureRepository();
        painter = loadPainter();


        Stage stage = new Stage(new ScreenViewport());
        Table table = new Table();
        stage.addActor(table);

        Label.LabelStyle style = new Label.LabelStyle(bitmapFont, Color.WHITE);
        //label = new Label("Chose picture of " + painter.getNames().get("en"), )

    }

    private Painter loadPainter() {
        String systemName = (String) gameManager.getParcel(PARAM_PAINTER);
        return pictureRepository.getPainterBySystemName(systemName);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        super.resize(width, height);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        for (int i = 0; i < DEFAULT_PICTURE_COUNT; i++) {
            pictures[i].dispose();
        }
        bitmapFont.dispose();
        stage.dispose();
    }
}
