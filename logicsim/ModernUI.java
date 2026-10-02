package logicsim;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Presentation-only helpers for the modern LogicSim interface.
 *
 * This class intentionally contains no simulation or circuit behaviour. It
 * centralises colours, typography, component sizing and lightweight Swing
 * renderers so the existing event and gate logic can remain untouched.
 */
final class ModernUI {
    static final Color BACKGROUND = new Color(0xF5F7FB);
    static final Color SURFACE = Color.WHITE;
    static final Color SIDEBAR = new Color(0x111827);
    static final Color SIDEBAR_ALT = new Color(0x172033);
    static final Color BORDER = new Color(0xDDE3EC);
    static final Color TEXT = new Color(0x182033);
    static final Color MUTED = new Color(0x6B7280);
    static final Color PRIMARY = new Color(0x5B5CE2);
    static final Color PRIMARY_DARK = new Color(0x4546C8);
    static final Color PRIMARY_SOFT = new Color(0xECECFF);
    static final Color SUCCESS = new Color(0x17A673);
    static final Color CANVAS = new Color(0xFBFCFE);
    static final Color GRID_MINOR = new Color(0xE9EDF4);
    static final Color GRID_MAJOR = new Color(0xD9E0EB);
    static final Color WIRE_LOW = new Color(0x526174);
    static final Color WIRE_HIGH = new Color(0xF04465);
    static final Color SELECTION = new Color(0x696AF0);

    private static final Font SANS = chooseFont("Inter", "Segoe UI", "SF Pro Display", "Dialog");
    static final Font NORMAL = SANS.deriveFont(Font.PLAIN, 14f);
    static final Font MEDIUM = SANS.deriveFont(Font.BOLD, 14f);
    static final Font SMALL = SANS.deriveFont(Font.PLAIN, 12f);

    private ModernUI() {
    }

    private static Font chooseFont(String... candidates) {
        String[] available;
        try {
            available = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getAvailableFontFamilyNames();
        } catch (Throwable ignored) {
            available = new String[0];
        }
        for (String candidate : candidates) {
            for (String name : available) {
                if (name.equalsIgnoreCase(candidate)) {
                    return new Font(candidate, Font.PLAIN, 14);
                }
            }
        }
        return new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    }

    static void installDefaults() {
        try {
            javax.swing.UIManager.put("Label.font", NORMAL);
            javax.swing.UIManager.put("Button.font", MEDIUM);
            javax.swing.UIManager.put("ToggleButton.font", MEDIUM);
            javax.swing.UIManager.put("Menu.font", NORMAL);
            javax.swing.UIManager.put("MenuItem.font", NORMAL);
            javax.swing.UIManager.put("OptionPane.messageFont", NORMAL);
            javax.swing.UIManager.put("TextField.font", NORMAL);
            javax.swing.UIManager.put("TextArea.font", NORMAL);
            javax.swing.UIManager.put("ComboBox.font", NORMAL);
            javax.swing.UIManager.put("List.font", NORMAL);
            javax.swing.UIManager.put("Table.font", NORMAL);
            javax.swing.UIManager.put("Tree.font", NORMAL);
            javax.swing.UIManager.put("control", SURFACE);
            javax.swing.UIManager.put("controlDisabled", new Color(0xAAB2C0));
            javax.swing.UIManager.put("text", TEXT);
            javax.swing.UIManager.put("textDisabled", new Color(0x8A94A6));
            javax.swing.UIManager.put("textInactive", MUTED);
            javax.swing.UIManager.put("Table.selectionBackground", PRIMARY_SOFT);
            javax.swing.UIManager.put("Table.selectionForeground", PRIMARY_DARK);
            javax.swing.UIManager.put("Menu.selectionBackground", PRIMARY_SOFT);
            javax.swing.UIManager.put("Menu.selectionForeground", PRIMARY_DARK);
            javax.swing.UIManager.put("List.selectionBackground", PRIMARY_SOFT);
            javax.swing.UIManager.put("ScrollPane.background", BACKGROUND);
            javax.swing.UIManager.put("Viewport.background", CANVAS);
            javax.swing.UIManager.put("ToolBar.background", SURFACE);
            javax.swing.UIManager.put("Panel.background", BACKGROUND);
            javax.swing.UIManager.put("ToolTip.background", SIDEBAR);
            javax.swing.UIManager.put("ToolTip.foreground", Color.WHITE);
            javax.swing.UIManager.put("ToolTip.border",
                    BorderFactory.createLineBorder(new Color(0x334155), 1));
        } catch (Throwable ignored) {
            // The host look and feel remains a safe fallback.
        }
    }

