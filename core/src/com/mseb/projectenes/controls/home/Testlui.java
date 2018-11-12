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


public class Testlui extends Actor {
    private Sprite image;
    private float imageAngle = 0;
    public Body body;
    private float width, height;


    public Testlui(float xpos, float ypos, float width, float height) {
        image = new Sprite(new Texture(Gdx.files.internal("ball.png")));
        image.setSize(width, height);
        image.setOriginCenter();
        setX(xpos);
        setY(ypos);
        image.setPosition(xpos, ypos);
        body = createBody(xpos + width / 2, ypos + height / 2, width / 2);
        setWidth(width);
        setHeight(height);

        this.width = width;
        this.height = height;

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

        def.position.set(xpos, ypos);
        def.fixedRotation = true;
        pBody = Model.world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        pBody.createFixture(shape, 1f);
        shape.dispose();
        return pBody;
    }

    public void inputUpdate(float delta) {
        float horizontalForce = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 1;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            System.out.println("up");
            body.applyForceToCenter(0, 3000, false);
        }

        body.setLinearVelocity(body.getLinearVelocity().x + horizontalForce * 5, body.getLinearVelocity().y);
    }

    @Override
    public void act(float delta) {
        inputUpdate(delta);
        float xpos = body.getPosition().x - width / 2;
        float ypos = body.getPosition().y - height / 2;
        setPosition(xpos, ypos);
        image.setPosition(xpos, ypos);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        image.setRotation(imageAngle);
        imageAngle -= body.getLinearVelocity().x / 10;
        image.draw(batch);
    }
}
