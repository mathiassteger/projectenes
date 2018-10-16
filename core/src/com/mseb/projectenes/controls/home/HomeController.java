package com.mseb.projectenes.controls.home;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


public class HomeController implements Screen, InputProcessor {
    Stage stage;
    OrthographicCamera camera;
    InputMultiplexer inputMultiplexer;
    ArrayList<ArrayList<Vector2>> lineContainer = new ArrayList();
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    testlui luidetest;
    int pan = 10;
    /**
     * Signals which lines touchpoints are currently added to
     */
    int lineCounter = 0;
    /**
     * Signals whether the screen is currently pressed or not
     */
    boolean isPressed = false;
    float width = 3000, height = 500;


    public HomeController() {
        inputMultiplexer = new InputMultiplexer();
        camera = new OrthographicCamera(500, height);
        camera.position.set(0, 0, 0);
        camera.update();

        Viewport screenViewport = new StretchViewport(width, height, camera);
        stage = new Stage(screenViewport);

        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);


        initListeners();

        this.luidetest = new testlui(0.0f, 0.0f, 100, 100);
        this.stage.addActor(luidetest);
        lineContainer.add(new ArrayList<Vector2>());
    }

    private void initListeners() {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(127, 127, 127, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        Gdx.gl.glLineWidth(2);

        checkForAndAddTouchPoint();

        drawLines();

        camera.position.x += pan;
    }

    /**
     * Calculates camera-coordinates to world-coordinates
     *
     * @param x
     * @param y
     * @return A 2D Vector of the now unprojected coordinates x and y
     */
    private Vector2 getUnprojectedPoint(float x, float y) {
        Vector3 coordinates3D = camera.unproject(new Vector3(x, y, 0));
        return new Vector2(coordinates3D.x, coordinates3D.y);
    }

    /**
     * Adds a touchpoint to the list with the number {@link HomeController#lineCounter} to touchpoints {@link HomeController#lineContainer}
     */
    private void checkForAndAddTouchPoint() {
        if (isPressed) {
            this.lineContainer.get(lineCounter).add(getUnprojectedPoint(Gdx.input.getX(), Gdx.input.getY()));
        }
    }

    /**
     * Draws lines according to lines formed from the list of touchpoints {@link HomeController#lineContainer}.
     * Every point from one list in {@link HomeController#lineContainer} is connected to the next of the same list.
     */
    private void drawLines() {
        for (int h = 0; h < lineContainer.size(); h++) {
            for (int i = 0; i < lineContainer.get(h).size() - 1; i++) {
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.line(lineContainer.get(h).get(i), lineContainer.get(h).get(i + 1));
                shapeRenderer.end();
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        this.isPressed = true;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        this.isPressed = false;
        this.lineContainer.add(new ArrayList<Vector2>());
        this.lineCounter++;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void dispose() {
        Gdx.app.debug("", "disposing");
        stage.dispose();
    }
}