    static void styleMenuBar(JMenuBar bar) {
        bar.setBackground(SURFACE);
        bar.setForeground(TEXT);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));
        bar.setPreferredSize(new Dimension(10, 48));
        for (int i = 0; i < bar.getMenuCount(); i++) {
            styleMenu(bar.getMenu(i));
        }
    }

    static void styleMenu(JMenu menu) {
        menu.setFont(NORMAL);
        menu.setForeground(TEXT);
        menu.setBorderPainted(false);
        menu.setContentAreaFilled(true);
        menu.setOpaque(true);
        menu.setBackground(SURFACE);
        menu.setMargin(new Insets(7, 14, 7, 14));
    }

    static void styleMenuItems(JMenuItem... items) {
        for (JMenuItem item : items) {
            item.setFont(NORMAL);
            item.setForeground(TEXT);
            item.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        }
    }

    static void styleToolbarButton(AbstractButton button, boolean toggle) {
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setMargin(new Insets(8, toggle ? 15 : 13, 8, toggle ? 15 : 13));
        button.setPreferredSize(new Dimension(toggle ? 122 : 106, 44));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setUI(new ModernButtonUI(toggle));
    }

    static void styleQuietButton(AbstractButton button) {
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setMargin(new Insets(8, 10, 8, 10));
        button.setPreferredSize(new Dimension(44, 42));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setUI(new ModernButtonUI(false));
    }

    static JPanel sidebarHeader(String title, String subtitle) {
        JPanel panel = new JPanel(new java.awt.BorderLayout());
        panel.setBackground(SIDEBAR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 18, 16, 18));

        JLabel brand = new JLabel("LOGICSIM");
        brand.setFont(SANS.deriveFont(Font.BOLD, 16f));
        brand.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel(subtitle == null ? "" : subtitle.toUpperCase());
        subtitleLabel.setFont(SANS.deriveFont(Font.BOLD, 10f));
        subtitleLabel.setForeground(new Color(0x8994AA));

        JPanel labels = new JPanel(new java.awt.BorderLayout());
        labels.setOpaque(false);
        labels.add(brand, java.awt.BorderLayout.NORTH);
        labels.add(subtitleLabel, java.awt.BorderLayout.SOUTH);

        JLabel mark = new JLabel();
        mark.setPreferredSize(new Dimension(34, 34));
        mark.setOpaque(false);
        mark.setIcon(ModernIcons.create("logo", 34, Color.WHITE));
        panel.add(ModernIcons.wrap(mark, PRIMARY), java.awt.BorderLayout.EAST);
        panel.add(labels, java.awt.BorderLayout.CENTER);
        return panel;
    }

    static JLabel sectionTitle(String text) {
        JLabel label = new JLabel(text.toUpperCase());
        label.setFont(SANS.deriveFont(Font.BOLD, 10f));
        label.setForeground(new Color(0x8994AA));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        return label;
    }

    static void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, BORDER));
        scrollPane.getViewport().setBackground(CANVAS);
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setOpaque(false);
        scrollPane.getHorizontalScrollBar().setOpaque(false);
    }

    static void styleSidebarScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(SIDEBAR);
        scrollPane.getVerticalScrollBar().setUI(new ModernSidebarScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ModernSidebarScrollBarUI());
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setOpaque(false);
    }

    static final class ModernSidebarScrollBarUI extends BasicScrollBarUI {
        ModernSidebarScrollBarUI() {
            configureScrollBarColors();
        }

        @Override
        protected void configureScrollBarColors() {
            trackColor = SIDEBAR;
            thumbColor = new Color(0x536176);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return zeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return zeroButton();
        }

        private JButton zeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void paintTrack(Graphics g, JComponent component, java.awt.Rectangle bounds) {
            g.setColor(trackColor);
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }

        @Override
        protected void paintThumb(Graphics graphics, JComponent component, java.awt.Rectangle bounds) {
            if (!scrollbar.isEnabled() || bounds.isEmpty()) {
                return;
            }
            Graphics2D g = (Graphics2D) graphics.create();
            try {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(thumbColor);
                g.fillRoundRect(bounds.x + 3, bounds.y + 2, Math.max(1, bounds.width - 6),
                        Math.max(1, bounds.height - 4), 8, 8);
            } finally {
                g.dispose();
            }
        }
    }

    private static final class ModernScrollBarUI extends BasicScrollBarUI {
        ModernScrollBarUI() {
            configureScrollBarColors();
        }

        @Override
        protected void configureScrollBarColors() {
            trackColor = new Color(0, 0, 0, 0);
            thumbColor = new Color(0xAEB8C8);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return zeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return zeroButton();
        }

        private JButton zeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void paintTrack(Graphics g, JComponent component, java.awt.Rectangle bounds) {
            g.setColor(trackColor);
            g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }

        @Override
        protected void paintThumb(Graphics graphics, JComponent component, java.awt.Rectangle bounds) {
            if (!scrollbar.isEnabled() || bounds.isEmpty()) {
                return;
            }
            Graphics2D g = (Graphics2D) graphics.create();
            try {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(thumbColor);
                g.fillRoundRect(bounds.x + 3, bounds.y + 3, Math.max(1, bounds.width - 6),
                        Math.max(1, bounds.height - 6), 8, 8);
            } finally {
                g.dispose();
            }
        }
    }

    private static final class ModernButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
        private final boolean toggle;

        ModernButtonUI(boolean toggle) {
            this.toggle = toggle;
        }

        @Override
        public void paint(Graphics graphics, JComponent component) {
            AbstractButton button = (AbstractButton) component;
            Graphics2D g = (Graphics2D) graphics.create();
            try {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                ButtonModel model = button.getModel();
                boolean selected = toggle && button.isSelected();
                Color fill = selected ? SUCCESS : (toggle ? PRIMARY : SURFACE);
                if (model.isPressed()) {
                    fill = selected ? SUCCESS.darker() : PRIMARY_SOFT;
                } else if (model.isRollover()) {
                    fill = selected ? SUCCESS.brighter() : PRIMARY_SOFT;
                }
                if (!toggle) {
                    g.setColor(BORDER);
                    g.drawRoundRect(0, 0, component.getWidth() - 1, component.getHeight() - 1, 10, 10);
                } else {
                    g.setColor(fill);
                    g.fillRoundRect(0, 0, component.getWidth(), component.getHeight(), 11, 11);
                }
                Color old = button.getForeground();
                button.setForeground(selected ? Color.WHITE : old);
                super.paint(g, component);
                button.setForeground(old);
            } finally {
                g.dispose();
            }
        }
    }
}
