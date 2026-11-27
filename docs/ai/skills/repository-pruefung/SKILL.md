---
name: repository-pruefung
description: Prüft PhotoApp-Änderungen gegen Issue, Architektur, Review- und Release-Anforderungen.
---

# Repository-Prüfung

1. Lies das Issue und `AGENTS.md`. Vergleiche die Anforderungen mit dem aktuellen Stand auf `main`.
2. Prüfe bei Repository-Aufgaben die Schichten unter `src/main/java`, die Dokumente im Stamm, `.gitignore` und den CI-Workflow.
3. Belege GitHub-Reviews mit Links zu den Pull Requests. Halte offene Prüfungen im PR fest.
4. Prüfe den konkreten Branch mit `./mvnw clean verify` (Windows: `.\mvnw.cmd clean verify`) und dokumentiere das Ergebnis. Verlinke bei KI-Unterstützung die verwendeten Artefakte in `AI_USAGE.md`.
5. Prüfe vor Release-Tags, dass alle Aufgaben des Blocks auf `main` gemergt sind und die CI für den Abschlussstand grün ist.
