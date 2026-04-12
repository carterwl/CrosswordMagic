package edu.jsu.mcis.cs408.crosswordmagic;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import edu.jsu.mcis.cs408.crosswordmagic.view.AbstractView;

public abstract class AbstractController {

    private PropertyChangeSupport propertyChangeSupport;

    public AbstractController() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void addView(AbstractView view) {
        propertyChangeSupport.addPropertyChangeListener(view);
    }

    public void removeView(AbstractView view) {
        propertyChangeSupport.removePropertyChangeListener(view);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}