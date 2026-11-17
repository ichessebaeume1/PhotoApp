package de.dhbw.photoalbum.domain;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/** Block-3-Grundlage mit fachlichen Invarianten; Persistenz und Use Cases folgen in Block 4. */
public final class Album {
    public static final int MAX_PHOTOS = 10_000;
    private final UUID id;
    private String name;
    private final java.util.Set<UUID> photoIds = new java.util.HashSet<>();
    private final Map<String, Photo> photosByHash = new LinkedHashMap<>();

    public Album(UUID id, String name) {
        this.id = Objects.requireNonNull(id, "Album-ID fehlt");
        rename(name);
    }

    public void rename(String newName) {
        if (newName == null || newName.isBlank()
                || newName.codePointCount(0, newName.length()) > 50) {
            throw new IllegalArgumentException("Der Albumname braucht 1 bis 50 Zeichen und darf nicht nur aus Leerzeichen bestehen.");
        }
        name = newName;
    }

    public void checkCanAdd(String hash) {
        Objects.requireNonNull(hash, "Hash fehlt");
        if (photosByHash.containsKey(hash.toLowerCase(java.util.Locale.ROOT))) {
            throw new IllegalArgumentException("Dieses Foto ist bereits im Album.");
        }
        if (photosByHash.size() >= MAX_PHOTOS) {
            throw new IllegalStateException("Ein Album darf höchstens 10.000 Fotos enthalten.");
        }
    }

    public void addPhoto(Photo photo) {
        Objects.requireNonNull(photo, "Foto fehlt");
        checkCanAdd(photo.hash());
        if (photoIds.contains(photo.id())) {
            throw new IllegalArgumentException("Foto-ID bereits im Album.");
        }
        photosByHash.put(photo.hash(), photo);
        photoIds.add(photo.id());
    }

    public UUID id() { return id; }
    public String name() { return name; }
    public int photoCount() { return photosByHash.size(); }
    public List<Photo> photos() { return List.copyOf(photosByHash.values()); }
}
