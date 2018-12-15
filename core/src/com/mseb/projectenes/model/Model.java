package com.mseb.projectenes.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mseb.projectenes.controls.home.HomeController;
import com.mseb.projectenes.model.entities.Goodie;
import com.mseb.projectenes.model.entities.Testlui;
import com.mseb.projectenes.model.entities.LuiWorld;
import com.mseb.projectenes.utilities.GoodieContainer;
import com.mseb.projectenes.utilities.propertychanges.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Model {
    private static final Model INSTANCE = new Model();
    public static PropertyChangeSupport screenPCS = new PropertyChangeSupport();
    public static Screen screen;
    public static Skin skin;
    public static Testlui testlui;
    public static volatile ArrayList<ArrayList<Vector2>> lineContainer = new ArrayList();
    public static LuiWorld world;
    public static GoodieContainer goodies = new GoodieContainer();

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
        Model.screen = screen;
        screenPCS.firePropertyChange(null, screen);
    }
}