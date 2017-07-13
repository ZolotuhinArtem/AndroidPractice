package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.model.JsonPictureRepository;
import com.zolotukhin.picturegame.model.Painter;
import com.zolotukhin.picturegame.model.Picture;
import com.zolotukhin.picturegame.model.PictureRepository;
import com.zolotukhin.picturegame.utils.TextureUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Artem Zolotukhin on 7/13/17.
 */

public class PictureChooseState extends State {

    public static final int DEFAULT_PICTURE_COUNT = 3;

    public static final float FONT_SIZE = 0.035f;

    public static final float MARGIN_SUMMARY = 0.1f;

    public static final String PARAM_PAINTER = PictureChooseState.class.getName() + ":param_painter";

    private PictureRepository pictureRepository;

    private Painter rightPainter;

    private Texture[] texturePictures;

    private TextureRegion[] thumbnails;

    private Button[] buttons;

    private Stage stage;

    private Label label;

    private BitmapFont bitmapFont;

    private int rightPictureIndex;

    private Picture rightPicture;

    public PictureChooseState(GameManager gsm) {
        super(gsm);
        pictureRepository = new JsonPictureRepository();
        rightPainter = loadPainter();

        rightPicture = rightPainter.getPictures().get(MathUtils.random(0, rightPainter.getPictures().size() - 1));

        List<Painter> otherPainters = pictureRepository.getAllPainters();
        otherPainters.remove(rightPainter);

        List<Picture> otherPictures = new ArrayList<>();
        for (Painter i : otherPainters) {
            otherPictures.addAll(i.getPictures());
        }
        Collections.shuffle(otherPictures, new Random(System.currentTimeMillis()));

        final List<Picture> pictures = new ArrayList<>(DEFAULT_PICTURE_COUNT);
        pictures.add(rightPicture);
        for (Picture i : otherPictures) {
            if (pictures.size() < DEFAULT_PICTURE_COUNT) {
                pictures.add(i);
            } else {
                break;
            }
        }
        Collections.shuffle(pictures, new Random(System.currentTimeMillis()));

        rightPictureIndex = pictures.indexOf(rightPicture);

        fillTextures(pictures);
        fillThumbnails(texturePictures);

        generateButtons();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        bitmapFont = gameManager.getDefaultFont(FONT_SIZE * getUnit(), Color.WHITE);
        Label.LabelStyle style = new Label.LabelStyle(bitmapFont, Color.WHITE);
        label = new Label("Chose picture of " + rightPainter.getNames().get("en")
                + "\nIf you want see more, click on any picture", style);
        label.setWrap(true);
        label.setAlignment(Align.center);
        table.add(label)
                .colspan(DEFAULT_PICTURE_COUNT)
                .expandX()
                .maxWidth(gsm.getScreenWidth())
                .padBottom(MARGIN_SUMMARY * getUnit());
        table.row();
        float pad = (MARGIN_SUMMARY / (DEFAULT_PICTURE_COUNT + 1)) * getUnit();
        float btnSize = ((1 - MARGIN_SUMMARY) / DEFAULT_PICTURE_COUNT) * getUnit();

        for (int i = 0; i < buttons.length; i++) {
            table.add(buttons[i])
                    .width(btnSize)
                    .height(btnSize)
                    .padLeft(i == 0 ? pad : 0)
                    .padRight(i == buttons.length - 1 ? pad : 0);
            final int ii = i;
            final Picture picture = pictures.get(i);
            final Painter painter = pictureRepository.getPainterByPicture(picture);

            buttons[i].addListener(new EventListener() {
                @Override
                public boolean handle(Event event) {
                    handlePictureButton(picture, painter, ii == rightPictureIndex);
                    return false;
                }
            });
        }
    }

    private void handlePictureButton(Picture picture, Painter painter, Boolean isRight) {

        gameManager.putParcel(PictureViewState.PARAM_PICTURE, picture);
        gameManager.putParcel(PictureViewState.PARAM_PAINTER, painter);
        gameManager.putParcel(PictureViewState.PARAM_RIGHT, isRight);
        gameManager.pushState(new PictureViewState(gameManager));
    }

    private void generateButtons() {
        buttons = new Button[thumbnails.length];
        for (int i = 0; i < thumbnails.length; i++) {

            TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(thumbnails[i]);

            Button.ButtonStyle style = new Button.ButtonStyle();
            style.up = textureRegionDrawable;
            style.down = textureRegionDrawable;
            buttons[i] = new Button(style);
        }
    }

    private void fillTextures(List<Picture> pictures) {

        texturePictures = new Texture[pictures.size()];

        int index = 0;
        for (Picture i : pictures) {
            texturePictures[index++] = new Texture(i.getPath());
        }
    }

    private void fillThumbnails(Texture[] textures) {

        thumbnails = new TextureRegion[textures.length];

        TextureUtils textureUtils = new TextureUtils();

        for (int i = 0; i < textures.length; i++) {
            thumbnails[i] = textureUtils.getSquareThumbnail(textures[i]);
        }
    }

    private Painter loadPainter() {

        return (Painter) gameManager.getParcel(PARAM_PAINTER);
    }

    @Override
    public void onResize(int width, int height) {
        stage.getViewport().update(width, height);
        super.onResize(width, height);
    }

    @Override
    public void onUpdate(float delta) {
        stage.act(delta);
    }

    @Override
    public void onRender(SpriteBatch batch) {
        camera.update();
        stage.draw();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onShow() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void onDispose() {
        for (int i = 0; i < DEFAULT_PICTURE_COUNT; i++) {
            texturePictures[i].dispose();
        }
        bitmapFont.dispose();
        stage.dispose();
    }
}
