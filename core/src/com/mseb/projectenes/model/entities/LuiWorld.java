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

                if (Model.goodies.containsKey(fixtureA)) {
                    //Gdx.app.debug("", "Firing GoodiePropertyChange");
                    goodiePCS.firePropertyChange(Model.goodies.get(fixtureA), null);
                } else if (Model.goodies.containsKey(fixtureB)) {
                    //Gdx.app.debug("", "Firing GoodiePropertyChange");
                    goodiePCS.firePropertyChange(Model.goodies.get(fixtureB), null);
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
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
