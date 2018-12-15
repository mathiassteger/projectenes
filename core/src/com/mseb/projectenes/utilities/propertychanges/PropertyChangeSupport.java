package com.mseb.projectenes.utilities.propertychanges;

import java.util.ArrayList;
import java.util.List;

/**
 * GWT CAN'T HANDLE BEANS, REWRITE PCS-SYSTEM
 */

public class PropertyChangeSupport {
    List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

    public void addPropertyChangeListener(PropertyChangeListener theListener) {
        assert theListener != null;
        assert !listeners.contains(theListener);

        listeners.add(theListener);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        assert pcl != null && listeners.contains(pcl);

        listeners.remove(pcl);
    }

    public void firePropertyChange(Object oldValue, Object newValue) {
        for (PropertyChangeListener next : listeners) {
            next.propertyChange(oldValue, newValue);
        }
    }
}
