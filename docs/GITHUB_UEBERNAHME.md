# Änderungen nach GitHub übernehmen

Der verbundene Account ist `sergveye`, das Ziel gehört `ichessebaeume1`.
Lesen funktionierte. Anlegen eines Issues, eines Branches und Git-Push wurden bei der
Bearbeitung jeweils mit HTTP 403 abgewiesen. Deshalb gibt es noch keinen Online-PR.
Das ist ein Zugriffsproblem der Verbindung, kein Buildfehler.

## Paket einfügen
1. Im eigenen aktuellen Checkout einen Branch anlegen:
   `git switch -c docs/block-3-architecture`
2. Im ZIP enthält `aenderungen/` nur neue/geänderte Dateien. Dessen Inhalt in das
   Repository kopieren; vorhandene Dateien werden dabei gezielt ersetzt. Vorher lokale
   Änderungen committen oder sichern. Keine `.git`-Ordner kopieren.
3. `./mvnw clean verify` (Windows: `.\mvnw.cmd clean verify`) mit JDK 21 ausführen.
4. Diff kontrollieren und committen:
   `git add ARCHITECTURE.md AI_USAGE.md README.md CHANGELOG.md docs src`
   `git commit -m "docs(FA-01,FA-04): Architektur und Entwurf für Block 3 ergänzen"`
5. `git push -u origin docs/block-3-architecture`
6. Pull Request gegen main öffnen; Titel „Block 3: Architektur und Entwurf der PhotoApp“.
   Label `ai-assisted` ergänzen. Sammel-Issue und Review-Issue anlegen, wenn nicht vorhanden.

Alternativ enthält `projekt/` einen vollständigen Stand ohne Git-Historie. Bestehendes Repo
bevorzugt über `aenderungen/` ergänzen. Die bisherigen Präsentationen/Block-2-Dateien werden
nicht ersetzt. README/CHANGELOG/AI_USAGE enthalten aber Änderungen und müssen mit eventuell
neueren Teamänderungen abgeglichen werden.

## Noch offen
Partnerteam muss die drei Fragen aus `docs/review-architecture-v1.md` prüfen. Danach
Rückmeldungen beantworten, Änderungen übernehmen und Approval/CI abwarten. Erst dann
mergen und den Merge-Commit mit `block-3` taggen. Keine Reviews oder Tags erfinden.

## Verbindung reparieren
Der Repository-Eigentümer sollte die Zugriffsfreigabe der GitHub-App für PhotoApp prüfen
(Inhalte, Issues und Pull Requests schreiben). Falls ihr die Verbindung neu autorisiert,
kann der vorbereitete Stand anschließend über einen PR übernommen werden.
