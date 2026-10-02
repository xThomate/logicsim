package logicsim;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/** Application entry point and top-level window host. */
public class App {

    LSFrame lsframe;

    public App() {
        ModernUI.installDefaults();
        new I18N();
        JFrame frame = new MyFrame();
        lsframe = new LSFrame(frame);
        if (lsframe.getUI() instanceof BasicInternalFrameUI) {
            ((BasicInternalFrameUI) lsframe.getUI()).setNorthPane(null);
        } else if (lsframe.getUI() instanceof javax.swing.plaf.metal.MetalInternalFrameUI) {
            ((javax.swing.plaf.metal.MetalInternalFrameUI) lsframe.getUI()).setNorthPane(null);
        }
        lsframe.setBorder(null);
        frame.getContentPane().setBackground(ModernUI.BACKGROUND);
        frame.getContentPane().add(lsframe);

        lsframe.window = frame;

        lsframe.validate();
        frame.validate();
        sizeAndCenter(frame);
        lsframe.setVisible(true);
        frame.setVisible(true);
    }

    private void sizeAndCenter(JFrame frame) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int taskbarAllowance = 48;
        int width = Math.min(1380, Math.max(1080, screen.width - 64));
        int height = Math.min(860, Math.max(720, screen.height - taskbarAllowance));
        if (screen.width < 900) {
            width = screen.width;
        }
        if (screen.height < 650) {
            height = screen.height;
        }
        Dimension frameSize = new Dimension(width, height);
        frame.setMinimumSize(new Dimension(Math.min(900, width), Math.min(620, height)));
        frame.setSize(frameSize);
        lsframe.setSize(frameSize);
        lsframe.setMinimumSize(new Dimension(900, 620));
        int x = Math.max(0, (screen.width - frameSize.width) / 2);
        int y = Math.max(0, (screen.height - frameSize.height) / 2 - taskbarAllowance / 2);
        frame.setLocation(x, y);
        lsframe.setLocation(0, 0);
    }

    public static void main(final String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("LogicSim needs a graphical desktop to run.");
            return;
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }

    public static String getModulePath() {
        String path = new File("").getAbsolutePath() + File.separator + "modules" + File.separator;
        File modules = new File(path);
        if (!modules.exists()) {
            modules.mkdirs();
        }
        if (modules.isDirectory()) {
            return path;
        }
        javax.swing.JOptionPane.showMessageDialog(null,
                "Directory modules could not be created.\nPlease check the program directory");
        System.exit(0);
        return "";
    }

    class MyFrame extends JFrame {
        private static final long serialVersionUID = -6532037559895208999L;

        MyFrame() {
            super();
            setTitle("LogicSim");
            setBackground(ModernUI.BACKGROUND);
            setIconImage(createAppIcon());
            enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        }

        private java.awt.Image createAppIcon() {
            java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(64, 64,
                    java.awt.image.BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = image.createGraphics();
            try {
                g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(ModernUI.PRIMARY);
                g.fillRoundRect(2, 2, 60, 60, 17, 17);
                g.setColor(Color.WHITE);
                g.setStroke(new java.awt.BasicStroke(4f, java.awt.BasicStroke.CAP_ROUND,
                        java.awt.BasicStroke.JOIN_ROUND));
                g.drawLine(20, 18, 20, 46);
                g.drawLine(20, 32, 35, 32);
                g.drawLine(35, 18, 35, 46);
                g.drawLine(35, 32, 47, 32);
                g.fillOval(16, 14, 8, 8);
                g.fillOval(31, 28, 8, 8);
            } finally {
                g.dispose();
            }
            return image;
        }

        @Override
        protected void processWindowEvent(WindowEvent e) {
            if (e.getID() == WindowEvent.WINDOW_CLOSING
                    && lsframe.showDiscardDialog(I18N.getString("MENU_EXIT")) == false) {
                return;
            }
            super.processWindowEvent(e);
            if (e.getID() == WindowEvent.WINDOW_CLOSING) {
                lsframe.jMenuFileExit_actionPerformed(null);
            }
        }
    }
}
