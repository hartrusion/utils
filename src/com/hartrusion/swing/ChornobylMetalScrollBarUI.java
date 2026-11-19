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
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.plaf.metal.MetalScrollButton;

/**
 * Paints the scroll bars of the ChornobylMetalTheme in the style of its flat
 * dark buttons. The Metal look and feel only allows to change the colors of
 * the scroll bar through the UIDefaults, but the arrow buttons at both ends
 * take their fill from the color of the track and draw their arrow in the
 * general text color of the theme, so that a dark button would get an
 * invisible arrow. This class therefore replaces the painting of the arrow
 * buttons, the thumb and the track, while the layout, the sizes and the
 * behavior of MetalScrollBarUI are kept.
 * <p>
 * The arrow buttons and the thumb are filled with the colors of the buttons
 * (Button.background, Button.select while pressed or dragged) and get the
 * same outline as the buttons, the arrows are drawn in Button.foreground and
 * the grip lines of the thumb are drawn flat without the highlight shadow of
 * the Ocean theme. The track is filled without the frame that Metal draws
 * around it, because that frame surrounds the thumb and makes it look like
 * an embedded element instead of a button. As all colors are read from the
 * UIDefaults when the UI is installed, ChornobylMetalThemeDark gets its own
 * dark colors without a subclass of this UI.
 * <p>
 * ChornobylMetalTheme registers this class as ScrollBarUI in the UIDefaults,
 * so that it is used by every JScrollBar and JScrollPane while the theme is
 * active.
 * <p>
 * LLM code generated with Claude Opus 5.5
 *
 * @author Viktor Alexander Hartung
 */
public class ChornobylMetalScrollBarUI extends MetalScrollBarUI {

    // Distance of the grip lines to the long edges of the thumb.
    private static final int GRIP_INSET = 4;

    // Distance between two grip lines in pixels.
    private static final int GRIP_SPACING = 2;

    // Number of grip lines in the center of the thumb.
    private static final int GRIP_LINE_COUNT = 3;

    // Fill of the arrow buttons and the thumb in their normal state.
    private Color buttonReleasedColor;

    // Fill of a pressed arrow button and of the thumb while it is dragged.
    private Color buttonPressedColor;

    // Color of the arrows on enabled arrow buttons.
    private Color arrowColor;

    // Color of the arrows on disabled arrow buttons.
    private Color arrowDisabledColor;

    // Color of the grip lines, which lies between the fill and the arrow
    // color, so that the lines are visible without dominating the thumb.
    private Color gripColor;

    public static ComponentUI createUI(JComponent component) {
        return new ChornobylMetalScrollBarUI();
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
        buttonReleasedColor = UIManager.getColor("Button.background");
        buttonPressedColor = UIManager.getColor("Button.select");
        arrowColor = UIManager.getColor("Button.foreground");
        arrowDisabledColor = UIManager.getColor("Button.disabledText");
        if (arrowDisabledColor == null) {
            arrowDisabledColor = MetalLookAndFeel.getInactiveControlTextColor();
        }
        gripColor = mixColors(buttonReleasedColor, arrowColor);
    }

    /**
     * Returns the color in the middle between two colors.
     *
     * @param first First color
     * @param second Second color
     * @return Color with the average of each component
     */
    private static Color mixColors(Color first, Color second) {
        return new Color((first.getRed() + second.getRed()) / 2,
                (first.getGreen() + second.getGreen()) / 2,
                (first.getBlue() + second.getBlue()) / 2);
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        // MetalScrollBarUI uses the decreaseButton field for its layout, so
        // the button has to be stored there as well.
        decreaseButton = new ChornobylScrollButton(orientation,
                scrollBarWidth, isFreeStanding);
        return decreaseButton;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        increaseButton = new ChornobylScrollButton(orientation,
                scrollBarWidth, isFreeStanding);
        return increaseButton;
    }

    /**
     * Fills the track flat with the track color of the theme, without the
     * frame that MetalScrollBarUI draws around the area between the arrow
     * buttons.
     */
    @Override
    protected void paintTrack(Graphics g, JComponent component,
            Rectangle trackBounds) {
        g.setColor(trackColor);
        g.fillRect(trackBounds.x, trackBounds.y,
                trackBounds.width, trackBounds.height);
    }

    /**
     * Paints the thumb like a flat button over the full width of the scroll
     * bar, with the pressed color while it is dragged and with three flat
     * grip lines in its center. A disabled scroll bar has no thumb, as in the
     * Metal look and feel.
     */
    @Override
    protected void paintThumb(Graphics g, JComponent component,
            Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }
        Color fillColor = buttonReleasedColor;
        if (isDragging) {
            fillColor = buttonPressedColor;
        }
        paintButtonBody(g, thumbBounds.x, thumbBounds.y,
                thumbBounds.width, thumbBounds.height, fillColor, true);

