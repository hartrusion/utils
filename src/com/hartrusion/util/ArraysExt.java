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

/**
 * Provides some very basic convenient functions for working with arrays.
 * <p>
 * It is intended to use a static import to access the provided functions.
 *
 * @author Viktor Alexander Hartung
 */
public abstract class ArraysExt {

    /**
     * Changes the length of an array of objects. If the new length is longer,
     * the array will be filled with null, if it is shorter, the values will be
     * lost.
     * <p>
     * As changing length is a thing that is not possible in java, this method
     * will create a new array with the values of the old one and return a
     * reference to that new array.
     *
     * @param array Array of objects which has to be resized
     * @param newLength The new length, as [].length()
     * @return Reference to a new array with the new size
     */
    public static Object[] newArrayLength(Object[] array, int newLength) {
        Object[] newArray;
        if (array.length != newLength) {
            newArray = new Object[newLength];
            System.arraycopy(array, 0, newArray, 0, array.length);
            return newArray;
        } else {
            return array;
        }
    }

    /**
     * Changes the length of an array of primitive types. If the new length is
     * longer, the array will be filled with the initial value of the primitive
     * type, which is zero (means it will not be set to anything), if it is
     * shorter, the values will be lost.
     * <p>
     * As changing length is a thing that is not possible in java, this method
     * will create a new array with the values of the old one and return a
     * reference to that new array.
     *
     * @param array Array of bytes which has to be resized
     * @param newLength The new length, as [].length()
     * @return Reference to a new array with the new size
     */
    public static byte[] newArrayLength(byte[] array, int newLength) {
        byte[] newArray;
        if (array.length != newLength) {
            newArray = new byte[newLength];
            System.arraycopy(array, 0, newArray, 0, array.length);
            return newArray;
        } else {
            return array;
        }
    }

    /**
     * Changes the length of an array of primitive types. If the new length is
     * longer, the array will be filled with the initial value of the primitive
     * type, which is zero (means it will not be set to anything), if it is
     * shorter, the values will be lost.
     * <p>
     * As changing length is a thing that is not possible in java, this method
     * will create a new array with the values of the old one and return a
     * reference to that new array.
     *
     * @param array Array of shorts which has to be resized
     * @param newLength The new length, as [].length()
     * @return Reference to a new array with the new size
     */
    public static short[] newArrayLength(short[] array, int newLength) {
        short[] newArray;
        if (array.length != newLength) {
            newArray = new short[newLength];
            System.arraycopy(array, 0, newArray, 0, array.length);
            return newArray;
        } else {
            return array;
        }
    }

    /**
     * Changes the length of an array of primitive types by replacing the array
     * with a new one. If the new length is longer, the array will be filled
     * with the initial value of the primitive type, which is zero (means it
     * will not be set to anything), if it is shorter, the values will be lost.
     * <p>
     * As changing length is a thing that is not possible in java, this method
     * will create a new array with the values of the old one and return a
     * reference to that new array.
     *
     * @param array Array of integers which has to be resized
     * @param newLength The new length, as [].length()
     * @return Reference to a new array with the new size
     */
    public static int[] newArrayLength(int[] array, int newLength) {
        int[] newArray;
        if (array.length != newLength) {
            newArray = new int[newLength];
            System.arraycopy(array, 0, newArray, 0, array.length);
            return newArray;
        } else {
            return array;
        }
    }

    /**
     * Changes the length of an array of primitive types. If the new length is
     * longer, the array will be filled with the initial value of the primitive
     * type, which is zero (means it will not be set to anything), if it is
     * shorter, the values will be lost.
     * <p>
     * As changing length is a thing that is not possible in java, this method
     * will create a new array with the values of the old one and return a
     * reference to that new array.
     *
     * @param array Array of longs which has to be resized
     * @param newLength The new length, as [].length()
     * @return Reference to a new array with the new size
     */
    public static long[] newArrayLength(long[] array, int newLength) {
        long[] newArray;
        if (array.length != newLength) {
            newArray = new long[newLength];
            System.arraycopy(array, 0, newArray, 0, array.length);
            return newArray;
        } else {
            return array;
        }
    }

    /**
     * Changes the length of an array of primitive types. If the new length is
     * longer, the array will be filled with the initial value of the primitive
     * type, which is zero (means it will not be set to anything), if it is
     * shorter, the values will be lost.
     * <p>
     * As changing length is a thing that is not possible in java, this method
     * will create a new array with the values of the old one and return a
     * reference to that new array.
     *
     * @param array Array of floats which has to be resized
     * @param newLength The new length, as [].length()
     * @return Reference to a new array with the new size
     */
    public static float[] newArrayLength(float[] array, int newLength) {
        float[] newArray;
        if (array.length != newLength) {
            newArray = new float[newLength];
            System.arraycopy(array, 0, newArray, 0, array.length);
            return newArray;
        } else {
            return array;
        }
    }

