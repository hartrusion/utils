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
import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;

/**
 * Dark variant of ChornobylMetalTheme, which keeps the Metal look and feel
 * with all its shapes of buttons, borders and sliders and only replaces the
 * colors. The class derives from ChornobylMetalTheme, so that the removal of
 * the gradients and all other UIDefaults entries of the light theme also
 * apply here, while each color getter is overridden with a dark color. The
 * colors of the panel buttons are overridden as well, because AbstractPanelWidget
 * reads them from the active theme, so that the colored state buttons of the
 * control panels can be adapted to the dark background in this class.
 * <p>
 * The Metal look and feel takes the text color from getBlack() and the
 * background of text fields, lists and tables from getWhite(), which is the
 * reason why these two getters are overridden with a light and a dark color
 * in this dark theme.
 * <p>
 * LLM code generated with Claude Opus 5.5
 *
 * @author Viktor Alexander Hartung
 */
public class ChornobylMetalThemeDark extends ChornobylMetalTheme {

    // Menu element outlines, table and tree selection frames
    private static final ColorUIResource PRIMARY1
            = new ColorUIResource(0x9A9A9A);

    // Selection highlight of menu entries and dropdown lists
    private static final ColorUIResource PRIMARY2
            = new ColorUIResource(0x5A5A5A);

    // Frame around a pressed button, tree and table selection
    private static final ColorUIResource PRIMARY3
            = new ColorUIResource(0x4A4A4A);

    // Button, checkbox and list outlines
    private static final ColorUIResource SECONDARY1
            = new ColorUIResource(0x8A8A8A);

    // Pressed buttons and inner frames of dropdowns
    private static final ColorUIResource SECONDARY2
            = new ColorUIResource(0x505050);

    // Frame, panel and menu backgrounds, also the background of internal
    // frames. It is close to black, but light enough that the raised,
    // lowered and etched borders of panels, which Swing derives from the
    // background with brighter() and darker(), still show their shading. The
    // buttons are slightly darker, like the black switches on the light panel
    // of the light theme.
    private static final ColorUIResource SECONDARY3
            = new ColorUIResource(0x303030);

    // Border of the selected internal frame, which is lighter than its title
    // bar (PRIMARY3), so that the border and the title bar can be told apart.
    private static final ColorUIResource FRAME_BORDER_ACTIVE
            = new ColorUIResource(0x666666);

    // Border of the internal frames that are not selected, which is lighter
    // than their title bar in the panel color.
    private static final ColorUIResource FRAME_BORDER_INACTIVE
            = new ColorUIResource(0x444444);

    // Outer line of all internal frames, which separates them from the
    // desktop background.
    private static final ColorUIResource FRAME_OUTLINE
            = new ColorUIResource(0x111111);

    // Completely black, used as the dark pixel of the inactive title bar
    // bumps, which has to be darker than the panel background (SECONDARY3).
    private static final ColorUIResource PURE_BLACK
            = new ColorUIResource(0x000000);

    // Background of text fields, lists and tables
    private static final ColorUIResource WHITE
            = new ColorUIResource(0x2B2B2B);

    // Text color of labels, menus and text fields
    private static final ColorUIResource BLACK
            = new ColorUIResource(0xDDDDDD);

    private static final ColorUIResource BUTTON_RELEASED
            = new ColorUIResource(0x1E1E1E);

    private static final ColorUIResource BUTTON_PRESSED
            = new ColorUIResource(0x2E2E2E);

    private static final ColorUIResource BUTTON_FOREGROUND
            = new ColorUIResource(0xDDDDDD);

    // Desktop behind the internal frames, darker than the panels so that
    // the frames stand out from it
    private static final ColorUIResource DESKTOP_BACKGROUND
            = new ColorUIResource(0x202020);

    // The selected tab and the frame around the tab content are lighter
    // than the panel background, while the unselected tabs are darker, which
    // inverts the brightness order of the light theme.
    private static final ColorUIResource TAB_SELECTED
            = new ColorUIResource(0x4A4A4A);

    private static final ColorUIResource TAB_AREA_BACKGROUND = SECONDARY3;

    private static final ColorUIResource TAB_UNSELECTED
            = new ColorUIResource(0x222222);

    @Override
    protected ColorUIResource getPrimary1() {
        return PRIMARY1;
    }

