# ADR-002 – JSON für lokale Metadaten
Status: vorgeschlagen für Teamreview · Datum: 2026-11-16

## Kontext
Ein Album umfasst bis zu 10.000 Fotoeinträge mit Identität, Hash, Datum, Titel, Beschreibung
und manueller Reihenfolge. Bildbytes gehören nicht in die Metadatendatei.

## Entscheidung
Pro Album `data/albums/<albumId>/album.json` mit `schemaVersion`, `version`, `deletedAt`,
Name und Fotoeinträgen. Originale unter `data/albums/<albumId>/photos/<photoId>`;
Dateinamen sind intern erzeugte IDs. JSON wird vom FileAlbumRepository umgesetzt.
Papierkorb unter `data/trash/<operationId>/`, Staging unter `data/staging/<operationId>/`.
JSON-Bibliothek wird erst in Block 4 ausgewählt; Block 3 braucht keine neue Laufzeitabhängigkeit.
Temporäre JSON-Datei im gleichen Verzeichnis schreiben, flushen und atomar ersetzen.
Wenn atomarer Austausch nicht unterstützt wird: Operation ablehnen, nicht unsicher überschreiben.

## Verworfene Alternative
`.properties`: für verschachtelte Fotolisten umständlich, fehleranfällige Indexschlüssel.
SQL-Datenbank: bietet Transaktionen, erhöht hier aber Setup und widerspricht dem einfachen
lokalen Dateispeicher als vorgegebenem Ausgangspunkt.

## Konsequenzen
Lesbar und versionierbar; Schema-Migration muss geplant werden. Ein Speichervorgang schreibt
eine ganze Metadatendatei neu. Bei 10.000 Einträgen messen; keine Behauptung konstanter
Schreibzeit. Parallelzugriffe müssen serialisiert werden. Keine Transaktion über Bild und JSON:
Kompensation und Recovery sind zusätzlich nötig (ADR-003).
