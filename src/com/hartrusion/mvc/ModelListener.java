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
 * To be implemented by a controller. This interface describes the part that is
 * called from the model.
 *
 * @author Viktor Alexander Hartung
 */
public interface ModelListener extends PrimitiveListener {

    /**
     * Returns the last propagated value from the controller. When sending a
     * property change from the model towards the controller, an old value, like
     * a previous value, has to be specified. This can be handled by the
     * application itself or this method can be used to return the last known
     * value.
     *
     * @param propertyName Programmatic name of the property
     * @return Last value that was sent to this Controller
     */
    public Object getOldChangeValue(String propertyName);

    /**
     * Tells the controller to send all property updates to the model. This is
     * required for the application to be threadsafe and allows the model to
     * recceive all property change request on a certain time and not in the
     * middle of all executions.
     * <p>
     * This method has to be implemented thread-save, so using "synchronized" is
     * required on implementation
     * <p>
     * This method will call updateProperty() on the model for all qued updates.
     */
    public void firePropertyUpdates();

    /**
     * Allows registering the model to the controller.
     *
     * @param model
     */
    public void registerModel(ModelManipulation model);
}
