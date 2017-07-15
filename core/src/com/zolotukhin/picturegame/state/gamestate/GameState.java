package com.zolotukhin.picturegame.state.gamestate;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.builder.ButtonBuilder;
import com.zolotukhin.picturegame.factory.FallItemFactory;
import com.zolotukhin.picturegame.factory.SimpleFallItemFactory;
import com.zolotukhin.picturegame.gameobject.Button;
import com.zolotukhin.picturegame.gameobject.FallingItem;
import com.zolotukhin.picturegame.gameobject.Floor;
import com.zolotukhin.picturegame.gameobject.GameObject;
import com.zolotukhin.picturegame.gameobject.Hud;
import com.zolotukhin.picturegame.gameobject.Player;
import com.zolotukhin.picturegame.gameobject.SuperPictureFallingItem;
import com.zolotukhin.picturegame.model.GalleryEntry;
import com.zolotukhin.picturegame.model.JsonPictureRepository;
import com.zolotukhin.picturegame.model.Painter;
import com.zolotukhin.picturegame.model.PictureRepository;
import com.zolotukhin.picturegame.model.SimpleGalleryRepository;
import com.zolotukhin.picturegame.state.GameOverState;
import com.zolotukhin.picturegame.state.PauseState;
import com.zolotukhin.picturegame.state.PictureChooseState;
import com.zolotukhin.picturegame.state.State;

import java.util.Iterator;

/**
 * Created by Artem Zolotukhin on 7/9/17.
 */

public class GameState extends State implements Button.ButtonEventListener, SuperItemCatchListener {

    public static final String PARAM_PAINTER = GameState.class.getName() + ":param_painter";
    public static final String PARAM_PICTURE_CHOOSE_RESULT = GameState.class.getName() + ":param_picture_choose_result";

    private static final float MIN_SPACE_INTERVAL_SPAWN_ITEM = 0.2f;

    private static final float START_SPACE_INTERVAL_SPAWN_ITEM = 1.0f;

    private static final float STEP_FACTOR_SPACE_INTERVAL_SPAWN_ITEM = 0.965f;

    public static final float BOTTOM_PANEL_HEIGHT = 0.15f;
    public static final float PAUSE_BUTTON_SIZE = 0.12f;

    public static final float FONT_SIZE = 0.05f;

    public static final float HUD_MARGIN = 0.033f;
    public static final float HUD_LIVE = 0.07f;
    public static final float HUD_FONT_SIZE = 0.05f;

    private Player player;

    private Array<FallingItem> fallingItems;
    private Array<GameObject> simpleObjects;

    private float spaceInterval;
    private FallingItem lastItem;
    private FallItemFactory fallItemFactory;

    private Hud hud;
    private Button btnPause;
    private Floor floor;

    private Painter currentPainter;

    private BitmapFont font;

    private Boolean isStarted;

    public GameState(GameManager gsm) {
        super(gsm);

        isStarted = false;
        font = gameManager.getDefaultFont(FONT_SIZE * getUnit(), Color.WHITE);

        simpleObjects = new Array<>();
        fallingItems = new Array<>();

        currentPainter = loadPainter();

        player = new Player(gsm.getScreenWidth() / 2, BOTTOM_PANEL_HEIGHT * getUnit() + 4, getUnit());

        spaceInterval = START_SPACE_INTERVAL_SPAWN_ITEM;

        int fontSize = Math.round(getUnit() * HUD_FONT_SIZE);
        float hudLiveSize = getUnit() * HUD_LIVE;
        hud = new Hud(HUD_MARGIN * getUnit(), gsm.getScreenHeight() - HUD_MARGIN * getUnit(), fontSize, hudLiveSize, gameManager);
        simpleObjects.add(hud);

        btnPause = new ButtonBuilder()
                .textureSimple(new Texture("btn_pause_simple.png"), true)
                .texturePressed(new Texture("btn_pause_pressed.png"), true)
                .height(PAUSE_BUTTON_SIZE * getUnit())
                .width(PAUSE_BUTTON_SIZE * getUnit())
                .state(this)
                .addEventListener(this)
                .build();
        btnPause.setX(gsm.getScreenWidth() - btnPause.getWidth());
        btnPause.setY(gsm.getScreenHeight() - btnPause.getHeight());
        simpleObjects.add(btnPause);


        floor = new Floor(0, 0, gsm.getScreenWidth(), BOTTOM_PANEL_HEIGHT * getUnit());
        GameObject.CollisionListener collisionListener = new GameStateCollisionListener(player, this);
        fallItemFactory = new SimpleFallItemFactory(gsm.getScreenWidth(), gsm.getScreenHeight(), player, floor, collisionListener);

        simpleObjects.add(floor);
    }

