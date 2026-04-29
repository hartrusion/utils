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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client-side MVC controller that connects to a ServerNetView.
 * Receives updates from the simulator server and forwards them to registered
 * UpdateReceivers. Sends user actions from the GUI to the server.
 *
 * @author Viktor Alexander Hartung
 */
public class NetViewAdapter implements ViewerController {

    private static final Logger LOGGER
            = Logger.getLogger(NetViewAdapter.class.getName());

    private final List<UpdateReceiver> updaters = new CopyOnWriteArrayList<>();
    private final Map<String, Object> lastPropertyChanges
            = new ConcurrentHashMap<>();

    private final ClassBlueprints blueprints;

    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Thread readThread;

    public NetViewAdapter(ClassBlueprints blueprints) {
        this.blueprints = blueprints;
    }

    /**
     * Connects to the specified server.
     *
     * @param host server address
     * @param port server port, e.g. 8080
     * @throws IOException
     */
    public void connect(String host, int port) throws IOException {
        socket = new Socket("127.0.0.1", port);
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());

        readThread = new Thread(() -> {
            readLoop();
        }, "ServerNetController-ReadThread");
        readThread.setDaemon(true);
        readThread.start();

        LOGGER.log(Level.INFO, "Connected to server at 127.0.0.1:{0}", port);
    }

    private void readLoop() {
        try {
            while (!socket.isClosed()) {
                byte msgType = inputStream.readByte();

                switch (msgType) {
                    case ViewConnection.MSG_UPDATE_EVENT: {
                        String propertyName = inputStream.readUTF();
                        Object oldValue = blueprints.readObject(inputStream);
                        Object newValue = blueprints.readObject(inputStream);
                        PropertyChangeEvent evt = new PropertyChangeEvent(
                                this, propertyName, oldValue, newValue);
                        propertyChange(evt);
                        break;
                    }

                    case ViewConnection.MSG_UPDATE_OBJECT: {
                        String propertyName = inputStream.readUTF();
                        Object newValue = blueprints.readObject(inputStream);
                        propertyChange(propertyName, newValue);
                        break;
                    }

                    case ViewConnection.MSG_UPDATE_DOUBLE: {
                        String propertyName = inputStream.readUTF();
                        double newValue = inputStream.readDouble();
                        propertyChange(propertyName, newValue);
                        break;
                    }

                    case ViewConnection.MSG_UPDATE_BOOLEAN: {
                        String propertyName = inputStream.readUTF();
                        boolean newValue = inputStream.readBoolean();
                        propertyChange(propertyName, newValue);
                        break;
                    }

                    default:
                        LOGGER.log(Level.WARNING,
                                "Unknown message type received: {0}", msgType);
                        break;
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Connection to server interrupted.", e);
        }
    }

    /**
     * Called for incoming PropertyChangeEvent messages from the network
     * connection.
     *
     * @param evt
     */
    public void propertyChange(PropertyChangeEvent evt) {
        lastPropertyChanges.put(evt.getPropertyName(), evt.getNewValue());

        for (UpdateReceiver v : updaters) {
            v.updateComponent(evt);
        }
    }

    /**
     * Called for incoming object updates from the network connection.
     *
     * @param propertyName
     * @param newValue
     */
    public void propertyChange(String propertyName, Object newValue) {
        lastPropertyChanges.put(propertyName, newValue);

        for (UpdateReceiver v : updaters) {
            v.updateComponent(propertyName, newValue);
        }
    }

    /**
     * Called for incoming double updates from the network connection.
     *
     * @param propertyName
     * @param newValue
     */
    public void propertyChange(String propertyName, double newValue) {
        lastPropertyChanges.put(propertyName, newValue);

        for (UpdateReceiver v : updaters) {
            v.updateComponent(propertyName, newValue);
        }
    }

    /**
     * Called for incoming boolean updates from the network connection.
     *
     * @param propertyName
     * @param newValue
     */
    public void propertyChange(String propertyName, boolean newValue) {
        lastPropertyChanges.put(propertyName, newValue);

        for (UpdateReceiver v : updaters) {
            v.updateComponent(propertyName, newValue);
        }
    }

    @Override
    public synchronized void userAction(ActionCommand evt) {
        if (outputStream == null) {
            throw new IllegalStateException("Not connected to server.");
        }

        try {
            outputStream.writeByte(ViewConnection.MSG_ACTION);
            outputStream.writeUTF(evt.getPropertyName());
            blueprints.writeObject(outputStream, evt.getValue());
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to send action to server.", e);
        }
    }

    @Override
    public void registerUpdater(UpdateReceiver updater) {
        if (!updaters.contains(updater)) {
            updaters.add(updater);
        }
    }

    @Override
    public void fireLastPropertyChangesTo(UpdateReceiver view) {
        for (Map.Entry<String, Object> pair : lastPropertyChanges.entrySet()) {
            PropertyChangeEvent evt = new PropertyChangeEvent(this,
                    pair.getKey(), null, pair.getValue());
            view.updateComponent(evt);
        }
    }

    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error while closing client socket.", e);
        }
    }
}