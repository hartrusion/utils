/*
 * Copyright (C) 2025 Viktor Alexander Hartung
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
import java.util.Arrays;
import java.util.List;
import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.OceanTheme;

/**
 * Modifies the default theme "Ocean" from the Metal look and feel. Gradients
 * from buttons are removed and the bluish color is replaced by gray colors.
 * <p>
 * Besides the colors of the Metal look and feel, the theme also defines the
 * colors of the panel buttons, which are the colored buttons of the control
 * panels that show a state like an open or closed valve. These buttons exist
 * in the types green, red, gray, yellow and white, and each type has a dark
 * background for the normal state, a bright background for the highlighted
 * state and a text color. AbstractPanelWidget reads these colors with the
 * getters of the active theme whenever it sets the state of such a button, so
 * that the panels do not need to know which theme is installed.
 * ChornobylMetalThemeDark derives from this class and only overrides the
 * getters of the colors, so that both themes keep the same Metal shapes and
 * just differ in color.
 * <p>
 * The panel button colors are plain Color objects instead of ColorUIResource,
 * because Swing replaces any ColorUIResource of a component by the default of
 * the look and feel when the component UI is updated, which would reset the
 * state colors of the buttons.
 *
 * @author Viktor Alexander Hartung
 */
public class ChornobylMetalTheme extends OceanTheme {

    // Menu element outlines
    // Table and tree selection frames
    // slider right side and selection highlight outline
    private static final ColorUIResource PRIMARY1
            = new ColorUIResource(0x808080); // removed bluish color

    @Override
    protected ColorUIResource getPrimary1() {
        return PRIMARY1;
    }

    // Selection highlight color for menu entries and dropdown lists
    // The frame around the text after a button was pressed
    // Tick marks and gradient color on Slider
    private static final ColorUIResource PRIMARY2
            = new ColorUIResource(0xCCCCCC);

    @Override
    protected ColorUIResource getPrimary2() {
        return PRIMARY2;
    }

    // double frame around a button while it is pressed down
    // Tree and table selection
    // Checkbox lower gradient
    // Selection highlight (outer frame) from the sliders slider.
    private static final ColorUIResource PRIMARY3
            = new ColorUIResource(0xE6E6E6);

    @Override
    protected ColorUIResource getPrimary3() {
        return PRIMARY3;
    }

    // Button and ToggleButton outlines, outline stays same also when pressed.
    // slider right side
    // Outlines for tree, table, dropdown list
    // Menu selection frame
    // Checkbox and RadioButton frame
    // Replaced with a grey only, slightly darker color
    private static final ColorUIResource SECONDARY1
            = new ColorUIResource(0x626262);

    @Override
    protected ColorUIResource getSecondary1() {
        return SECONDARY1;
    }

    // Replace the light blue color when buttons are pressed with a greyish
    // non-blue color, also applies for some gradients for the lower side.
    // also the lower border of a checkbox and inner frame on dropdowns.
    // a replacement would be E6E6E6 but we go slightly darker to D0D0D0
    private static final ColorUIResource SECONDARY2
            = new ColorUIResource(0xD0D0D0);

    @Override
    protected ColorUIResource getSecondary2() {
        return SECONDARY2;
    }

    // Frame and Panel Background colors
    // Dropdown and Menu entry backgrounds
    // Replaced with a slightly darker Brackground from the Nimbus theme
    private static final ColorUIResource SECONDARY3
            = new ColorUIResource(0xD6D9DF);

    @Override
    protected ColorUIResource getSecondary3() {
        return SECONDARY3;
    }

    // Background of all standard buttons and toggle buttons in released
    // state, a dark gray without gradient.
    private static final ColorUIResource BUTTON_RELEASED
            = new ColorUIResource(48, 48, 48);

    /**
     * Returns the background of standard buttons and toggle buttons, which is
     * written into the UIDefaults by addCustomEntriesToTable.
     *
     * @return Background of a released standard button
     */
    protected ColorUIResource getButtonReleased() {
        return BUTTON_RELEASED;
    }

    // Background of standard buttons while pressed and of selected toggle
    // buttons, slightly lighter than the released state.
    private static final ColorUIResource BUTTON_PRESSED
            = new ColorUIResource(64, 64, 64);

    /**
     *
     * @return Background of a pressed standard button and a selected toggle
     * button
     */
    protected ColorUIResource getButtonPressed() {
        return BUTTON_PRESSED;
    }

    // Text color of the standard buttons and toggle buttons.
    private static final ColorUIResource BUTTON_FOREGROUND
            = new ColorUIResource(0xFFFFFF);

    /**
     *
     * @return Text color of standard buttons and toggle buttons
     */
    protected ColorUIResource getButtonForeground() {
        return BUTTON_FOREGROUND;
    }

