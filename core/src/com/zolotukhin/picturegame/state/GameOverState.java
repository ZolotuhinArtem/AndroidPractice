package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.model.UserInfoRepository;
import com.zolotukhin.picturegame.model.UserInfoRepositoryImpl;
import com.zolotukhin.picturegame.resource.ButtonTexture;

/**
 * Created by Artem Zolotukhin on 7/11/17.
 */

public class GameOverState extends State {

    public static final float LABEL_FONT_SIZE = 0.07f;
    public static final float EXIT_FONT_SIZE = 0.05f;
    public static final String POINTS_KEY = GameOverState.class.getName() + ":points_key";

    public static final float BUTTON_REFRESH_HEIGHT = 0.15f;
    public static final float BUTTON_REFRESH_WIDTH = 0.15f;
    public static final float BUTTON_EXIT_WIDTH = 1f;

    public static final float MARGIN = 0.05f;

    private int quantityPoints;
    private long record;

    private BitmapFont font;
    private BitmapFont buttonFont;

    private Texture textureButtonRefresh;

    private Stage stage;

    private UserInfoRepository userInfoRepository;


    public GameOverState(GameManager gsm) {
        super(gsm);
        ButtonTexture buttonTexture = gameManager.getResourceManager().getDefaultButtonTexture();

        float margin = MARGIN;
        float labelFontSize = LABEL_FONT_SIZE;
        float exitFontSize = EXIT_FONT_SIZE;
        float buttonRefreshHeight = BUTTON_REFRESH_HEIGHT;
        float buttonRefreshWidth = BUTTON_REFRESH_WIDTH;
        float buttonExitWidth = BUTTON_EXIT_WIDTH;
        float buttonExitHeight = (float) buttonTexture.getUp().getRegionHeight() /
                (float) buttonTexture.getUp().getRegionWidth() * buttonExitWidth;


        font = gameManager.getResourceManager()
                .getNewInstanceOfDefaultFont(labelFontSize * getUnit(), Color.WHITE);
        buttonFont = gameManager.getResourceManager().getNewInstanceOfDefaultFont(exitFontSize * getUnit(), Color.WHITE);
        textureButtonRefresh = new Texture("refresh.png");

        userInfoRepository = new UserInfoRepositoryImpl();

        handleQuantityPoints();

        stage = new Stage(new ScreenViewport());
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        String text = "GAME OVER!" +
                "\nYour points: " + quantityPoints + "\nRecord: " + record;
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(buttonTexture.getUp());
        buttonStyle.down = new TextureRegionDrawable(buttonTexture.getDown());
        buttonStyle.font = buttonFont;

        Button.ButtonStyle refreshStyle = new Button.ButtonStyle();
        refreshStyle.up = new TextureRegionDrawable(new TextureRegion(textureButtonRefresh));
        refreshStyle.down = new TextureRegionDrawable(new TextureRegion(textureButtonRefresh));


        Label label = new Label(text, labelStyle);
        label.setWrap(true);
        label.setAlignment(Align.center);

        TextButton buttonExit = new TextButton("Exit", buttonStyle);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.popState();
            }
        });

        Button buttonRefresh = new Button(refreshStyle);
        buttonRefresh.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                newGame();
            }
        });

        table.add(label)
                .padBottom(margin * getUnit());
        table.row();
        table.add(buttonRefresh)
                .width(buttonRefreshWidth * getUnit())
                .height(buttonRefreshHeight * getUnit())
                .padBottom(margin * getUnit());
        table.row();
        table.add(buttonExit)
                .width(buttonExitWidth * getUnit())
                .height(buttonExitHeight * getUnit());
        table.row();
    }

    private void newGame() {
        gameManager.setState(new com.zolotukhin.picturegame.state.gamestate.GameState(gameManager));
    }

    private void handleQuantityPoints() {
        quantityPoints = (int) gameManager.getParcel(POINTS_KEY);
        record = userInfoRepository.getRecord();
        if (quantityPoints > record) {
            record = quantityPoints;
            userInfoRepository.writeRecord(record);
        }
    }


    @Override
    public void onShow() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void onUpdate(float delta) {
        stage.act(delta);
    }

    @Override
    public void onResize(int width, int height) {
        stage.getViewport().update(gameManager.getScreenWidth(), gameManager.getScreenHeight());
    }

    @Override
    public void onRender(SpriteBatch batch) {
        super.onRender(batch);
        batch.begin();
        stage.draw();
        batch.end();
    }


    @Override
    public void onDispose() {
        stage.dispose();
        font.dispose();
        buttonFont.dispose();
        textureButtonRefresh.dispose();
    }
}
