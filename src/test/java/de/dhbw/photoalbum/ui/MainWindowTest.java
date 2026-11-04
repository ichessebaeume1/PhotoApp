package de.dhbw.photoalbum.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import org.junit.jupiter.api.Test;

/**
 * Pr?ft nur das Platzhalter-Fenster. Wird ?bersprungen, wenn keine grafische Umgebung
 * vorhanden ist (z. B. in der CI). Darf entfernt werden, sobald die echte UI existiert.
 */
class MainWindowTest {

    @Test
    void placeholderWindowHasTitleAndOnlyDisabledButtons() throws Exception {
        assumeFalse(GraphicsEnvironment.isHeadless(), "keine grafische Umgebung");

        AtomicReference<MainWindow> ref = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> ref.set(new MainWindow()));
        MainWindow window = ref.get();
        try {
            assertEquals(MainWindow.TITLE, window.getTitle());
            for (Component c : window.toolBar().getComponents()) {
                if (c instanceof JButton button) {
                    assertFalse(button.isEnabled());
                }
            }
        } finally {
            SwingUtilities.invokeAndWait(window::dispose);
        }
    }
}
