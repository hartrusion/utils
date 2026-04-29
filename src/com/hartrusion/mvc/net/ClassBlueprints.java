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

/**
 * A registry that knows all objects that shall be transferred via network with
 * the provided mvc concept. The registry will take expressions for construction
 * of each object that is to be transferred.
 *
 * @author Viktor Alexander Hartung
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClassBlueprints {

    private final Map<Class<?>, Byte> classToId = new HashMap<>();
    private final Map<Byte, ValueReader<?>> idToReader = new HashMap<>();
    private final Map<Byte, ValueWriter<Object>> idToWriter = new HashMap<>();

    private byte nextId = 1; // 0 used for null

    /**
     * Registeres a class with its own read and write logic
     */
    @SuppressWarnings("unchecked")
    public <T> void registerType(Class<T> clss, ValueWriter<T> writer, ValueReader<T> reader) {
        if (nextId == Byte.MAX_VALUE) {
            throw new IllegalStateException("Zu viele Typen registriert");
        }

        classToId.put(clss, nextId);
        idToReader.put(nextId, reader);
        // Cast ist sicher, da wir nur exakt T über writeObject zulassen
        idToWriter.put(nextId, (ValueWriter<Object>) writer);

        nextId++;
    }

    /**
     * Registers enumerations
     */
    public <E extends Enum<E>> void registerEnum(Class<E> enumClass) {
        registerType(enumClass,
                (outputStream, value) -> outputStream.writeUTF(value.name()), // Als String schreiben
                (inputStream) -> Enum.valueOf(enumClass, inputStream.readUTF()) // Sicher aus String rekonstruieren
        );
    }

    /**
     * Writs an object into stream
     */
    public void writeObject(DataOutputStream outputStream, Object value) throws IOException {
        if (value == null) {
            outputStream.writeByte(0); // 0 = null
            return;
        }

        Class<?> clazz = value.getClass();
        Byte typeId = classToId.get(clazz);

        if (typeId == null) {
            throw new IllegalArgumentException("Non registered type " + clazz.getName());
        }

        outputStream.writeByte(typeId); // write Type ID
        idToWriter.get(typeId).write(outputStream, value); // write Payload
    }

    /**
     * Reads an object from stream
     */
    public Object readObject(DataInputStream inputStream) throws IOException {
        byte typeId = inputStream.readByte();
        if (typeId == 0) {
            return null;
        }

        ValueReader<?> reader = idToReader.get(typeId);
        if (reader == null) {
            throw new IOException("Unknown Type ID " + typeId);
        }

        return reader.read(inputStream); // Generate object with the registered lambda
    }
}
