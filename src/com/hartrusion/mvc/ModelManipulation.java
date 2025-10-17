/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.hartrusion.mvc;

import java.beans.PropertyChangeEvent;

/**
 * To be implemented by a model, allows the controller to maniuplate the model.
 * It provides methods which will be invoked by a controller instance to
 * propagate events from controller to model.
 *
 * @author Viktor Alexander Hartung
 */
public interface ModelManipulation {

    /**
     * Notifys the model about an update in the controller. This gets called
     * directly from the view and the corresponding thread from the view. To
     * keep the application thread safe, this should not be used to make
     * extensive method calls.
     * <p>
     * An implementation of this method can be used in the model to trigger
     * something that will get the property and do something with it.
     *
     * @param propertyName
     */
    public void updateNotification(String propertyName);

    /**
     * Propagates an action towards the model, mostly resulting in an update of
     * a property of the model. This is called from the controller on the model.
     * For thread safety, this will not be called from threads from the view.
     * <p>
     * To make the controller invoke this method, see firePropertyUpdates from
     * the ModelListener interface.
     * <p>
     * Implementations of this method on the model contains code that will do
     * something with the PropertyChangeEvent coming from the controller to the
     * model.
     *
     * @param ac
     */
    public void updateProperty(ActionCommand ac);

    public void registerController(ModelListener controller);
}
