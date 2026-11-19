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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicSpinnerUI;

/**
 * Gives the two arrow buttons of a JSpinner the look of the flat dark
 * buttons of ChornobylMetalTheme. The Metal look and feel has no spinner UI
 * of its own and uses BasicSpinnerUI, whose BasicArrowButtons are filled with
 * the light control color and draw a dark arrow, so that they can not be
 * changed by the colors of the UIDefaults alone. This class only replaces
 * the creation of the two buttons, which are painted by
 * ChornobylButtonPainter like the arrow buttons of the scroll bars and combo
 * boxes, while the editor, the layout and the behavior of BasicSpinnerUI are
 * kept.
 * <p>
 * ChornobylMetalTheme registers this class as SpinnerUI in the UIDefaults.
 * <p>
 * LLM code generated with Claude Opus 5.5
 *
 * @author Viktor Alexander Hartung
 */
public class ChornobylMetalSpinnerUI extends BasicSpinnerUI {

    public static ComponentUI createUI(JComponent component) {
        return new ChornobylMetalSpinnerUI();
    }

    @Override
    protected Component createNextButton() {
        // The name and the listeners are required by BasicSpinnerUI, which
        // finds the buttons by their name and increments the value through
        // the installed listeners.
        JButton button = new SpinnerArrowButton(SwingConstants.NORTH);
        button.setName("Spinner.nextButton");
        installNextButtonListeners(button);
        return button;
    }

    @Override
    protected Component createPreviousButton() {
        JButton button = new SpinnerArrowButton(SwingConstants.SOUTH);
        button.setName("Spinner.previousButton");
        installPreviousButtonListeners(button);
        return button;
    }

    /**
     * Arrow button of the spinner, which keeps the size and the behavior of
     * BasicArrowButton and only replaces its painting by the flat button of
     * ChornobylButtonPainter.
     */
    private static class SpinnerArrowButton extends BasicArrowButton {

        private static final long serialVersionUID = 1L;

        SpinnerArrowButton(int direction) {
            super(direction);
            setInheritsPopupMenu(true);
        }

        @Override
        public void paint(Graphics g) {
            ChornobylButtonPainter.paintArrowButton(g, getModel(),
                    isEnabled(), getDirection(), getWidth(), getHeight());
        }
    }
}
