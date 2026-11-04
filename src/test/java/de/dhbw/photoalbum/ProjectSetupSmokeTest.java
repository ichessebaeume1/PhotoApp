package de.dhbw.photoalbum;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Pr?ft nur das Projektger?st. Z?hlt NICHT zu den geforderten 10 Unit-Tests
 * und darf entfernt werden, sobald echte Tests existieren.
 */
class ProjectSetupSmokeTest {

    @Test
    void runsOnJava21OrNewer() {
        assertTrue(Runtime.version().feature() >= 21);
    }
}
