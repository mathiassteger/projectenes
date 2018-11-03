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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mseb.projectenes.model.Model;

import java.util.ArrayList;


public class HomeController implements Screen, InputProcessor {
    Stage stage;
    InputMultiplexer inputMultiplexer;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    Testlui luidetest;
    Viewport screenViewport;
    /**
     * Signals which lines touchpoints are currently added to
     */
    int lineCounter = 0;
    /**
     * Signals whether the screen is currently pressed or not
     */
    boolean isPressed = false;
    float width = 500, height = 500;


    public HomeController() {
        inputMultiplexer = new InputMultiplexer();

        screenViewport = new ExtendViewport(width, height);
        stage = new Stage(screenViewport);

        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);


        initListeners();

        this.luidetest = new Testlui(0.0f, 400f, 30, 30);
        this.stage.addActor(luidetest);
        Model.lineContainer.add(new ArrayList<Vector2>());
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

        speedAdjust();

    }


    public void speedAdjust(){
        for (ArrayList<Vector2> line: Model.lineContainer){
            for(Vector2 point: line){
                point.x -= Model.yspeed;
            }
        }
    }


    /**
     * Calculates camera-coordinates to world-coordinates
     *
     * @param x
     * @param y
     * @return A 2D Vector of the now unprojected coordinates x and y
     */
    private Vector2 getUnprojectedPoint(float x, float y) {
        Vector3 coordinates3D = screenViewport.getCamera().unproject(new Vector3(x, y, 0));
        return new Vector2(coordinates3D.x, coordinates3D.y);
    }

    /**
     * Adds a touchpoint to the list with the number {@link HomeController#lineCounter} to touchpoints {@link Model#lineContainer}
     */
    private void checkForAndAddTouchPoint() {
        if (isPressed) {
            Model.lineContainer.get(lineCounter).add(getUnprojectedPoint(Gdx.input.getX(), Gdx.input.getY()));
        }
    }

    /**
     * Draws lines according to lines formed from the list of touchpoints {@link Model#lineContainer}.
     * Every point from one list in {@link Model#lineContainer} is connected to the next of the same list.
     */
    private void drawLines() {
        for (int h = 0; h < Model.lineContainer.size(); h++) {
            for (int i = 0; i < Model.lineContainer.get(h).size() - 1; i++) {
                shapeRenderer.setProjectionMatrix(screenViewport.getCamera().combined);
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.line(Model.lineContainer.get(h).get(i), Model.lineContainer.get(h).get(i + 1));
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
        Model.lineContainer.add(new ArrayList<Vector2>());
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
