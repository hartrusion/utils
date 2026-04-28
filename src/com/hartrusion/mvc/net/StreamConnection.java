/*
 * The MIT License
 *
 * Copyright 2026 Viktor Alexander Hartung.
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Viktor Alexander Hartung
 */
public class StreamConnection {
    public static final byte MSG_ACTION = 1;
    public static final byte MSG_PROP_CHANGE = 2;

    private final DataOutputStream dos;
    private final DataInputStream dis;
    private final ClassRegistry registry;

    public StreamConnection(DataInputStream dis, DataOutputStream dos, ClassRegistry registry) {
        this.dis = dis;
        this.dos = dos;
        this.registry = registry;
    }

    public void sendPropertyChange(String propertyName, Object newValue) throws IOException {
        dos.writeByte(MSG_PROP_CHANGE);
        dos.writeUTF(propertyName);
        registry.writeObject(dos, newValue); // Das kann nun String, Enum, oder dein DTO sein!
        dos.flush();
    }
    
    // sendAction() etc...
    
    // Die Read-Schleife läuft in einem Thread und ruft bei MSG_PROP_CHANGE:
    // String propName = dis.readUTF();
    // Object value = registry.readObject(dis);
    // ... feuert Event lokal
}