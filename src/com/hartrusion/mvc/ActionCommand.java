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
 * An object that is designed to be sent from a view towards the model. Contrary
 * to the PropertyChangeEvent which is used to propagate changes from the model
 * to the view, an action does not propagate changes and does not have a
 * previous value. Instead, it is a command that can do something by its own
 * existance, without the need of changing.
 *
 * @author Viktor Alexander Hartung
 */
public class ActionCommand {

    String propertyName;
    Object value;

    /**
     * Constructs a new {@code ActionEvent}.
     *
     * @param propertyName the programmatic name of the property that was
     * changed
     * @param value the value of the property
     *
     * @throws IllegalArgumentException if {@code propertyName} is {@code null}.
     */
    public ActionCommand(String propertyName, Object value) {
        if (propertyName == null) {
            throw new IllegalArgumentException(
                    "propertyName must not be null.");
        }
        this.propertyName = propertyName;
        this.value = value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getValue() {
        return value;
    }
}
