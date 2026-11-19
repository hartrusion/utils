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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.plaf.UIResource;

/**
 * Provides the icons of check boxes and radio buttons in the style of the
 * flat dark buttons of ChornobylMetalTheme, in the same way as the
 * MetalIconFactory provides them for the Metal look and feel. The Ocean
 * theme paints these icons with a light gradient (CheckBox.gradient,
 * RadioButton.gradient) and a dark check mark, which can not be turned into
 * the button look with colors alone and which was hardly usable in the dark
 * theme. The icons of this class are filled with the button color, get the
 * button outline and draw the check mark or the dot in the text color of the
 * buttons, so that they look like the black switches of the control panel,
 * which the theme is modeled on, in the light and in the dark theme. The
 * title bar buttons of internal frames (close, iconify, maximize, restore
 * and the small close button of palette frames) are provided in the same
 * style, because the images of the Ocean theme contain blue and a shading
 * that looks as if the light came from the lower right.
 * <p>
 * ChornobylMetalTheme writes the icons into CheckBox.icon, RadioButton.icon
 * and the InternalFrame icon keys of the UIDefaults. The colors are taken
 * from ChornobylButtonPainter at paint time, so that both themes use the
 * same icons.
 * <p>
 * LLM code generated with Claude Opus 5.5
 *
 * @author Viktor Alexander Hartung
 */
public final class ChornobylMetalIconFactory {

    // Width and height of both icons, which is the size of the Metal icons,
    // so that the text of check boxes and radio buttons keeps its position.
    private static final int ICON_SIZE = 13;

    // Size of the title bar buttons of internal frames, which is the size of
    // the icons of the Ocean theme, so that the title bar keeps its height.
    private static final int TITLE_ICON_SIZE = 16;

    // Size of the close button in the small title bar of palette frames.
    private static final int PALETTE_ICON_SIZE = 7;

    /**
     * Symbols of the title bar buttons of internal frames. RESTORE is the
     * button that returns a maximized frame to its normal size, which Swing
     * calls minimize icon.
     */
    private enum TitleButtonSymbol {
        CLOSE, ICONIFY, MAXIMIZE, RESTORE
    }

    private ChornobylMetalIconFactory() {
        // Only static methods
    }

    public static Icon getCheckBoxIcon() {
        return new CheckBoxIcon();
    }

    public static Icon getRadioButtonIcon() {
        return new RadioButtonIcon();
    }

    public static Icon getInternalFrameCloseIcon() {
        return new TitleButtonIcon(TitleButtonSymbol.CLOSE, TITLE_ICON_SIZE);
    }

    public static Icon getInternalFrameIconifyIcon() {
        return new TitleButtonIcon(TitleButtonSymbol.ICONIFY, TITLE_ICON_SIZE);
    }

    public static Icon getInternalFrameMaximizeIcon() {
        return new TitleButtonIcon(TitleButtonSymbol.MAXIMIZE,
                TITLE_ICON_SIZE);
    }

    public static Icon getInternalFrameMinimizeIcon() {
        return new TitleButtonIcon(TitleButtonSymbol.RESTORE,
                TITLE_ICON_SIZE);
    }

    public static Icon getInternalFramePaletteCloseIcon() {
        return new TitleButtonIcon(TitleButtonSymbol.CLOSE,
                PALETTE_ICON_SIZE);
    }

    /**
     * Square flat button with a check mark of two pixels width while the
     * check box is selected. A check box that is held down with the mouse is
     * painted in the pressed button color.
     */
    private static class CheckBoxIcon implements Icon, UIResource,
            Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public void paintIcon(Component component, Graphics g, int x, int y) {
            ButtonModel model = ((AbstractButton) component).getModel();
            boolean enabled = model.isEnabled();
            boolean pressed = model.isPressed() && model.isArmed();
            ChornobylButtonPainter.paintButtonBody(g, x, y, ICON_SIZE,
                    ICON_SIZE, pressed, enabled);
            if (!model.isSelected()) {
                return;
            }
            g.setColor(ChornobylButtonPainter.getButtonForeground(enabled));
            // Two parallel lines for each leg give a check mark of two
            // pixels width, the short leg goes down and the long leg up.
            for (int offset = 0; offset < 2; offset++) {
                g.drawLine(x + 3, y + 5 + offset, x + 5, y + 7 + offset);
                g.drawLine(x + 5, y + 7 + offset, x + 9, y + 3 + offset);
            }
        }

        @Override
        public int getIconWidth() {
            return ICON_SIZE;
        }

