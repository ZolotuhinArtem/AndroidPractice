package com.zolotukhin.picturegame.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Artem Zolotukhin on 7/9/17.
 */

public abstract class GameObject implements Disposable {

    private Rectangle collisionBody;

    private boolean isDead;

    private Array<GameObject> collisionObject;

    private Array<CollisionListener> collisionListeners;

    public GameObject(float x, float y, float width, float height) {
        collisionBody = new Rectangle(x, y, width, height);
        isDead = false;
        collisionObject = new Array<>();
        collisionListeners = new Array<>();
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

    public void update(float delta) {
        for(GameObject i : collisionObject) {
            if (getCollisionBody().overlaps(i.getCollisionBody())) {
                for (CollisionListener j : collisionListeners) {
                    j.onCollision(this, i);
                }
            }
        }
    }

    public abstract void renderWithoutBeginEnd(SpriteBatch batch);

    public GameObject addCollisionListener(CollisionListener listener) {
        collisionListeners.add(listener);
        return this;
    }

    public GameObject removeCollisionListener(CollisionListener listener) {
        collisionListeners.removeValue(listener, true);
        return this;
    }

    public GameObject clearCollisionListeners(){
        collisionListeners.clear();
        return this;
    }

    public GameObject addCollisionObject(GameObject gameObject) {
        collisionObject.add(gameObject);
        return this;
    }

    public GameObject removeCollisionObject(GameObject gameObject) {
        collisionObject.removeValue(gameObject, true);
        return this;
    }

    public GameObject clearCollisionObject(GameObject gameObject) {
        collisionObject.clear();
        return this;
    }

    @Override
    public abstract void dispose();

    public interface CollisionListener{
        void onCollision(GameObject object, GameObject cause);
    }
}
