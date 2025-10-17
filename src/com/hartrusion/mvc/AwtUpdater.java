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
 * Puts all updates that this class receives into the AWT event queue by
 * wrapping the call of the updateComponent on the view into a runnable object
 * and passing it to the AWT event queue.
 *
 * @author Viktor Alexander Hartung
 */
public class AwtUpdater implements UpdateReceiver {

    /**
     * The attached view instance which implements the PrimitiveListener
     * interface, this is an AWT based GUI or component.
     */
    private InteractiveView view;

    @Override
    public void updateComponent(PropertyChangeEvent evt) {
        java.awt.EventQueue.invokeLater(() -> {
            view.updateComponent(evt);
        });
    }

    @Override
    public void updateComponent(String propertyName, Object newValue) {
        java.awt.EventQueue.invokeLater(() -> {
            view.updateComponent(propertyName, newValue);
        });
    }

    @Override
    public void updateComponent(String propertyName, double newValue) {
        java.awt.EventQueue.invokeLater(() -> {
            view.updateComponent(propertyName, newValue);
        });
    }

    @Override
    public void updateComponent(String propertyName, boolean newValue) {
        java.awt.EventQueue.invokeLater(() -> {
            view.updateComponent(propertyName, newValue);
        });
    }

    public void registerView(InteractiveView view) {
        this.view = view;
    }
}