    // JDesktopPane has a white background as default, use something a bit
    // darker than the panel backgrounds with even less blue parts
    private static final ColorUIResource DESKTOP_BACKGROUND
            = new ColorUIResource(0xA5ABB5);

    /**
     *
     * @return Background of the JDesktopPane behind the internal frames
     */
    protected ColorUIResource getDesktopBackground() {
        return DESKTOP_BACKGROUND;
    }

    // The Ocean theme paints the selected tab and the content area border of
    // a JTabbedPane in a light blue (0xC8DDF2), which is replaced by a gray
    // that is darker than the unselected tabs, like the selection highlight.
    private static final ColorUIResource TAB_SELECTED
            = new ColorUIResource(0xCCCCCC);

    /**
     *
     * @return Background of the selected tab and the content area border of
     * a JTabbedPane
     */
    protected ColorUIResource getTabSelected() {
        return TAB_SELECTED;
    }

    // Background of the area behind the tabs of a JTabbedPane, which is the
    // neutral gray of the Ocean theme.
    private static final ColorUIResource TAB_AREA_BACKGROUND
            = new ColorUIResource(0xDADADA);

    /**
     *
     * @return Background of the area behind the tabs of a JTabbedPane
     */
    protected ColorUIResource getTabAreaBackground() {
        return TAB_AREA_BACKGROUND;
    }

    // Background of the tabs that are not selected, which is the neutral
    // light gray of the Ocean theme.
    private static final ColorUIResource TAB_UNSELECTED
            = new ColorUIResource(0xEEEEEE);

    /**
     *
     * @return Background of the tabs of a JTabbedPane that are not selected
     */
    protected ColorUIResource getTabUnselected() {
        return TAB_UNSELECTED;
    }

    /**
     * Tells whether this theme has a dark background with light text, so that
     * elements that are not colored by the Metal look and feel, such as the
     * plots of the diagrams, can choose a matching color palette.
     *
     * @return true for a dark theme, false for a light theme
     */
    public boolean isDarkTheme() {
        return false;
    }

    @Override
    public String getName() {
        return "Chornobyl Metal";
    }

    @Override
    public void addCustomEntriesToTable(UIDefaults table) {
        // This gets called from getDefaults from the metal look and feel.
        // The ocean theme will add all of its special things here if we call
        // the method from the ocean theme.
        super.addCustomEntriesToTable(table);
        // Manipulate table (it is derived from HashTable) again for our needs.

        // No text shall be "bold" by default, see DefaultMetalTheme docs.
        table.put("swing.boldMetal", Boolean.FALSE);

        // Button: No gradient color, but a more darker gray as base color.
        table.remove("Button.gradient");
        table.put("Button.background", getButtonReleased());
        table.put("Button.select", getButtonPressed());
        table.put("Button.foreground", getButtonForeground());

        // Toggle Button, same color as normal button, select-state will be
        // a bit lighter
        table.remove("ToggleButton.gradient");
        table.put("ToggleButton.background", getButtonReleased());
        table.put("ToggleButton.select", getButtonPressed());
        table.put("ToggleButton.foreground", getButtonForeground());

        table.put("Desktop.background", getDesktopBackground());

        // Tabbed pane: remove the light blue of the selected tab
        table.put("TabbedPane.selected", getTabSelected());
        table.put("TabbedPane.contentAreaColor", getTabSelected());
        table.put("TabbedPane.tabAreaBackground", getTabAreaBackground());
        table.put("TabbedPane.unselectedBackground", getTabUnselected());

        // The Ocean theme paints the thumb of sliders and scroll bars with a
        // bluish gradient, which is replaced by a gradient that has only the
        // color of the buttons, so that the thumbs look like the flat buttons.
        // A focused slider thumb gets the color of a pressed button.
        table.put("Slider.gradient", createFlatGradient(getButtonReleased()));
        table.put("Slider.focusGradient",
                createFlatGradient(getButtonPressed()));
        table.put("ScrollBar.gradient", createFlatGradient(getButtonReleased()));
        // The scroll bar thumb uses these colors where no gradient is painted
        // and for its highlight edge.
        table.put("ScrollBar.thumb", getButtonReleased());
        table.put("ScrollBar.thumbHighlight", getButtonPressed());

        // Frames inside JDesktopPane are JInternalFrames
        // Remove the gradient from the selected frame
        table.remove("InternalFrame.activeTitleGradient");
    }

    /**
     * Creates a gradient in the format of the Ocean theme UIDefaults, which is
     * a list of two ratios and three colors, with all three colors set to the
     * same color. The Metal UI classes then fill the area with this color, so
     * that elements that only support a gradient are painted flat.
     *
     * @param color The only color of the gradient
     * @return Gradient value for the UIDefaults table
     */
    private static List<Object> createFlatGradient(Color color) {
        return Arrays.asList(0.3F, 0.0F, color, color, color);
    }
}
