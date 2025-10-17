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
 * called from the view.
 *
 * @author Viktor Alexander Hartung
 */
public interface ViewerController extends ActionReceiver {

    public void registerUpdater(UpdateReceiver updater);
    
    /**
     * The controller has a list of all PropertyChangeEvents with the
     * propertyName and the last received value. Using this method all stored
     * propertyName and value pairs will be packed into new PropertyChangeEvents
     * and sent to the UpdateReceiver passed as an argument.
     *
     * <p>
     * This can be used to initialize a new created frame with proper values.
     *
     * @param view UpdateReceiver that will get all propertyChangeEvents fired
     * to.
     */
    public void fireLastPropertyChangesTo(UpdateReceiver view);
}
