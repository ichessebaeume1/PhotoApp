# ADR-001 – Ports, Repository und Adapter
Status: vorgeschlagen für Teamreview · Datum: 2026-11-16

## Kontext
Eine zweite Oberfläche und Tests sollen dieselben Regeln nutzen. Direkte Datei- und Swing-Zugriffe
in Anwendungsfällen würden technische Details mit Regeln koppeln.

## Entscheidung
Vier Schichten mit nach innen gerichteten Quellcode-Abhängigkeiten. AlbumRepository kapselt
Metadatenzugriff (Repository-Muster), PhotoStorage die Bildablage. Validator und Hasher sind
Anwendungsports mit Dateiadaptern (Adapter-Muster). Der Aufrufer kennt nur das Interface.
Startverdrahtung injiziert konkrete Adapter; Tests können InMemory-/Fake-Adapter einsetzen.
Ports verarbeiten neutrale IDs, Referenzen und DTOs, keine Swing- oder JSON-Typen.

## Verworfene Alternative
Direktes Lesen/Schreiben im Use Case: kürzer, aber Dateifehler kaum isolierbar und zweite UI
würde technische Kopplung übernehmen. Zusätzliche Factory, Observer und Strategy werden
bewusst nicht eingeführt: Es gibt noch kein belegtes Problem, das diese Muster erfordert.
Sortierarten lassen sich zunächst durch eine einfache Auswahl mit Comparator ausdrücken.

## Konsequenzen
Mehr Interfaces/Mapping, dafür austauschbare Infrastruktur und testbare Anwendungsfälle.
Die Abhängigkeiten von AddPhotoUseCase sind begründet: Album laden, Inhalt prüfen, hashen,
Datei ablegen und Metadaten persistieren sind verschiedene technische Aufgaben.
