# Schnittstellenverträge und Konsistenz
Das Klassendiagramm enthält alle synchronen Methodenaufrufe der Sequenzdiagramme.
Pfeile mit Ergebnis/Fehler sind Rückgaben, keine aufzurufenden Operationen.
Actor-Pfeile rufen on*-UI-Methoden auf; die UI stellt Ergebnisse mit show*-Methoden dar.
Die Diagramme sind Zielentwurf; im Code sind nur die Domänengrundlagen umgesetzt.

| Port | Vertrag |
|---|---|
| AlbumRepository.findById(id) | isolierte Arbeitskopie, Fehler bei unbekannter ID; kein null |
| AlbumRepository.findAll() | List<AlbumSummary> ohne Bildbytes, auch gelöschte Alben ausfiltern |
| AlbumRepository.save(album) | atomarer JSON-Austausch; Versionsprüfung; Fehler lässt alte Metadaten gültig |
| PhotoStorage.stage(source) | begrenzte, unveränderliche Kopie; bei Fehler eigene Teilkopien verwerfen |
| PhotoStorage.store(staged) | neue eindeutige Foto-ID und Referenz, kein Überschreiben vorhandener Dateien; bei Fehler keine committed neue Datei |
| PhotoStorage.discardStage(staged) | idempotent, Fehler werden für spätere Bereinigung erfasst, kein Fehler nach außen |
| PhotoStorage.removeStored(stored) | löscht nur noch nicht referenzierte Importdatei; Fehler als Recovery-Aufgabe erfassen |
| PhotoStorage.moveToTrash(albumId) | Vertrag nach ADR-003: vor Commit alle Originale erhalten, nach Commit nur Erfolg mit evtl. offener Bereinigung |
| PhotoStorage.loadPage(albumId, offset, limit) | sortierte PhotoPage mit max. 100 Vorschauen, keine Originale im UI-Puffer |
| ImageFileValidator.validate(staged, policy) | tatsächlichen Inhalt und Größe prüfen; unbekannte Policy ablehnen |
| ImageHasher.sha256(staged) | SHA-256 gestreamt auf genau demselben Snapshot wie Validator/store |
| DescriptionSuggester.suggest(request) | optional, deaktiviert; vor/nach Guardrails, liefert nur Vorschlag |

AlbumSummary: id, name, photoCount, version. StagedImage: opaker Token; Infrastruktur löst ihn
intern auf einen Pfad auf. StoredPhoto: id, storageRef, capturedAt; toPhoto(hash) erzeugt Photo.
PhotoPage: Liste aus Foto-ID und Vorschaubildreferenz, offset, hasMore; Details in Block 4.
SourceRef, SuggestionRequest, Suggestion und Fehlerklassen sind im Zielentwurf noch nicht
vollständig ausmodellierte DTOs. `source` bezeichnet eine neutrale, vom Adapter geprüfte
Quellreferenz, kein beliebiger vom Agenten übergebener Dateisystempfad.

## Geschäftsregeln: eindeutiger Eigentümer
- Album: Name; 10.000; Hash-Eindeutigkeit je Album; aktive/gelöschte Zustandsübergänge;
  Foto-Reihenfolge und Entfernen.
- Photo: ID, Hash, Ablagereferenz und editierbare Metadaten; offene Längen später ergänzen.
- ImportPolicy: Dateigrenze und erlaubte Formate; ImageFileValidator führt die technische
  Prüfung dieser Policy aus. Kein Dateizugriff in der Anwendung selbst.
- Anwendungsfall: Reihenfolge, Sperre/Version, Fehlerkompensation; keine zweite Limitkonstante.
- UI: Eingabe erfassen, Bestätigung einholen, Fehlertexte und Vorschauen anzeigen.

## Weitere Pflichtfunktionen im Zielentwurf
Album erstellen/anzeigen/umbenennen und Foto entfernen werden in Block 4 über eigene
Anwendungsfälle an AlbumRepository und PhotoStorage angebunden. `Album.rename`,
`removePhoto`, `sort` und `Photo.editMetadata` sind im Diagramm vorbereitet, noch nicht im
Gerüst implementiert. Kein Vollständigkeitsanspruch einer fertigen App.
