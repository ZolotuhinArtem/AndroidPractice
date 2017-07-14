package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.model.Painter;
import com.zolotukhin.picturegame.model.Picture;
import com.zolotukhin.picturegame.state.gamestate.GameState;

/**
 * Created by Artem Zolotukhin on 7/13/17.
 */

public class PictureViewInfoState extends State {

    public static final float FONT_SIZE = 0.05f;

    public static final String PARAM_PICTURE = PictureViewInfoState.class.getName() + ":param_picture";
    public static final String PARAM_PAINTER = PictureViewInfoState.class.getName() + ":param_painter";
    public static final String PARAM_RIGHT = PictureViewInfoState.class.getName() + ":param_right";

    private Stage stage;

    private Picture picture;
    private Painter painter;
    private Boolean isRight;

    private BitmapFont font;


    public PictureViewInfoState(GameManager gameManager) {
        super(gameManager);

        loadParcel();

        font = gameManager.getDefaultFont(FONT_SIZE * getUnit(), Color.WHITE);

        stage = new Stage(new ScreenViewport());
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);



        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);
        Label label = new Label(generateText(), style);
        label.setWrap(true);
        label.setAlignment(Align.center);
        table.add(label);

    }

    private CharSequence generateText() {
        String text = "";
        if (isRight) {
            text += "YES\n\n";
        } else {
            text += "NO\n\n";
        }

        text += "Painter is\n" + painter.getNames().get("en");
        text += "\nPicture is\n" + picture.getNames().get("en");
        text += "\n\nTap to\ncontinue";
        return text;
    }

    private void loadParcel() {
        picture = (Picture) gameManager.getParcel(PARAM_PICTURE);
        painter = (Painter) gameManager.getParcel(PARAM_PAINTER);
        isRight = (Boolean) gameManager.getParcel(PARAM_RIGHT);
    }

    @Override
    public void onDispose() {
        font.dispose();
        stage.dispose();
    }

    @Override
    public void onUpdate(float delta) {
        stage.act(delta);
        if (isTouched()) {
            gameManager.putParcel(GameState.PARAM_PICTURE_CHOOSE_RESULT, isRight);
            gameManager.popState();
        }
    }

    @Override
    public void onResize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void onRender(SpriteBatch batch) {
        stage.draw();
    }
}
