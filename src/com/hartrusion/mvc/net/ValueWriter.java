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

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Writes an object of a specific type to a binary data stream.
 * <p>
 * This interface is used by the network class registry. A registered type needs
 * a matching writer so that Java objects can be converted into binary form
 * before they are sent over the network.
 * <p>
 * The generic type parameter T is the type that can be written by this writer.
 * Typical examples are String, Double, Boolean, Integer, enums, or custom
 * snapshot classes such as ValueSnapshot.
 * <p>
 * Implementations are usually provided as lambda expressions when a type is
 * registered in the ClassRegistry.
 * <p>
 * @param <T> type of the object that is written to the stream
 * @author Viktor Alexander Hartung
 */
public interface ValueWriter<T> {

    void write(DataOutputStream outputStream, T value) throws IOException;
}
