package com.mseb.projectenes.controls.home;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mseb.projectenes.model.Model;

import static com.badlogic.gdx.math.Intersector.intersectSegmentCircle;
import static com.badlogic.gdx.math.Intersector.intersectSegmentCircleDisplace;
import static com.mseb.projectenes.model.Model.xcceleration;
import static com.mseb.projectenes.model.Model.xspeed;
import static com.mseb.projectenes.model.Model.ycceleration;

public class Testlui extends Actor {
    private Sprite image;
    private float imageAngle = 0;
    public Circle hitbox;
    private float width, height;
    private Vector2 displacement = new Vector2();


    public Testlui(float xpos, float ypos, float width, float height) {
        image = new Sprite(new Texture(Gdx.files.internal("ball.png")));
        image.setSize(width, height);
        image.setOriginCenter();
        setX(xpos);
        setY(ypos);
        image.setPosition(xpos, ypos);
        hitbox = new Circle(xpos + width / 2, ypos + height / 2, width / 2);

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

    @Override
    public void act(float delta) {

        if (iscolliding()) {


            Model.yspeed *= -0.9*Math.signum(displacement.y);                                       /** Damit es so wirkt als wäre es ein inelastischer Stoß */


            setY(getY() - (Math.abs(Model.yspeed)+ Model.xspeed + Model.addy / 2) * (Model.yspeed / Math.abs(Model.yspeed)));     /** Ballposition für ersten bounce moment anpassen */

            hitbox.x = getX() + getWidth() / 2;
            hitbox.y = getY() + getHeight() / 2;
            image.setPosition(getX(), getY());
            //Model.xspeed -= xcceleration;                                                                       /** war ein lustiges feedback für wenn man den ball "rollen lässt" */


        } else {
            setY(getY() - Model.yspeed);
            hitbox.x = getX() + getWidth() / 2;
            hitbox.y = getY() + getHeight() / 2;

            Model.yspeed += ycceleration;
            Model.xspeed += xcceleration;
            image.setPosition(getX(), getY());
        }
    }


    private boolean iscolliding() {
        for (int h = 0; h < Model.lineContainer.size(); h++) {
            for (int i = 0; i < Model.lineContainer.get(h).size() - 1; i++) {
                float dispstrength = intersectSegmentCircleDisplace(Model.lineContainer.get(h).get(i), Model.lineContainer.get(h).get(i + 1), new Vector2(hitbox.x, hitbox.y), hitbox.radius, displacement);


                if (dispstrength < Float.POSITIVE_INFINITY) {
                    System.out.println(this.displacement.angle());
                    /** Mein versuch den ball so zu verschieben, dass die nächste Linie nicht durch den ball geht.  */
                    Model.addy = Model.lineContainer.get(h).get(i + 1).y - Model.lineContainer.get(h).get(i).y;

                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        image.setRotation(imageAngle);
        imageAngle -= Model.xspeed;
        image.draw(batch);
    }
}
