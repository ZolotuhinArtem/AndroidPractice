package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
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
import com.zolotukhin.picturegame.resource.ButtonTexture;

/**
 * Created by Artem Zolotukhin on 7/13/17.
 */

public class PictureInGameViewState extends State implements GestureDetector.GestureListener {

    public static final float BUTTON_WIDTH = 0.5f;
    public static final float BUTTON_FONT_SIZE = 0.05f;
    public static final float BUTTON_MARGIN = 0.05f;

    public static final float MAX_SCALE = 3;
    public static final float MIN_SCALE = 0.7f;

    public static final String PARAM_PICTURE = PictureInGameViewState.class.getName() + ":param_picture";
    public static final String PARAM_PAINTER = PictureInGameViewState.class.getName() + ":param_painter";
    public static final String PARAM_RIGHT = PictureInGameViewState.class.getName() + ":param_right";

    private Picture picture;

    private Texture texture;

    private float pictureX, pictureY, pictureWidth, pictureHeight,
            pictureMinWidth, pictureMinHeight, pictureMaxHeight, pictureMaxWidth;

    private Stage stage;

    private BitmapFont font;

    private Boolean isRight;

    private Painter painter;
    private GestureDetector gestureDetector;

    private InputMultiplexer inputMultiplexer;


    public PictureInGameViewState(final GameManager gameManager) {
        super(gameManager);

        picture = loadPicture();
        isRight = loadIsRight();
        painter = loadPainter();

        ButtonTexture buttonTexture = gameManager.getResourceManager().getDefaultButtonTexture();

        float buttonWidth = BUTTON_WIDTH;
        float buttonHeight = (float) buttonTexture.getUp().getRegionHeight() / (float) buttonTexture.getUp().getRegionWidth()
                * buttonWidth;
        float buttonFontSize = BUTTON_FONT_SIZE;
        float buttonMargin = BUTTON_MARGIN;

        texture = new Texture(picture.getPath());

        font = gameManager.getResourceManager()
                .getNewInstanceOfDefaultFont(buttonFontSize * getUnit(), Color.WHITE);

        stage = new Stage(new ScreenViewport());
        Table table = new Table();
        table.setFillParent(true);
        table.center().bottom();
        stage.addActor(table);

        gestureDetector = new GestureDetector(this);

        configureButtons(table, buttonWidth, buttonHeight, buttonMargin, buttonTexture);

        preparePictureTextureParameters(buttonHeight + buttonMargin * 2);


        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(gestureDetector);
    }

    private void preparePictureTextureParameters(float bottomPanelHeight) {
        pictureX = 0;
        pictureY = 0;

        if (texture.getWidth() > texture.getHeight()) {
            pictureWidth = gameManager.getScreenWidth();
            pictureHeight = ((float) texture.getHeight() / (float) texture.getWidth()) * pictureWidth;

            pictureY = (gameManager.getScreenHeight() - bottomPanelHeight * getUnit() - pictureHeight) / 2f + bottomPanelHeight * getUnit();
        } else {
            pictureHeight = (gameManager.getScreenHeight() - bottomPanelHeight * getUnit());
            pictureY = bottomPanelHeight;
            pictureWidth = ((float) texture.getWidth() / (float) texture.getHeight()) * pictureHeight;
            pictureX = (gameManager.getScreenWidth() - pictureWidth) / 2;
            if (pictureWidth > gameManager.getScreenWidth()) {
                pictureWidth = gameManager.getScreenWidth();
                pictureHeight = ((float) texture.getHeight() / (float) texture.getWidth()) * pictureWidth;
                pictureX = 0;
                pictureY = (gameManager.getScreenHeight() - pictureHeight - bottomPanelHeight * getUnit()) / 2f + bottomPanelHeight * getUnit();
            }
        }
        pictureMinWidth = pictureWidth * MIN_SCALE;
        pictureMinHeight = (float) texture.getHeight() / (float) texture.getWidth() * pictureMinWidth;
        pictureMaxWidth = pictureWidth * MAX_SCALE;
        pictureMaxHeight = (float) texture.getHeight() / (float) texture.getWidth() * pictureMaxWidth;
    }

    private void configureButtons(Table table, float buttonWidth, float buttonHeight, float buttonMargin, ButtonTexture buttonTexture) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();

        style.up = new TextureRegionDrawable(buttonTexture.getUp());
        style.down = new TextureRegionDrawable(buttonTexture.getDown());
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
                .width(buttonWidth * getUnit())
                .height(buttonHeight * getUnit())
                .padTop(buttonMargin * getUnit())
                .padBottom(buttonMargin * getUnit());
        table.add(btnAccept)
                .width(buttonWidth * getUnit())
                .height(buttonHeight * getUnit())
                .padTop(buttonMargin * getUnit())
                .padBottom(buttonMargin * getUnit());
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
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void onUpdate(float delta) {
        stage.act(delta);
    }

    @Override
    public void onRender(SpriteBatch batch) {

        batch.begin();
        batch.draw(texture, pictureX, pictureY, pictureWidth, pictureHeight);
        batch.end();
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
        pictureY -= deltaY;
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {

        float xx = ((float) texture.getWidth() / (float) (texture.getHeight() + texture.getWidth())) * (distance - initialDistance);
        float yy = ((float) texture.getHeight() / (float) (texture.getHeight() + texture.getWidth())) * (distance - initialDistance);


        pictureWidth += xx;
        pictureHeight += yy;


        pictureX -= xx / 2f;
        pictureY -= yy / 2f;

        if (pictureWidth < pictureMinWidth) {
            pictureWidth = pictureMinWidth;
            pictureHeight = pictureMinHeight;

            pictureX += xx / 2f;
            pictureY += yy / 2f;
        } else {
            if (pictureWidth > pictureMaxWidth) {

                pictureWidth = pictureMaxWidth;
                pictureHeight = pictureMaxHeight;

                pictureX += xx / 2f;
                pictureY += yy / 2f;
            }
        }

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
