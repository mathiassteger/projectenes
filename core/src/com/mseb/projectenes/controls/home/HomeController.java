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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mseb.projectenes.model.Model;

import java.util.ArrayList;

import static com.mseb.projectenes.utilities.box2d.B2DConstants.PPM;


public class HomeController implements Screen, InputProcessor {
    private Stage stage;
    private InputMultiplexer inputMultiplexer;
    private ShapeRenderer shapeRenderer;
    private Body roof, floor;
    public static OrthographicCamera camera;
    // LUIDETEST MUSS EIGENTLICH AUS DIESER KLASSE RAUS...
    private Testlui luidetest;
    private Box2DDebugRenderer b2dr;

    /**
     * Signals which lines touchpoints are currently added to
     */
    private int lineCounter = 0;
    /**
     * Signals whether the screen is currently pressed or not
     */
    private boolean isPressed = false;
    float width = Gdx.graphics.getWidth();
    float height = Gdx.graphics.getHeight();
    private float scale = 1f;

    public HomeController() {
        inputMultiplexer = new InputMultiplexer();
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, width / scale, height / scale);

        stage = new Stage();

        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.BLACK);
        initListeners();

        Model.world = new World(new Vector2(0, -3.81f), false);
        b2dr = new Box2DDebugRenderer();
        createCollisionListener();

        this.luidetest = new Testlui(20f, 1f, 15);
        this.stage.addActor(luidetest);
        Model.lineContainer.add(new ArrayList<Vector2>());
        floor = createKinematicLine(new Vector2(-2300 / PPM, 0 / PPM), new Vector2(2300 / PPM, 0 / PPM));
        roof = createKinematicLine(new Vector2(-2300 / PPM, 500 / PPM), new Vector2(2300 / PPM, 500 / PPM));
        luidetest.body.applyForceToCenter(new Vector2(5000, 200), false);
    }

    private void createCollisionListener() {
        Model.world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Gdx.app.log("beginContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }

        });
    }


    public void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        position.x = luidetest.body.getPosition().x * PPM;
        //position.y = luidetest.body.getPosition().y * PPM;
        camera.position.set(position);

        camera.update();
    }

    private Body createKinematicLine(Vector2 v1, Vector2 v2) {
        Body pBody;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.KinematicBody;
        def.fixedRotation = true;

        pBody = Model.world.createBody(def);

        EdgeShape shape = new EdgeShape();
        shape.set(v1, v2);
        pBody.createFixture(shape, 1.0f);
        shape.dispose();

        return pBody;
    }

    private Body createStaticLine(Vector2 v1, Vector2 v2) {
        Body pBody;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.StaticBody;
        def.fixedRotation = true;

        pBody = Model.world.createBody(def);

        EdgeShape shape = new EdgeShape();
        shape.set(v1, v2);
        pBody.createFixture(shape, 1.0f);
        shape.dispose();

        return pBody;
    }

    private void initListeners() {

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(127, 127, 127, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        Gdx.gl.glLineWidth(2);

        checkForAndAddTouchPoint();

        drawLines();

        b2dr.render(Model.world, camera.combined.scl(PPM));

    }

    private void update(float delta) {
        Model.world.step(1 / 60f, 6, 2);
        cameraUpdate(delta);


        floor.setLinearVelocity(luidetest.body.getLinearVelocity().x, 0);
        roof.setLinearVelocity(luidetest.body.getLinearVelocity().x, 0);
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
     * Adds a touchpoint to the list with the number {@link HomeController#lineCounter} to touchpoints {@link Model#lineContainer}
     */
    private void checkForAndAddTouchPoint() {
        if (isPressed) {
            ArrayList<Vector2> currentLine = Model.lineContainer.get(lineCounter);
            currentLine.add(getUnprojectedPoint(Gdx.input.getX(), Gdx.input.getY()));

            if (currentLine.size() > 1) {
                Vector2 v1 = currentLine.get(0);
                Vector2 v2 = currentLine.get(1);
                createStaticLine((new Vector2(v1.x / PPM, v1.y / PPM)), new Vector2(v2.x / PPM, v2.y / PPM));
                currentLine.remove(0);
            }
        }
    }

    /**
     * Draws lines according to lines formed from the list of touchpoints {@link Model#lineContainer}.
     * Every point from one list in {@link Model#lineContainer} is connected to the next of the same list.
     */
    private void drawLines() {
        for (int h = 0; h < Model.lineContainer.size(); h++) {
            for (int i = 0; i < Model.lineContainer.get(h).size() - 1; i++) {
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
