package com.mseb.projectenes.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mseb.projectenes.controls.home.HomeController;
import com.mseb.projectenes.utilities.propertychanges.PropertyChangeSupport;

import java.util.ArrayList;

public class Model {
    private static final Model INSTANCE = new Model();
    public static PropertyChangeSupport screenPCS = new PropertyChangeSupport();
    public static Screen screen;
    public static Skin skin;
    public static float xspeed = .2f, yspeed = 1f;
    public static volatile ArrayList<ArrayList<Vector2>> lineContainer = new ArrayList();


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
        this.screen = screen;
        screenPCS.firePropertyChange(null, this.screen);

    }
}