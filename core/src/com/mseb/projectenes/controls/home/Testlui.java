package com.mseb.projectenes.controls.home;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mseb.projectenes.model.Model;

import static com.badlogic.gdx.math.Intersector.intersectSegmentCircle;

public class Testlui extends Actor {
    private Texture image;
    private Circle hitbox;
    private float width, height;


    public Testlui (float xpos, float ypos, float width, float height){
        image = new Texture(Gdx.files.internal("ball.png"));
        setX(xpos);
        setY(ypos);
        hitbox = new Circle(xpos, ypos, width/2);

        setWidth(width);
        setHeight(height);

        this.width = width;
        this.height = height;

        this.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("test");
            }
        });
    }

    @Override
    public void act(float delta){

        if (iscolliding()){

        } else {
            setY(getY()-Model.yspeed);
            hitbox.x = getX();
            hitbox.y = getY();

        }

    }


    private boolean iscolliding(){
        for (int h = 0; h < Model.lineContainer.size(); h++) {
            for (int i = 0; i < Model.lineContainer.get(h).size() - 1; i++) {
                if (intersectSegmentCircle(Model.lineContainer.get(h).get(i), Model.lineContainer.get(h).get(i + 1), new Vector2(getY(), getY()), hitbox.radius)){
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(image, this.getX(), this.getY(), width, height);

    }
}
