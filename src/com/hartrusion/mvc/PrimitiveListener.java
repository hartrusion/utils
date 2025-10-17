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

import java.beans.PropertyChangeListener;

/**
 * Extends the java beans PropertyChangeListener by adding methods for using
 * primitive types instead of an object. This can be used when sending loads of
 * data like for a measurement gauge and will not create a new object each time
 * it gets called.
 *
 * @author Viktor Alexander Hartung
 */
public interface PrimitiveListener extends PropertyChangeListener {

    /**
     * Called by the registered model to notify that a property was changed.
     * This is a simplified variant of propertyChange(PropertyChangeEvent evt)
     * to allow the model to just call this function instead of creating an
     * event object.
     *
     * @param propertyName the programmatic name of the property that was
     * changed
     * @param newValue the new value of the property.
     *
     */
    public void propertyChange(String propertyName, Object newValue);

    /**
     * Called by the registered model to notify that a property was changed.
     * This is a simplified variant of propertyChange(PropertyChangeEvent evt)
     * to allow the model to just call this function instead of creating an
     * event object.
     *
     * @param propertyName the programmatic name of the property that was
     * changed
     * @param newValue the new value of the property.
     *
     */
    public void propertyChange(String propertyName, double newValue);

    /**
     * Called by the registered model to notify that a property was changed.
     * This is a simplified variant of propertyChange(PropertyChangeEvent evt)
     * to allow the model to just call this function instead of creating an
     * event object.
     *
     * @param propertyName the programmatic name of the property that was
     * changed
     * @param newValue the new value of the property.
     *
     */
    public void propertyChange(String propertyName, boolean newValue);
}
