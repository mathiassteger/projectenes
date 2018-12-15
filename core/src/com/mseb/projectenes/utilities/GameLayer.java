package com.mseb.projectenes.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mseb.projectenes.model.Model;
import com.mseb.projectenes.model.entities.Goodie;
import com.mseb.projectenes.utilities.propertychanges.PropertyChangeListener;

import static com.mseb.projectenes.utilities.box2d.B2DConstants.PPM;

public class GameLayer extends Group {
    public GameLayer() {
        this.addActor(Model.testlui);
        initListeners();
    }



    private void initListeners() {
        Model.goodies.subscribeToAdding(new PropertyChangeListener() {
            @Override
            public void propertyChange(Object oldValue, Object newValue) {
                Gdx.app.debug("", "Adding goodie: " + (Goodie) newValue);
                Gdx.app.debug("", "Goodie x:" + ((Goodie) newValue).getX() * PPM);
                GameLayer.this.addActor((Goodie) newValue);
            }
        });

        Model.goodies.subscribeToRemoving(new PropertyChangeListener() {
            @Override
            public void propertyChange(Object oldValue, Object newValue) {
                GameLayer.this.getChildren().removeValue((Goodie) oldValue, false);
            }
        });
    }
}
