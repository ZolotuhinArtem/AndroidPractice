package com.zolotukhin.picturegame.builder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.zolotukhin.picturegame.gameobject.Button;
import com.zolotukhin.picturegame.state.State;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class ButtonBuilder {
    private Button button;
    private float x, y, width, height;
    private Texture simple, pressed;
    private BitmapFont font;
    private Array<Button.ButtonEventListener> listeners;
    private String text;
    private State state;
    private boolean textureSimpleDisposeAfterDeathOrRemove,
            texturePressedDisposeAfterDeathOrRemove,
            fontDisposeAfterDeathOrRemove;

    public ButtonBuilder() {
        this.x = 0;
        this.y = 0;
        this.width = 256;
        this.height = 64;
        this.simple = null;
        this.pressed = null;
        this.listeners = new Array<>();
        this.text = "";
        this.font = null;
        this.texturePressedDisposeAfterDeathOrRemove = false;
        this.textureSimpleDisposeAfterDeathOrRemove = false;
        this.fontDisposeAfterDeathOrRemove = false;
        this.state = null;
        this.button = new Button(x, y, width, height);
    }

    public ButtonBuilder x(float x) {
        this.x = x;
        return this;
    }

    public ButtonBuilder y(float y) {
        this.y = y;
        return this;
    }

    public ButtonBuilder width(float width) {
        this.width = width;
        return this;
    }

    public ButtonBuilder height(float height) {
        this.height = height;
        return this;
    }

    public ButtonBuilder textureSimple(Texture texture, boolean disposeAfterDeathOrRemove) {
        this.simple = texture;
        this.textureSimpleDisposeAfterDeathOrRemove = disposeAfterDeathOrRemove;
        return this;
    }

    public ButtonBuilder texturePressed(Texture texture, boolean disposeAfterDeathOrRemove) {
        this.pressed = texture;
        this.texturePressedDisposeAfterDeathOrRemove = disposeAfterDeathOrRemove;
        return this;
    }

    public ButtonBuilder font(BitmapFont font, boolean disposeAfterDeathOrRemove) {
        this.font = font;
        this.fontDisposeAfterDeathOrRemove = disposeAfterDeathOrRemove;
        return this;
    }

    public ButtonBuilder text(String text) {
        this.text = text;
        return this;
    }

    public ButtonBuilder addEventListener(Button.ButtonEventListener listener) {
        this.listeners.add(listener);
        return this;
    }

    public ButtonBuilder state(State state) {
        this.state = state;
        return this;
    }

    public Button build() {
        button.setXY(x, y);
        button.setSize(this.width, this.height);
        if (font == null) {
            font = new BitmapFont();
        }
        button.setFont(font, this.fontDisposeAfterDeathOrRemove);
        button.setTexturePressed(this.pressed, this.texturePressedDisposeAfterDeathOrRemove);
        button.setTextureSimple(this.simple, this.textureSimpleDisposeAfterDeathOrRemove);
        button.setState(this.state);
        button.setText(this.text);
        for (Button.ButtonEventListener i : this.listeners) {
            button.addEventListener(i);
        }

        return button;
    }
}
