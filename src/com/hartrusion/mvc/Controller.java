/*
 * The MIT License
 *
 * Copyright 2025 Viktor Alexander Hartung.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.hartrusion.mvc;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Implements some interpretation of the MVC pattern.
 * <p>
 * Will redirect all received property changes from a model as a component
 * update to an UpdateReceiver. An UpdateReceiver is a view-specialized class
 * that will, for example, put the Updates into the event queue, depending on
 * what the view architecture ist.
 * <p>
 * The view sends user actions to this controller, the controller will offer
 * those as action commands in a concurrent queue to the model.
 *
 * @author Viktor Alexander Hartung
 */
public class Controller implements ViewerController, ModelListener {

    private ModelManipulation model;

    /**
     * Holds a list of all registered update receivers. This is an instance that
     * will forward the updates to the view and is dependend on the viewer
     * implementation.
     */
    private final List<UpdateReceiver> updaters = new ArrayList<>();

    private final Map<String, Object> lastPropertyChanges = new HashMap<>();

    /**
     * Actions from views are getting stored in this queue. The model has to
     * request the actions from this view to process them.
     */
    private final ConcurrentLinkedQueue<ActionCommand> pendingActions
            = new ConcurrentLinkedQueue<>();

    // called from view
    @Override
    public void userAction(ActionCommand evt) {
        pendingActions.offer(evt);
        model.updateNotification(evt.getPropertyName());
    }

    @Override
    public void fireActions() {
        ActionCommand evt;
        while (!pendingActions.isEmpty()) {
            evt = pendingActions.poll();
            model.handleAction(evt);
        }
    }

    // To be called from the model
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        for (UpdateReceiver v : updaters) {
            v.updateComponent(evt);
        }
        lastPropertyChanges.put(evt.getPropertyName(), evt.getNewValue());
    }

    // To be called from the model
    @Override
    public void propertyChange(String propertyName, Object newValue) {
        for (UpdateReceiver v : updaters) {
            v.updateComponent(propertyName, newValue);
        }
    }

    // To be called from the model
    @Override
    public void propertyChange(String propertyName, double newValue) {
        for (UpdateReceiver v : updaters) {
            v.updateComponent(propertyName, newValue);
        }
    }

    // To be called from the model
    @Override
    public void propertyChange(String propertyName, boolean newValue) {
        for (UpdateReceiver v : updaters) {
            v.updateComponent(propertyName, newValue);
        }
    }

    @Override
    public Object getOldChangeValue(String propertyName) {
        return lastPropertyChanges.get(propertyName);
    }

    @Override
    public void registerUpdater(UpdateReceiver updater) {
        if (!updaters.contains(updater)) {
            updaters.add(updater);
        }
    }

    @Override
    public void registerModel(ModelManipulation model) {
        this.model = model;
    }

    @Override
    public void fireLastPropertyChangesTo(UpdateReceiver view) {
        for (Map.Entry<String, Object> pair : lastPropertyChanges.entrySet()) {
            PropertyChangeEvent evt = new PropertyChangeEvent(this,
                    pair.getKey(), null, pair.getValue());
            view.updateComponent(evt);
        }

    }

}