    /**
     * Changes the length of an array of primitive types. If the new length is
     * longer, the array will be filled with the initial value of the primitive
     * type, which is zero (means it will not be set to anything), if it is
     * shorter, the values will be lost.
     * <p>
     * As changing length is a thing that is not possible in java, this method
     * will create a new array with the values of the old one and return a
     * reference to that new array.
     *
     * @param array Array of doubles which has to be resized
     * @param newLength The new length, as [].length()
     * @return Reference to a new array with the new size
     */
    public static double[] newArrayLength(double[] array, int newLength) {
        double[] newArray;
        if (array.length != newLength) {
            newArray = new double[newLength];
            System.arraycopy(array, 0, newArray, 0, array.length);
            return newArray;
        } else {
            return array;
        }
    }

    /**
     * Changes the length of an array of primitive types. If the new length is
     * longer, the array will be filled with the initial value of the primitive
     * type, which is zero (means it will not be set to anything), if it is
     * shorter, the values will be lost.
     * <p>
     * As changing length is a thing that is not possible in java, this method
     * will create a new array with the values of the old one and return a
     * reference to that new array.
     *
     * @param array Array of booleans which has to be resized
     * @param newLength The new length, as [].length()
     * @return Reference to a new array with the new size
     */
    public static boolean[] newArrayLength(boolean[] array, int newLength) {
        boolean[] newArray;
        if (array.length != newLength) {
            newArray = new boolean[newLength];
            System.arraycopy(array, 0, newArray, 0, array.length);
            return newArray;
        } else {
            return array;
        }
    }

    /**
     * Changes the length of an array of primitive types. If the new length is
     * longer, the array will be filled with the initial value of the primitive
     * type, which is zero (means it will not be set to anything), if it is
     * shorter, the values will be lost.
     * <p>
     * As changing length is a thing that is not possible in java, this method
     * will create a new array with the values of the old one and return a
     * reference to that new array.
     *
     * @param array Array of cahrs which has to be resized
     * @param newLength The new length, as [].length()
     * @return Reference to a new array with the new size
     */
    public static char[] newArrayLength(char[] array, int newLength) {
        char[] newArray;
        if (array.length != newLength) {
            newArray = new char[newLength];
            System.arraycopy(array, 0, newArray, 0, array.length);
            return newArray;
        } else {
            return array;
        }
    }

    /**
     * Copies contents from one array to another, provided they have the same
     * size. This is basically a shorter variant of System.arraycopy() intended
     * to be used if source and target arrays have the same size.
     *
     * @param source
     * @param target
     */
    public static void copyArrayValues(double[] source, double[] target) {
        if (source.length != target.length) {
            throw new IllegalArgumentException("Length mismatch.");
        } else {
            System.arraycopy(source, 0, target, 0, source.length);
        }
    }

