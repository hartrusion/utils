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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalInternalFrameTitlePane;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalInternalFrameUI;

/**
 * Paints the title bar of internal frames with the dot pattern ("bumps") in
 * colors that the theme defines itself. MetalInternalFrameTitlePane takes the
 * colors of its bumps from the highlight and dark shadow colors of the Metal
 * theme, which are the light upper left and the dark lower right dot of each
 * pair. In ChornobylMetalThemeDark the highlight is darker than the shadow,
 * so that the bumps look as if they were lit from the lower right, and these
 * colors can not be changed globally, because they are also the outline
 * colors of many other elements. This UI therefore uses a subclass of
 * MetalInternalFrameTitlePane that paints the background, the frame icon,
 * the title and the bumps itself, with the bump colors read from the
 * UIDefaults keys that ChornobylMetalTheme writes:
 * ChornobylInternalFrame.activeBumpsHighlight, activeBumpsShadow,
 * inactiveBumpsHighlight, inactiveBumpsShadow, paletteBumpsHighlight and
 * paletteBumpsShadow. The buttons and the behavior of the Metal title pane
 * are kept, and its layout is only changed so far that the buttons are
 * centered vertically like the frame icon and the title.
 * <p>
 * Frames with a right-to-left orientation are painted by the Metal title
 * pane as before, because the theme is only used with left-to-right texts.
 * <p>
 * ChornobylMetalTheme registers this class as InternalFrameUI in the
 * UIDefaults.
 * <p>
 * LLM code generated with Claude Opus 5.5
 *
 * @author Viktor Alexander Hartung
 */
public class ChornobylMetalInternalFrameUI extends MetalInternalFrameUI {

    // Distance between the elements of the title bar in pixels, which is the
    // distance that MetalInternalFrameTitlePane uses.
    private static final int TITLE_GAP = 5;

    // Space that the title text keeps to the title bar buttons.
    private static final int TITLE_BUTTON_GAP = 4;

    // Distance of the bumps to the upper and lower edge of the title bar.
    private static final int BUMPS_INSET = 3;

    // Distance of the bumps to the edges of the small palette title bar.
    private static final int PALETTE_BUMPS_INSET = 2;

    // Size of the repeating tile of the bump pattern in pixels.
    private static final int BUMPS_TILE = 4;

    public ChornobylMetalInternalFrameUI(JInternalFrame frame) {
        super(frame);
    }

    public static ComponentUI createUI(JComponent component) {
        return new ChornobylMetalInternalFrameUI((JInternalFrame) component);
    }

    @Override
    protected JComponent createNorthPane(JInternalFrame internalFrame) {
        // MetalInternalFrameUI casts the titlePane field of BasicInternalFrameUI
        // to MetalInternalFrameTitlePane when the palette or frame type
        // changes, so the field has to hold the replacement as well.
        titlePane = new ChornobylTitlePane(internalFrame);
        return titlePane;
    }

    /**
     * Draws the bump pattern of the Metal look and feel, which repeats a
     * tile of four times four pixels with two highlight pixels on its
     * diagonal and two shadow pixels on the diagonal below them.
     *
     * @param g Graphics to paint on
     * @param x Left edge of the bump area
     * @param y Upper edge of the bump area
     * @param width Width of the bump area
     * @param height Height of the bump area
     * @param highlight Color of the upper left pixel of each pair
     * @param shadow Color of the lower right pixel of each pair
     */
    private static void paintBumps(Graphics g, int x, int y, int width,
            int height, Color highlight, Color shadow) {
        for (int tileX = 0; tileX < width; tileX += BUMPS_TILE) {
            for (int tileY = 0; tileY < height; tileY += BUMPS_TILE) {
                g.setColor(highlight);
                paintPixel(g, x, y, width, height, tileX, tileY);
                paintPixel(g, x, y, width, height, tileX + 2, tileY + 2);
                g.setColor(shadow);
                paintPixel(g, x, y, width, height, tileX + 1, tileY + 1);
                paintPixel(g, x, y, width, height, tileX + 3, tileY + 3);
            }
        }
    }

    /**
     * Paints a single pixel of the bump pattern if it lies inside the bump
     * area, so that the pattern ends exactly at the area edges.
     */
    private static void paintPixel(Graphics g, int x, int y, int width,
            int height, int pixelX, int pixelY) {
        if (pixelX < width && pixelY < height) {
            g.fillRect(x + pixelX, y + pixelY, 1, 1);
        }
    }

