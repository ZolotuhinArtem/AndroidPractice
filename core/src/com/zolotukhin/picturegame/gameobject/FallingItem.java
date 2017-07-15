package com.zolotukhin.picturegame.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Artem Zolotukhin on 7/10/17.
 */

public class FallingItem extends GameObject {

    public static final float WIDTH_IN_PART_OF_WIDTH = 0.07f;
    public static final float HEIGHT_IN_PART_OF_WIDTH = 0.07f;

    public static final float FALLING_SPEED_PART_OF_WIDTH = 0.6f;

    public static final int DEFAULT_COST = 1;

    private float screenWidth;

    private float fallingSpeed;

    private Texture texture;

    private Player player;

    private GameObject floor;

    private int cost;


    public FallingItem(float x, float y, float screenWidth) {
        super(x, y);

        this.screenWidth = screenWidth;
        float width = screenWidth * WIDTH_IN_PART_OF_WIDTH;
        float height = screenWidth * HEIGHT_IN_PART_OF_WIDTH;
        setWidth(width);
        setHeight(height);
        fallingSpeed = screenWidth * FALLING_SPEED_PART_OF_WIDTH;
        texture = new Texture("item.png");
        cost = DEFAULT_COST;

    }


    @Override
    public void update(float delta) {

        super.update(delta);

        addY(-fallingSpeed * delta);

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

    public GameObject getFloor() {
        return floor;
    }

    public FallingItem setFloor(GameObject floor) {
        this.floor = floor;
        return this;
    }

    public int getCost() {
        return cost;
    }

    public FallingItem setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public Texture getTexture() {
        return texture;
    }

    public FallingItem setTexture(Texture texture) {
        this.texture = texture;
        return this;
    }


}