    private Painter loadPainter() {

        return (Painter) gameManager.getParcel(PARAM_PAINTER);
    }


    @Override
    public void onUpdate(float delta) {

        checkPlayerMovementControl(delta);

        if (isStarted) {
            checkAndSpawnFallingItem();
            updateFallingItems(delta);
        } else {
            if (isTouched()) {
                isStarted = true;
            }
        }

        updateSimpleObjects(delta);

        player.update(delta);

        hud.setPoints(player.getPoints());
        hud.setLives(player.getLives());

        checkParcels();

        checkPlayerLives();
    }

    private void checkPlayerLives() {
        if (player.getLives() <= 0) {
            gameManager.putParcel(GameOverState.POINTS_KEY, player.getPoints());
            gameManager.setState(new GameOverState(gameManager));
        }
    }

    private void checkParcels() {
        Boolean bool = (Boolean) gameManager.getParcel(PARAM_PICTURE_CHOOSE_RESULT);
        if (bool != null) {
            if (bool) {
                player.addPoints(SuperPictureFallingItem.DEFAULT_COST);
            } else {
                player.subLives(1);
            }
            gameManager.putParcel(PARAM_PICTURE_CHOOSE_RESULT, null);
        }
    }

    private void checkPlayerMovementControl(float delta) {
        if (isTouched()) {
            if (getPointX() < gameManager.getScreenWidth() / 2) {
                player.move(Player.Direction.LEFT, delta);
            } else {
                player.move(Player.Direction.RIGHT, delta);
            }
        }
    }

    private void updateSimpleObjects(float delta) {
        Iterator<GameObject> iterator = simpleObjects.iterator();
        GameObject object;
        while (iterator.hasNext()) {
            object = iterator.next();
            object.update(delta);
            if (object.isDead()) {
                iterator.remove();
            }
        }
    }

    private void updateFallingItems(float delta) {
        Iterator<FallingItem> iterator = fallingItems.iterator();
        FallingItem item;
        while (iterator.hasNext()) {
            item = iterator.next();
            item.update(delta);
            if (item.isDead()) {
                item.dispose();
                iterator.remove();
            }
        }
        btnPause.update(delta);
    }

    private void checkAndSpawnFallingItem() {

        if (lastItem == null) {
            lastItem = fallItemFactory.getItem();
            fallingItems.add(lastItem);
        } else {

            if (lastItem.getY() < gameManager.getScreenHeight() - gameManager.getScreenWidth() * spaceInterval - lastItem.getHeight()) {

                lastItem = fallItemFactory.getItem();
                fallingItems.add(lastItem);

                if (spaceInterval > MIN_SPACE_INTERVAL_SPAWN_ITEM) {
                    spaceInterval *= STEP_FACTOR_SPACE_INTERVAL_SPAWN_ITEM;
                } else {
                    spaceInterval = MIN_SPACE_INTERVAL_SPAWN_ITEM;
                }
            }
        }
        lastItem.setFloor(floor);
    }

    @Override
    public void onRender(SpriteBatch batch) {

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        player.renderWithoutBeginEnd(batch);

        for (FallingItem i : fallingItems) {
            i.renderWithoutBeginEnd(batch);
        }

        renderSimpleObjects(batch);

        if (!isStarted) {
            font.draw(batch, "Tap to\nStart", 0, gameManager.getScreenHeight() / 2,
                    gameManager.getScreenWidth(), Align.center, true);
        }

        batch.end();
    }

    private void renderSimpleObjects(SpriteBatch batch) {
        for (GameObject i : simpleObjects) {
            i.renderWithoutBeginEnd(batch);
        }
    }

    @Override
    public void onPause() {

        gameManager.pushState(new PauseState(gameManager));
    }


    @Override
    public void onDispose() {
        for (FallingItem i : fallingItems) {
            i.dispose();
        }

        player.dispose();
        hud.dispose();
        font.dispose();
    }

    @Override
    public void onEvent(Button button, Button.Event event) {

        switch (event) {
            case RELEASED:
                if (button == btnPause) {
                    gameManager.pushState(new PauseState(gameManager));
                }
                break;
        }
    }

    @Override
    public void onCatch(SuperPictureFallingItem item) {
        gameManager.putParcel(PictureChooseState.PARAM_PAINTER, currentPainter);
        gameManager.pushState(new PictureChooseState(gameManager));
    }
}
