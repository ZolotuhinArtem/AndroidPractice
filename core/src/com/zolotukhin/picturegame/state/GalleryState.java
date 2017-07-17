package com.zolotukhin.picturegame.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.zolotukhin.picturegame.GameManager;
import com.zolotukhin.picturegame.model.GalleryEntry;
import com.zolotukhin.picturegame.model.GalleryRepository;
import com.zolotukhin.picturegame.model.JsonPictureRepository;
import com.zolotukhin.picturegame.model.PictureRepository;
import com.zolotukhin.picturegame.model.SimpleGalleryRepository;
import com.zolotukhin.picturegame.resource.ButtonTexture;

import java.util.List;

/**
 * Created by Nurislam on 13.07.2017.
 */

class GalleryState extends State {

    public static final float BUTTON_WIDTH = 1f;
    public static final float BUTTON_FONT_SIZE = 0.05f;
    public static final float BUTTON_MARGIN = 0.05f;
    public static final float LABEL_FONT_SIZE = 0.08f;

    private TextButton btnExit;
    private BitmapFont font, buttonFont;
    private Table menu;
    private Stage stage;
    private ScrollPane pane;
    private List<GalleryEntry> allPicture;
    private GalleryRepository galleryRepository;
    private PictureRepository pictureRepository;

    private TextureRegion btnTextureUp;
    private TextureRegion btnTextureDown;

    public GalleryState(final GameManager gsm) {
        super(gsm);

        float buttonFontSize = BUTTON_FONT_SIZE;
        float labelFontSize = LABEL_FONT_SIZE;
        float buttonWidth = BUTTON_WIDTH;
        float buttonMargin = BUTTON_MARGIN;
        ButtonTexture buttonTexture = gameManager.getResourceManager().getDefaultButtonTexture();
        float buttonHeight = (float) buttonTexture.getUp().getRegionHeight() / (float) buttonTexture.getUp().getRegionWidth()
                * buttonWidth;


        font = gameManager.getResourceManager()
                .getNewInstanceOfDefaultFont(labelFontSize * getUnit(), Color.WHITE);
        buttonFont = gameManager.getResourceManager()
                .getNewInstanceOfDefaultFont(buttonFontSize * getUnit(), Color.WHITE);


        pictureRepository = new JsonPictureRepository();
        galleryRepository = new SimpleGalleryRepository(pictureRepository);

        allPicture = galleryRepository.get();


        btnTextureUp = buttonTexture.getUp();
        btnTextureDown = buttonTexture.getDown();

        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.up = new TextureRegionDrawable(btnTextureUp);
        btnStyle.down = new TextureRegionDrawable(btnTextureDown);
        btnStyle.font = buttonFont;

        boolean isFirst = true;

        menu = new Table();
        menu.center().bottom();
        stage = new Stage();

        if (allPicture.size() > 0) {
            for (GalleryEntry i : allPicture) {

                String text = i.getPainter().getNames().get("en") + "\n" + i.getPicture().getNames().get("en");
                Button btnPicture = new TextButton(text, btnStyle);
                final GalleryEntry galleryEntry = i;

                btnPicture.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        gameManager.putParcel(PictureShowState.PARAM_GALLERY_ENTRY, galleryEntry);
                        gameManager.pushState(new PictureShowState(gameManager));
                    }
                });

                menu.add(btnPicture)
                        .width(buttonWidth * getUnit())
                        .height(buttonHeight * getUnit())
                        .padTop(!isFirst ? buttonMargin * getUnit() : 0);
                menu.row();
                isFirst = false;
            }
        } else {
            Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
            Label label = new Label("Empty!", labelStyle);
            label.setWrap(true);
            label.setAlignment(Align.center);
            menu.add(label)
                    .width(buttonWidth * getUnit())
                    .height(buttonHeight * getUnit())
                    .padTop(buttonMargin * getUnit());
            menu.row();
        }

        btnExit = new TextButton("Back", btnStyle);
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameManager.popState();
            }
        });

        menu.add(btnExit)
                .width(buttonWidth * getUnit())
                .height(buttonHeight * getUnit())
                .padTop(buttonMargin * getUnit());
        ;
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
    public void onResize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void onUpdate(float delta) {
        stage.act(delta);
    }

    @Override
    public void onRender(SpriteBatch batch) {
        super.onRender(batch);
        batch.begin();
        font.draw(batch, "GAME NAME!", 0, gameManager.getScreenHeight() / 4 * 3,
                gameManager.getScreenWidth(), Align.center, false);
        stage.draw();
        batch.end();
    }

    @Override
    public void onDispose() {
        font.dispose();
        stage.dispose();
    }
}
