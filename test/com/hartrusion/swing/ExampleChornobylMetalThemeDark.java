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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Shows all Swing components of the NetBeans palette in a single frame, so
 * that the ChornobylMetalThemeDark can be checked visually without starting
 * the RBMK simulator, which is the project the theme was made for. The class is
 * not a unit test but a plain application that is started with its main
 * method, and it is deliberately kept in one single file, so that it can be
 * started from the test packages without any further dependency than the
 * theme itself.
 * <p>
 * Most tabs place each component twice next to each other, once enabled and
 * once disabled, because the Metal look and feel paints the disabled state
 * with separate colors of the theme. One tab shows panels with all borders
 * of BorderFactory, because the bevel and etched borders derive their
 * shading from the panel background. The windows, menus and dialogs are
 * reachable with the menu bar, the tool bar, the popup menu (right click on a
 * column) and the buttons of the last tab. All texts are taken one after the
 * other from a Lorem Ipsum text by nextLoremText, so that each component
 * shows a different text and the text sizes and fonts can be compared.
 * <p>
 * This class is a copy of ExampleChornobylMetalTheme that installs
 * ChornobylMetalThemeDark instead of the light theme, which was requested as
 * a separate class so that each theme can be started on its own.
 * <p>
 * LLM code generated with Claude Opus 5.5
 *
 * @author Viktor Alexander Hartung
 */
public class ExampleChornobylMetalThemeDark {

    // Source of all texts of the frame, which is consumed word by word by
    // nextLoremText and starts over at its beginning when it is used up.
    private static final String[] LOREM_WORDS = ("lorem ipsum dolor sit amet "
            + "consectetur adipiscing elit sed do eiusmod tempor incididunt ut "
            + "labore et dolore magna aliqua ut enim ad minim veniam quis "
            + "nostrud exercitation ullamco laboris nisi ut aliquip ex ea "
            + "commodo consequat duis aute irure dolor in reprehenderit in "
            + "voluptate velit esse cillum dolore eu fugiat nulla pariatur "
            + "excepteur sint occaecat cupidatat non proident sunt in culpa "
            + "qui officia deserunt mollit anim id est laborum curabitur "
            + "pretium tincidunt lacus nulla gravida orci a odio nullam varius "
            + "turpis et commodo pharetra est eros bibendum elit nec luctus "
            + "magna felis sollicitudin mauris integer in mauris eu nibh "
            + "euismod gravida duis ac tellus et risus vulputate vehicula donec "
            + "lobortis risus a elit etiam tempor ut ullamcorper ligula eu "
            + "tempor congue eros est euismod turpis id tincidunt sapien risus "
            + "a quam maecenas fermentum consequat mi donec fermentum "
            + "pellentesque malesuada nulla a mi duis sapien sem aliquet nec "
            + "commodo eget consequat quis neque aliquam faucibus elit ut "
            + "dictum aliquet felis nisl adipiscing sapien sed malesuada diam "
            + "lacus eget erat cras mollis scelerisque nunc nullam arcu "
            + "aliquam consequat curabitur augue lorem dapibus quis laoreet "
            + "et pretium ac nisi aenean magna nisl mollis quis molestie eu "
            + "feugiat in orci in hac habitasse platea dictumst").split(" ");

    // Index of the word in LOREM_WORDS that the next call of nextLoremText
    // starts with.
    private int loremPosition = 0;

    // Main window of the demo, which is the parent of all dialogs.
    private JFrame frame;

    // Button of the enabled buttons column that is made the default button
    // of the root pane, so that the Metal default button border is visible.
    private JButton defaultButton;

