# CRC-Karten und Walkthrough
| Klasse | Verantwortlichkeiten | Zusammenarbeit |
|---|---|---|
| Album | Name 1–50/nicht leer; 10.000; Hash einmal pro Album; Reihenfolge; Löschstatus; Monotone Version bei allen Zustandsänderungen; enthaltene Fotometadaten koordinieren | Photo |
| Photo | Identität, SHA-256, Ablagereferenz, Titel/Beschreibung; Titel validieren und NFC-normalisieren | Album |
| ImportPolicy | Größenlimit und erlaubte Formate fachlich anhand technischer Fakten prüfen | ImageFacts |
| ImageFacts | Dateigröße und erkanntes Format neutral ausdrücken | ImageFactsInspector |
| AddPhotoUseCase | serialisierter Import; Titel in der Domäne prüfen, Fakten ermitteln, Policy prüfen, Fehlerkompensation, Mehrfachimport als Einzelimporte | AlbumRepository, Album, Photo, PhotoStorage, ImageFactsInspector, ImportPolicy, ImageHasher, PhotoView |
| DeleteAlbumUseCase | Vorschau mit Anzahl/Version; Albumversion fachlich prüfen; Status zuerst; Rollback vor Trash-Commit | AlbumRepository, Album, PhotoStorage |
| AlbumRepository | Arbeitskopien/Übersicht/atomar speichern, ohne Bestätigungsversion zu entscheiden | FileAlbumRepository, AlbumSummary |
| PhotoStorage | Snapshot, Ablage, Vorschaubildseiten, Copy-before-delete, TrashResult | LocalPhotoStorage, StagedImage, StoredPhoto |
| ImageFactsInspector | technische Fakten aus dem Snapshot ermitteln, keine Policy-Entscheidung | FileImageFactsInspector, ImageFacts |
| ImageHasher | Hash-Port ohne Dateizugriff im Use Case | Sha256ImageHasher |
| PhotoView | UI-neutrale Fotoansicht mit ID, Titel und Vorschaureferenz | AddPhotoUseCase, PhotoController |
| PhotoController | Eingaben, Warnung, kurze Fehler, Darstellung; kein Domänenzugriff | AddPhotoUseCase, DeleteAlbumUseCase, PhotoView |
| DescriptionSuggester | optionaler Beschreibungsvorschlag | LlmDescriptionSuggester; Won’t |

## Walkthrough
1. UI übergibt ausgewähltes Foto und seinen Titel an `AddPhotoUseCase`; dieser prüft den Titel
   vor dem Staging über die Domäne (trimmen, NFC, 1–100 Unicode-Codepoints), lädt danach eine
   Album-Arbeitskopie.
2. Bei ungültigem Titel wird die Datei sofort abgelehnt. Sonst erzeugt Storage den Snapshot,
   Inspector ermittelt Größe/Format; ImportPolicy entscheidet,
   Hasher berechnet SHA-256.
3. Album prüft Limit und Hash. Duplikat? Keine Ablage/Metadatenänderung; Snapshot aufräumen.
4. Andernfalls neue Datei ablegen, Photo erzeugen, Album ändern, Metadaten speichern.
5. Metadatenfehler? Nur neue Datei bereinigen; gespeichertes Album bleibt unverändert.
6. Löschen: Summary zeigen, Bestätigungsversion an `Album.markAsDeleted` übergeben
   (einziger Versionsprüfer), Status speichern, sicheren Trash aufrufen.
   Storage-Fehler vor Commit? Status zurücksetzen. Rollback-Fehler? Recovery, alle Originale da.

Selbstprüfung durch KI ist kein Partnerreview. Die drei Prüffragen stehen im Review-Entwurf.
