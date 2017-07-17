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

    public static float SPRITE_WIDTH_PART_OF_WIDTH = 0.18611111f;
    public static float SPRITE_HEIGHT_PART_OF_WIDTH = 0.25f;

    public static float DEFAULT_WIDTH = 0.1f;
    public static float DEFAULT_HEIGHT = 0.23f;

    public static int START_LIVES = 3;
    public static int MAX_LIVES = 5;

    private float factSpeed;

    private float screenWidth;

    private int points;

    private int lives;

    private boolean isRun;

    private Texture textureRun;

    private Texture textureStand;

    private TextureRegion currentFrame;
    private float stateTime;

    private Animation<TextureRegion> animation;

    private float spriteWidth, spriteHeight;

    public void addPoints(int i) {
        points += i;
    }

    public Direction direction;


    public enum Direction {
        LEFT, RIGHT
    }


    public Player(float x, float y, float screenWidth) {
        super(x, y);
        this.screenWidth = screenWidth;

        factSpeed = MOVEMENT_SPEED_PART_OF_WIDTH_PER_SECOND * screenWidth;
        spriteWidth = screenWidth * SPRITE_WIDTH_PART_OF_WIDTH;
        spriteHeight = screenWidth * SPRITE_HEIGHT_PART_OF_WIDTH;
        setWidth(screenWidth * DEFAULT_WIDTH);
        setHeight(screenWidth * DEFAULT_HEIGHT);
        textureRun = new Texture("run.png");
        textureStand = new Texture("stand.png");
        points = 0;
        lives = START_LIVES;

        AnimationBuilder animationBuilder = new AnimationBuilder();
        animation = animationBuilder.from(textureRun, 1f / 30f, 1, 14);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        stateTime = 0;
        direction = Direction.RIGHT;
    }


    public void move(Direction direction, float delta) {
        this.direction = direction;
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

    public void setRun(boolean run) {
        isRun = run;
    }

    @Override
    public void update(float delta) {
        if (isRun) {
            stateTime += delta;
        } else {
            stateTime = 0;
        }
        currentFrame = animation.getKeyFrame(stateTime, true);
    }

    @Override
    public void renderWithoutBeginEnd(SpriteBatch batch) {

        if (currentFrame != null) {
            if (isRun) {
                if (direction == Direction.LEFT) {
                    batch.draw(currentFrame, getX() + getWidth() - (getWidth() - spriteWidth) / 2, getY(), -spriteWidth, spriteHeight);
                } else {
                    batch.draw(currentFrame, getX() + (getWidth() - spriteWidth) / 2, getY(), spriteWidth, spriteHeight);
                }
            } else {
                if (direction == Direction.LEFT) {
                    batch.draw(textureStand, getX() + getWidth() - (getWidth() - spriteWidth) / 2, getY(), -spriteWidth, spriteHeight);
                } else {
                    batch.draw(textureStand, getX() + (getWidth() - spriteWidth) / 2, getY(), spriteWidth, spriteHeight);
                }
            }
        }
    }

    @Override
    public void dispose() {
        textureRun.dispose();
        textureStand.dispose();
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
