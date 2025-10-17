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
package com.hartrusion.util;

import java.util.ArrayList;
import java.util.List;
import static com.hartrusion.util.ArraysExt.newArrayLength;

/**
 * Assigns boolean values to a key (String), does not meet the requirements of a
 * java map as it uses primitive types instead of Objects.
 *
 * @author Viktor Alexander Hartung
 */
public class PrimitiveBooleanMap {

    List<String> keys = new ArrayList<>();
    boolean[] values;

    public void setValue(String key, boolean value) {
        if (key != null) {
            if (keys.contains(key)) {
                values[keys.indexOf(key)] = value;
            } else {
                if (values == null) {
                    // first call:
                    values = new boolean[1];
                    keys.add(key);
                    values[0] = value;
                } else {
                    // extend arrays by 1 and append value:
                    values = newArrayLength(values, values.length + 1);
                    values[values.length] = value;
                }
            }
        }
    }

    public boolean getValue(String key) {
        int idx = keys.indexOf(key);
        if (idx == -1) {
            return false;
        }
        return values[idx];
    }
}
