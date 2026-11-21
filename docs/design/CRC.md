# CRC-Karten und Walkthrough
| Klasse | Verantwortlichkeiten | Zusammenarbeit |
|---|---|---|
| Album | Name 1–50/nicht leer; 10.000; Hash einmal pro Album; Reihenfolge; Löschstatus | Photo |
| Photo | Identität, SHA-256, Ablagereferenz, Titel/Beschreibung | Album |
| ImportPolicy | vereinbarte Größe und erlaubte Formate ausdrücken | ImageFileValidator |
| AddPhotoUseCase | serialisierter Import, Fehlerkompensation, Mehrfachimport als Einzelimporte | AlbumRepository, Album, PhotoStorage, ImageFileValidator, ImageHasher |
| DeleteAlbumUseCase | Vorschau mit Anzahl/Version; Status zuerst; Rollback vor Trash-Commit | AlbumRepository, Album, PhotoStorage |
| AlbumRepository | Arbeitskopien/Übersicht/atomar speichern als Vertrag | FileAlbumRepository, AlbumSummary |
| PhotoStorage | Snapshot, Ablage, Vorschaubildseiten, Copy-before-delete | LocalPhotoStorage, StagedImage, StoredPhoto |
| ImageFileValidator | Port für reale Größen-/Inhaltsprüfung | FileImageFileValidator, ImportPolicy |
| ImageHasher | Hash-Port ohne Dateizugriff im Use Case | Sha256ImageHasher |
| PhotoController | Eingaben, Warnung, kurze Fehler, Darstellung | AddPhotoUseCase, DeleteAlbumUseCase |
| DescriptionSuggester | optionaler Beschreibungsvorschlag | LlmDescriptionSuggester; Won’t |

## Walkthrough
1. UI übergibt ausgewähltes Foto an AddPhotoUseCase; dieser lädt eine Album-Arbeitskopie.
2. Storage erzeugt Snapshot, Validator prüft Policy, Hasher berechnet SHA-256.
3. Album prüft Limit und Hash. Duplikat? Keine Ablage/Metadatenänderung; Snapshot aufräumen.
4. Andernfalls neue Datei ablegen, Photo erzeugen, Album ändern, Metadaten speichern.
5. Metadatenfehler? Nur neue Datei bereinigen; gespeichertes Album bleibt unverändert.
6. Löschen: Summary zeigen, Version bestätigen, Status speichern, sicheren Trash aufrufen.
   Storage-Fehler vor Commit? Status zurücksetzen. Rollback-Fehler? Recovery, alle Originale da.

Selbstprüfung durch KI ist kein Partnerreview. Die drei Prüffragen stehen im Review-Entwurf.
