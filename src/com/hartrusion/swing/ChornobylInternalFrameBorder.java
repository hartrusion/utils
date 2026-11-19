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
package com.hartrusion.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JInternalFrame;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;

/**
 * Flat frame border of internal frames, which replaces the border of the
 * Metal look and feel in ChornobylMetalThemeDark. Metal paints a frame of
 * five pixels in the outline color of the theme with light and dark corner
 * marks, which is a light gray in the dark theme, so that the frames look
 * heavy on the dark desktop. This border keeps the width of five pixels,
 * because the frame is resized by dragging it, but paints it flat in a fill
 * color that differs from the title bar, so that the border and the title
 * bar can be told apart, with a one pixel outer line that separates the
 * frame from the desktop, and without the corner marks and their shading.
 * The selected frame gets a brighter fill than the others.
 * <p>
 * LLM code generated with Claude Opus 5.5
 *
 * @author Viktor Alexander Hartung
 */
public class ChornobylInternalFrameBorder extends AbstractBorder
        implements UIResource {

    private static final long serialVersionUID = 1L;

    // Width of the border on each side in pixels, which is the width of the
    // Metal internal frame border.
    private static final int BORDER_WIDTH = 5;

    // Fill and outer line of the selected frame.
    private final Color activeFill;
    private final Color activeOutline;

    // Fill and outer line of the frames that are not selected.
    private final Color inactiveFill;
    private final Color inactiveOutline;

    /**
     * Creates the border with the colors given by the theme.
     *
     * @param activeFill Fill of the border of the selected frame
     * @param activeOutline Outer line of the selected frame
     * @param inactiveFill Fill of the border of other frames
     * @param inactiveOutline Outer line of other frames
     */
    public ChornobylInternalFrameBorder(Color activeFill, Color activeOutline,
            Color inactiveFill, Color inactiveOutline) {
        this.activeFill = activeFill;
        this.activeOutline = activeOutline;
        this.inactiveFill = inactiveFill;
        this.inactiveOutline = inactiveOutline;
    }

    @Override
    public void paintBorder(Component component, Graphics g, int x, int y,
            int width, int height) {
        boolean selected = component instanceof JInternalFrame frame
                && frame.isSelected();
        Color fill = inactiveFill;
        Color outline = inactiveOutline;
        if (selected) {
            fill = activeFill;
            outline = activeOutline;
        }
        g.setColor(fill);
        for (int ring = 1; ring < BORDER_WIDTH; ring++) {
            g.drawRect(x + ring, y + ring, width - 2 * ring - 1,
                    height - 2 * ring - 1);
        }
        g.setColor(outline);
        g.drawRect(x, y, width - 1, height - 1);
    }

    @Override
    public Insets getBorderInsets(Component component, Insets insets) {
        insets.set(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH);
        return insets;
    }
}
