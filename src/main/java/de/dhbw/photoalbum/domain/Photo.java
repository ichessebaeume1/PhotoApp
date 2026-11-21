package de.dhbw.photoalbum.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/** Block-3-Grundlage: unveränderliche Fotoidentität; Metadatenbearbeitung folgt in Block 4. */
public final class Photo {
    private final UUID id;
    private final String hash;
    private final String storageRef;
    private final Instant capturedAt;

    public Photo(UUID id, String hash, String storageRef, Instant capturedAt) {
        this.id = Objects.requireNonNull(id, "Foto-ID fehlt");
        if (hash == null || !hash.matches("[0-9a-fA-F]{64}")) {
            throw new IllegalArgumentException("Ungültiger SHA-256-Hash");
        }
        this.hash = hash.toLowerCase(java.util.Locale.ROOT);
        if (storageRef == null || storageRef.isBlank()) {
            throw new IllegalArgumentException("Ablagereferenz fehlt");
        }
        this.storageRef = storageRef;
        this.capturedAt = Objects.requireNonNull(capturedAt, "Fotodatum fehlt");
    }

    public UUID id() { return id; }
    public String hash() { return hash; }
    public String storageRef() { return storageRef; }
    public Instant capturedAt() { return capturedAt; }
}
