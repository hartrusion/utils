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

import java.awt.Graphics;
import javax.swing.CellRendererPane;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalComboBoxButton;
import javax.swing.plaf.metal.MetalComboBoxUI;

/**
 * Gives the arrow button of a JComboBox the look of the flat dark buttons of
 * ChornobylMetalTheme. With the Ocean theme, MetalComboBoxUI paints the
 * selected value itself and uses a MetalComboBoxButton only for the arrow at
 * the right side, which is not filled in its released state and draws the
 * arrow in the general text color of the theme. This class creates a
 * subclass of MetalComboBoxButton instead, so that MetalComboBoxUI keeps its
 * layout and behavior, and lets ChornobylButtonPainter paint the button like
 * the arrow buttons of the scroll bars and spinners.
 * <p>
 * The original button is still created by MetalComboBoxUI, but only to take
 * over its margin and its client properties, so that the replacement is
 * configured like the button that MetalComboBoxUI would have used.
 * <p>
 * ChornobylMetalTheme registers this class as ComboBoxUI in the UIDefaults.
 * <p>
 * LLM code generated with Claude Opus 5.5
 *
 * @author Viktor Alexander Hartung
 */
public class ChornobylMetalComboBoxUI extends MetalComboBoxUI {

    public static ComponentUI createUI(JComponent component) {
        return new ChornobylMetalComboBoxUI();
    }

    @Override
    protected JButton createArrowButton() {
        MetalComboBoxButton originalButton
                = (MetalComboBoxButton) super.createArrowButton();
        ComboArrowButton button = new ComboArrowButton(comboBox,
                originalButton.getComboIcon(), originalButton.isIconOnly(),
                currentValuePane, listBox);
        button.setMargin(originalButton.getMargin());
        button.setBorder(originalButton.getBorder());
        // Metal disables the rollover border of this button with a client
        // property, which is taken over as it is.
        button.putClientProperty("NoButtonRollover",
                originalButton.getClientProperty("NoButtonRollover"));
        return button;
    }

    /**
     * Arrow button of the combo box, which keeps the size and the behavior of
     * MetalComboBoxButton and replaces its painting by the flat button of
     * ChornobylButtonPainter. If the button shows the whole value, which
     * MetalComboBoxUI only does without the Ocean theme, the painting of
     * MetalComboBoxButton is kept, because the value would be lost otherwise.
     */
    private static class ComboArrowButton extends MetalComboBoxButton {

        private static final long serialVersionUID = 1L;

        ComboArrowButton(JComboBox<Object> comboBox, Icon icon,
                boolean iconOnly, CellRendererPane rendererPane,
                JList<Object> list) {
            super(comboBox, icon, iconOnly, rendererPane, list);
        }

        @Override
        public void paintComponent(Graphics g) {
            if (!isIconOnly()) {
                super.paintComponent(g);
                return;
            }
            ChornobylButtonPainter.paintArrowButton(g, getModel(),
                    isEnabled(), SwingConstants.SOUTH, getWidth(),
                    getHeight());
        }

        @Override
        protected void paintBorder(Graphics g) {
            if (!isIconOnly()) {
                super.paintBorder(g);
            }
            // The outline is painted by ChornobylButtonPainter.
        }
    }
}
