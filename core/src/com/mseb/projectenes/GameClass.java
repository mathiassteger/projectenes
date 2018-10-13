package com.mseb.projectenes;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mseb.projectenes.logging.CustomLogger;
import com.mseb.projectenes.model.Model;
import com.mseb.projectenes.utilities.propertychanges.PropertyChangeListener;

public class GameClass extends Game {


    @Override
    public void create() {
        Gdx.app.setApplicationLogger(new CustomLogger());
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Model.libgdxInit();
        setScreen(Model.screen);
        Model.screenPCS.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(Object oldValue, Object newValue) {
                setScreen((Screen) newValue);
            }
        });
    }

    @Override
    public void render() {
        screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void dispose() {
        screen.dispose();
    }
}
