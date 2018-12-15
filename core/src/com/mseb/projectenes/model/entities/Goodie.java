package com.mseb.projectenes.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mseb.projectenes.model.Model;

import static com.mseb.projectenes.utilities.box2d.B2DConstants.PPM;

public class Goodie extends Actor {
    private Sprite image;
    private float imageAngle = 0;
    public Body body;
    public float radius;

    public Goodie(float xpos, float ypos, float radius) {
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

        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(xpos / PPM, ypos / PPM);

        def.fixedRotation = true;
        def.bullet = true;
        def.angularDamping = 0.9f;
        def.linearDamping = 0.09f;

        CircleShape circle = new CircleShape();

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.000001f;
        fixtureDef.restitution = 0.6f;
        fixtureDef.shape = circle;

        pBody = Model.world.getWorld().createBody(def);

        circle.setRadius(radius / PPM);
        pBody.createFixture(fixtureDef);
        circle.dispose();
        return pBody;
    }
}
