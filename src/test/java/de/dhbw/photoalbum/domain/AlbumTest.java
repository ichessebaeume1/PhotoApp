package de.dhbw.photoalbum.domain;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class AlbumTest {
    private Photo photo(int n) {
        return new Photo(new UUID(0, n), String.format("%064x", n), "photos/" + n, Instant.EPOCH);
    }

    @Test void invalidRenamePreservesName() {
        Album album = new Album(UUID.randomUUID(), "Urlaub");
        for (String invalid : new String[] {"", "   ", "a".repeat(51)}) {
            assertThrows(IllegalArgumentException.class, () -> album.rename(invalid));
            assertEquals("Urlaub", album.name());
        }
        album.rename("a".repeat(50));
        assertEquals(50, album.name().length());
    }

    @Test void rejectsDuplicateContentWithoutChangeButAllowsItInAnotherAlbum() {
        Album first = new Album(UUID.randomUUID(), "A");
        Album second = new Album(UUID.randomUUID(), "B");
        Photo original = photo(1);
        first.addPhoto(original);
        Photo sameContent = new Photo(UUID.randomUUID(), original.hash().toUpperCase(), "another", Instant.EPOCH);
        assertThrows(IllegalArgumentException.class, () -> first.addPhoto(sameContent));
        assertEquals(java.util.List.of(original), first.photos());
        second.addPhoto(sameContent);
        assertEquals(1, second.photoCount());
    }

    @Test void acceptsTenThousandAndRejectsNextWithoutChangingState() {
        Album album = new Album(UUID.randomUUID(), "Großes Album");
        for (int n = 1; n <= 9_999; n++) album.addPhoto(photo(n));
        assertEquals(9_999, album.photoCount());
        album.addPhoto(photo(10_000));
        var before = album.photos();
        assertThrows(IllegalStateException.class, () -> album.addPhoto(photo(10_001)));
        assertEquals(10_000, album.photoCount());
        assertEquals(before, album.photos());
        assertThrows(UnsupportedOperationException.class, () -> album.photos().clear());
    }

    @Test void rejectsReuseOfPhotoIdentityWithoutChangingState() {
        Album album = new Album(UUID.randomUUID(), "A");
        Photo original = photo(1);
        album.addPhoto(original);
        Photo conflicting = new Photo(original.id(), photo(2).hash(), "other", Instant.EPOCH);
        assertThrows(IllegalArgumentException.class, () -> album.addPhoto(conflicting));
        assertEquals(java.util.List.of(original), album.photos());
    }
}
