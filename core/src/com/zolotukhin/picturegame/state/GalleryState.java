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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.model.GalleryEntry;
import com.zolotukhin.picturegame.model.JsonPictureRepository;
import com.zolotukhin.picturegame.model.Painter;
import com.zolotukhin.picturegame.model.Picture;

import java.util.List;

/**
 * Created by Nurislam on 13.07.2017.
 */

class GalleryState extends State {

    public static final float BUTTON_DEFAULT_WIDTH = 0.9f;
    public static final float BUTTON_DEFAULT_HEIGHT = 0.27f;
    public static final float BUTTON_FONT_SIZE = 0.07f;
    public static final float BUTTON_MARGIN = 0.05f;
    public static final float LABEL_FONT_SIZE = 0.08f;

    private TextButton btnExit;
    private BitmapFont font, buttonFont;
    private Table menu;
    private Stage stage;
    private ScrollPane pane;
    private List<Picture> allPicture;

    private Texture btnTextureUp;
    private Texture btnTextureDown;

    public GalleryState(final GameManager gsm) {
        super(gsm);

        font = gameManager.getDefaultFont(LABEL_FONT_SIZE * getUnit(), Color.WHITE);
        buttonFont = gameManager.getDefaultFont(BUTTON_FONT_SIZE * getUnit(), Color.BLACK);

        allPicture = new JsonPictureRepository().getAll();
        btnTextureUp = new Texture("btn_simple.png");
        btnTextureDown = new Texture("btn_pressed.png");

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.up = new TextureRegionDrawable(new TextureRegion(btnTextureUp));
        btnStyle.down = new TextureRegionDrawable(new TextureRegion(btnTextureDown));
        btnStyle.font = buttonFont;

        boolean isFirst = true;

        menu = new Table();
        menu.bottom();
        stage = new Stage();

        if (allPicture != null || true) {
            for (Picture picture : allPicture) {
                Painter painter = new JsonPictureRepository().getPainterByPicture(picture);
                String text = painter.getNames().get("en") + "\n" + picture.getNames().get("en");
                Button btnPicture = new TextButton(text, btnStyle);
                final GalleryEntry galleryEntry = new GalleryEntry(painter,picture);
                btnPicture.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        gameManager.putParcel(PictureShowState.PARAM_GALLERY_ENTRY,galleryEntry);
                        gameManager.setState(new PictureShowState(gameManager));
                    }
                });
                menu.add(btnPicture)
                        .width(BUTTON_DEFAULT_WIDTH * getUnit())
                        .height(BUTTON_DEFAULT_HEIGHT * getUnit())
                        .padTop(!isFirst ? BUTTON_MARGIN * getUnit() : 0);
                menu.row();
                isFirst = false;
            }
            btnExit = new TextButton("Back", btnStyle);
            btnExit.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameManager.popState();
                }
            });
            menu.add(btnExit)
                    .width(BUTTON_DEFAULT_WIDTH * getUnit())
                    .height(BUTTON_DEFAULT_HEIGHT * getUnit())
                    .padTop(BUTTON_MARGIN * getUnit());;
        }
        menu.setFillParent(true);
        pane = new ScrollPane(menu);
        pane.setScrollingDisabled(true, false);
        pane.setFillParent(true);
        stage.addActor(pane);

    }

    @Override
    public void onShow() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void onUpdate(float delta) {
        stage.getViewport().update(gameManager.getScreenWidth(), gameManager.getScreenHeight(), true);
    }

    @Override
    public void onRender(SpriteBatch batch) {
        batch.begin();
        font.draw(batch, "GAME NAME! ", 0, gameManager.getScreenHeight() / 4 * 3,
                gameManager.getScreenWidth(), Align.center, false);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.end();
    }

    @Override
    public void onDispose() {
        font.dispose();
        stage.dispose();
    }
}
