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
 * which the theme is modeled on, in the light and in the dark theme.
 * <p>
 * ChornobylMetalTheme writes the icons into CheckBox.icon and
 * RadioButton.icon of the UIDefaults. The colors are taken from
 * ChornobylButtonPainter at paint time, so that both themes use the same
 * icons.
 * <p>
 * LLM code generated with Claude Opus 5.5
 *
 * @author Viktor Alexander Hartung
 */
public final class ChornobylMetalIconFactory {

    // Width and height of both icons, which is the size of the Metal icons,
    // so that the text of check boxes and radio buttons keeps its position.
    private static final int ICON_SIZE = 13;

    private ChornobylMetalIconFactory() {
        // Only static methods
    }

    public static Icon getCheckBoxIcon() {
        return new CheckBoxIcon();
    }

    public static Icon getRadioButtonIcon() {
        return new RadioButtonIcon();
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
