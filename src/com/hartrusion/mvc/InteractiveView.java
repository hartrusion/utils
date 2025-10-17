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
 * To be implemented by a view that is registered with an Updater. It extends
 * the UpdateReceiver which provides methods which wil be invoked by the
 * controller to propagate changes towards the view. Additionally, this allows
 * the view to register with a controller so the view knows where to send its
 * actions to.
 *
 * @author Viktor Alexander Hartung
 */
public interface InteractiveView extends UpdateReceiver {

    /**
     * Allows the controller to be referenced with the update receiving
     * instance. While there are other means of organizing this, this will force
     * the update receiving class to provide a way to register a controler.
     *
     * @param controller
     */
    public void registerController(ViewerController controller);
}
