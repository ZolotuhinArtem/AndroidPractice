package com.zolotukhin.picturegame.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Artem Zolotukhin on 7/9/17.
 */

public abstract class GameObject implements Disposable {

    private Rectangle collisionBody;

    private boolean isDead;

    public GameObject(float x, float y, float width, float height) {
        collisionBody = new Rectangle(x, y, width, height);
        isDead = false;
    }

    public GameObject(float x, float y) {
        this(x, y, 0, 0);
    }

    public void addX(float x) {
        collisionBody.setX(collisionBody.getX() + x);
    }

    public void addY(float y) {
        collisionBody.setY(collisionBody.getY() + y);
    }

    public void addXY(float x, float y) {
        addX(x);
        addY(y);
    }

    public void setX(float x) {
        collisionBody.setX(x);
    }

    public void setY(float y) {
        collisionBody.setY(y);
    }

    public void setXY(float x, float y) {
        setX(x);
        setY(y);
    }

    public float getX() {
        return collisionBody.getX();
    }

    public float getY() {
        return collisionBody.getY();
    }

    public GameObject setWidth(float width) {
        collisionBody.setWidth(width);
        return this;
    }

    public GameObject setHeight(float height) {
        collisionBody.setHeight(height);
        return this;
    }

    public GameObject setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
        return this;
    }

    public float getWidth() {
        return collisionBody.getWidth();
    }

    public float getHeight() {
        return collisionBody.getHeight();
    }

    public Rectangle getCollisionBody() {
        return collisionBody;
    }

    public GameObject setCollisionBody(Rectangle collisionBody) {
        this.collisionBody = collisionBody;
        return this;
    }

    public void destroy() {
        isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }

    public abstract void update(float delta);

    public abstract void renderWithoutBeginEnd(SpriteBatch batch);

    @Override
    public abstract void dispose();
}
