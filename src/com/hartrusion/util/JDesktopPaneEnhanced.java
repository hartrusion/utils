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
package com.hartrusion.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 * Enhances the JDesktopPane to have a behavior that is more convenient to the
 * user from early days windows products.
 * <p>
 * The class messes around with the preferred size and makes the component
 * scrollable. The default JDesktopPane is not implementing the Scrollable
 * interface and therefor putting it into a JScollPane does not have any effect.
 *
 * @author Viktor Alexander Hartung
 */
public class JDesktopPaneEnhanced extends JDesktopPane implements Scrollable {

    private static final int UNIT_INCREMENT = 16;
    private static final int BLOCK_INCREMENT = 128;
    private static final int GAP_SIZE = 4;

    public JDesktopPaneEnhanced() {
        // Use a timer that will call the update method every 300 ms so in case 
        // of any update ever beeing missed, the scrolling behavior will be 
        // fixed.
        Timer fallbackUpdater = new Timer(300, e -> updateView());
        fallbackUpdater.setRepeats(true);
        fallbackUpdater.start();

    }

    @Override
    public Dimension getPreferredSize() {
        Dimension result = super.getPreferredSize();
        // Make a max selection from viewport size to make sure 
        JViewport vp = findViewport();
        if (vp != null) {
            result.width = Math.max(result.width, vp.getWidth());
            result.height = Math.max(result.height, vp.getHeight());
        }
        return result;
    }

    /**
     * Checks and updates the size of the contents of the JDesktopPane to decide
     * if scrolling is now necessary or not. There is no negative scrolling.
     */
    public void updateView() {
        Rectangle max = new Rectangle(0, 0, 0, 0);
        for (JInternalFrame f : getAllFrames()) {
            if (f.isVisible()) {
                Rectangle r = f.getBounds();
                max = max.union(r);
            }
        }
        // fügen wir einen Rand hinzu
        int pad = 32;
        Dimension newPref = new Dimension(max.x + max.width + pad, max.y + max.height + pad);

        // Nicht kleiner als aktueller Viewport
        JViewport vp = findViewport();
        if (vp != null) {
            newPref.width = Math.max(newPref.width, vp.getWidth());
            newPref.height = Math.max(newPref.height, vp.getHeight());
        }

        setPreferredSize(newPref);
        revalidate(); // Recalculate scroll bars
    }

    private JViewport findViewport() {
        Container p = getParent();
        if (p instanceof JViewport) {
            return (JViewport) p;
        }
        return null;
    }

