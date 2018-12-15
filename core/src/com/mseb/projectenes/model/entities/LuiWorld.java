package com.mseb.projectenes.model.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.mseb.projectenes.model.Model;
import com.mseb.projectenes.utilities.propertychanges.PropertyChangeListener;
import com.mseb.projectenes.utilities.propertychanges.PropertyChangeSupport;

public class LuiWorld {
    World world;
    PropertyChangeSupport goodiePCS = new PropertyChangeSupport();

    public LuiWorld(Vector2 gravity, Boolean doSleep) {
        this.world = new World(gravity, doSleep);
        this.createCollisionListener();
    }

    private void createCollisionListener() {
        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                // Check if Goodie somehow
                if (true) {
                    Gdx.app.debug("", "Firing GoodiePropertyChange");
                    goodiePCS.firePropertyChange(null, 50);
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }

    public World getWorld() {
        return this.world;
    }

    public void subscribeToGoodiePCS(PropertyChangeListener l) {
        goodiePCS.addPropertyChangeListener(l);
    }
}
