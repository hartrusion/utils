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
