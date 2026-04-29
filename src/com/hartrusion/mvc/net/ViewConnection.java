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
package com.hartrusion.mvc.net;

import com.hartrusion.mvc.ActionCommand;
import com.hartrusion.mvc.UpdateReceiver;
import com.hartrusion.mvc.ViewerController;
import java.beans.PropertyChangeEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a single connected network client on server side.
 * Receives ActionCommands from the client and forwards them to the controller.
 * Receives updates from the controller and forwards them to the client.
 * <p>
 * Generated with the assistance of Copilot using Gemini 3.1 Pro and GPT 5.4
 *
 * @author Viktor Alexander Hartung
 */
public class ViewConnection implements UpdateReceiver {

    private static final Logger LOGGER
            = Logger.getLogger(ViewConnection.class.getName());

    public static final byte MSG_ACTION = 1;
    public static final byte MSG_UPDATE_EVENT = 2;
    public static final byte MSG_UPDATE_OBJECT = 3;
    public static final byte MSG_UPDATE_DOUBLE = 4;
    public static final byte MSG_UPDATE_BOOLEAN = 5;

    private final Socket socket;
    private final ClassBlueprints registry;
    private final ViewerController controller;

    private final DataInputStream dis;
    private final DataOutputStream dos;

    private Thread readThread;

    public ViewConnection(Socket socket,
            ClassBlueprints registry,
            ViewerController controller) throws IOException {
        this.socket = socket;
        this.registry = registry;
        this.controller = controller;
        this.dis = new DataInputStream(socket.getInputStream());
        this.dos = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * Starts the reader thread for incoming client messages.
     */
    public void start() {
        readThread = new Thread(() -> {
            readLoop();
        }, "ServerNetClientConnection-ReadThread-" + socket.getPort());
        readThread.setDaemon(true);
        readThread.start();
    }

    private void readLoop() {
        try {
            while (!socket.isClosed()) {
                byte msgType = dis.readByte();

                if (msgType == MSG_ACTION) {
                    String propertyName = dis.readUTF();
                    Object value = registry.readObject(dis);

                    ActionCommand action = new ActionCommand(propertyName, value);
                    controller.userAction(action);
                } else {
                    LOGGER.log(Level.WARNING,
                            "Unknown message type received from client: {0}",
                            msgType);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING,
                    "Connection to client interrupted: " + socket, e);
        } finally {
            close();
        }
    }

    private synchronized void sendPropertyChangeEvent(PropertyChangeEvent evt) {
        if (socket.isClosed()) {
            return;
        }

        try {
            dos.writeByte(MSG_UPDATE_EVENT);
            dos.writeUTF(evt.getPropertyName());
            registry.writeObject(dos, evt.getOldValue());
            registry.writeObject(dos, evt.getNewValue());
            dos.flush();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while sending PropertyChangeEvent to client "
                    + socket, e);
            close();
        }
    }

    private synchronized void sendObjectUpdate(String propertyName, Object newValue) {
        if (socket.isClosed()) {
            return;
        }

        try {
            dos.writeByte(MSG_UPDATE_OBJECT);
            dos.writeUTF(propertyName);
            registry.writeObject(dos, newValue);
            dos.flush();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while sending object update to client " + socket
                    + " for " + propertyName, e);
            close();
        }
    }

    private synchronized void sendDoubleUpdate(String propertyName, double newValue) {
        if (socket.isClosed()) {
            return;
        }

        try {
            dos.writeByte(MSG_UPDATE_DOUBLE);
            dos.writeUTF(propertyName);
            dos.writeDouble(newValue);
            dos.flush();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while sending double update to client " + socket
                    + " for " + propertyName, e);
            close();
        }
    }

    private synchronized void sendBooleanUpdate(String propertyName, boolean newValue) {
        if (socket.isClosed()) {
            return;
        }

        try {
            dos.writeByte(MSG_UPDATE_BOOLEAN);
            dos.writeUTF(propertyName);
            dos.writeBoolean(newValue);
            dos.flush();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,
                    "Error while sending boolean update to client " + socket
                    + " for " + propertyName, e);
            close();
        }
    }

    @Override
    public void updateComponent(PropertyChangeEvent evt) {
        sendPropertyChangeEvent(evt);
    }

    @Override
    public void updateComponent(String propertyName, Object newValue) {
        sendObjectUpdate(propertyName, newValue);
    }

    @Override
    public void updateComponent(String propertyName, double newValue) {
        sendDoubleUpdate(propertyName, newValue);
    }

    @Override
    public void updateComponent(String propertyName, boolean newValue) {
        sendBooleanUpdate(propertyName, newValue);
    }

    public synchronized void close() {
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.log(Level.WARNING,
                    "Error while closing client socket " + socket, e);
        }
    }
}