    @Override
    protected ColorUIResource getPrimary2() {
        return PRIMARY2;
    }

    @Override
    protected ColorUIResource getPrimary3() {
        return PRIMARY3;
    }

    @Override
    protected ColorUIResource getSecondary1() {
        return SECONDARY1;
    }

    @Override
    protected ColorUIResource getSecondary2() {
        return SECONDARY2;
    }

    @Override
    protected ColorUIResource getSecondary3() {
        return SECONDARY3;
    }

    @Override
    protected ColorUIResource getWhite() {
        return WHITE;
    }

    @Override
    protected ColorUIResource getBlack() {
        return BLACK;
    }

    @Override
    protected ColorUIResource getButtonReleased() {
        return BUTTON_RELEASED;
    }

    @Override
    protected ColorUIResource getButtonPressed() {
        return BUTTON_PRESSED;
    }

    @Override
    protected ColorUIResource getButtonForeground() {
        return BUTTON_FOREGROUND;
    }

    @Override
    protected ColorUIResource getDesktopBackground() {
        return DESKTOP_BACKGROUND;
    }

    @Override
    protected ColorUIResource getTabSelected() {
        return TAB_SELECTED;
    }

    @Override
    protected ColorUIResource getTabAreaBackground() {
        return TAB_AREA_BACKGROUND;
    }

    @Override
    protected ColorUIResource getTabUnselected() {
        return TAB_UNSELECTED;
    }

    /**
     * Keeps the progress bar fill of the dark theme at the selection gray,
     * because the button color that the light theme uses would hardly be
     * visible on the black background.
     *
     * @return Fill color of the done part of a JProgressBar
     */
    @Override
    protected ColorUIResource getProgressBarFill() {
        return PRIMARY2;
    }

    /**
     * Returns the text color of controls such as tabs, check boxes, radio
     * buttons, combo boxes and tables. The Ocean theme returns its own dark
     * gray here instead of getBlack(), so that this getter has to be
     * overridden as well to get light text on the dark controls.
     *
     * @return Light text color of the dark theme
     */
    @Override
    public ColorUIResource getControlTextColor() {
        return BLACK;
    }

    // The title bar bumps of the light theme have a light upper left and a
    // dark lower right pixel, which the Metal colors invert in the dark theme
    // because its highlight colors are darker than its shadow colors. These
    // getters swap them, so that the bumps look lit from the upper left.
    @Override
    protected ColorUIResource getActiveTitleBumpsHighlight() {
        return PRIMARY1;
    }

    @Override
    protected ColorUIResource getActiveTitleBumpsShadow() {
        return WHITE;
    }

    @Override
    protected ColorUIResource getInactiveTitleBumpsHighlight() {
        return PRIMARY2;
    }

    @Override
    protected ColorUIResource getInactiveTitleBumpsShadow() {
        return PURE_BLACK;
    }

    @Override
    protected ColorUIResource getPaletteBumpsHighlight() {
        return PRIMARY1;
    }

    @Override
    protected ColorUIResource getPaletteBumpsShadow() {
        return WHITE;
    }

    @Override
    public void addCustomEntriesToTable(UIDefaults table) {
        super.addCustomEntriesToTable(table);
        // The Metal frame border of internal frames is a light gray frame of
        // five pixels, which looks heavy on the dark desktop. The flat border
        // is a medium gray for the selected frame and a dark gray for the
        // others, both lighter than their title bars, with a dark outer line.
        table.put("InternalFrame.border", new ChornobylInternalFrameBorder(
                FRAME_BORDER_ACTIVE, FRAME_OUTLINE, FRAME_BORDER_INACTIVE,
                FRAME_OUTLINE));
        // The Ocean theme paints the menu bar and the tool bar with a gradient
        // from getWhite() to a hard-coded light gray, which gives a bright bar
        // on the black background. Without the gradient, the menu bar is
        // painted flat in the color of the text fields and lists, so that it
        // stands out from the black panels, and gets a line in the outline
        // color below it, while the tool bar becomes black like the panels.
        table.remove("MenuBar.gradient");
        table.put("MenuBar.background", WHITE);
        table.put("MenuBar.borderColor", SECONDARY1);
    }

    @Override
    public boolean isDarkTheme() {
        return true;
    }

    @Override
    public String getName() {
        return "Chornobyl Metal Dark";
    }
}
