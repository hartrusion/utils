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
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.UIDefaults;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
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
     * Returns the fill of the progress bars, which is the color of the
     * buttons, because the default fill (PRIMARY2) is hardly visible on the
     * panel background. The text on the fill gets the text color of the
     * buttons.
     *
     * @return Fill color of the done part of a JProgressBar
     */
    protected ColorUIResource getProgressBarFill() {
        return getButtonReleased();
    }

    // Colors of the reading displays of the RBMK simulator (the JLabels
    // named jLabelReading... in PanelCoreControl), which show values in a
    // green monospaced font on a dark gray field with a black frame. The
    // same colors mark the unacknowledged alarms in the alarm table. Single
    // line text fields, spinners, combo boxes and tables use this style, so
    // that they look like the displays of the control panel.
    private static final ColorUIResource READING_BACKGROUND
            = new ColorUIResource(0x333333);

    private static final ColorUIResource READING_FOREGROUND
            = new ColorUIResource(0x00FF99);

    // Text of disabled reading displays, which is the reading green mixed
    // half with the reading background.
    private static final ColorUIResource READING_DISABLED_FOREGROUND
            = new ColorUIResource(0x1A9966);

    private static final ColorUIResource READING_BORDER
            = new ColorUIResource(0x000000);

    /**
     *
     * @return Background of reading displays such as text fields and tables
     */
    protected ColorUIResource getReadingBackground() {
        return READING_BACKGROUND;
    }

    /**
     *
     * @return Text color of reading displays
     */
    protected ColorUIResource getReadingForeground() {
        return READING_FOREGROUND;
    }

    /**
     *
     * @return Text color of disabled reading displays
     */
    protected ColorUIResource getReadingDisabledForeground() {
        return READING_DISABLED_FOREGROUND;
    }

    /**
     *
     * @return Color of the frame around reading displays and of table grids
     */
    protected ColorUIResource getReadingBorder() {
        return READING_BORDER;
    }

    /**
     * Returns the monospaced font of the reading displays in the size of
     * the control font of the theme, so that text fields keep their height.
     *
     * @return Font of reading displays
     */
    protected FontUIResource getReadingFont() {
        return new FontUIResource(Font.MONOSPACED, Font.PLAIN,
                getControlTextFont().getSize());
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

        // The Ocean theme paints the slider thumb with a bluish gradient,
        // which is replaced by a gradient that has only the color of the
        // buttons, so that the thumb looks like the flat buttons. A focused
        // slider thumb gets the color of a pressed button.
        table.put("Slider.gradient", createFlatGradient(getButtonReleased()));
        table.put("Slider.focusGradient",
                createFlatGradient(getButtonPressed()));

        // Scroll bars: arrow buttons and thumb in the style of the buttons.
        // This can not be done with colors only, because the Metal arrow
        // buttons take the track color as fill and the text color as arrow.
        table.put("ScrollBarUI", ChornobylMetalScrollBarUI.class.getName());
        // The track would have the panel background, which hides the extent
        // of the scroll bar, so it gets the selection color of the dropdown
        // lists instead.
        table.put("ScrollBar.track", getPrimary2());

        // Spinner and combo box arrow buttons in the style of the buttons,
        // for the same reason as the scroll bar.
        table.put("SpinnerUI", ChornobylMetalSpinnerUI.class.getName());
        table.put("ComboBoxUI", ChornobylMetalComboBoxUI.class.getName());

        // The ticks of the slider scale are painted in the slider foreground,
        // which is PRIMARY2 by default and hardly visible, so they get the
        // color of the button and list outlines.
        table.put("Slider.foreground", getSecondary1());

        // The lines of a TitledBorder are hardly visible in PRIMARY2, so they
        // get the outline color as well.
        table.put("TitledBorder.border",
                new BorderUIResource.LineBorderUIResource(getSecondary1()));

        table.put("ProgressBar.foreground", getProgressBarFill());
        table.put("ProgressBar.selectionForeground", getButtonForeground());

        addReadingEntriesToTable(table);

        // Check boxes and radio buttons look like the flat buttons, which is
        // done by icons of their own instead of the Ocean gradients.
        table.put("CheckBox.icon", ChornobylMetalIconFactory.getCheckBoxIcon());
        table.put("RadioButton.icon",
                ChornobylMetalIconFactory.getRadioButtonIcon());

        // Frames inside JDesktopPane are JInternalFrames
        // Remove the gradient from the selected frame
        table.remove("InternalFrame.activeTitleGradient");
    }

    /**
     * Writes the style of the reading displays (green monospaced text on dark
     * gray with a black frame) into the UIDefaults of all single line text
     * fields, of the spinner editor (which is a JFormattedTextField), of the
     * combo box value and its popup list and of tables. Multi line text
     * components keep the default style. Selected text and selected rows are
     * shown inverted, green with dark gray text.
     *
     * @param table UIDefaults of the look and feel
     */
    private void addReadingEntriesToTable(UIDefaults table) {
        BorderUIResource readingFieldBorder = new BorderUIResource(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(getReadingBorder()),
                        BorderFactory.createEmptyBorder(1, 2, 1, 2)));
        String[] fieldPrefixes = {"TextField", "FormattedTextField",
            "PasswordField"};
        for (String prefix : fieldPrefixes) {
            table.put(prefix + ".background", getReadingBackground());
            // Metal uses the inactive colors for read-only and disabled
            // fields, a read-only field is a display and keeps its green.
            table.put(prefix + ".inactiveBackground", getReadingBackground());
            table.put(prefix + ".foreground", getReadingForeground());
            table.put(prefix + ".inactiveForeground",
                    getReadingDisabledForeground());
            table.put(prefix + ".caretForeground", getReadingForeground());
            table.put(prefix + ".selectionBackground", getReadingForeground());
            table.put(prefix + ".selectionForeground", getReadingBackground());
            table.put(prefix + ".font", getReadingFont());
            table.put(prefix + ".border", readingFieldBorder);
        }

        table.put("Spinner.border",
                new BorderUIResource.LineBorderUIResource(getReadingBorder()));
        table.put("Spinner.font", getReadingFont());

        table.put("ComboBox.background", getReadingBackground());
        table.put("ComboBox.disabledBackground", getReadingBackground());
        table.put("ComboBox.foreground", getReadingForeground());
        table.put("ComboBox.disabledForeground",
                getReadingDisabledForeground());
        table.put("ComboBox.selectionBackground", getReadingForeground());
        table.put("ComboBox.selectionForeground", getReadingBackground());
        table.put("ComboBox.font", getReadingFont());

        table.put("Table.background", getReadingBackground());
        table.put("Table.foreground", getReadingForeground());
        table.put("Table.focusCellBackground", getReadingBackground());
        table.put("Table.focusCellForeground", getReadingForeground());
        table.put("Table.selectionBackground", getReadingForeground());
        table.put("Table.selectionForeground", getReadingBackground());
        table.put("Table.gridColor", getReadingBorder());
        // Remaining light blue colors of the Ocean theme around tables
        table.put("Table.dropCellBackground", getPrimary2());
        table.put("TableHeader.focusCellBackground", getPrimary2());
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
