package com.zolotukhin.picturegame.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Artem Zolotukhin on 7/10/17.
 */

public class FallingItem extends GameObject {

    public static final float WIDTH_IN_PART_OF_WIDTH = 0.1f;
    public static final float HEIGHT_IN_PART_OF_WIDTH = 0.1f;

    public static final float FALLING_SPEED_PART_OF_WIDTH = 0.6f;

    private float screenWidth;

    private float fallingSpeed;

    private Texture texture;

    private Player player;

    public FallingItem(float x, float y, float screenWidth) {
        this(x, y, screenWidth, null);

    }

    public FallingItem(float x, float y, float screenWidth, Player player ) {
        super(x, y);

        this.screenWidth = screenWidth;
        float width = screenWidth * WIDTH_IN_PART_OF_WIDTH;
        float height = screenWidth * HEIGHT_IN_PART_OF_WIDTH;
        setWidth(width);
        setHeight(height);
        fallingSpeed = screenWidth * FALLING_SPEED_PART_OF_WIDTH;
        texture = new Texture("coin.png");
        this.player = player;
    }


    @Override
    public void update(float delta) {

        addY( -fallingSpeed * delta );
        if (getY() < 0) {
            player.subLives(1);
            destroy();
            return;
        }
        if (player != null) {
            if (getCollisionBody().overlaps(player.getCollisionBody())) {
                player.addPoints(1);
                destroy();
                return;
            }
        }

    }



    @Override
    public void renderWithoutBeginEnd(SpriteBatch batch) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }


    public Player getPlayer() {
        return player;
    }

    public FallingItem setPlayer(Player player) {
        this.player = player;
        return this;
    }
}
