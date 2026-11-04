package de.dhbw.photoalbum.ui;

import java.awt.GraphicsEnvironment;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Einstiegspunkt der Anwendung. Startet aktuell nur das Platzhalter-Fenster.
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("Keine grafische Umgebung gefunden - Oberfl?che kann nicht gestartet werden.");
            return;
        }
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Standard-Look-and-Feel verwenden
            }
            new MainWindow().setVisible(true);
        });
    }
}
