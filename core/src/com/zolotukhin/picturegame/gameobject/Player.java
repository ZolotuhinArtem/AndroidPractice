package com.zolotukhin.picturegame.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.zolotukhin.picturegame.builder.AnimationBuilder;

/**
 * Created by Artem Zolotukhin on 7/10/17.
 */

public class Player extends GameObject {

    public static float MOVEMENT_SPEED_PART_OF_WIDTH_PER_SECOND = 1f;

    public static float SPRITE_WIDTH_PART_OF_WIDTH = 0.25f;
    public static float SPRITE_HEIGHT_PART_OF_WIDTH = 0.25f;

    public static int START_LIVES = 3;
    public static int MAX_LIVES = 5;

    private float factSpeed;

    private float screenWidth;

    private int points;

    private int lives;

    private Texture texture;

    private TextureRegion currentFrame;
    private float stateTime;

    Animation<TextureRegion> animation;

    public void addPoints(int i) {
        points += i;
    }


    public enum Direction {
        LEFT, RIGHT
    }


    public Player(float x, float y, float screenWidth) {
        super(x, y);
        this.screenWidth = screenWidth;
        factSpeed = MOVEMENT_SPEED_PART_OF_WIDTH_PER_SECOND * screenWidth;
        float factSpriteWidth = screenWidth * SPRITE_WIDTH_PART_OF_WIDTH;
        float factSpriteHeight = screenWidth * SPRITE_HEIGHT_PART_OF_WIDTH;
        setWidth(factSpriteWidth);
        setHeight(factSpriteHeight);
        texture = new Texture("avatar.png");
        points = 0;
        lives = START_LIVES;


        AnimationBuilder animationBuilder = new AnimationBuilder();
        animation = animationBuilder.from(texture, 0.1f, 2, 2);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        stateTime = 0;
    }


    public void move(Direction direction, float delta) {
        switch (direction) {
            case LEFT:
                addX(-delta * factSpeed);
                break;
            case RIGHT:
                addX(delta * factSpeed);
                break;
        }
        checkPosition();
    }

    private void checkPosition() {
        if (getX() < 0) {
            setX(0);
        }
        if (getX() > screenWidth - getWidth()) {
            setX(screenWidth - getWidth());
        }
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        currentFrame = animation.getKeyFrame(stateTime, true);
    }

    @Override
    public void renderWithoutBeginEnd(SpriteBatch batch) {

        if (currentFrame != null) {
            batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public int getPoints() {
        return points;
    }

    public Player setPoints(int points) {
        this.points = points;
        return this;
    }

    public Player addLives(int i) {
        lives = Math.min(MAX_LIVES, lives + i);
        return this;
    }

    public Player subLives(int i) {
        lives = Math.max(0, lives - i);
        return this;
    }

    public int getLives() {
        return lives;
    }

    public Player setLives(int lives) {
        this.lives = lives;
        return this;
    }
}
