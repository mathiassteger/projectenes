package com.mseb.projectenes.controls.home;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mseb.projectenes.model.Model;
import com.mseb.projectenes.utilities.box2d.B2DConstants;

import static com.mseb.projectenes.controls.home.HomeController.camera;
import static com.mseb.projectenes.utilities.box2d.B2DConstants.PPM;


public class Testlui extends Actor {
    private Sprite image;
    private float imageAngle = 0;
    public Body body;
    public float radius;

    public Testlui(float xpos, float ypos, float radius) {
        image = new Sprite(new Texture(Gdx.files.internal("ball.png")));

        this.radius = radius;

        body = createBody(xpos, ypos, radius);
        image.setPosition(body.getPosition().x * PPM - radius, body.getPosition().y * PPM - radius);
        this.setPosition(body.getPosition().x * PPM - radius, body.getPosition().y * PPM - radius);
        image.setSize(radius * 2, radius * 2);
        this.setSize(radius * 2, radius * 2);

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("test");
            }
        });
    }

    private Body createBody(float xpos, float ypos, float radius) {
        Body pBody;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(xpos / PPM, ypos / PPM);
        def.fixedRotation = true;
        pBody = Model.world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / PPM);
        pBody.createFixture(shape, 1f);
        shape.dispose();
        return pBody;
    }

    public void inputUpdate(float delta) {
        float horizontalForce = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= .1f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += .1f;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            body.applyForceToCenter(0, 25, false);
        }

        body.setLinearVelocity(body.getLinearVelocity().x + horizontalForce * 5, body.getLinearVelocity().y);
    }

    @Override
    public void act(float delta) {
        inputUpdate(delta);
        float xpos = (body.getPosition().x * PPM - radius);
        float ypos = (body.getPosition().y * PPM - radius);
        setPosition(xpos, ypos);
        image.setPosition(xpos, ypos);
        image.setOriginCenter();
        image.setRotation(imageAngle);
        imageAngle -= body.getLinearVelocity().x / 2.5;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setProjectionMatrix(camera.combined); // DO THIS HERE OR DIE
        image.draw(batch);
    }
}
