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

/**
 * Updates are events coming from the updater towards the view. A view can be
 * any kind of GUI or even something else, it jut needs to implement all those
 * mehtods here and present something to the user when those methods are called.
 *
 * @author Viktor Alexander Hartung
 */
public interface UpdateReceiver {

    /**
     * This is a Views method to receive a PropertyChangeEvent from the
     * controller. It will be implemented in the view and the implementation
     * will handle the updates on the view.
     * <p>
     * An update transfers data in direction from Controller to View. It can
     * also be used by the view itself to transfer the data to other view
     * instances.
     *
     * @param evt
     */
    public void updateComponent(PropertyChangeEvent evt);

    /**
     * This is a Views method to receive an update from the controller,
     * specified by a String with a given Object value. It will be implemented
     * in the view and the implementation will handle the updates on the view.
     * <p>
     * An update transfers data in direction from Controller to View. It can
     * also be used by the view itself to transfer the data to other view
     * instances.
     *
     * @param propertyName the programmatic name of the property that was
     * changed
     * @param newValue the new value of the property specified as
     * java.lang.Object
     */
    public void updateComponent(String propertyName, Object newValue);

    /**
     * This is a Views method to receive an update from the controller,
     * specified by a String with a given primitive double value. It will be
     * implemented in the view and the implementation will handle the updates on
     * the view. This can be used for cyclic or mass updates if the
     * PropertyChangeEvent would create too many objects requiring massive
     * garbage collection.
     * <p>
     * An update transfers data in direction from Controller to View. It can
     * also be used by the view itself to transfer the data to other view
     * instances.
     *
     * @param propertyName the programmatic name of the property that was
     * changed
     * @param newValue the new value of the property specified as double value
     */
    public void updateComponent(String propertyName, double newValue);

    /**
     * This is a Views method to receive an update from the controller,
     * specified by a String with a given primitive boolean value. It will be
     * implemented in the view and the implementation will handle the updates on
     * the view. This can be used for cyclic or mass updates if the
     * PropertyChangeEvent would create too many objects requiring massive
     * garbage collection.
     * <p>
     * An update transfers data in direction from Controller to View. It can
     * also be used by the view itself to transfer the data to other view
     * instances.
     *
     * @param propertyName the programmatic name of the property that was
     * changed
     * @param newValue the new value of the property specified as bool
     */
    public void updateComponent(String propertyName, boolean newValue);
}
