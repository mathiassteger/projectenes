package com.mseb.projectenes.utilities;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.mseb.projectenes.model.entities.Goodie;
import com.mseb.projectenes.utilities.propertychanges.PropertyChangeListener;
import com.mseb.projectenes.utilities.propertychanges.PropertyChangeSupport;

import java.util.HashMap;

// SYNCHRONIZE THIS PLS
public class GoodieContainer extends HashMap<Fixture, Goodie> {
    PropertyChangeSupport addingPCS = new PropertyChangeSupport();
    PropertyChangeSupport removingPCS = new PropertyChangeSupport();

    @Override
    public Goodie put(Fixture fixture, Goodie goodie) {
        addingPCS.firePropertyChange(null, goodie);
        return super.put(fixture, goodie);
    }

    @Override
    public Goodie remove(Object o) {
        removingPCS.firePropertyChange(o, null);
        return super.remove(o);
    }

    public void subscribeToAdding(PropertyChangeListener l) {
        this.addingPCS.addPropertyChangeListener(l);
    }

    public void subscribeToRemoving(PropertyChangeListener l) {
        this.removingPCS.addPropertyChangeListener(l);
    }
}
