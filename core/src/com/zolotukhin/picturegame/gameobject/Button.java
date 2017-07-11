package com.zolotukhin.picturegame.gameobject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.zolotukhin.picturegame.state.State;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class Button extends GameObject {

    public enum Event {
        PRESSED, HOLDING, RELEASED
    }

    private State state;
    private Texture textureSimple, texturePressed;

    private Array<ButtonEventListener> listeners;

    private boolean isPressed;

    private BitmapFont font;

    private String text;

    private boolean textureSimpleDisposeAfterDeathOrRemove,
            texturePressedDisposeAfterDeathOrRemove,
            fontDisposeAfterDeathOrRemove;


    public Button(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.listeners = new Array<>();
        isPressed = false;
        text = "";
        texturePressedDisposeAfterDeathOrRemove = false;
        textureSimpleDisposeAfterDeathOrRemove = false;
        fontDisposeAfterDeathOrRemove = false;
    }

    @Override
    public void update(float delta) {
        if (state == null) {
            return;
        }

        if (state.isTouched()) {
            if (getCollisionBody().contains(state.getPointX(), state.getPointY())) {
                if (!isPressed) {
                    for (ButtonEventListener i : listeners) {
                        i.onEvent(this, Event.PRESSED);
                    }
                } else {
                    for (ButtonEventListener i : listeners) {
                        i.onEvent(this, Event.HOLDING);
                    }
                }
                isPressed = true;
            } else {
                isPressed = false;
            }
        } else {
            if (isPressed) {
                isPressed = false;
                for (ButtonEventListener i : listeners) {
                    i.onEvent(this, Event.RELEASED);
                }
            }
        }
    }

    @Override
    public void renderWithoutBeginEnd(SpriteBatch batch) {
        if (isPressed) {
            batch.draw(texturePressed, getX(), getY(), getWidth(), getHeight());
        } else {
            batch.draw(textureSimple, getX(), getY(), getWidth(), getHeight());
        }
        if (font != null) {
            font.draw(batch, text, getX(), getY() + getHeight() - (getHeight() - font.getCapHeight()) / 2, getWidth(), Align.center, false);
        }
    }

    @Override
    public void dispose() {
        if ((textureSimple != null) && textureSimpleDisposeAfterDeathOrRemove) {
            textureSimple.dispose();
        }
        if ((texturePressed != null) && texturePressedDisposeAfterDeathOrRemove) {
            texturePressed.dispose();
        }
        if ((font != null) && fontDisposeAfterDeathOrRemove) {
            font.dispose();
        }
    }

    public Button addEventListener(ButtonEventListener listener) {
        listeners.add(listener);
        return this;
    }

    public Button removeEventListener(ButtonEventListener listener) {
        listeners.removeValue(listener, true);
        return this;
    }

    public Button removeAllEventListener() {
        listeners.clear();
        return this;
    }

    public Button setState(State state) {
        this.state = state;
        return this;
    }

    public State getState() {
        return state;
    }

    public Button setTextureSimple(Texture textureSimple, boolean disposeAfterDeathOrRemove) {

        if ((this.textureSimple != null) && textureSimpleDisposeAfterDeathOrRemove) {
            this.textureSimple.dispose();
        }

        this.textureSimple = textureSimple;
        textureSimpleDisposeAfterDeathOrRemove = disposeAfterDeathOrRemove;

        return this;
    }

    public Button setTexturePressed(Texture texturePressed, boolean disposeAfterDeathOrRemove) {

        if ((this.texturePressed != null) && texturePressedDisposeAfterDeathOrRemove) {
            this.texturePressed.dispose();
        }

        this.texturePressed = texturePressed;
        texturePressedDisposeAfterDeathOrRemove = disposeAfterDeathOrRemove;
        return this;
    }

    public Texture getTextureSimple() {
        return textureSimple;
    }

    public Texture getTexturePressed() {
        return texturePressed;
    }

    public BitmapFont getFont() {
        return font;
    }

    public Button setFont(BitmapFont font, boolean disposeAfterDeathOrRemove) {

        if ((this.font != null) && fontDisposeAfterDeathOrRemove) {
            this.font.dispose();
        }


        this.font = font;
        fontDisposeAfterDeathOrRemove = disposeAfterDeathOrRemove;
        return this;
    }

    public String getText() {
        return text;
    }

    public Button setText(String text) {
        this.text = text;
        return this;
    }

    public boolean isTextureSimpleDisposeAfterDeathOrRemove() {
        return textureSimpleDisposeAfterDeathOrRemove;
    }

    public Button setTextureSimpleDisposeAfterDeathOrRemove(boolean textureSimpleDisposeAfterDeathOrRemove) {
        this.textureSimpleDisposeAfterDeathOrRemove = textureSimpleDisposeAfterDeathOrRemove;
        return this;
    }

    public boolean isTexturePressedDisposeAfterDeathOrRemove() {
        return texturePressedDisposeAfterDeathOrRemove;
    }

    public Button setTexturePressedDisposeAfterDeathOrRemove(boolean texturePressedDisposeAfterDeathOrRemove) {
        this.texturePressedDisposeAfterDeathOrRemove = texturePressedDisposeAfterDeathOrRemove;
        return this;
    }

    public boolean isFontDisposeAfterDeathOrRemove() {
        return fontDisposeAfterDeathOrRemove;
    }

    public Button setFontDisposeAfterDeathOrRemove(boolean fontDisposeAfterDeathOrRemove) {
        this.fontDisposeAfterDeathOrRemove = fontDisposeAfterDeathOrRemove;
        return this;
    }

    public interface ButtonEventListener {
        void onEvent(Button button, Event event);
    }
}
