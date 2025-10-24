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
    public void handleAction(ActionCommand ac);

    public void registerController(ModelListener controller);
}