    /**
     * Title bar of an internal frame that keeps the buttons and the layout of
     * MetalInternalFrameTitlePane and only replaces its painting.
     */
    private static class ChornobylTitlePane
            extends MetalInternalFrameTitlePane {

        private static final long serialVersionUID = 1L;

        ChornobylTitlePane(JInternalFrame internalFrame) {
            super(internalFrame);
        }

        @Override
        protected LayoutManager createLayout() {
            return new CenteredButtonsLayout(super.createLayout());
        }

        /**
         * Layout of the title bar that lets the layout of the Metal title
         * pane place the buttons and then centers them vertically. Metal
         * places the buttons two pixels below the upper edge, while the frame
         * icon and the title are centered, so that the buttons looked as if
         * they were placed too high. The small palette title bar is left as
         * Metal lays it out.
         */
        private class CenteredButtonsLayout implements LayoutManager {

            // Layout of MetalInternalFrameTitlePane, which does the work.
            private final LayoutManager metalLayout;

            CenteredButtonsLayout(LayoutManager metalLayout) {
                this.metalLayout = metalLayout;
            }

            @Override
            public void addLayoutComponent(String name, Component component) {
                metalLayout.addLayoutComponent(name, component);
            }

            @Override
            public void removeLayoutComponent(Component component) {
                metalLayout.removeLayoutComponent(component);
            }

            @Override
            public Dimension preferredLayoutSize(Container container) {
                return metalLayout.preferredLayoutSize(container);
            }

            @Override
            public Dimension minimumLayoutSize(Container container) {
                return metalLayout.minimumLayoutSize(container);
            }

            @Override
            public void layoutContainer(Container container) {
                metalLayout.layoutContainer(container);
                if (isPalette) {
                    return;
                }
                for (Component child : container.getComponents()) {
                    // Centered the same way as the frame icon is painted
                    int centeredY = container.getHeight() / 2
                            - child.getHeight() / 2;
                    child.setLocation(child.getX(), centeredY);
                }
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            if (isPalette) {
                paintPalette(g);
                return;
            }
            if (!frame.getComponentOrientation().isLeftToRight()) {
                super.paintComponent(g);
                return;
            }
            int width = getWidth();
            int height = getHeight();
            boolean selected = frame.isSelected();
            Color background;
            Color foreground;
            Color separator;
            Color bumpsHighlight;
            Color bumpsShadow;
            if (selected) {
                background = UIManager.getColor(
                        "InternalFrame.activeTitleBackground");
                foreground = UIManager.getColor(
                        "InternalFrame.activeTitleForeground");
                separator = MetalLookAndFeel.getPrimaryControlDarkShadow();
                bumpsHighlight = UIManager.getColor(
                        "ChornobylInternalFrame.activeBumpsHighlight");
                bumpsShadow = UIManager.getColor(
                        "ChornobylInternalFrame.activeBumpsShadow");
            } else {
                background = UIManager.getColor(
                        "InternalFrame.inactiveTitleBackground");
                foreground = UIManager.getColor(
                        "InternalFrame.inactiveTitleForeground");
                separator = MetalLookAndFeel.getControlDarkShadow();
                bumpsHighlight = UIManager.getColor(
                        "ChornobylInternalFrame.inactiveBumpsHighlight");
                bumpsShadow = UIManager.getColor(
                        "ChornobylInternalFrame.inactiveBumpsShadow");
            }
            g.setColor(background);
            g.fillRect(0, 0, width, height);
            g.setColor(separator);
            g.drawLine(0, height - 1, width, height - 1);

            int xOffset = TITLE_GAP;
            Icon frameIcon = frame.getFrameIcon();
            if (frameIcon != null) {
                int iconY = height / 2 - frameIcon.getIconHeight() / 2;
                frameIcon.paintIcon(frame, g, xOffset, iconY);
                xOffset += frameIcon.getIconWidth() + TITLE_GAP;
            }
            int buttonsX = getLeftmostButtonX();
            String title = frame.getTitle();
            if (title != null && !title.isEmpty()) {
                Graphics2D g2 = (Graphics2D) g.create();
                // Use the text antialiasing of the desktop like Swing does
                Object desktopHints = Toolkit.getDefaultToolkit()
                        .getDesktopProperty("awt.font.desktophints");
                if (desktopHints instanceof Map<?, ?> hints) {
                    g2.addRenderingHints(hints);
                } else {
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                }
                g2.setFont(getFont());
                FontMetrics metrics = g2.getFontMetrics();
                // Metal keeps four pixels between the title and the buttons
                String clippedTitle = getTitle(title, metrics,
                        buttonsX - xOffset - TITLE_BUTTON_GAP);
                int textY = (height - metrics.getHeight()) / 2
                        + metrics.getAscent();
                g2.setColor(foreground);
                g2.drawString(clippedTitle, xOffset, textY);
                g2.dispose();
                xOffset += metrics.stringWidth(clippedTitle) + TITLE_GAP;
            }
            int bumpsWidth = buttonsX - TITLE_GAP - xOffset;
            if (bumpsWidth > 0) {
                paintBumps(g, xOffset, BUMPS_INSET, bumpsWidth,
                        height - 2 * BUMPS_INSET, bumpsHighlight, bumpsShadow);
            }
        }

        @Override
        public void paintPalette(Graphics g) {
            int width = getWidth();
            int height = getHeight();
            // Same colors as MetalInternalFrameTitlePane uses for palettes
            g.setColor(MetalLookAndFeel.getPrimaryControlShadow());
            g.fillRect(0, 0, width, height);
            g.setColor(MetalLookAndFeel.getPrimaryControlDarkShadow());
            g.drawLine(0, height - 1, width, height - 1);
            int bumpsWidth = getLeftmostButtonX() - 2 * PALETTE_BUMPS_INSET;
            paintBumps(g, PALETTE_BUMPS_INSET, PALETTE_BUMPS_INSET, bumpsWidth,
                    height - 2 * PALETTE_BUMPS_INSET,
                    UIManager.getColor(
                            "ChornobylInternalFrame.paletteBumpsHighlight"),
                    UIManager.getColor(
                            "ChornobylInternalFrame.paletteBumpsShadow"));
        }

        /**
         * Returns the left edge of the leftmost visible title bar button, so
         * that the title and the bumps end in front of the buttons.
         *
         * @return x coordinate of the first button, or the width of the title
         * bar minus a gap if there is no button
         */
        private int getLeftmostButtonX() {
            int leftmostX = getWidth() - TITLE_GAP;
            for (Component child : getComponents()) {
                if (child.isVisible() && child.getWidth() > 0) {
                    leftmostX = Math.min(leftmostX, child.getX());
                }
            }
            return leftmostX;
        }
    }
}
