# Changelog

## [Unreleased]

### Added
- Arbeitsauftrag, Modellkonfigurationen und Skill zur Repository-Prüfung sowie Review-Nachweise und Ablauf für den Block-4-Release dokumentiert. (#9)

### Changed
- Architekturreview #17 umgesetzt: ImportPolicy entscheidet über ImageFacts, Album besitzt die
  Versionsprüfung beim Löschen, PhotoView/TrashResult und der Konfliktpfad sind modelliert;
  Architekturtexte und Diagrammquellen abgeglichen.
- Projektdokumentation mit Implementierungsstand und Anforderungen abgeglichen. Vorlagen für Änderungsanträge und KI-Nutzung sowie eine Risikoübersicht ergänzt. (#4)
- Block-2-Anforderungen übernommen und als Soll-Verhalten von der aktuellen Implementierung abgegrenzt.
- Architektur und README weisen Cloud-, Medien- und Plattformanforderungen sowie offene Entscheidungen aus.
- Reviewer-Feedback eingearbeitet: lokale Java-21-Systemgrenze, messbare NFA, getrennte Entscheidungen/Annahmen, Dateiformate und KI-/Sicherheitsabschnitt.
- CI-Build um Windows-, macOS- und Linux-Runner erweitert.
- Review-Abnahme präzisiert: drei Abnahmeszenarien, angenommene Formatliste, Bereinigungszeitpunkt und Ausgangszustand des Performance-Tests.

## [0.1.0-SNAPSHOT] - 2026-11-04

### Added
- Initiales Projektgerüst mit Maven-Wrapper, Java 21, Swing-Platzhalter und CI-Konfiguration.

## Block-3-Entwurf

- Architektur, ADRs, CRC und PlantUML mit Fehlerpfaden ergänzt.
- Album/Photo-Grundlagen mit 10.000-Limit und Duplikatprüfung ergänzt.
- Offene Anforderungen und noch ausstehendes Teamreview dokumentiert.