        g.setColor(gripColor);
        int gripExtent = (GRIP_LINE_COUNT - 1) * GRIP_SPACING;
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            if (thumbBounds.height < gripExtent + 2 * GRIP_INSET) {
                return;
            }
            int firstLineY = thumbBounds.y
                    + (thumbBounds.height - gripExtent) / 2;
            for (int idx = 0; idx < GRIP_LINE_COUNT; idx++) {
                int lineY = firstLineY + idx * GRIP_SPACING;
                g.drawLine(thumbBounds.x + GRIP_INSET, lineY,
                        thumbBounds.x + thumbBounds.width - 1 - GRIP_INSET,
                        lineY);
            }
        } else {
            if (thumbBounds.width < gripExtent + 2 * GRIP_INSET) {
                return;
            }
            int firstLineX = thumbBounds.x
                    + (thumbBounds.width - gripExtent) / 2;
            for (int idx = 0; idx < GRIP_LINE_COUNT; idx++) {
                int lineX = firstLineX + idx * GRIP_SPACING;
                g.drawLine(lineX, thumbBounds.y + GRIP_INSET,
                        lineX,
                        thumbBounds.y + thumbBounds.height - 1 - GRIP_INSET);
            }
        }
    }

    /**
     * Paints the fill and the one pixel outline of a flat button, which is
     * shared by the thumb and the arrow buttons. The outline uses the same
     * theme color as the border of the buttons of the Metal look and feel.
     *
     * @param g Graphics to paint on
     * @param x Left edge
     * @param y Upper edge
     * @param width Width of the button
     * @param height Height of the button
     * @param fillColor Fill inside the outline
     * @param enabled false paints the outline in the disabled color
     */
    private static void paintButtonBody(Graphics g, int x, int y, int width,
            int height, Color fillColor, boolean enabled) {
        g.setColor(fillColor);
        g.fillRect(x, y, width, height);
        if (enabled) {
            g.setColor(MetalLookAndFeel.getControlDarkShadow());
        } else {
            g.setColor(MetalLookAndFeel.getControlShadow());
        }
        g.drawRect(x, y, width - 1, height - 1);
    }

    /**
     * Arrow button at the ends of the scroll bar, which keeps the size
     * calculation of MetalScrollButton and only replaces its painting by a
     * flat button with an arrow in the text color of the buttons. It is an
     * inner class because it reads the colors that the enclosing UI has taken
     * from the UIDefaults.
     */
    private class ChornobylScrollButton extends MetalScrollButton {

        private static final long serialVersionUID = 1L;

        ChornobylScrollButton(int direction, int width,
                boolean freeStanding) {
            super(direction, width, freeStanding);
        }

        @Override
        public void paint(Graphics g) {
            int width = getWidth();
            int height = getHeight();
            Color fillColor = buttonReleasedColor;
            if (getModel().isArmed() && getModel().isPressed()) {
                fillColor = buttonPressedColor;
            }
            paintButtonBody(g, 0, 0, width, height, fillColor, isEnabled());

            if (isEnabled()) {
                g.setColor(arrowColor);
            } else {
                g.setColor(arrowDisabledColor);
            }
            paintArrow(g, getDirection(), width, height);
        }

        /**
         * Draws a filled triangle line by line, which gives the same sharp
         * pixel arrows as the Metal look and feel without antialiasing. The
         * triangle has a base of 2 * arrowHeight - 1 pixels and its tip
         * points into the scroll direction.
         *
         * @param g Graphics to paint on
         * @param direction SwingConstants direction of the button
         * @param width Width of the button
         * @param height Height of the button
         */
        private void paintArrow(Graphics g, int direction, int width,
                int height) {
            int arrowHeight = (Math.min(width, height) + 2) / 4;
            int centerX = width / 2;
            int centerY = height / 2;
            int startX = centerX - arrowHeight / 2;
            int startY = centerY - arrowHeight / 2;
            for (int row = 0; row < arrowHeight; row++) {
                switch (direction) {
                    case SwingConstants.NORTH ->
                        g.drawLine(centerX - row, startY + row,
                                centerX + row, startY + row);
                    case SwingConstants.SOUTH -> {
                        int halfWidth = arrowHeight - 1 - row;
                        g.drawLine(centerX - halfWidth, startY + row,
                                centerX + halfWidth, startY + row);
                    }
                    case SwingConstants.WEST ->
                        g.drawLine(startX + row, centerY - row,
                                startX + row, centerY + row);
                    default -> {
                        int halfWidth = arrowHeight - 1 - row;
                        g.drawLine(startX + row, centerY - halfWidth,
                                startX + row, centerY + halfWidth);
                    }
                }
            }
        }
    }
}
