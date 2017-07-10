package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.zolotukhin.picturegame.factory.FallItemFactory;
import com.zolotukhin.picturegame.gameobject.FallingItem;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.gameobject.Player;
import com.zolotukhin.picturegame.factory.SimpleFallItemFactory;
import com.zolotukhin.picturegame.gameobject.Hud;

import java.util.Iterator;

/**
 * Created by Artem Zolotukhin on 7/9/17.
 */

public class GameState extends State {


    private static final float MIN_SPACE_INTERVAL_SPAWN_ITEM = 0.2f;

    private static final float START_SPACE_INTERVAL_SPAWN_ITEM = 1.0f;

    private static final float STEP_FACTOR_SPACE_INTERVAL_SPAWN_ITEM = 0.99f;

    private static final int MAX_ITEM_ON_SCREEN = 10;

    private Player player;

    private Array<FallingItem> fallingItems;

    private float spaceInterval;
    private FallingItem lastItem;
    private FallItemFactory fallItemFactory;

    private Hud hud;

    public GameState(GameManager gsm) {
        super(gsm);

        camera.setToOrtho(false, gsm.getScreenWidth(), gsm.getScreenHeight());

        player = new Player(gsm.getScreenWidth() / 2, 0, gsm.getScreenWidth());

        fallItemFactory = new SimpleFallItemFactory(gsm.getScreenWidth(), gsm.getScreenHeight(), player);

        fallingItems = new Array<FallingItem>();

        spaceInterval = START_SPACE_INTERVAL_SPAWN_ITEM;

        int fontSize = Math.round(gsm.getScreenWidth() * 0.05f);
        int hudMargin = 24;


        hud = new Hud(hudMargin, gsm.getScreenHeight() - fontSize, fontSize);

    }


    @Override
    public void update(float delta) {
        if (isTouched()) {
            if (getPointX() < gsm.getScreenWidth() / 2) {
                player.move(Player.Direction.LEFT, delta);
            } else {
                player.move(Player.Direction.RIGHT, delta);
            }
        }

        checkAndSpawnFallingItem();
        updateFallingItems(delta);

        hud.setPoints(player.getPoints());
        hud.setLives(player.getLives());

        if (player.getLives() <= 0) {
            //TODO gsm SET state game over screen
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
    }

    private void checkAndSpawnFallingItem() {

        if (lastItem == null) {
            lastItem = fallItemFactory.getItem();
            fallingItems.add(lastItem);
            return;
        }

        if (lastItem.getY() < gsm.getScreenHeight() - gsm.getScreenWidth() * spaceInterval - lastItem.getHeight()) {

            lastItem = fallItemFactory.getItem();
            fallingItems.add(lastItem);

            if (spaceInterval > MIN_SPACE_INTERVAL_SPAWN_ITEM) {
                spaceInterval *= STEP_FACTOR_SPACE_INTERVAL_SPAWN_ITEM;
            } else {
                spaceInterval = MIN_SPACE_INTERVAL_SPAWN_ITEM;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        player.renderWithoutBeginEnd(batch);

        for (FallingItem i : fallingItems) {
            i.renderWithoutBeginEnd(batch);
        }
        hud.renderWithoutBeginEnd(batch);

        batch.end();


    }

    @Override
    public void pause() {

        //TODO gsm PUSH pause screen

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        for (FallingItem i : fallingItems) {
            i.dispose();
        }

        player.dispose();
        hud.dispose();
    }
}
