package logicsim;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.JLabel;

/** Crisp, vector-painted icons used by the modern chrome. */
final class ModernIcons {
    private ModernIcons() {
    }

    static Icon create(String name, int size, Color color) {
        return new VectorIcon(name, size, color);
    }

    static Icon createThemed(String name, int size) {
        return new VectorIcon(name, size, null);
    }

    static JComponentIconHolder wrap(JLabel label, Color background) {
        return new JComponentIconHolder(label, background);
    }

    static final class JComponentIconHolder extends javax.swing.JPanel {
        private static final long serialVersionUID = 1L;

        JComponentIconHolder(JLabel label, Color background) {
            setOpaque(false);
            setLayout(new java.awt.BorderLayout());
            setPreferredSize(new Dimension(50, 50));
            label.setOpaque(false);
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            label.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
            label.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createLineBorder(background, 1, true),
                    javax.swing.BorderFactory.createEmptyBorder(7, 7, 7, 7)));
            add(label, java.awt.BorderLayout.CENTER);
        }
    }

    private static final class VectorIcon implements Icon {
        private final String name;
        private final int size;
        private final Color color;

        VectorIcon(String name, int size, Color color) {
            this.name = name;
            this.size = size;
            this.color = color;
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }

        @Override
        public void paintIcon(Component component, Graphics graphics, int x, int y) {
            Graphics2D g = (Graphics2D) graphics.create(x, y, size, size);
            try {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                g.setColor(color == null && component != null ? component.getForeground() : color);
                g.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                paint(g);
            } finally {
                g.dispose();
            }
        }

        private void paint(Graphics2D g) {
            int s = size;
            double u = s / 20.0;
            if ("new".equals(name)) {
                g.draw(new RoundRectangle2D.Double(3 * u, 2 * u, 14 * u, 16 * u, 2 * u, 2 * u));
                g.draw(new Line2D.Double(6 * u, 7 * u, 14 * u, 7 * u));
                g.draw(new Line2D.Double(6 * u, 11 * u, 12 * u, 11 * u));
                g.draw(new Line2D.Double(15 * u, 14 * u, 19 * u, 14 * u));
                g.draw(new Line2D.Double(17 * u, 12 * u, 19 * u, 14 * u));
                g.draw(new Line2D.Double(17 * u, 16 * u, 19 * u, 14 * u));
            } else if ("open".equals(name)) {
                Path2D folder = new Path2D.Double();
                folder.moveTo(2.5 * u, 16.5 * u);
                folder.lineTo(2.5 * u, 5 * u);
                folder.lineTo(7.5 * u, 5 * u);
                folder.lineTo(9.2 * u, 7 * u);
                folder.lineTo(17.5 * u, 7 * u);
                folder.lineTo(17.5 * u, 16.5 * u);
                folder.closePath();
                g.draw(folder);
                g.draw(new Line2D.Double(2.5 * u, 9 * u, 17.5 * u, 9 * u));
                g.draw(new Line2D.Double(11 * u, 12 * u, 17 * u, 12 * u));
            } else if ("save".equals(name)) {
                g.draw(new RoundRectangle2D.Double(3 * u, 2.5 * u, 14 * u, 15 * u, 2 * u, 2 * u));
                g.draw(new RoundRectangle2D.Double(6 * u, 3 * u, 7 * u, 5 * u, 1 * u, 1 * u));
                g.draw(new RoundRectangle2D.Double(6 * u, 11 * u, 8 * u, 6 * u, 1 * u, 1 * u));
                g.draw(new Line2D.Double(13 * u, 4.5 * u, 13 * u, 6.5 * u));
            } else if ("add-point".equals(name)) {
                g.draw(new Line2D.Double(3 * u, 10 * u, 17 * u, 10 * u));
                g.fill(new Ellipse2D.Double(1.8 * u, 8.8 * u, 2.4 * u, 2.4 * u));
                g.fill(new Ellipse2D.Double(15.8 * u, 8.8 * u, 2.4 * u, 2.4 * u));
                g.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g.draw(new Line2D.Double(10 * u, 4 * u, 10 * u, 16 * u));
                g.draw(new Line2D.Double(7 * u, 7 * u, 13 * u, 13 * u));
            } else if ("delete-point".equals(name)) {
                g.draw(new Line2D.Double(3 * u, 10 * u, 17 * u, 10 * u));
                g.fill(new Ellipse2D.Double(1.8 * u, 8.8 * u, 2.4 * u, 2.4 * u));
                g.fill(new Ellipse2D.Double(15.8 * u, 8.8 * u, 2.4 * u, 2.4 * u));
                g.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g.draw(new Line2D.Double(7 * u, 7 * u, 13 * u, 13 * u));
                g.draw(new Line2D.Double(13 * u, 7 * u, 7 * u, 13 * u));
            } else if ("play".equals(name)) {
                g.fill(new Polygon(
                        new int[]{(int) (5 * u), (int) (16 * u), (int) (5 * u)},
                        new int[]{(int) (3 * u), (int) (10 * u), (int) (17 * u)}, 3));
            } else if ("reset".equals(name)) {
                g.draw(new Ellipse2D.Double(3.5 * u, 3.5 * u, 13 * u, 13 * u));
                Path2D arrow = new Path2D.Double();
                arrow.moveTo(10 * u, 0.8 * u);
                arrow.lineTo(10 * u, 6 * u);
                arrow.lineTo(5 * u, 3.2 * u);
                arrow.closePath();
                g.fill(arrow);
            } else if ("logo".equals(name)) {
                g.setStroke(new BasicStroke(1.8f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g.draw(new Line2D.Double(5 * u, 5 * u, 5 * u, 15 * u));
                g.draw(new Line2D.Double(5 * u, 10 * u, 11 * u, 10 * u));
                g.draw(new Line2D.Double(11 * u, 5 * u, 11 * u, 15 * u));
                g.draw(new Line2D.Double(15 * u, 10 * u, 17 * u, 10 * u));
                g.fill(new Ellipse2D.Double(3.2 * u, 3.2 * u, 3.6 * u, 3.6 * u));
                g.fill(new Ellipse2D.Double(9.2 * u, 8.2 * u, 3.6 * u, 3.6 * u));
            } else {
                g.draw(new Ellipse2D.Double(3 * u, 3 * u, 14 * u, 14 * u));
            }
        }
    }
}