    // Popup menu that is shared by all comparison columns and the desktop
    // pane and is shown with a right mouse click on them.
    private JPopupMenu sharedPopupMenu;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            installTheme();
            new ExampleChornobylMetalThemeDark().createAndShowFrame();
        });
    }

    /**
     * Installs the Metal look and feel with the theme to be shown. This must
     * happen before the first component is created, because the components
     * take their UI and colors from the look and feel at creation time.
     */
    private static void installTheme() {
        MetalLookAndFeel.setCurrentTheme(new ChornobylMetalThemeDark());
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            throw new IllegalStateException(
                    "Metal look and feel is not supported", ex);
        }
    }

    /**
     * Returns the next words of the Lorem Ipsum text with a capital first
     * letter. Each call continues where the previous call stopped, which is
     * the reason why no two components show the same text.
     *
     * @param wordCount Number of words to return
     * @return Text with wordCount words separated by spaces
     */
    private String nextLoremText(int wordCount) {
        StringBuilder text = new StringBuilder();
        for (int idx = 0; idx < wordCount; idx++) {
            if (idx > 0) {
                text.append(' ');
            }
            text.append(LOREM_WORDS[loremPosition]);
            loremPosition = (loremPosition + 1) % LOREM_WORDS.length;
        }
        text.setCharAt(0, Character.toUpperCase(text.charAt(0)));
        return text.toString();
    }

    /**
     * Returns an array of different Lorem Ipsum texts, which is used as
     * content of combo boxes, lists and spinners.
     *
     * @param itemCount Number of entries
     * @param wordCount Number of words per entry
     * @return Array of texts
     */
    private String[] nextLoremItems(int itemCount, int wordCount) {
        String[] items = new String[itemCount];
        for (int idx = 0; idx < itemCount; idx++) {
            items[idx] = nextLoremText(wordCount);
        }
        return items;
    }

    /**
     * Builds the complete frame with menu bar, tool bar, tabs and status line
     * and shows it on the screen.
     */
    private void createAndShowFrame() {
        frame = new JFrame(new ChornobylMetalThemeDark().getName() + " - "
                + nextLoremText(3));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        sharedPopupMenu = createPopupMenu();
        frame.setJMenuBar(createMenuBar());

        JTabbedPane mainTabs = new JTabbedPane();
        mainTabs.addTab(nextLoremText(1), createComparisonPanel(
                createButtonsColumn(true), createButtonsColumn(false)));
        mainTabs.addTab(nextLoremText(1), createComparisonPanel(
                createSelectionColumn(true), createSelectionColumn(false)));
        mainTabs.addTab(nextLoremText(1), createComparisonPanel(
                createTextColumn(true), createTextColumn(false)));
        mainTabs.addTab(nextLoremText(1), createComparisonPanel(
                createDataColumn(true), createDataColumn(false)));
        mainTabs.addTab(nextLoremText(1), createContainersPanel());
        mainTabs.addTab(nextLoremText(1), createBordersPanel());
        mainTabs.addTab(nextLoremText(1), createComparisonPanel(
                createWindowsColumn(true), createWindowsColumn(false)));
        mainTabs.addTab(nextLoremText(1), new JLabel(nextLoremText(3)));
        mainTabs.setEnabledAt(mainTabs.getTabCount() - 1, false);
        mainTabs.setToolTipTextAt(0, nextLoremText(4));

        JLabel statusLabel = new JLabel(nextLoremText(8));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(createToolBar(), BorderLayout.NORTH);
        frame.getContentPane().add(mainTabs, BorderLayout.CENTER);
        frame.getContentPane().add(statusLabel, BorderLayout.SOUTH);
        frame.getRootPane().setDefaultButton(defaultButton);

        frame.setSize(1200, 850);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Creates the menu bar with all kinds of menu entries, which are normal
     * items with icons, accelerators and mnemonics, sub menus, check box and
     * radio button items, each of them also in disabled form, and a disabled
     * menu on the bar itself.
     *
     * @return Menu bar of the frame
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu firstMenu = new JMenu(nextLoremText(1));
        firstMenu.setMnemonic(KeyEvent.VK_L);
        JMenuItem itemWithAccelerator = new JMenuItem(nextLoremText(2));
        itemWithAccelerator.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        firstMenu.add(itemWithAccelerator);
        JMenuItem itemWithIcon = new JMenuItem(nextLoremText(2),
                UIManager.getIcon("Tree.openIcon"));
        itemWithIcon.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        firstMenu.add(itemWithIcon);
        JMenuItem itemWithMnemonic = new JMenuItem(nextLoremText(2));
        itemWithMnemonic.setMnemonic(itemWithMnemonic.getText().charAt(0));
        firstMenu.add(itemWithMnemonic);
        JMenuItem disabledItem = new JMenuItem(nextLoremText(2),
                UIManager.getIcon("Tree.closedIcon"));
        disabledItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        disabledItem.setEnabled(false);
        firstMenu.add(disabledItem);
        firstMenu.addSeparator();
        JMenu subMenu = new JMenu(nextLoremText(2));
        subMenu.add(new JMenuItem(nextLoremText(2)));
        subMenu.add(new JMenuItem(nextLoremText(3)));
        JMenu nestedSubMenu = new JMenu(nextLoremText(2));
        nestedSubMenu.add(new JMenuItem(nextLoremText(2)));
        nestedSubMenu.add(new JMenuItem(nextLoremText(2)));
        subMenu.add(nestedSubMenu);
        firstMenu.add(subMenu);
        JMenu disabledSubMenu = new JMenu(nextLoremText(2));
        disabledSubMenu.add(new JMenuItem(nextLoremText(2)));
        disabledSubMenu.setEnabled(false);
        firstMenu.add(disabledSubMenu);
        firstMenu.addSeparator();
        JMenuItem closeItem = new JMenuItem(nextLoremText(1));
        closeItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        closeItem.addActionListener((ActionEvent e) -> frame.dispose());
        firstMenu.add(closeItem);
        menuBar.add(firstMenu);

        JMenu secondMenu = new JMenu(nextLoremText(1));
        JCheckBoxMenuItem checkSelected
                = new JCheckBoxMenuItem(nextLoremText(2), true);
        secondMenu.add(checkSelected);
        secondMenu.add(new JCheckBoxMenuItem(nextLoremText(2), false));
        JCheckBoxMenuItem checkDisabledSelected
                = new JCheckBoxMenuItem(nextLoremText(2), true);
        checkDisabledSelected.setEnabled(false);
        secondMenu.add(checkDisabledSelected);
        JCheckBoxMenuItem checkDisabled
                = new JCheckBoxMenuItem(nextLoremText(2), false);
        checkDisabled.setEnabled(false);
        secondMenu.add(checkDisabled);
        secondMenu.addSeparator();
        ButtonGroup radioMenuGroup = new ButtonGroup();
        for (int idx = 0; idx < 3; idx++) {
            JRadioButtonMenuItem radioItem
                    = new JRadioButtonMenuItem(nextLoremText(2), idx == 0);
            radioMenuGroup.add(radioItem);
            secondMenu.add(radioItem);
        }
        JRadioButtonMenuItem radioDisabled
                = new JRadioButtonMenuItem(nextLoremText(2));
        radioDisabled.setEnabled(false);
        secondMenu.add(radioDisabled);
        menuBar.add(secondMenu);

        JMenu disabledMenu = new JMenu(nextLoremText(1));
        disabledMenu.add(new JMenuItem(nextLoremText(2)));
        disabledMenu.setEnabled(false);
        menuBar.add(disabledMenu);

        JMenu thirdMenu = new JMenu(nextLoremText(1));
        JMenuItem aboutItem = new JMenuItem(nextLoremText(2));
        String aboutMessage = nextLoremText(12);
        aboutItem.addActionListener((ActionEvent e)
                -> JOptionPane.showMessageDialog(frame, aboutMessage));
        thirdMenu.add(aboutItem);
        menuBar.add(thirdMenu);

        return menuBar;
    }

    /**
     * Creates the popup menu that is shown with a right click on the
     * comparison columns and the desktop pane.
     *
     * @return Popup menu with normal, disabled, check box and sub menu items
     */
    private JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem(nextLoremText(2),
                UIManager.getIcon("FileView.fileIcon")));
        popupMenu.add(new JMenuItem(nextLoremText(2)));
        JMenuItem disabledItem = new JMenuItem(nextLoremText(2));
        disabledItem.setEnabled(false);
        popupMenu.add(disabledItem);
        popupMenu.addSeparator();
        popupMenu.add(new JCheckBoxMenuItem(nextLoremText(2), true));
        JMenu subMenu = new JMenu(nextLoremText(2));
        subMenu.add(new JMenuItem(nextLoremText(2)));
        subMenu.add(new JMenuItem(nextLoremText(2)));
        popupMenu.add(subMenu);
        return popupMenu;
    }

    /**
     * Creates the tool bar with icon buttons, toggle buttons, a combo box and
     * a text field, of which some are disabled.
     *
     * @return Floatable tool bar
     */
    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar(nextLoremText(2));
        String[] iconKeys = {"FileView.directoryIcon", "FileView.fileIcon",
            "FileView.hardDriveIcon", "FileView.floppyDriveIcon"};
        for (String iconKey : iconKeys) {
            JButton iconButton = new JButton(UIManager.getIcon(iconKey));
            iconButton.setToolTipText(nextLoremText(3));
            toolBar.add(iconButton);
        }
        toolBar.getComponent(toolBar.getComponentCount() - 1)
                .setEnabled(false);
        toolBar.addSeparator();
        toolBar.add(new JButton(nextLoremText(1)));
        JButton disabledButton = new JButton(nextLoremText(1));
        disabledButton.setEnabled(false);
        toolBar.add(disabledButton);
        toolBar.add(new JToggleButton(nextLoremText(1), true));
        toolBar.add(new JToggleButton(nextLoremText(1), false));
        JToggleButton disabledToggle
                = new JToggleButton(nextLoremText(1), true);
        disabledToggle.setEnabled(false);
        toolBar.add(disabledToggle);
        toolBar.addSeparator();
        toolBar.add(new JComboBox<>(nextLoremItems(5, 2)));
        toolBar.addSeparator();
        toolBar.add(new JTextField(nextLoremText(2), 12));
        return toolBar;
    }

    /**
     * Places the enabled and the disabled column of a tab next to each other
     * inside a scroll pane, so that both states can be compared directly.
     *
     * @param enabledColumn Column with the enabled components
     * @param disabledColumn Same column with the disabled components
     * @return Scroll pane that is added as tab
     */
    private JComponent createComparisonPanel(JPanel enabledColumn,
            JPanel disabledColumn) {
        JPanel comparisonPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        comparisonPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        comparisonPanel.add(enabledColumn);
        comparisonPanel.add(disabledColumn);
        JScrollPane scrollPane = new JScrollPane(comparisonPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }

    /**
     * Creates an empty column for one state of a comparison tab. The titled
     * border names the state, because the components look different only in
     * their colors.
     *
     * @param enabled true for the enabled column
     * @return Panel with GridBagLayout to be filled by addLabeledRow
     */
    private JPanel createColumnPanel(boolean enabled) {
        JPanel column = new JPanel(new GridBagLayout());
        String stateName = "disabled";
        if (enabled) {
            stateName = "enabled";
        }
        column.setBorder(BorderFactory.createTitledBorder(
                nextLoremText(2) + " (" + stateName + ")"));
        column.setComponentPopupMenu(sharedPopupMenu);
        return column;
    }

    /**
     * Adds a component with a label in front of it as the next row of a
     * column. The label and the component get the enabled state of the
     * column and the component gets a tool tip. Components inside a scroll
     * pane are passed as scroll pane, so the caller has to set the enabled
     * state of the component inside the scroll pane itself.
     *
     * @param column Column created by createColumnPanel
     * @param component Component of the row
     * @param verticalWeight Share of additional height, 0 for a fixed height
     * @param enabled Enabled state of the column
     */
    private void addLabeledRow(JPanel column, JComponent component,
            double verticalWeight, boolean enabled) {
        // Each row consists of a label and a component.
        int row = column.getComponentCount() / 2;
        JLabel label = new JLabel(nextLoremText(2) + ":");
        label.setLabelFor(component);
        label.setEnabled(enabled);
        component.setEnabled(enabled);
        component.setToolTipText(nextLoremText(5));

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.gridx = 0;
        labelConstraints.gridy = row;
        labelConstraints.anchor = GridBagConstraints.NORTHWEST;
        labelConstraints.insets = new Insets(4, 4, 4, 8);
        column.add(label, labelConstraints);

        GridBagConstraints componentConstraints = new GridBagConstraints();
        componentConstraints.gridx = 1;
        componentConstraints.gridy = row;
        componentConstraints.weightx = 1.0;
        componentConstraints.weighty = verticalWeight;
        componentConstraints.fill = GridBagConstraints.BOTH;
        componentConstraints.insets = new Insets(4, 0, 4, 4);
        column.add(component, componentConstraints);
    }

    /**
     * Creates the column with the button like components, which are buttons,
     * toggle buttons, check boxes, radio buttons, labels and a separator.
     *
     * @param enabled Enabled state of all components of the column
     * @return Filled column
     */
    private JPanel createButtonsColumn(boolean enabled) {
        JPanel column = createColumnPanel(enabled);
        JButton plainButton = new JButton(nextLoremText(2));
        addLabeledRow(column, plainButton, 0, enabled);
        if (enabled) {
            defaultButton = plainButton;
        }
        JButton mnemonicButton = new JButton(nextLoremText(2));
        mnemonicButton.setMnemonic(mnemonicButton.getText().charAt(0));
        addLabeledRow(column, mnemonicButton, 0, enabled);
        addLabeledRow(column, new JButton(nextLoremText(2),
                UIManager.getIcon("FileView.computerIcon")), 0, enabled);
        addLabeledRow(column, new JToggleButton(nextLoremText(2), false),
                0, enabled);
        addLabeledRow(column, new JToggleButton(nextLoremText(2), true),
                0, enabled);
        addLabeledRow(column, new JCheckBox(nextLoremText(3), false),
                0, enabled);
        addLabeledRow(column, new JCheckBox(nextLoremText(3), true),
                0, enabled);
        ButtonGroup radioGroup = new ButtonGroup();
        for (int idx = 0; idx < 3; idx++) {
            JRadioButton radioButton
                    = new JRadioButton(nextLoremText(3), idx == 1);
            radioGroup.add(radioButton);
            addLabeledRow(column, radioButton, 0, enabled);
        }
        addLabeledRow(column, new JLabel(nextLoremText(4)), 0, enabled);
        addLabeledRow(column, new JLabel(nextLoremText(3),
                UIManager.getIcon("OptionPane.informationIcon"),
                JLabel.LEADING), 0, enabled);
        addLabeledRow(column, new JLabel("<html><b>" + nextLoremText(2)
                + "</b> <i>" + nextLoremText(2) + "</i> <u>"
                + nextLoremText(2) + "</u></html>"), 0, enabled);
        addLabeledRow(column, new JSeparator(), 0, enabled);
        // Filler row that takes the remaining height so the rows stay on top
        addLabeledRow(column, new JPanel(), 1.0, enabled);
        return column;
    }

    /**
     * Creates the column with the components that select a value, which are
     * combo boxes, spinners, sliders, progress bars, a scroll bar and a list.
     *
     * @param enabled Enabled state of all components of the column
     * @return Filled column
     */
    private JPanel createSelectionColumn(boolean enabled) {
        JPanel column = createColumnPanel(enabled);
        JComboBox<String> comboBox = new JComboBox<>(nextLoremItems(10, 2));
        comboBox.setSelectedIndex(2);
        addLabeledRow(column, comboBox, 0, enabled);
        JComboBox<String> editableComboBox
                = new JComboBox<>(nextLoremItems(10, 3));
        editableComboBox.setEditable(true);
        addLabeledRow(column, editableComboBox, 0, enabled);
        addLabeledRow(column, new JSpinner(
                new SpinnerNumberModel(42.5, -100.0, 100.0, 0.5)), 0, enabled);
        addLabeledRow(column, new JSpinner(
                new SpinnerListModel(nextLoremItems(8, 1))), 0, enabled);
        addLabeledRow(column, new JSpinner(new SpinnerDateModel()),
                0, enabled);

        JSlider decoratedSlider = new JSlider(0, 100, 35);
        decoratedSlider.setMajorTickSpacing(20);
        decoratedSlider.setMinorTickSpacing(5);
        decoratedSlider.setPaintTicks(true);
        decoratedSlider.setPaintLabels(true);
        addLabeledRow(column, decoratedSlider, 0, enabled);
        addLabeledRow(column, new JSlider(0, 100, 70), 0, enabled);
        JSlider filledSlider = new JSlider(0, 100, 55);
        // Metal specific property that paints the track up to the thumb
        filledSlider.putClientProperty("JSlider.isFilled", Boolean.TRUE);
        addLabeledRow(column, filledSlider, 0, enabled);

        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(65);
        progressBar.setStringPainted(true);
        progressBar.setString(nextLoremText(2) + " 65 %");
        addLabeledRow(column, progressBar, 0, enabled);
        JProgressBar plainProgressBar = new JProgressBar(0, 100);
        plainProgressBar.setValue(30);
        addLabeledRow(column, plainProgressBar, 0, enabled);
        JProgressBar indeterminateProgressBar = new JProgressBar();
        indeterminateProgressBar.setIndeterminate(true);
        addLabeledRow(column, indeterminateProgressBar, 0, enabled);

        addLabeledRow(column, new JScrollBar(JScrollBar.HORIZONTAL, 30, 20,
                0, 100), 0, enabled);

        JList<String> list = new JList<>(nextLoremItems(20, 2));
        list.setSelectedIndices(new int[]{1, 2, 5});
        list.setEnabled(enabled);
        addLabeledRow(column, new JScrollPane(list), 1.0, enabled);
        return column;
    }

    /**
     * Creates the column with all text components, which are text fields,
     * formatted and password fields, a text area, a text pane with styled
     * text and an editor pane with HTML content.
     *
     * @param enabled Enabled state of all components of the column
     * @return Filled column
     */
    private JPanel createTextColumn(boolean enabled) {
        JPanel column = createColumnPanel(enabled);
        addLabeledRow(column, new JTextField(nextLoremText(4)), 0, enabled);
        JTextField readOnlyField = new JTextField(nextLoremText(4));
        readOnlyField.setEditable(false);
        addLabeledRow(column, readOnlyField, 0, enabled);
        JFormattedTextField formattedField
                = new JFormattedTextField(NumberFormat.getNumberInstance());
        formattedField.setValue(12345.678);
        addLabeledRow(column, formattedField, 0, enabled);
        addLabeledRow(column, new JPasswordField(nextLoremText(1)),
                0, enabled);

        JTextArea textArea = new JTextArea(nextLoremText(60), 6, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.select(6, 30);
        textArea.setEnabled(enabled);
        addLabeledRow(column, new JScrollPane(textArea), 1.0, enabled);

        JTextPane textPane = new JTextPane();
        StyledDocument document = textPane.getStyledDocument();
        SimpleAttributeSet boldStyle = new SimpleAttributeSet();
        StyleConstants.setBold(boldStyle, true);
        SimpleAttributeSet italicStyle = new SimpleAttributeSet();
        StyleConstants.setItalic(italicStyle, true);
        SimpleAttributeSet coloredStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(coloredStyle, new Color(0xC04040));
        try {
            document.insertString(document.getLength(),
                    nextLoremText(8) + " ", null);
            document.insertString(document.getLength(),
                    nextLoremText(5) + " ", boldStyle);
            document.insertString(document.getLength(),
                    nextLoremText(5) + " ", italicStyle);
            document.insertString(document.getLength(),
                    nextLoremText(5) + " ", coloredStyle);
            document.insertString(document.getLength(),
                    nextLoremText(20), null);
        } catch (BadLocationException ex) {
            throw new IllegalStateException(ex);
        }
        textPane.setEnabled(enabled);
        addLabeledRow(column, new JScrollPane(textPane), 1.0, enabled);

        JEditorPane editorPane = new JEditorPane("text/html",
                "<html><body><h3>" + nextLoremText(3) + "</h3><p>"
                + nextLoremText(15) + " <a href=\"#\">" + nextLoremText(2)
                + "</a></p><ul><li>" + nextLoremText(3) + "</li><li>"
                + nextLoremText(3) + "</li></ul></body></html>");
        editorPane.setEnabled(enabled);
        addLabeledRow(column, new JScrollPane(editorPane), 1.0, enabled);
        return column;
    }

    /**
     * Creates the column with the components that show structured data,
     * which are a tree and a table with a header, a sorter and a check box
     * column.
     *
     * @param enabled Enabled state of all components of the column
     * @return Filled column
     */
    private JPanel createDataColumn(boolean enabled) {
        JPanel column = createColumnPanel(enabled);

        DefaultMutableTreeNode rootNode
                = new DefaultMutableTreeNode(nextLoremText(2));
        for (int branchIdx = 0; branchIdx < 4; branchIdx++) {
            DefaultMutableTreeNode branchNode
                    = new DefaultMutableTreeNode(nextLoremText(2));
            for (int leafIdx = 0; leafIdx < 3; leafIdx++) {
                branchNode.add(new DefaultMutableTreeNode(nextLoremText(2)));
            }
            rootNode.add(branchNode);
        }
        JTree tree = new JTree(rootNode);
        // Expand the first two branches, the others stay collapsed so that
        // both handle icons are visible.
        tree.expandRow(1);
        tree.expandRow(5);
        tree.setSelectionRow(3);
        tree.setEnabled(enabled);
        addLabeledRow(column, new JScrollPane(tree), 1.0, enabled);

        String[] columnNames = nextLoremItems(4, 1);
        Object[][] tableData = new Object[15][4];
        for (int rowIdx = 0; rowIdx < tableData.length; rowIdx++) {
            tableData[rowIdx][0] = nextLoremText(2);
            tableData[rowIdx][1] = rowIdx * 17 % 23;
            tableData[rowIdx][2] = rowIdx * 3.14159;
            tableData[rowIdx][3] = rowIdx % 3 == 0;
        }
        DefaultTableModel tableModel
                = new DefaultTableModel(tableData, columnNames) {
            // Column classes are needed so that the table uses the number
            // and check box renderers instead of plain text.
            private final Class<?>[] columnClasses
                    = {String.class, Integer.class, Double.class,
                        Boolean.class};

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnClasses[columnIndex];
            }
        };
        JTable table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        table.setRowSelectionInterval(2, 4);
        table.setEnabled(enabled);
        table.getTableHeader().setEnabled(enabled);
        addLabeledRow(column, new JScrollPane(table), 1.0, enabled);
        return column;
    }

    /**
     * Creates the tab with the containers, which is a split pane that holds a
     * nested tabbed pane with a disabled tab on its left side and a desktop
     * pane with internal frames on its right side.
     *
     * @return Split pane that is added as tab
     */
    private JComponent createContainersPanel() {
        JTabbedPane nestedTabs = new JTabbedPane(JTabbedPane.BOTTOM);
        for (int idx = 0; idx < 4; idx++) {
            JTextArea tabContent = new JTextArea(nextLoremText(40));
            tabContent.setLineWrap(true);
            tabContent.setWrapStyleWord(true);
            nestedTabs.addTab(nextLoremText(1), new JScrollPane(tabContent));
        }
        nestedTabs.setEnabledAt(2, false);

        JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                nestedTabs, new JScrollPane(new JList<>(
                        nextLoremItems(12, 2))));
        verticalSplit.setResizeWeight(0.6);

        JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.setComponentPopupMenu(sharedPopupMenu);
        // The last regular frame is selected, so that the active and the
        // inactive title bars can be compared.
        JInternalFrame lastFrame = null;
        for (int idx = 0; idx < 3; idx++) {
            JInternalFrame internalFrame = new JInternalFrame(
                    nextLoremText(2), true, true, true, true);
            JPanel framePanel = new JPanel(new BorderLayout(4, 4));
            framePanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
            framePanel.add(new JLabel(nextLoremText(4)), BorderLayout.NORTH);
            JTextArea frameText = new JTextArea(nextLoremText(25));
            frameText.setLineWrap(true);
            frameText.setWrapStyleWord(true);
            framePanel.add(new JScrollPane(frameText), BorderLayout.CENTER);
            framePanel.add(new JButton(nextLoremText(1)), BorderLayout.SOUTH);
            internalFrame.setContentPane(framePanel);
            internalFrame.setBounds(20 + idx * 60, 20 + idx * 50, 320, 220);
            internalFrame.setVisible(true);
            desktopPane.add(internalFrame);
            lastFrame = internalFrame;
        }
        // A palette frame shows the small title bar of the Metal look and feel
        JInternalFrame paletteFrame = new JInternalFrame(nextLoremText(1),
                false, true);
        paletteFrame.putClientProperty("JInternalFrame.isPalette",
                Boolean.TRUE);
        paletteFrame.getContentPane().add(new JLabel(nextLoremText(3)));
        paletteFrame.setBounds(300, 330, 200, 90);
        paletteFrame.setVisible(true);
        desktopPane.add(paletteFrame);
        // One iconified frame shows the desktop icon of the look and feel
        JInternalFrame iconifiedFrame = new JInternalFrame(nextLoremText(2),
                true, true, true, true);
        iconifiedFrame.setBounds(40, 300, 240, 160);
        iconifiedFrame.setVisible(true);
        desktopPane.add(iconifiedFrame);
        try {
            iconifiedFrame.setIcon(true);
            lastFrame.setSelected(true);
        } catch (PropertyVetoException ex) {
            // Only affects the initial state of the demo frames
        }

        JSplitPane horizontalSplit = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, verticalSplit, desktopPane);
        horizontalSplit.setOneTouchExpandable(true);
        horizontalSplit.setDividerLocation(380);
        return horizontalSplit;
    }

    /**
     * Creates the tab with panels that show the borders of BorderFactory,
     * which are the raised and lowered bevel borders, the soft bevel
     * borders, the etched borders, line, matte and dashed borders, titled
     * borders in different positions and compound borders. The bevel and
     * etched borders derive their colors from the panel background, so that
     * this tab shows whether the background of a theme still allows their
     * shading to be seen.
     *
     * @return Scroll pane that is added as tab
     */
    private JComponent createBordersPanel() {
        Color lineColor = UIManager.getColor("controlDkShadow");
        Border[] borders = {
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createLoweredBevelBorder(),
            BorderFactory.createRaisedSoftBevelBorder(),
            BorderFactory.createLoweredSoftBevelBorder(),
            BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),
            BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
            BorderFactory.createLineBorder(lineColor),
            BorderFactory.createLineBorder(lineColor, 3, true),
            BorderFactory.createMatteBorder(2, 6, 2, 6, lineColor),
            BorderFactory.createDashedBorder(lineColor, 2, 4, 2, true),
            BorderFactory.createTitledBorder(nextLoremText(2)),
            BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(), nextLoremText(2),
                    TitledBorder.CENTER, TitledBorder.TOP),
            BorderFactory.createTitledBorder(
                    BorderFactory.createLoweredBevelBorder(),
                    nextLoremText(2), TitledBorder.RIGHT,
                    TitledBorder.BOTTOM),
            BorderFactory.createTitledBorder(
                    BorderFactory.createRaisedBevelBorder(),
                    nextLoremText(2), TitledBorder.LEADING,
                    TitledBorder.ABOVE_TOP),
            BorderFactory.createCompoundBorder(
                    BorderFactory.createRaisedBevelBorder(),
                    BorderFactory.createLoweredBevelBorder()),
            BorderFactory.createCompoundBorder(
                    BorderFactory.createEtchedBorder(),
                    BorderFactory.createEmptyBorder(6, 6, 6, 6))
        };
        JPanel bordersPanel = new JPanel(new GridLayout(0, 4, 12, 12));
        bordersPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        for (Border border : borders) {
            JPanel borderedPanel = new JPanel(new BorderLayout());
            borderedPanel.setBorder(border);
            JLabel label = new JLabel(nextLoremText(3), JLabel.CENTER);
            label.setPreferredSize(new Dimension(160, 70));
            borderedPanel.add(label, BorderLayout.CENTER);
            bordersPanel.add(borderedPanel);
        }
        JPanel northWrapper = new JPanel(new BorderLayout());
        northWrapper.add(bordersPanel, BorderLayout.NORTH);
        return new JScrollPane(northWrapper);
    }

    /**
     * Creates the column with buttons that open the windows of the palette,
     * which are the different option pane dialogs, a custom dialog, the
     * color chooser and the file chooser.
     *
     * @param enabled Enabled state of all components of the column
     * @return Filled column
     */
    private JPanel createWindowsColumn(boolean enabled) {
        JPanel column = createColumnPanel(enabled);
        int[] messageTypes = {JOptionPane.INFORMATION_MESSAGE,
            JOptionPane.WARNING_MESSAGE, JOptionPane.ERROR_MESSAGE,
            JOptionPane.QUESTION_MESSAGE, JOptionPane.PLAIN_MESSAGE};
        for (int messageType : messageTypes) {
            JButton messageButton = new JButton(nextLoremText(2));
            String title = nextLoremText(2);
            String message = nextLoremText(10);
            messageButton.addActionListener((ActionEvent e)
                    -> JOptionPane.showMessageDialog(frame, message, title,
                            messageType));
            addLabeledRow(column, messageButton, 0, enabled);
        }

        JButton confirmButton = new JButton(nextLoremText(2));
        String confirmMessage = nextLoremText(8) + "?";
        confirmButton.addActionListener((ActionEvent e)
                -> JOptionPane.showConfirmDialog(frame, confirmMessage));
        addLabeledRow(column, confirmButton, 0, enabled);

        JButton inputButton = new JButton(nextLoremText(2));
        String inputMessage = nextLoremText(6);
        inputButton.addActionListener((ActionEvent e)
                -> JOptionPane.showInputDialog(frame, inputMessage));
        addLabeledRow(column, inputButton, 0, enabled);

        JButton selectionButton = new JButton(nextLoremText(2));
        String selectionMessage = nextLoremText(6);
        String[] selectionValues = nextLoremItems(6, 2);
        selectionButton.addActionListener((ActionEvent e)
                -> JOptionPane.showInputDialog(frame, selectionMessage,
                        selectionValues[0], JOptionPane.QUESTION_MESSAGE,
                        null, selectionValues, selectionValues[1]));
        addLabeledRow(column, selectionButton, 0, enabled);

        JButton dialogButton = new JButton(nextLoremText(2));
        dialogButton.addActionListener((ActionEvent e) -> showCustomDialog());
        addLabeledRow(column, dialogButton, 0, enabled);

        JButton colorButton = new JButton(nextLoremText(2));
        String colorTitle = nextLoremText(3);
        colorButton.addActionListener((ActionEvent e)
                -> JColorChooser.showDialog(frame, colorTitle,
                        new Color(0x808080)));
        addLabeledRow(column, colorButton, 0, enabled);

        JButton fileButton = new JButton(nextLoremText(2));
        fileButton.addActionListener((ActionEvent e)
                -> new JFileChooser().showOpenDialog(frame));
        addLabeledRow(column, fileButton, 0, enabled);

        JButton frameButton = new JButton(nextLoremText(2));
        frameButton.addActionListener((ActionEvent e) -> showSecondFrame());
        addLabeledRow(column, frameButton, 0, enabled);

        addLabeledRow(column, new JPanel(), 1.0, enabled);
        return column;
    }

    /**
     * Shows a modal dialog with some components and the typical button row,
     * so that the dialog decoration and the default button can be seen.
     */
    private void showCustomDialog() {
        JDialog dialog = new JDialog(frame, nextLoremText(3), true);
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        addLabeledRow(contentPanel, new JTextField(nextLoremText(3)), 0, true);
        addLabeledRow(contentPanel, new JComboBox<>(nextLoremItems(5, 2)),
                0, true);
        addLabeledRow(contentPanel, new JCheckBox(nextLoremText(3)), 0, true);
        addLabeledRow(contentPanel, new JTextField(nextLoremText(3)),
                0, false);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 6, 0));
        JButton okButton = new JButton(nextLoremText(1));
        okButton.addActionListener((ActionEvent e) -> dialog.dispose());
        JButton cancelButton = new JButton(nextLoremText(1));
        cancelButton.addActionListener((ActionEvent e) -> dialog.dispose());
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        southPanel.add(buttonPanel, BorderLayout.EAST);

        dialog.getContentPane().add(contentPanel, BorderLayout.CENTER);
        dialog.getContentPane().add(southPanel, BorderLayout.SOUTH);
        dialog.getRootPane().setDefaultButton(okButton);
        dialog.pack();
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    /**
     * Shows a second, non-modal frame with an embedded color chooser, which
     * is a large compound component that also belongs to the palette.
     */
    private void showSecondFrame() {
        JFrame secondFrame = new JFrame(nextLoremText(2));
        secondFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        secondFrame.getContentPane().add(new JColorChooser(
                new Color(0x5A7A9A)), BorderLayout.CENTER);
        secondFrame.setMinimumSize(new Dimension(400, 300));
        secondFrame.pack();
        secondFrame.setLocationRelativeTo(frame);
        secondFrame.setVisible(true);
    }
}
