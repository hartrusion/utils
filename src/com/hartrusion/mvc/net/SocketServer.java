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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Accepts incoming network client connections and creates one
 * ServerNetClientConnection per client.
 *
 * Each client connection is registered as an UpdateReceiver at the controller
 * and immediately receives the latest known property states.
 *
 * @author Viktor Alexander Hartung
 */
public class SocketServer {

    private static final Logger LOGGER
            = Logger.getLogger(SocketServer.class.getName());

    private final ViewerController controller;
    private final ClassBlueprints registry;

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
        try (ServerSocket serverSocket = new ServerSocket(port)) {
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
        }
    }
}