    /**
     * Inserts a given value on index 0 into a given array and shifts all values
     * by to the nearby higher index. The value on the highest index gets lost.
     * The name leftShift is a reference to C language and the representation of
     * int numbers where the bit on index 0 is the least significant bit and
     * shifting left means to shift from lsb to msb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void leftShiftInsert(byte[] target, byte value) {
        for (int idx = target.length - 1; idx > 0; idx--) {
            target[idx] = target[idx - 1]; // shift
        }
        target[0] = value;
    }

    /**
     * Inserts a given value on index 0 into a given array and shifts all values
     * by to the nearby higher index. The value on the highest index gets lost.
     * The name leftShift is a reference to C language and the representation of
     * int numbers where the bit on index 0 is the least significant bit and
     * shifting left means to shift from lsb to msb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void leftShiftInsert(short[] target, short value) {
        for (int idx = target.length - 1; idx > 0; idx--) {
            target[idx] = target[idx - 1]; // shift
        }
        target[0] = value;
    }

    /**
     * Inserts a given value on index 0 into a given array and shifts all values
     * by to the nearby higher index. The value on the highest index gets lost.
     * The name leftShift is a reference to C language and the representation of
     * int numbers where the bit on index 0 is the least significant bit and
     * shifting left means to shift from lsb to msb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void leftShiftInsert(int[] target, int value) {
        for (int idx = target.length - 1; idx > 0; idx--) {
            target[idx] = target[idx - 1]; // shift
        }
        target[0] = value;
    }

    /**
     * Inserts a given value on index 0 into a given array and shifts all values
     * by to the nearby higher index. The value on the highest index gets lost.
     * The name leftShift is a reference to C language and the representation of
     * int numbers where the bit on index 0 is the least significant bit and
     * shifting left means to shift from lsb to msb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void leftShiftInsert(long[] target, long value) {
        for (int idx = target.length - 1; idx > 0; idx--) {
            target[idx] = target[idx - 1]; // shift
        }
        target[0] = value;
    }

    /**
     * Inserts a given value on index 0 into a given array and shifts all values
     * by to the nearby higher index. The value on the highest index gets lost.
     * The name leftShift is a reference to C language and the representation of
     * int numbers where the bit on index 0 is the least significant bit and
     * shifting left means to shift from lsb to msb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void leftShiftInsert(float[] target, float value) {
        for (int idx = target.length - 1; idx > 0; idx--) {
            target[idx] = target[idx - 1]; // shift
        }
        target[0] = value;
    }

    /**
     * Inserts a given value on index 0 into a given array and shifts all values
     * by to the nearby higher index. The value on the highest index gets lost.
     * The name leftShift is a reference to C language and the representation of
     * int numbers where the bit on index 0 is the least significant bit and
     * shifting left means to shift from lsb to msb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void leftShiftInsert(double[] target, double value) {
        for (int idx = target.length - 1; idx > 0; idx--) {
            target[idx] = target[idx - 1]; // shift
        }
        target[0] = value;
    }

    /**
     * Inserts a given value on index 0 into a given array and shifts all values
     * by to the nearby higher index. The value on the highest index gets lost.
     * The name leftShift is a reference to C language and the representation of
     * int numbers where the bit on index 0 is the least significant bit and
     * shifting left means to shift from lsb to msb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void leftShiftInsert(boolean[] target, boolean value) {
        for (int idx = target.length - 1; idx > 0; idx--) {
            target[idx] = target[idx - 1]; // shift
        }
        target[0] = value;
    }

    /**
     * Inserts a given value on index 0 into a given array and shifts all values
     * by to the nearby higher index. The value on the highest index gets lost.
     * The name leftShift is a reference to C language and the representation of
     * int numbers where the bit on index 0 is the least significant bit and
     * shifting left means to shift from lsb to msb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void leftShiftInsert(char[] target, char value) {
        for (int idx = target.length - 1; idx > 0; idx--) {
            target[idx] = target[idx - 1]; // shift
        }
        target[0] = value;
    }

    /**
     * Inserts a given value on index 0 into a given array and shifts all values
     * by to the nearby higher index. The value on the highest index gets lost.
     * The name leftShift is a reference to C language and the representation of
     * int numbers where the bit on index 0 is the least significant bit and
     * shifting left means to shift from lsb to msb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void leftShiftInsert(Object[] target, Object value) {
        for (int idx = target.length - 1; idx > 0; idx--) {
            target[idx] = target[idx - 1]; // shift
        }
        target[0] = value;
    }

    /**
     * Inserts a given value on the end (highest index) of a given array and
     * shifts all values by to the nearby lower index. The value on index 0 gets
     * lost. The name rightShift is a reference to C language and the
     * representation of int numbers where the bit on highest index is the most
     * significant bit and shifting right means to shift from msb to lsb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void rightShiftInsert(byte[] target, byte value) {
        for (int idx = 1; idx < target.length; idx++) {
            target[idx - 1] = target[idx]; // shift
        }
        target[target.length - 1] = value;
    }

    /**
     * Inserts a given value on the end (highest index) of a given array and
     * shifts all values by to the nearby lower index. The value on index 0 gets
     * lost. The name rightShift is a reference to C language and the
     * representation of int numbers where the bit on highest index is the most
     * significant bit and shifting right means to shift from msb to lsb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void rightShiftInsert(short[] target, short value) {
        for (int idx = 1; idx < target.length; idx++) {
            target[idx - 1] = target[idx]; // shift
        }
        target[target.length - 1] = value;
    }

    /**
     * Inserts a given value on the end (highest index) of a given array and
     * shifts all values by to the nearby lower index. The value on index 0 gets
     * lost. The name rightShift is a reference to C language and the
     * representation of int numbers where the bit on highest index is the most
     * significant bit and shifting right means to shift from msb to lsb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void rightShiftInsert(int[] target, int value) {
        for (int idx = 1; idx < target.length; idx++) {
            target[idx - 1] = target[idx]; // shift
        }
        target[target.length - 1] = value;
    }

    /**
     * Inserts a given value on the end (highest index) of a given array and
     * shifts all values by to the nearby lower index. The value on index 0 gets
     * lost. The name rightShift is a reference to C language and the
     * representation of int numbers where the bit on highest index is the most
     * significant bit and shifting right means to shift from msb to lsb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void rightShiftInsert(long[] target, long value) {
        for (int idx = 1; idx < target.length; idx++) {
            target[idx - 1] = target[idx]; // shift
        }
        target[target.length - 1] = value;
    }

    /**
     * Inserts a given value on the end (highest index) of a given array and
     * shifts all values by to the nearby lower index. The value on index 0 gets
     * lost. The name rightShift is a reference to C language and the
     * representation of int numbers where the bit on highest index is the most
     * significant bit and shifting right means to shift from msb to lsb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void rightShiftInsert(float[] target, float value) {
        for (int idx = 1; idx < target.length; idx++) {
            target[idx - 1] = target[idx]; // shift
        }
        target[target.length - 1] = value;
    }

    /**
     * Inserts a given value on the end (highest index) of a given array and
     * shifts all values by to the nearby lower index. The value on index 0 gets
     * lost. The name rightShift is a reference to C language and the
     * representation of int numbers where the bit on highest index is the most
     * significant bit and shifting right means to shift from msb to lsb.
     *
     * @param value
     * @param target
     */
    public static void rightShiftInsert(double[] target, double value) {
        for (int idx = 1; idx < target.length; idx++) {
            target[idx - 1] = target[idx]; // shift
        }
        target[target.length - 1] = value;
    }

