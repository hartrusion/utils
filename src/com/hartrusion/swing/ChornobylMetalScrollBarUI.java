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
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
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
 * The arrow buttons and the thumb are painted by ChornobylButtonPainter,
 * which is shared with the spinner and combo box UIs, so that they get the
 * fill, the outline and the arrows of the flat buttons, while the thumb takes
 * the pressed color while it is dragged. The grip lines of the thumb are
 * drawn flat without the highlight shadow of the Ocean theme. The track is
 * filled with ScrollBar.track and without the frame that Metal draws around
 * it, because that frame surrounds the thumb and makes it look like an
 * embedded element instead of a button. As all colors are read from the
 * UIDefaults, ChornobylMetalThemeDark gets its own dark colors without a
 * subclass of this UI.
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

    // Color of the grip lines, which lies between the fill and the arrow
    // color, so that the lines are visible without dominating the thumb.
    private Color gripColor;

    public static ComponentUI createUI(JComponent component) {
        return new ChornobylMetalScrollBarUI();
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
        gripColor = mixColors(UIManager.getColor("Button.background"),
                ChornobylButtonPainter.getButtonForeground(true));
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
        ChornobylButtonPainter.paintButtonBody(g, thumbBounds.x,
                thumbBounds.y, thumbBounds.width, thumbBounds.height,
                isDragging, true);

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
     * Arrow button at the ends of the scroll bar, which keeps the size
     * calculation of MetalScrollButton and only replaces its painting by the
     * flat button of ChornobylButtonPainter.
     */
    private static class ChornobylScrollButton extends MetalScrollButton {

        private static final long serialVersionUID = 1L;

        ChornobylScrollButton(int direction, int width,
                boolean freeStanding) {
            super(direction, width, freeStanding);
        }

        @Override
        public void paint(Graphics g) {
            ChornobylButtonPainter.paintArrowButton(g, getModel(),
                    isEnabled(), getDirection(), getWidth(), getHeight());
        }
    }
}
