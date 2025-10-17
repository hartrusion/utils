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
 * Actions are coming from the view (GUI) and are propagated to the model.
 * Classes that implement this interface can receive actions, mostly from the
 * view, extend ActionListener.
 *
 * <p>
 * For example, a button click can be an action that sends an event to a
 * controller implementing this action receiver interface. It is also possible
 * for panels and other container objects to have the parent object implementing
 * this interface to receive actions from its child object.
 *
 * <p>
 * This does use the ActionCommand class to propagate user actions.
 *
 * @author Viktor Alexander Hartung
 */
public interface ActionReceiver {

    /**
     * Called by any registered view on user input. Communicates actions done by
     * user interacting with the view to a controller implementing this
     * interface.
     *
     * <p>
     * This can be called from within a UI
     *
     * @param evt The action described as an event.
     */
    public void userAction(ActionCommand evt);
}
