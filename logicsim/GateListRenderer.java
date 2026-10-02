package logicsim;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/** Renders the dark component library with generous, easy-to-scan rows. */
final class GateListRenderer extends JComponent implements ListCellRenderer {
    private static final long serialVersionUID = 1L;
    private static final int ROW_HEIGHT = 38;

    private String label = "";
    private boolean selected;
    private boolean separator;

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        label = value == null ? "" : value.toString();
        selected = isSelected;
        separator = "---".equals(label);
        setBackground(index % 2 == 0 ? ModernUI.SIDEBAR : ModernUI.SIDEBAR_ALT);
        return this;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics.create();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setColor(selected ? ModernUI.PRIMARY : getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());

            if (separator) {
                g.setColor(new Color(0x2A354A));
                g.fillRect(14, getHeight() - 1, getWidth() - 28, 1);
                return;
            }
            if (selected) {
                g.setColor(new Color(0x7374F5));
                g.fillRoundRect(0, 2, getWidth(), getHeight() - 4, 10, 10);
            }

            g.setFont(ModernUI.NORMAL);
            FontMetrics metrics = g.getFontMetrics();
            g.setColor(selected ? Color.WHITE : new Color(0xD6DCE8));
            int baseline = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
            g.drawString(label, 14, baseline);

            g.setColor(selected ? Color.WHITE : new Color(0x5F6B7F));
            g.drawString("\u2022", Math.max(15, getWidth() - 23), baseline);
        } finally {
            g.dispose();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(190, ROW_HEIGHT);
    }
}
