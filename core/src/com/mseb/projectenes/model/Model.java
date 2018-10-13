package com.mseb.projectenes.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mseb.projectenes.controls.home.HomeController;
import com.mseb.projectenes.utilities.propertychanges.PropertyChangeSupport;

public class Model {
    private static final Model INSTANCE = new Model();
    public static PropertyChangeSupport screenPCS = new PropertyChangeSupport();
    public static Screen screen;
    public static Skin skin;

    private Model() {
    }

    public static void libgdxInit() {
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        screen = new HomeController();
    }

    public static Model getInstance() {
        return INSTANCE;
    }

    public void setScreen(Screen screen) {

    }
}