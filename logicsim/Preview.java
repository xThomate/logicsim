package logicsim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/** Development-only visual smoke-test entry point. */
public final class Preview {
    private Preview() {
    }

    public static void main(String[] args) throws Exception {
        final File output = new File(args.length == 0 ? "logicsim-preview.png" : args[0]);
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                try {
                    ModernUI.installDefaults();
                    new I18N();
                    final JFrame frame = new JFrame("LogicSim");
                    final LSFrame app = new LSFrame(frame);
                    if (app.getUI() instanceof javax.swing.plaf.basic.BasicInternalFrameUI) {
                        ((javax.swing.plaf.basic.BasicInternalFrameUI) app.getUI()).setNorthPane(null);
                    } else if (app.getUI() instanceof javax.swing.plaf.metal.MetalInternalFrameUI) {
                        ((javax.swing.plaf.metal.MetalInternalFrameUI) app.getUI()).setNorthPane(null);
                    }
                    app.setBorder(null);
                    frame.getContentPane().add(app);
                    app.window = frame;
                    frame.setSize(1380, 860);
                    app.setSize(1380, 860);
                    frame.setLocation(0, 0);
                    frame.setVisible(true);
                    app.setVisible(true);
                    addDemoCircuit(app);
                    app.lspanel.repaint();
                    frame.validate();

                    BufferedImage image = new BufferedImage(1380, 860, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g = image.createGraphics();
                    try {
                        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
                        g.setColor(ModernUI.BACKGROUND);
                        g.fillRect(0, 0, image.getWidth(), image.getHeight());
                        frame.getContentPane().paintAll(g);
                    } finally {
                        g.dispose();
                    }
                    ImageIO.write(image, "png", output);
                    frame.dispose();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        System.exit(0);
    }

    private static void addDemoCircuit(LSFrame app) {
        SWITCH input = new SWITCH();
        input.x = 270;
        input.y = 300;
        AND and1 = new AND(2);
        and1.x = 520;
        and1.y = 250;
        AND and2 = new AND(2);
        and2.x = 520;
        and2.y = 380;
        OR outputGate = new OR(2);
        outputGate.x = 820;
        outputGate.y = 310;
        LED led = new LED();
        led.x = 1080;
        led.y = 310;

        Wire a = new Wire(input, 0);
        a.addPoint(320, 325);
        a.addPoint(430, 325);
        a.addPoint(430, 275);
        a.addPoint(520, 275);
        input.setOutput(true);
        and1.setInput(0, a);

        Wire b = new Wire(input, 0);
        b.addPoint(320, 325);
        b.addPoint(430, 325);
        b.addPoint(430, 405);
        b.addPoint(520, 405);
        and2.setInput(0, b);

        Wire c = new Wire(and1, 0);
        c.addPoint(570, 275);
        c.addPoint(710, 275);
        c.addPoint(710, 335);
        c.addPoint(820, 335);
        outputGate.setInput(0, c);

        Wire d = new Wire(and2, 0);
        d.addPoint(570, 405);
        d.addPoint(710, 405);
        d.addPoint(710, 335);
        d.addPoint(820, 335);
        outputGate.setInput(1, d);

        Wire e = new Wire(outputGate, 0);
        e.addPoint(870, 335);
        e.addPoint(1080, 335);
        led.setInput(0, e);

        app.lspanel.gates.addGate(input);
        app.lspanel.gates.addGate(and1);
        app.lspanel.gates.addGate(and2);
        app.lspanel.gates.addGate(outputGate);
        app.lspanel.gates.addGate(led);
        app.lspanel.gates.simulate();
    }
}