    @Override // Scrollable Interface
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override // Scrollable Interface
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return UNIT_INCREMENT;
    }

    @Override // Scrollable Interface
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return BLOCK_INCREMENT;
    }

    @Override // Scrollable Interface
    public boolean getScrollableTracksViewportWidth() {
        // false: Breite kann größer als Viewport -> horizontal scrollen möglich
        return false;
    }

    @Override // Scrollable Interface
    public boolean getScrollableTracksViewportHeight() {
        // false: Höhe kann größer als Viewport -> vertikal scrollen möglich
        return false;
    }

    @Override
    protected void addImpl(Component comp, Object constraints, int index) {
        // Before adding, increment a value that will be used to determine the
        // position of the component that will be added. We use the height of 
        // the title bar (should be the same on every frame but who knows) as a
        // space. No matter which position the internal frame actually has!
        int tilePos = GAP_SIZE;
        JInternalFrame[] frames = getVisibleFrames();
        for (JInternalFrame f : frames) {
            BasicInternalFrameUI ui = (BasicInternalFrameUI) f.getUI();
            JComponent titleBar = ui.getNorthPane();
            tilePos += titleBar.getHeight() + GAP_SIZE;
        }

        // Add the component now
        super.addImpl(comp, constraints, index);

        if (!(comp instanceof JInternalFrame)) {
            return; // only if JInternalFrames are added
        }
        // cast the component to the frame to get access to its methods
        JInternalFrame compFrame = (JInternalFrame) comp;

        // Add listeners which will call the updateView method so the internal
        // frames will make the container desktop updating itself.
        // Mouse release on title bar after dragging:
        BasicInternalFrameUI ui = (BasicInternalFrameUI) compFrame.getUI();
        if (ui != null && ui.getNorthPane() != null) {
            ui.getNorthPane().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    SwingUtilities.invokeLater(
                            JDesktopPaneEnhanced.this::updateView);
                }
            });
        }

        // 2) Größenänderungen (über Border-Handles) -> bei Ende des Resize 
        // (MouseReleased)
        // Trick: globaler MouseListener auf Frame; fired beim Loslassen 
        // auch vom Border
        compFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                SwingUtilities.invokeLater(
                        JDesktopPaneEnhanced.this::updateView);
            }
        });

        // move/resize (z. B. via API)
        compFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                SwingUtilities.invokeLater(
                        JDesktopPaneEnhanced.this::updateView);
            }

            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(
                        JDesktopPaneEnhanced.this::updateView);
            }
        });

        // Iconify/Deiconify/Close/Maximum
        compFrame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameIconified(InternalFrameEvent e) {
                updateView();
            }

            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {
                updateView();
            }

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                updateView();
            }

            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                updateView();
            }
        });

        // make a new bounds rectangle with previous width and height values
        // and replace the x and y position with the previously determined 
        // positions.
        Rectangle newBounds = new Rectangle(tilePos, tilePos,
                compFrame.getBounds().width, compFrame.getBounds().height);
        compFrame.setBounds(newBounds);

        // Call the update after adding the component
        updateView();
    }

    public void windowsTileVertical(JDesktopPane desktop) {
        JInternalFrame[] frames = getVisibleFrames();
        if (frames.length == 0) {
            return;
        }

        Rectangle d = desktop.getBounds();
        int w = d.width / frames.length;
        int x = 0;

        for (JInternalFrame f : frames) {
            // f.setIcon(false);
            // f.setMaximum(false);
            f.setBounds(x, 0, w, d.height);
            x += w;
        }
        desktop.revalidate();
        desktop.repaint();
    }

    public void windowsTileHorizontal(JDesktopPane desktop) {
        JInternalFrame[] frames = getVisibleFrames();
        if (frames.length == 0) {
            return;
        }

        Rectangle d = desktop.getBounds();
        int h = d.height / frames.length;
        int y = 0;

        for (JInternalFrame f : frames) {
            //f.setIcon(false);
            //f.setMaximum(false);
            f.setBounds(0, y, d.width, h);
            y += h;
        }
        desktop.revalidate();
        desktop.repaint();
    }

    public void windowsCascade() {
        JInternalFrame[] frames = getVisibleFrames();
        if (frames.length == 0) {
            return;
        }

        Rectangle d = getBounds();
        int offset = 28; // Versatz zwischen Fenstern
        int w = (int) (d.width * 0.6);
        int h = (int) (d.height * 0.6);

        int x = 0, y = 0;
        for (JInternalFrame f : frames) {
            //f.setIcon(false);
            //f.setMaximum(false);
            f.setBounds(x, y, w, h);
            x += offset;
            y += offset;
            // Wenn wir aus dem Desktop „fallen“, wieder von vorne
            if (x + w > d.width || y + h > d.height) {
                x = 0;
                y = 0;
            }
        }
        revalidate();
        repaint();
    }

    /**
     * Places a frame on the right next to another given frame.
     *
     * @param frame The JInternalFrame that will be moved and placed.
     * @param ref JInternalFrame where frame will be put next to (will not move)
     */
    public void windowPlaceRightTo(JInternalFrame frame, JInternalFrame ref) {
        Rectangle refBounds = ref.getBounds();
        Rectangle newBounds = new Rectangle(
                refBounds.x + refBounds.width + GAP_SIZE,
                refBounds.y,
                frame.getBounds().width, frame.getBounds().height);
        frame.setBounds(newBounds);
    }

    /**
     * Places a frame on the right next to another given frame.
     *
     * @param frame The JInternalFrame that will be moved and placed.
     * @param ref JInternalFrame where frame will be put next to (will not move)
     */
    public void windowPlaceBelow(JInternalFrame frame, JInternalFrame ref) {
        Rectangle refBounds = ref.getBounds();
        Rectangle newBounds = new Rectangle(
                refBounds.x,
                refBounds.y + refBounds.height + GAP_SIZE,
                frame.getBounds().width, frame.getBounds().height);
        frame.setBounds(newBounds);
    }

    /**
     * Places a frame on the upper left corner.
     *
     * @param frame JInternalFrame
     */
    public void windowPlaceAtZero(JInternalFrame frame) {
        Rectangle newBounds = new Rectangle(
                GAP_SIZE, GAP_SIZE,
                frame.getBounds().width, frame.getBounds().height);
        frame.setBounds(newBounds);
    }

    /**
     * Returns an array containing only visible internal frames instead of all
     * of them.
     *
     * @return Array of JInternalFrame
     */
    private JInternalFrame[] getVisibleFrames() {
        return Arrays.stream(getAllFrames())
                .filter(f -> f.isVisible() && !f.isIcon())
                .toArray(JInternalFrame[]::new);
    }
}