    /**
     * Inserts a given value on the end (highest index) of a given array and
     * shifts all values by to the nearby lower index. The value on index 0 gets
     * lost. The name rightShift is a reference to C language and the
     * representation of int numbers where the bit on highest index is the most
     * significant bit and shifting right means to shift from msb to lsb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void rightShiftInsert(boolean[] target, boolean value) {
        for (int idx = 1; idx < target.length; idx++) {
            target[idx - 1] = target[idx]; // shift
        }
        target[target.length - 1] = value;
    }

    /**
     * Inserts a given value on the end (highest index) of a given array and
     * shifts all values by to the nearby lower index. The value on index 0 gets
     * lost. The name rightShift is a reference to C language and the
     * representation of int numbers where the bit on highest index is the most
     * significant bit and shifting right means to shift from msb to lsb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void rightShiftInsert(char[] target, char value) {
        for (int idx = 1; idx < target.length; idx++) {
            target[idx - 1] = target[idx]; // shift
        }
        target[target.length - 1] = value;
    }

    /**
     * Inserts a given value on the end (highest index) of a given array and
     * shifts all values by to the nearby lower index. The value on index 0 gets
     * lost. The name rightShift is a reference to C language and the
     * representation of int numbers where the bit on highest index is the most
     * significant bit and shifting right means to shift from msb to lsb.
     *
     * @param value The value that is to be inserted into the array
     * @param target The target array
     */
    public static void rightShiftInsert(Object[] target, Object value) {
        for (int idx = 1; idx < target.length; idx++) {
            target[idx - 1] = target[idx]; // shift
        }
        target[target.length - 1] = value;
    }

    /**
     * Adds an Object to an array of Objects at the next reference that is null.
     * This requires the array of objects to have at least one null.
     *
     * @param array Array of Objects, containing at least one null reference
     * @param obj Object to insert at next null index.
     */
    public static void addObject(Object[] array, Object obj) {
        for (int idx = 0; idx < array.length; idx++) {
            if (array[idx] == null) {
                array[idx] = obj;
                return;
            }
        }
        throw new IndexOutOfBoundsException("Array is fully used.");
    }

    /**
     * Checks if the provided array cointains a reference to a specified Object.
     *
     * @param array The array that has to be inspected
     * @param obj Object
     * @return true, if array contains object.
     */
    public static boolean containsObject(Object[] array, Object obj) {
        for (int idx = 0; idx < array.length; idx++) {
            if (array[idx] == obj) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the index of the reference to an Object in an array of Objects.
     *
     * @param array The array that has to be inspected
     * @param obj Object
     * @return index in array, -1 if not found.
     */
    public static int indexOfObject(Object[] array, Object obj) {
        for (int idx = 0; idx < array.length; idx++) {
            if (array[idx] == obj) {
                return idx;
            }
        }
        return -1;
    }

}
