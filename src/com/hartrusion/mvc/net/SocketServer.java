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

import com.hartrusion.mvc.ViewerController;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Accepts incoming network client connections and creates one
 * ServerNetClientConnection per client.
 *
 * Each client connection is registered as an UpdateReceiver at the controller
 * and immediately receives the latest known property states.
 * <p>
 * The server can be asked for its state from other threads (whether it
 * listens, on which port and which clients are connected), which allows a
 * status view to display it while startServer() blocks its own thread.
 *
 * @author Viktor Alexander Hartung
 */
public class SocketServer {

    private static final Logger LOGGER
            = Logger.getLogger(SocketServer.class.getName());

    private final ViewerController controller;
    private final ClassBlueprints registry;

    /**
     * True while the server socket is bound and accepts clients. Written by
     * the server thread and read by status views.
     */
    private volatile boolean listening;

    /**
     * Port of the server socket, which is 0 until startServer() was called.
     */
    private volatile int port;

    /**
     * All client connections that were accepted and are still open. Closed
     * connections are removed when the connected clients are requested.
     */
    private final List<ViewConnection> connections
            = new CopyOnWriteArrayList<>();

    public SocketServer(ViewerController controller, ClassBlueprints registry) {
        this.controller = controller;
        this.registry = registry;
    }

    /**
     * Starts the server socket and accepts clients forever.
     *
     * @param port
     * @throws IOException
     */
    public void startServer(int port) throws IOException {
        this.port = port;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            listening = true;
            LOGGER.log(Level.INFO, "Server waiting on port {0}...", port);

            while (true) {
                Socket socket = serverSocket.accept();
                LOGGER.log(Level.INFO, "Client connected: {0}",
                        socket.getInetAddress());

                try {
                    ViewConnection connection
                            = new ViewConnection(socket, registry, controller);

                    controller.registerUpdater(connection);
                    controller.fireLastPropertyChangesTo(connection);
                    connection.start();
                    connections.add(connection);

                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE,
                            "Failed to initialize client connection: " + socket, e);
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        LOGGER.log(Level.WARNING,
                                "Error while closing failed client socket.", ex);
                    }
                }
            }
        } finally {
            listening = false;
        }
    }

    /**
     * Returns whether the server socket is bound and accepts clients.
     * <p>
     * LLM code generated with Claude Opus 5.5
     *
     * @return true while clients can connect
     */
    public boolean isListening() {
        return listening;
    }

    /**
     * LLM code generated with Claude Opus 5.5
     *
     * @return port the server was started on, 0 if it was not started yet
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns the remote addresses of all clients that are currently connected.
     * Connections that were closed in the meantime are removed from the list
     * of connections on this call.
     * <p>
     * LLM code generated with Claude Opus 5.5
     *
     * @return remote addresses of the connected clients
     */
    public List<String> getConnectedClients() {
        List<String> clients = new ArrayList<>();
        for (ViewConnection connection : connections) {
            if (connection.isConnected()) {
                clients.add(connection.getRemoteAddress());
            } else {
                connections.remove(connection);
            }
        }
        return clients;
    }
}
