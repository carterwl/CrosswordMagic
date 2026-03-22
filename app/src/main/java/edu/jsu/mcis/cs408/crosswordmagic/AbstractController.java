package edu.jsu.mcis.cs408.crosswordmagic;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class AbstractController {

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addView(PropertyChangeListener view) {
        pcs.addPropertyChangeListener(view);
    }

    public void removeView(PropertyChangeListener view) {
        pcs.removePropertyChangeListener(view);
    }

    protected void setModelProperty(String propertyName, Object newValue) {
        pcs.firePropertyChange(propertyName, null, newValue);
    }
}