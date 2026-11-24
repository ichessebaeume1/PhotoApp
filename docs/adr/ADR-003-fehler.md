# ADR-003 – Fehler, Schreibreihenfolge und Wiederanlauf
Status: vorgeschlagen für Teamreview · Datum: 2026-11-16

## Kontext
Dateien und JSON sind keine gemeinsame Transaktion. Ein einfaches Zurückverschieben nach
einem Teilfehler reicht nicht: Es kann selbst fehlschlagen. Ein Absturz kann jeden Schritt treffen.

## Entscheidung
Anwendungsfälle liefern benannte Fehler (ValidationError, DuplicatePhoto, AlbumFull,
StorageError, PersistenceError, VersionConflict), UI übersetzt sie in höchstens 50 Wörter.
Repository liefert getrennte Arbeitskopien. Pro Album wird eine Operation serialisiert, auch
gegen andere lokale Prozesse (Sperre). Bei ungeklärtem Recovery wird das Album gesperrt.
Technische Details gehen ins lokale Log, keine privaten Pfade in Nutzermeldungen.

### Foto hinzufügen
1. Den von der UI übergebenen Fototitel vor dem Staging in der Domäne prüfen: außen trimmen,
   nach NFC normalisieren und 1–100 Unicode-Codepoints verlangen (GR-05, A-01).
   Bei `ValidationError` keine Staging-Datei erzeugen.
2. Begrenzten, unveränderlichen Snapshot erzeugen, echten Inhalt prüfen, SHA-256 berechnen.
3. Albumregeln prüfen, neue Foto-ID vergeben, geprüften Titel weitergeben und Kopie unter
   dieser ID ablegen.
4. Arbeitskopie ändern und JSON atomar speichern. Dies ist der fachliche Commit.
4. Wenn Schritt 3 scheitert: neue Ablage löschen; scheitert das auch, bleibt nur eine verwaiste
Datei. Das bisher gespeicherte Album bleibt gültig. `discardStage` ist idempotent, scheitert
nicht nach außen; Staging-Reste werden beim nächsten Start erneut entfernt.
Keine automatische Löschung erreichbarer Originale. Journal und gespeicherte Referenzen
entscheiden bei Startbereinigung, nicht das Alter der Datei allein.

### Album löschen
`requestDeletion` liefert Summary inklusive Version und Anzahl, schreibt nichts.
`confirmDeletion` lädt unter Sperre eine Arbeitskopie und ruft
`Album.markAsDeleted(at, expectedVersion)` auf. Album ist der einzige Besitzer der
fachlichen Versionsprüfung: bei abweichender Version wirft es `VersionConflict`, ohne
den Zustand zu verändern. Bei passender Version setzt Album `deletedAt` und erhöht die
Version. Erst danach wird der Löschstatus gespeichert; bei Speicherfehler keine Dateibewegung.
`AlbumRepository.save(album)` führt den atomaren JSON-Austausch aus, entscheidet aber nicht,
ob die Bestätigungsversion fachlich gültig ist.
Die Albumsperre gilt vom Laden der Arbeitskopie bis zum atomaren Speichern. Album erhöht seine
Version bei jeder erfolgreichen Zustandsänderung, einschließlich Add/Remove, Rename,
Fotometadaten, Löschen und Wiederherstellen. Das Zurücksetzen nach einem Storage-Fehler
ist selbst eine Zustandsänderung und erhöht daher die Version weiterhin monoton.
`VersionConflict` lässt weder Zustand noch Version ändern; die UI lädt danach eine frische
Vorschau und verlangt eine neue Bestätigung.
`moveToTrash` ist eine logische Verschiebung mit folgendem Vertrag:
- Vor Beginn Journal mit Operation-ID und Albumversion dauerhaft schreiben.
- Alle Dateien in ein separates Trash-Verzeichnis **kopieren**, Integrität prüfen;
  vor Commit keine aktive Originaldatei entfernen.
- Trash-Manifest und Commit-Markierung dauerhaft schreiben. Ab jetzt gilt die Bewegung
  als erfolgreich; Entfernen der Originale ist wiederholbare Nachbereinigung.
- Fehler vor Commit: Originale vollständig; StorageError auslösen. Use Case setzt Status
  in Arbeitskopie zurück und speichert ihn. Auch bei Teilkopien bleiben alle Originale vorhanden.
- Fehler nach Commit: keine fehlgeschlagene Bewegung melden und keinen Status-Rollback
  versuchen; Trash ist vollständig, nur Bereinigung offen. Erfolg liefert
  `TrashResult(cleanupPending)`, damit die Rückgabe des Ports den Bereinigungsstatus ausdrückt.
- Rollback-Speichern scheitert: Album bleibt gelöscht markiert, Originale vollständig,
  Recovery erforderlich. UI meldet „Löschen nicht abgeschlossen. Dateien bleiben erhalten.
  Bitte starte die App neu.“ Kein Erfolg und keine automatische Endlöschung.

| Absturzpunkt | Persistenter Zustand | Wiederanlauf vor UI-Freigabe |
|---|---|---|
| vor Löschstatus | aktiv, Originale vollständig | unverändert öffnen |
| nach Status, vor Trash-Commit | gelöscht, Originale vollständig, evtl. Teilkopien | Move idempotent fortsetzen; bei Fehler gesperrt lassen |
| nach Trash-Commit | gelöscht, Trash vollständig, evtl. Originalduplikate | Originalduplikate bereinigen |
| nach erfolgreichem Status-Rollback | aktiv, Originale vollständig | uncommittete Teilkopien anhand Journal entfernen |
| nach fehlgeschlagenem Rollback | gelöscht, Originale vollständig | Journal auswerten, fortsetzen, keine Originale löschen |

Einmalige Operation-IDs und commit-Markierungen machen Wiederholungen erkennbar. Ein falsches
„aktiv, Originale fehlen“ darf nicht entstehen. Betriebssystem-/Hardware-Dauerhaftigkeit bleibt
eine technische Grenze; in Block 4 sind Flush/Atomic-Move und Recovery mit Fehlerinjektion zu testen.
Endgültiges Löschen ist ein **eigener**, erneut bestätigter Anwendungsfall; keine automatische
Papierkorbleerung ohne geklärte Aufbewahrung. Entfernen einzelner Fotos soll dieselbe
Copy-before-delete-Regel nutzen. Diese Abläufe sind entworfen, noch nicht implementiert.

## Verworfene Alternative
Erst Dateien verschieben, danach JSON speichern: bei Fehlern kann ein aktives Album auf
fehlende Dateien zeigen. Bloßes Zurücksetzen von deletedAt nach teilweise destruktivem Move
löst das Problem nicht. Eine verteilte Transaktionsinfrastruktur wäre unverhältnismäßig.

## Konsequenzen
Temporär doppelter Platzbedarf und Journal-/Recovery-Aufwand. Vorher freien Speicher prüfen;
bei Platzmangel vor dem Commit abbrechen. Bewusst Vorrang für Erhalt aller Fotos statt
sofortiger Platzfreigabe. Bestätigung allein garantiert keine crashfeste Dateioperation.
