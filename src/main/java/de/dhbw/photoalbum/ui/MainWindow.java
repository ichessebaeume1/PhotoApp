package de.dhbw.photoalbum.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

/**
 * Platzhalter-Hauptfenster: zeigt das grobe Layout (Albumliste links, Fotobereich rechts).
 * Alle Aktionen sind deaktiviert. Keine Fachlogik, keine Dateizugriffe.
 */
final class MainWindow extends JFrame {

    static final String TITLE = "Photo Album";
    private static final String HINT = "Platzhalter - Funktionen folgen ab Block 4";

    private final JToolBar toolBar = createToolBar();

    MainWindow() {
        super(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        add(toolBar, BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);

        JLabel status = new JLabel(HINT);
        status.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        add(status, BorderLayout.SOUTH);
    }

    JToolBar toolBar() {
        return toolBar;
    }

    private static JToolBar createToolBar() {
        JToolBar bar = new JToolBar();
        bar.setFloatable(false);
        for (String label : new String[] {
            "Neues Album", "Umbenennen", "L?schen", "Foto hinzuf?gen", "Foto entfernen"}) {
            JButton button = new JButton(label);
            button.setEnabled(false);
            button.setToolTipText("Folgt in Block 4");
            bar.add(button);
        }
        return bar;
    }

    private static JComponent createContent() {
        DefaultListModel<String> albums = new DefaultListModel<>();
        albums.addElement("(noch keine Alben)");
        JList<String> albumList = new JList<>(albums);
        albumList.setEnabled(false);

        JLabel photoArea = new JLabel("Hier erscheinen sp?ter die Fotos des Albums",
            SwingConstants.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            new JScrollPane(albumList), photoArea);
        split.setDividerLocation(220);
        return split;
    }
}
