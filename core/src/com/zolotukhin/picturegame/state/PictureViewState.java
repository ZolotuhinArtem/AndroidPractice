package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.model.Painter;
import com.zolotukhin.picturegame.model.Picture;

/**
 * Created by Artem Zolotukhin on 7/13/17.
 */

public class PictureViewState extends State implements GestureDetector.GestureListener {

    public static final float BUTTON_WIDTH = 0.5f;
    public static final float BUTTON_HEIGHT = 0.2f;
    public static final float BUTTON_FONT_SIZE = 0.05f;

    public static final String PARAM_PICTURE = PictureViewState.class.getName() + ":param_picture";
    public static final String PARAM_PAINTER = PictureViewState.class.getName() + ":param_painter";
    public static final String PARAM_RIGHT = PictureViewState.class.getName() + ":param_right";

    private Picture picture;

    private Texture texture;

    private Texture btnUpTexture, btnDownTexture;

    private float pictureX, pictureY, pictureWidth, pictureHeight;

    private Stage stage;

    private BitmapFont font;

    private Boolean isRight;

    private Painter painter;

    public PictureViewState(final GameManager gameManager) {
        super(gameManager);

        picture = loadPicture();
        isRight = loadIsRight();
        painter = loadPainter();

        loadTextures();

        font = gameManager.getDefaultFont(BUTTON_FONT_SIZE * getUnit(), Color.BLACK);

        stage = new Stage(new ScreenViewport());
        Table table = new Table();
        table.setFillParent(true);
        table.center().bottom();
        stage.addActor(table);

        configureButtons(table);

        preparePictureTextureParameters();
    }

    private void preparePictureTextureParameters() {
        pictureX = 0;
        pictureY = 0;

        if (texture.getWidth() > texture.getHeight()) {
            pictureWidth = gameManager.getScreenWidth();
            pictureHeight = ((float) texture.getHeight() / (float) texture.getWidth()) * pictureWidth;

            pictureY = (gameManager.getScreenHeight() - BUTTON_HEIGHT * getUnit() - pictureHeight) / 2 + BUTTON_HEIGHT;
        } else {
            pictureHeight = (gameManager.getScreenHeight() - BUTTON_HEIGHT * getUnit());
            pictureY = BUTTON_HEIGHT;
            pictureWidth = ((float) texture.getWidth() / (float) texture.getHeight()) * pictureHeight;
            pictureX = (gameManager.getScreenWidth() - pictureWidth) / 2;
        }
    }

    private void configureButtons(Table table) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(btnUpTexture));
        style.down = new TextureRegionDrawable(new TextureRegion(btnDownTexture));
        style.font = font;

        TextButton btnCancel = new TextButton("Cancel", style);
        btnCancel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backToPreviousState();
            }
        });

        TextButton btnAccept = new TextButton("Accept", style);
        btnAccept.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startPictureViewInfoState();
            }
        });

        table.add(btnCancel)
                .width(BUTTON_WIDTH * getUnit())
                .height(BUTTON_HEIGHT * getUnit());
        table.add(btnAccept)
                .width(BUTTON_WIDTH * getUnit())
                .height(BUTTON_HEIGHT * getUnit());
    }

    private void startPictureViewInfoState() {
        gameManager.putParcel(PictureViewInfoState.PARAM_PAINTER, painter);
        gameManager.putParcel(PictureViewInfoState.PARAM_PICTURE, picture);
        gameManager.putParcel(PictureViewInfoState.PARAM_RIGHT, isRight);
        gameManager.popState();
        gameManager.setState(new PictureViewInfoState(gameManager));
    }

    private void backToPreviousState() {
        gameManager.popState();
    }

    private void loadTextures() {

        texture = new Texture(picture.getPath());
        btnUpTexture = new Texture("btn_simple.png");
        btnDownTexture = new Texture("btn_pressed.png");
    }

    private Painter loadPainter() {
        return (Painter) gameManager.getParcel(PARAM_PAINTER);
    }

    private Boolean loadIsRight() {
        return (Boolean) gameManager.getParcel(PARAM_RIGHT);
    }

    private Picture loadPicture() {

        return (Picture) gameManager.getParcel(PARAM_PICTURE);
    }

    // LIFECYCLE


    @Override
    public void onShow() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void onUpdate(float delta) {
        stage.act(delta);
    }

    @Override
    public void onRender(SpriteBatch batch) {
        stage.draw();
    }

    @Override
    public void onResize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void onDispose() {
        font.dispose();
        texture.dispose();
        btnUpTexture.dispose();
        btnDownTexture.dispose();
        stage.dispose();
    }

    // GESTURE HANDLING

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        pictureX += deltaX;
        pictureY += deltaY;
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
