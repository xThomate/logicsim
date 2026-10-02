package logicsim;

import java.awt.AWTEvent;
import java.io.ObjectInputStream;
import java.net.URL;
import javax.swing.JApplet;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.plaf.metal.MetalInternalFrameUI;

/** Browser entry point retained for backwards compatibility. */
public class Applet extends JApplet {
    private static final long serialVersionUID = 1L;

    LSFrame lsframe;

    @Override
    public void init() {
        ModernUI.installDefaults();
        new I18N(this);
        lsframe = new LSFrame(this);
        if (lsframe.getUI() instanceof BasicInternalFrameUI) {
            ((BasicInternalFrameUI) lsframe.getUI()).setNorthPane(null);
        } else if (lsframe.getUI() instanceof MetalInternalFrameUI) {
            ((MetalInternalFrameUI) lsframe.getUI()).setNorthPane(null);
        }
        lsframe.setVisible(true);
        lsframe.setBorder(null);
        getContentPane().add(lsframe);
    }

    @Override
    public void start() {
        // Check applet params.
        String loadcircuit = getParameter("loadcircuit");
        if (loadcircuit != null && loadcircuit.length() > 0) {
            loadCircuit(loadcircuit);
        }

        String startsim = getParameter("startsimulation");
        if (startsim != null && startsim.equals("true")) {
            lsframe.jToggleButton_simulate.setSelected(true);
            lsframe.sim = new Simulate(lsframe.lspanel);
        }
    }

    public void loadCircuit(String fname) {
        try {
            URL url = new URL(getCodeBase() + fname);
            ObjectInputStream s = new ObjectInputStream(url.openStream());
            lsframe.lspanel.gates = (GateList) s.readObject();
            s.close();
        } catch (Exception ex) {
            lsframe.showMessage(ex.toString());
        }

        lsframe.lspanel.gates.reconnect();
        lsframe.lspanel.repaint();
        lsframe.lspanel.changed = false;
    }
}
