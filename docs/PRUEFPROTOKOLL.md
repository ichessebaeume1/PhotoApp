# Prüfprotokoll Block 3

Datum: 2026-11-16 · Prüfung durch Codex, kein menschliches Partnerreview.

## Build
Maven Wrapper 3.9.9, Eclipse Temurin JDK 21, JUnit 5.11.4.
`./mvnw clean verify`: **BUILD SUCCESS**.
Die Ausführungsumgebung benötigte einen lokalen Maven-Cache, Proxy-/Truststore-Konfiguration
und ein zusätzlich geladenes vollständiges JDK 21. Diese Umgebungskonfiguration ist nicht Teil
des Repos und keine Projektabhängigkeit.

Ergebnis: 6 entdeckte Tests, 5 ausgeführt/erfolgreich, 1 UI-Test mangels grafischer Umgebung
übersprungen, 0 Fehler. Vier neue fachliche Tests prüfen ungültiges Umbenennen ohne Änderung,
Duplikate innerhalb/zwischen Alben, die Grenzen 9.999/10.000/10.001 und doppelte Foto-IDs.
Der vorhandene Smoke-Test zählt nicht als fachlicher Block-5-Test. Keine Behauptung von
zehn vollständigen Unit-Tests oder implementierter Datei-/Recovery-Logik.

## Diagramme
PlantUML 1.2025.2 mit Graphviz: vier .puml-Dateien erfolgreich als PNG gerendert.
Alle vier PNGs visuell geprüft: keine abgeschnittenen Elemente; Originalauflösung verwenden.
40 synchrone Nachrichten beider Sequenzen gegen die Methoden der jeweiligen Empfängerklasse
abgeglichen: 0 ohne passende Operation. Rückgaben und Notizen nicht als Aufrufe gezählt.
Die PDF-Lesefassung umfasst 16 Seiten; der Architekturtext darin etwa zwei Seiten.

## Grenzen
Noch kein menschliches Review, keine Persistenz-/Fehlerinjektionstests, kein Leistungsnachweis
für 10.000 Originalbilder, keine Web-App und kein Sharing-Backend. Geplante Methoden des
Klassendiagramms sind nicht alle bereits im Gerüst implementiert. .puml/Markdown sind maßgeblich.
GitHub verweigerte das Anlegen des Sammel-Issues, des Remote-Branches und den normalen
Git-Push mit HTTP 403. Kein Online-PR, Review-Issue, Merge oder Tag wurde angelegt.
