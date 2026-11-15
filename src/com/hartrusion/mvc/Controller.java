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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements some interpretation of the MVC pattern.
 * <p>
 * Will redirect all received property changes from a model as a component
 * update to an UpdateReceiver. An UpdateReceiver is a view-specialized class
 * that will, for example, put the Updates into the event queue, depending on
 * what the view architecture is.
 * <p>
 * The view sends user actions to this controller, the controller will offer
 * those as action commands in a concurrent queue to the model.
 * <p>
 * The model can be unregistered and replaced by a new model instance at
 * runtime, for example when an application restarts a crashed simulation while
 * its views and network connections stay connected to this controller. While
 * no model is registered, the controller keeps working for its update
 * receivers and discards user actions, so that views do not fail.
 *
 * @author Viktor Alexander Hartung
 */
public class Controller implements ViewerController, ModelListener {

    private static final Logger LOGGER
            = Logger.getLogger(Controller.class.getName());

    /**
     * The model that receives the user actions, or null while no model is
     * registered. It is volatile because it is replaced by the application
     * thread while the view threads and the cyclic model thread read it.
     */
    private volatile ModelManipulation model;

    /**
     * Holds a list of all registered update receivers. This is an instance that
     * will forward the updates to the view and is dependent on the viewer
     * implementation.
     */
    private final List<UpdateReceiver> updaters = new CopyOnWriteArrayList<>();

    private final Map<String, Object> lastPropertyChanges
            = new ConcurrentHashMap<>();

    /**
     * Actions from views are getting stored in this queue. The model has to
     * request the actions from this view to process them.
     */
    private final ConcurrentLinkedQueue<ActionCommand> pendingActions
            = new ConcurrentLinkedQueue<>();

    // called from view
    @Override
    public void userAction(ActionCommand evt) {
        // The field is read once, because the model can be unregistered by
        // another thread between the check and the call.
        ModelManipulation currentModel = model;
        if (currentModel == null) {
            LOGGER.log(Level.WARNING, "No model registered, discarding action "
                    + evt.getPropertyName());
            return;
        }
        pendingActions.offer(evt);
        currentModel.updateNotification(evt.getPropertyName());
    }

    @Override
    public synchronized void fireActions() {
        ModelManipulation currentModel = model;
        if (currentModel == null) {
            pendingActions.clear();
            return;
        }
        ActionCommand evt;
        while (!pendingActions.isEmpty()) {
            evt = pendingActions.poll();
            currentModel.handleAction(evt);
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

    /**
     * Removes the registered model from this controller. Pending actions that
     * were meant for the removed model are discarded, and the stored last
     * property changes are cleared because they describe the state of the
     * removed model and must not be sent to views that connect later. The
     * update receivers stay registered, so a model that is registered
     * afterwards reaches the same views. The model itself should be stopped
     * before, because property changes it still sends would be stored and
     * distributed again.
     * <p>
     * This method is not synchronized with fireActions(), so that a model
     * thread which hangs inside fireActions() can not block the removal.
     * <p>
     * LLM code generated with Claude Opus 5.5
     */
    @Override
    public void unregisterModel() {
        model = null;
        pendingActions.clear();
        lastPropertyChanges.clear();
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
