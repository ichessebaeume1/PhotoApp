package de.dhbw.photoalbum.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createWindow);
    }

    private static void createWindow() {
        JFrame window = new JFrame("Photo Album");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        String[] actions = {
            "Neues Album",
            "Umbenennen",
            "Löschen",
            "Foto hinzufügen",
            "Foto entfernen"
        };

        for (String action : actions) {
            JButton button = new JButton(action);
            button.setEnabled(false);
            button.setToolTipText("Funktion folgt in Block 4");
            toolbar.add(button);
        }

        JList<String> albums = new JList<>(
            new String[] {"(noch keine Alben)"}
        );
        albums.setEnabled(false);

        JLabel photoArea = new JLabel(
            "Hier erscheinen später die Fotos des Albums",
            SwingConstants.CENTER
        );

        JSplitPane content = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            new JScrollPane(albums),
            photoArea
        );
        content.setDividerLocation(220);

        window.add(toolbar, BorderLayout.NORTH);
        window.add(content, BorderLayout.CENTER);
        window.add(
            new JLabel("Platzhalter – Funktionen folgen ab Block 4"),
            BorderLayout.SOUTH
        );

        window.setSize(900, 600);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}