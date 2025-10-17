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
 * Modifies the default java util logging to output all logs on command line
 * using System.out instead of System.err. The Output is shortend and compacted
 * to one line and outputs miliseconds.
 *
 * @author Viktor Alexander Hartung
 */
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

public class SimpleLogOut {

    public static void configureLoggingToStdOut() {
        Logger root = Logger.getLogger(""); // get root logger
        // Remove all handlers
        for (Handler h : root.getHandlers()) {
            root.removeHandler(h);
        }

        // Generate StreamHandler that 
        Handler outHandler = new StreamHandler(
                System.out, new OneLineFormatter()
        ) {
            @Override
            public synchronized void publish(LogRecord record) {
                super.publish(record);
                flush(); // do not buffer
            }
        };

        // Only log and output on and above level CONFIG
        outHandler.setLevel(Level.CONFIG);
        root.setLevel(Level.CONFIG);

        root.addHandler(outHandler);
    }
}

class OneLineFormatter extends Formatter {
    private static final DateTimeFormatter TIME_FMT
            = DateTimeFormatter.ofPattern("HH:mm:ss:SSS")
                    .withZone(ZoneId.systemDefault());

    @Override
    public String format(LogRecord record) {
        String time = TIME_FMT.format(Instant.ofEpochMilli(record.getMillis()));
        String source;
        if (record.getSourceClassName() != null) {
            source = record.getSourceClassName();
            // Use only last part that comes after . if there is a .
            int idx = source.lastIndexOf('.');
            if (idx >= 0) {
                source = source.substring(idx + 1);
            }
        } else {
            source = record.getLoggerName();
        }

        String message = formatMessage(record);

        StringBuilder sb = new StringBuilder(128);
        sb.append(time)
                .append(' ')
                .append(record.getLevel().getName())
                .append(' ')
                .append('(')
                .append(source)
                .append(')')
                .append(':')
                .append(' ')
                .append(message)
                .append(System.lineSeparator());

        if (record.getThrown() != null) {
            Throwable t = record.getThrown();
            sb.append(t.toString()).append(System.lineSeparator());
            for (StackTraceElement e : t.getStackTrace()) {
                sb.append("\t at ")
                        .append(e.toString())
                        .append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
