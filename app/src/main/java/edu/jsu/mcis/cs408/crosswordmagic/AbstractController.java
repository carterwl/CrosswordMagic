package edu.jsu.mcis.cs408.crosswordmagic;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import edu.jsu.mcis.cs408.crosswordmagic.view.AbstractView;

public abstract class AbstractController {

    protected List<AbstractView> views = new ArrayList<>();

    public void addView(AbstractView view) {
        views.add(view);
    }

    protected void setModelProperty(String propertyName, Object newValue) {
        PropertyChangeEvent evt =
                new PropertyChangeEvent(this, propertyName, null, newValue);

        for (AbstractView view : views) {
            view.propertyChange(evt);
        }
    }
}