package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.gameobject.Button;
import com.zolotukhin.picturegame.model.Picture;

/**
 * Created by Artem Zolotukhin on 7/13/17.
 */

public class PictureViewState extends State implements GestureDetector.GestureListener {

    public static final float BUTTON_WIDTH = 0.5f;
    public static final float BUTTON_HEIGHT = 0.2f;


    public static final String PARAM_PICTURE = PictureViewState.class.getName() + ":param_picture";

    private Picture picture;

    private Texture texture;

    private Texture btnUp, btnDown;

    private float pictureX, pictureY, pictureWidth, pictureHeight;

    private Stage stage;

    public PictureViewState(GameManager gameManager) {
        super(gameManager);

        picture = loadPicture();

        texture = new Texture(picture.getPath());

        stage = new Stage(new ScreenViewport());


    }

    private Picture loadPicture() {

        return (Picture) gameManager.getParcel(PARAM_PICTURE);
    }

    // LIFECYCLE

    @Override
    public void onUpdate(float delta) {

    }

    @Override
    public void onRender(SpriteBatch batch) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDispose() {
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
