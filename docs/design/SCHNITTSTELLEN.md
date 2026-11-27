# Schnittstellenverträge und Konsistenz
Das Klassendiagramm enthält alle synchronen Methodenaufrufe der Sequenzdiagramme.
Pfeile mit Ergebnis/Fehler sind Rückgaben, keine aufzurufenden Operationen.
Actor-Pfeile rufen on*-UI-Methoden auf; die UI stellt Ergebnisse mit show*-Methoden dar.
Die Diagramme sind Zielentwurf; im Code sind nur die Domänengrundlagen umgesetzt.

| Port | Vertrag |
|---|---|
| AlbumRepository.findById(id) | isolierte Arbeitskopie, Fehler bei unbekannter ID; kein null |
| AlbumRepository.findAll() | List<AlbumSummary> ohne Bildbytes, auch gelöschte Alben ausfiltern |
| AlbumRepository.save(album) | atomarer JSON-Austausch; Fehler lässt alte Metadaten gültig. Pro Album wird die Sperre vom Laden bis zum Schreiben gehalten. Save prüft keine fachliche Bestätigungsversion. |
| PhotoStorage.stage(source) | begrenzte, unveränderliche Kopie; bei Fehler eigene Teilkopien verwerfen |
| PhotoStorage.store(staged) | neue eindeutige Foto-ID, Ablage- und Vorschaureferenz, kein Überschreiben vorhandener Dateien; bei Fehler keine committed neue Datei |
| PhotoStorage.discardStage(staged) | idempotent, Fehler werden für spätere Bereinigung erfasst, kein Fehler nach außen |
| PhotoStorage.removeStored(stored) | löscht nur noch nicht referenzierte Importdatei; Fehler als Recovery-Aufgabe erfassen |
| PhotoStorage.moveToTrash(albumId) | Vertrag nach ADR-003: vor Commit alle Originale erhalten; nach Commit `TrashResult(cleanupPending)` zurückgeben |
| PhotoStorage.loadPage(albumId, offset, limit) | sortierte PhotoPage mit max. 100 Vorschauen, keine Originale im UI-Puffer |
| ImageFactsInspector.inspect(staged) | technische Fakten des unveränderlichen Snapshots liefern (`ImageFacts(sizeBytes, detectedFormat)`); bei Lesefehler technischer Fehler |
| ImageHasher.sha256(staged) | SHA-256 gestreamt auf genau demselben Snapshot wie ImageFactsInspector/store |
| DescriptionSuggester.suggest(request) | optional, deaktiviert; vor/nach Guardrails, liefert nur Vorschlag |

AlbumSummary: id, name, photoCount, version. PhotoView: id, title, previewRef; einziges
Foto-Ergebnis für die UI. TrashResult: cleanupPending. StagedImage: opaker Token; Infrastruktur löst ihn
intern auf einen Pfad auf. StoredPhoto: id, storageRef, previewRef, capturedAt;
toPhoto(hash, title) erzeugt Photo mit dem vom Use Case geprüften Titel. Die Domäne prüft
trimmt außen, normalisiert NFC und verlangt 1–100 Unicode-Codepoints. Photo.title() liefert
den gespeicherten Titel für PhotoView.
PhotoPage: Liste aus Foto-ID und Vorschaubildreferenz, offset, hasMore; Details in Block 4.
SourceRef, SuggestionRequest, Suggestion und Fehlerklassen sind im Zielentwurf noch nicht
vollständig ausmodellierte DTOs. `source` bezeichnet eine neutrale, vom Adapter geprüfte
Quellreferenz, kein beliebiger vom Agenten übergebener Dateisystempfad.

## Geschäftsregeln: eindeutiger Eigentümer
- Album: Name; 10.000; Hash-Eindeutigkeit je Album; aktive/gelöschte Zustandsübergänge;
  Foto-Reihenfolge und Entfernen.
- Photo: ID, Hash, Ablagereferenz und editierbare Metadaten. `validateAndNormalizeTitle`
  stellt für den Fototitel GR-05 und A-01 sicher (Trim, NFC, 1–100 Unicode-Codepoints).
- Album: Jede erfolgreiche Zustandsänderung erhöht die Version monoton. Enthaltene Fotometadaten
  werden über Album geändert, damit das Album die Version zuverlässig mitführt.
- ImportPolicy: Dateigrenze und erlaubte Formate; `check(ImageFacts)` entscheidet fachlich und
  wirft `ValidationError`. ImageFactsInspector ermittelt nur Größe und tatsächlich erkanntes
  Format. Kein Dateizugriff in der Domäne oder Anwendung.
- Album: `markAsDeleted(at, expectedVersion)` ist alleiniger Besitzer der fachlichen
  Versionsprüfung und wirft `VersionConflict`; bei Erfolg erhöht Album die Version.
- Anwendungsfall: Reihenfolge, Sperre und Fehlerkompensation; keine zweite Limitkonstante oder
  parallele Versionsentscheidung. `save(album)` persistiert atomar, prüft aber keine Bestätigungsversion.
- UI: Eingabe erfassen, Bestätigung einholen, Fehlertexte und Vorschauen anzeigen.
  Sie erhält bei Fotoimport ausschließlich `PhotoView`, nie ein `Photo`-Domänenobjekt.

## Weitere Pflichtfunktionen im Zielentwurf
Album erstellen/anzeigen/umbenennen und Foto entfernen werden in Block 4 über eigene
Anwendungsfälle an AlbumRepository und PhotoStorage angebunden. `Album.rename`,
`removePhoto`, `sort` und `Photo.editMetadata` sind im Diagramm vorbereitet, noch nicht im
Gerüst implementiert. Kein Vollständigkeitsanspruch einer fertigen App.