        @Override
        public int getIconHeight() {
            return ICON_SIZE;
        }
    }

    /**
     * Title bar button of an internal frame, which is a flat button with a
     * light symbol and without any shading. The Ocean theme uses images
     * here, which contain blue and light edges at the lower right side, so
     * that they look as if they were lit from the lower right. The symbols
     * are drawn line by line for sharp pixels, and the button is painted in
     * the pressed color while the mouse holds it down.
     */
    private static class TitleButtonIcon implements Icon, UIResource,
            Serializable {

        private static final long serialVersionUID = 1L;

        // Symbol drawn on the button.
        private final TitleButtonSymbol symbol;

        // Width and height of the button in pixels.
        private final int size;

        TitleButtonIcon(TitleButtonSymbol symbol, int size) {
            this.symbol = symbol;
            this.size = size;
        }

        @Override
        public void paintIcon(Component component, Graphics g, int x, int y) {
            boolean pressed = false;
            boolean enabled = true;
            if (component instanceof AbstractButton button) {
                ButtonModel model = button.getModel();
                pressed = model.isPressed() && model.isArmed();
                enabled = model.isEnabled();
            }
            ChornobylButtonPainter.paintButtonBody(g, x, y, size, size,
                    pressed, enabled);
            g.setColor(ChornobylButtonPainter.getButtonForeground(enabled));
            if (size < TITLE_ICON_SIZE) {
                // The small palette close button only has room for a thin X
                g.drawLine(x + 2, y + 2, x + size - 3, y + size - 3);
                g.drawLine(x + size - 3, y + 2, x + 2, y + size - 3);
                return;
            }
            switch (symbol) {
                case CLOSE -> {
                    // X of three pixels width
                    g.drawLine(x + 4, y + 4, x + 11, y + 11);
                    g.drawLine(x + 5, y + 4, x + 11, y + 10);
                    g.drawLine(x + 4, y + 5, x + 10, y + 11);
                    g.drawLine(x + 11, y + 4, x + 4, y + 11);
                    g.drawLine(x + 10, y + 4, x + 4, y + 10);
                    g.drawLine(x + 11, y + 5, x + 5, y + 11);
                }
                case ICONIFY ->
                    g.fillRect(x + 4, y + 10, 8, 2);
                case MAXIMIZE -> {
                    // Window outline with a thicker title bar on top
                    g.drawRect(x + 4, y + 4, 7, 7);
                    g.drawLine(x + 4, y + 5, x + 11, y + 5);
                }
                case RESTORE -> {
                    // Two overlapping windows, the front one hides the
                    // lower left part of the back one.
                    g.drawRect(x + 6, y + 3, 6, 6);
                    g.drawLine(x + 6, y + 4, x + 12, y + 4);
                    g.setColor(ChornobylButtonPainter.getButtonFill(pressed));
                    g.fillRect(x + 3, y + 6, 7, 7);
                    g.setColor(
                            ChornobylButtonPainter.getButtonForeground(enabled));
                    g.drawRect(x + 3, y + 6, 6, 6);
                    g.drawLine(x + 3, y + 7, x + 9, y + 7);
                }
                default -> {
                    // All symbols are handled above
                }
            }
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }
    }

    /**
     * Round flat button with a dot in its center while the radio button is
     * selected. The circle is painted with antialiasing, because a circle of
     * this size looks ragged without it.
     */
    private static class RadioButtonIcon implements Icon, UIResource,
            Serializable {

        private static final long serialVersionUID = 1L;

        // Diameter of the dot of a selected radio button.
        private static final double DOT_SIZE = 5.0;

        @Override
        public void paintIcon(Component component, Graphics g, int x, int y) {
            ButtonModel model = ((AbstractButton) component).getModel();
            boolean enabled = model.isEnabled();
            boolean pressed = model.isPressed() && model.isArmed();
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            // The outline is drawn on the pixel centers, so the circle is
            // shifted by half a pixel to keep it inside the icon.
            Ellipse2D circle = new Ellipse2D.Double(x + 0.5, y + 0.5,
                    ICON_SIZE - 1, ICON_SIZE - 1);
            g2.setColor(ChornobylButtonPainter.getButtonFill(pressed));
            g2.fill(circle);
            g2.setColor(ChornobylButtonPainter.getButtonOutline(enabled));
            g2.draw(circle);
            if (model.isSelected()) {
                double dotOffset = (ICON_SIZE - DOT_SIZE) / 2.0;
                g2.setColor(
                        ChornobylButtonPainter.getButtonForeground(enabled));
                g2.fill(new Ellipse2D.Double(x + dotOffset, y + dotOffset,
                        DOT_SIZE, DOT_SIZE));
            }
            g2.dispose();
        }

        @Override
        public int getIconWidth() {
            return ICON_SIZE;
        }

        @Override
        public int getIconHeight() {
            return ICON_SIZE;
        }
    }
}
