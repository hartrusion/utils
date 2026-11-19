/*
 * Copyright (C) 2026 Viktor Alexander Hartung
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.hartrusion.swing;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ButtonModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * Paints small elements in the style of the flat dark buttons of
 * ChornobylMetalTheme, which are the arrow buttons of scroll bars, spinners
 * and combo boxes and the thumb of the scroll bar. The Metal look and feel
 * paints these elements with their own classes, which take the track color
 * or the panel color as fill and the general text color of the theme for
 * their arrows, so that they can not be given the button style with colors
 * of the UIDefaults alone. The UI classes ChornobylMetalScrollBarUI,
 * ChornobylMetalSpinnerUI and ChornobylMetalComboBoxUI therefore replace the
 * paint methods of these elements and call this class, so that all of them
 * share one implementation of the button look.
 * <p>
 * All colors are read from the UIDefaults at paint time (Button.background,
 * Button.select, Button.foreground, Button.disabledText) and from the
 * current Metal theme for the outline, so that ChornobylMetalThemeDark gets
 * its own colors without any further code.
 * <p>
 * LLM code generated with Claude Opus 5.5
 *
 * @author Viktor Alexander Hartung
 */
public final class ChornobylButtonPainter {

    private ChornobylButtonPainter() {
        // Only static methods
    }

    /**
     * Paints a complete arrow button with its fill, outline and arrow. The
     * button is painted pressed while the mouse holds it down, which is the
     * same condition the Metal buttons use.
     *
     * @param g Graphics of the button
     * @param model Model of the button that holds the pressed state
     * @param enabled Enabled state of the button
     * @param direction SwingConstants direction the arrow points to
     * @param width Width of the button
     * @param height Height of the button
     */
    public static void paintArrowButton(Graphics g, ButtonModel model,
            boolean enabled, int direction, int width, int height) {
        boolean pressed = model.isArmed() && model.isPressed();
        paintButtonBody(g, 0, 0, width, height, pressed, enabled);
        paintArrow(g, direction, width, height, enabled);
    }

    /**
     * Paints the fill and the one pixel outline of a flat button. The
     * outline uses the same theme color as the border of the buttons of the
     * Metal look and feel.
     *
     * @param g Graphics to paint on
     * @param x Left edge
     * @param y Upper edge
     * @param width Width of the button
     * @param height Height of the button
     * @param pressed true fills with the color of a pressed button
     * @param enabled false paints the outline in the disabled color
     */
    public static void paintButtonBody(Graphics g, int x, int y, int width,
            int height, boolean pressed, boolean enabled) {
        if (pressed) {
            g.setColor(UIManager.getColor("Button.select"));
        } else {
            g.setColor(UIManager.getColor("Button.background"));
        }
        g.fillRect(x, y, width, height);
        if (enabled) {
            g.setColor(MetalLookAndFeel.getControlDarkShadow());
        } else {
            g.setColor(MetalLookAndFeel.getControlShadow());
        }
        g.drawRect(x, y, width - 1, height - 1);
    }

    /**
     * Returns the color of the arrows and texts on the buttons.
     *
     * @param enabled false returns the color of disabled buttons
     * @return Foreground color of the flat buttons
     */
    public static Color getButtonForeground(boolean enabled) {
        Color foreground = null;
        if (enabled) {
            foreground = UIManager.getColor("Button.foreground");
        } else {
            foreground = UIManager.getColor("Button.disabledText");
        }
        if (foreground == null) {
            foreground = MetalLookAndFeel.getControlTextColor();
        }
        return foreground;
    }

    /**
     * Draws a filled triangle line by line in the center of a button, which
     * gives the same sharp pixel arrows as the Metal look and feel without
     * antialiasing. The triangle has a base of 2 * arrowHeight - 1 pixels,
     * where arrowHeight grows with the smaller side of the button, and its
     * tip points into the given direction.
     *
     * @param g Graphics to paint on
     * @param direction SwingConstants direction the arrow points to
     * @param width Width of the button
     * @param height Height of the button
     * @param enabled false paints the arrow in the disabled color
     */
    public static void paintArrow(Graphics g, int direction, int width,
            int height, boolean enabled) {
        g.setColor(getButtonForeground(enabled));
        int arrowHeight = Math.max(2, (Math.min(width, height) + 2) / 4);
        int centerX = width / 2;
        int centerY = height / 2;
        int startX = centerX - arrowHeight / 2;
        int startY = centerY - arrowHeight / 2;
        for (int row = 0; row < arrowHeight; row++) {
            int widening = arrowHeight - 1 - row;
            switch (direction) {
                case SwingConstants.NORTH ->
                    g.drawLine(centerX - row, startY + row,
                            centerX + row, startY + row);
                case SwingConstants.SOUTH ->
                    g.drawLine(centerX - widening, startY + row,
                            centerX + widening, startY + row);
                case SwingConstants.WEST ->
                    g.drawLine(startX + row, centerY - row,
                            startX + row, centerY + row);
                default ->
                    g.drawLine(startX + row, centerY - widening,
                            startX + row, centerY + widening);
            }
        }
    }
}
