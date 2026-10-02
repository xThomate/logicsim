package logicsim;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * File chooser adapter that uses the operating system's native dialog on
 * Windows and a styled Swing chooser everywhere else.
 *
 * The rest of LogicSim stores plain file paths, so this class deliberately
 * exposes only a selected File and does not change the circuit file format.
 */
final class NativeFileChooser {
    private NativeFileChooser() {
    }

    static File choose(Component parent, Frame owner, String currentPath,
                       String title, boolean save, String extension,
                       String description) {
        File current = currentPath == null ? null : new File(currentPath);
        File directory = directoryFor(current);
        String suggestedName = suggestedName(current, save, extension);

        if (isWindows()) {
            FileDialog dialog = new FileDialog(owner, title,
                    save ? FileDialog.SAVE : FileDialog.LOAD);
            dialog.setDirectory(directory.getAbsolutePath());
            if (suggestedName.length() > 0) {
                dialog.setFile(suggestedName);
            }
            if (extension != null && extension.length() > 0) {
                final String suffix = "." + extension.toLowerCase();
                dialog.setFilenameFilter(new java.io.FilenameFilter() {
                    public boolean accept(File dir, String name) {
                        return new File(dir, name).isDirectory()
                                || name.toLowerCase().endsWith(suffix);
                    }
                });
            }
            dialog.setVisible(true);
            String selected = dialog.getFile();
            if (selected == null || selected.length() == 0) {
                return null;
            }
            return new File(dialog.getDirectory(), selected).getAbsoluteFile();
        }

        JFileChooser chooser = new JFileChooser(directory);
        chooser.setDialogTitle(title);
        if (suggestedName.length() > 0 && save) {
            chooser.setSelectedFile(new File(directory, suggestedName));
        } else if (!save && current != null && current.isFile()) {
            chooser.setSelectedFile(current);
        }
        if (extension != null && extension.length() > 0) {
            String label = description == null ? extension.toUpperCase() : description;
            chooser.setFileFilter(new FileNameExtensionFilter(label, extension));
        }
        int result = save ? chooser.showSaveDialog(parent) : chooser.showOpenDialog(parent);
        return result == JFileChooser.APPROVE_OPTION
                ? chooser.getSelectedFile().getAbsoluteFile() : null;
    }

    private static File directoryFor(File current) {
        if (current == null) {
            return new File(".");
        }
        if (current.isDirectory()) {
            return current;
        }
        String path = current.getPath();
        if (path.endsWith(File.separator) || path.endsWith("/")) {
            return new File(path);
        }
        File parent = current.getParentFile();
        return parent != null ? parent : new File(".");
    }

    private static String suggestedName(File current, boolean save, String extension) {
        if (!save || current == null || current.isDirectory()) {
            return "";
        }
        String name = current.getName();
        if (extension != null && extension.length() > 0
                && !name.toLowerCase().endsWith("." + extension.toLowerCase())) {
            name = name + "." + extension;
        }
        return name;
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }
}