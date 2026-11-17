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
package com.hartrusion.util;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;

/**
 * Samples the state of the Java virtual machine in which the application runs,
 * which is the memory usage, the number of threads, the CPU load and the work
 * of the garbage collectors, so that an application can display these values
 * to judge whether it runs into a problem with RAM or processing power. The
 * values are read from the management beans (MXBeans) of the JVM when sample()
 * is called and are kept until the next call, so that all getters describe the
 * same point in time. The class does not decide when to sample, because the
 * CPU load is an average since the previous query and therefore depends on the
 * interval that the caller chooses, for which about one second is a sensible
 * value.
 * <p>
 * The RbmkSimulator uses this class in its MainLoop to transport the values of
 * the JVM that runs the simulation to its GUI and to network clients.
 * <p>
 * LLM code generated with Claude Opus 5.5
 *
 * @author Viktor Alexander Hartung
 */
public class JvmStatistics {

    private static final double BYTES_PER_MEBIBYTE = 1024.0 * 1024.0;

    /**
     * Factor that converts a load from the management beans, which is a
     * fraction between 0.0 and 1.0, into percent.
     */
    private static final double PERCENT_PER_FRACTION = 100.0;

    /**
     * Value of a load that could not be determined, because either the
     * platform does not provide it or the JVM has no extended operating
     * system bean.
     */
    public static final double LOAD_NOT_AVAILABLE = -1.0;

    private final MemoryMXBean memoryBean
            = ManagementFactory.getMemoryMXBean();

    private final ThreadMXBean threadBean
            = ManagementFactory.getThreadMXBean();

    private final OperatingSystemMXBean operatingSystemBean
            = ManagementFactory.getOperatingSystemMXBean();

    /**
     * Heap memory that is occupied by objects in MiB.
     */
    private double heapUsedMiB;

    /**
     * Heap memory that the JVM has reserved from the operating system in MiB,
     * which is at least the used heap memory.
     */
    private double heapCommittedMiB;

    /**
     * Maximum heap memory that the JVM may reserve in MiB, which is given by
     * the -Xmx option or the default of the JVM. It is -1 if no limit is
     * defined.
     */
    private double heapMaxMiB;

    /**
     * Memory outside of the heap that is used by the JVM itself in MiB, for
     * example for loaded classes and compiled code.
     */
    private double nonHeapUsedMiB;

    /**
     * Number of live threads, including daemon threads.
     */
    private int threadCount;

    /**
     * Highest number of live threads since the start of the JVM.
     */
    private int peakThreadCount;

    private int daemonThreadCount;

    /**
     * CPU load of the JVM process in percent of all processors, or
     * LOAD_NOT_AVAILABLE.
     */
    private double processCpuLoad = LOAD_NOT_AVAILABLE;

    /**
     * CPU load of the whole system in percent of all processors, or
     * LOAD_NOT_AVAILABLE.
     */
    private double systemCpuLoad = LOAD_NOT_AVAILABLE;

    private int availableProcessors;

    /**
     * Sum of the collections of all garbage collectors since the start of the
     * JVM.
     */
    private long garbageCollectionCount;

    /**
     * Sum of the accumulated collection times of all garbage collectors since
     * the start of the JVM in milliseconds.
     */
    private long garbageCollectionTimeMillis;

    /**
     * Reads all values from the management beans of the JVM and keeps them
     * until the next call.
     */
    public void sample() {
        MemoryUsage heap = memoryBean.getHeapMemoryUsage();
        heapUsedMiB = heap.getUsed() / BYTES_PER_MEBIBYTE;
        heapCommittedMiB = heap.getCommitted() / BYTES_PER_MEBIBYTE;
        if (heap.getMax() < 0) {
            heapMaxMiB = -1;
        } else {
            heapMaxMiB = heap.getMax() / BYTES_PER_MEBIBYTE;
        }
        nonHeapUsedMiB = memoryBean.getNonHeapMemoryUsage().getUsed()
                / BYTES_PER_MEBIBYTE;

        threadCount = threadBean.getThreadCount();
        peakThreadCount = threadBean.getPeakThreadCount();
        daemonThreadCount = threadBean.getDaemonThreadCount();

        availableProcessors = operatingSystemBean.getAvailableProcessors();
        // The CPU loads are only provided by the extended bean of the JDK,
        // which is present in all common JVMs but not guaranteed.
        if (operatingSystemBean
                instanceof com.sun.management.OperatingSystemMXBean extendedBean) {
            processCpuLoad = toPercent(extendedBean.getProcessCpuLoad());
            systemCpuLoad = toPercent(extendedBean.getCpuLoad());
        }

        long collections = 0;
        long collectionTime = 0;
        for (GarbageCollectorMXBean gc
                : ManagementFactory.getGarbageCollectorMXBeans()) {
            // Each value is -1 if a collector does not provide it.
            if (gc.getCollectionCount() > 0) {
                collections += gc.getCollectionCount();
            }
            if (gc.getCollectionTime() > 0) {
                collectionTime += gc.getCollectionTime();
            }
        }
        garbageCollectionCount = collections;
        garbageCollectionTimeMillis = collectionTime;
    }

    /**
     * Converts a load fraction of the management beans into percent, while a
     * negative value, which the beans return if the load is not available,
     * results in LOAD_NOT_AVAILABLE.
     *
     * @param loadFraction load between 0.0 and 1.0 or a negative value
     * @return load in percent or LOAD_NOT_AVAILABLE
     */
    private static double toPercent(double loadFraction) {
        if (loadFraction < 0) {
            return LOAD_NOT_AVAILABLE;
        }
        return loadFraction * PERCENT_PER_FRACTION;
    }

    public double getHeapUsedMiB() {
        return heapUsedMiB;
    }

    public double getHeapCommittedMiB() {
        return heapCommittedMiB;
    }

    /**
     * @return maximum heap in MiB or -1 if the heap is not limited
     */
    public double getHeapMaxMiB() {
        return heapMaxMiB;
    }

    public double getNonHeapUsedMiB() {
        return nonHeapUsedMiB;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public int getPeakThreadCount() {
        return peakThreadCount;
    }

    public int getDaemonThreadCount() {
        return daemonThreadCount;
    }

    /**
     * @return CPU load of the JVM process in percent or LOAD_NOT_AVAILABLE
     */
    public double getProcessCpuLoad() {
        return processCpuLoad;
    }

    /**
     * @return CPU load of the whole system in percent or LOAD_NOT_AVAILABLE
     */
    public double getSystemCpuLoad() {
        return systemCpuLoad;
    }

    public int getAvailableProcessors() {
        return availableProcessors;
    }

    public long getGarbageCollectionCount() {
        return garbageCollectionCount;
    }

    public long getGarbageCollectionTimeMillis() {
        return garbageCollectionTimeMillis;
    }
}
