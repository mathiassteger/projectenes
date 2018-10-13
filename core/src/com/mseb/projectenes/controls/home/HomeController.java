package com.mseb.projectenes.controls.home;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;


public class HomeController implements Screen, GestureDetector.GestureListener {
    Stage stage;
    OrthographicCamera camera;
    InputMultiplexer inputMultiplexer;
    ArrayList<ArrayList<Vector3>> polinePoints = new ArrayList();
    int currentLineIndex = 0;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    boolean firstPan = true;
    testlui luidetest;

    float width = 1000, height = 1000;
    //Model.map.getPixelWidth(), height = Model.map.getPixelHeight();
    double zoomDelta = 0.02;

    public HomeController() {
        inputMultiplexer = new InputMultiplexer();
        camera = new OrthographicCamera(width, height);
        camera.position.set(width / 2, height / 2, 0);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

        Viewport screenViewport = new StretchViewport(width, height, camera);
        stage = new Stage(screenViewport);

        inputMultiplexer.addProcessor(new GestureDetector(this));
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);


        initListeners();
        this.luidetest = new testlui(0.0f, 0.0f, 100, 100);
        this.stage.addActor(luidetest);
        polinePoints.add(new ArrayList<Vector3>());
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

        Gdx.app.debug("", "" + polinePoints.size());
        for (int h = 0; h < polinePoints.size(); h++) {
            for (int i = 0; i < polinePoints.get(h).size() - 1; i++) {
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.setColor(Color.BLACK);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.line(polinePoints.get(h).get(i), polinePoints.get(h).get(i + 1));
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
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        Vector3 coordinates3D = camera.unproject(new Vector3(x, y, 0));
        polinePoints.get(currentLineIndex).add(coordinates3D);
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        polinePoints.add(new ArrayList<Vector3>());
        currentLineIndex++;
        return true;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    @Override
    public void dispose() {
        Gdx.app.debug("", "disposing");
        stage.dispose();
    }
}
