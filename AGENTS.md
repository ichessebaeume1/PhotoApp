# AGENTS.md – Regeln für Coding-Agents

Diese Regeln gelten für jede KI, die in diesem Repository Code oder Doku ändert.
Die Verantwortung für das Ergebnis liegt beim Team.

## Projektüberblick
Java 21, Maven Wrapper, JUnit 5 und JaCoCo. Der aktuelle Code enthält eine Swing-Platzhalter-UI und die Domänenklassen Album und Photo. Fotoimport, Persistenz und Papierkorb sind Zielanforderungen.
Ziel: lokale Java-21-Desktopanwendung mit Dateisystem-Persistenz für Windows, macOS und Linux. Cloud-, Web- und Mobile-Funktionen liegen außerhalb des aktuellen Prüfungsumfangs.
Schichten: `domain` -> (keine Abhängigkeit), `application` -> `domain`,
`infrastructure` -> `application`/`domain`, `ui` -> `application`.
(Verbindlich erst nach `ARCHITECTURE.md`, Block 3.)

## Befehle
- Bauen: `./mvnw clean compile`
- Tests + Coverage: `./mvnw clean test`
- Voller Check wie CI: `./mvnw clean verify`
- Starten: `./mvnw exec:java`

**Vor Abschluss jeder Aufgabe muss `./mvnw clean verify` grün sein.**

## Arbeitsweise
1. Nie direkt auf `main`. Immer: Issue -> Branch -> Pull Request -> Review -> CI -> Merge.
2. Commits: Conventional Commits mit Issue-/Anforderungs-ID, z. B. `feat(#12): ...`,
   `test(#14): ...`, `refactor(#20): ...`. Refactoring-Commits ändern kein Verhalten
   und werden getrennt von fachlichen Änderungen committet.
3. Jeder PR von einem Agenten erhält das Label `ai-assisted` und einen Eintrag in `AI_USAGE.md`.
4. Versioniere wiederverwendbare Prompts, projektspezifische Skills und bekannte Modellkonfigurationen unter `docs/ai/`. Verlinke die Arbeitsgrundlagen in `AI_USAGE.md`.
5. Kleine, fokussierte Änderungen. Nur umsetzen, was im Issue / in `REQUIREMENTS.md` steht.
   Keine zusätzlichen Features.

## Code-Konventionen
- Bezeichner und Code auf Englisch, Dokumentation und Fehlermeldungen für Nutzer:innen auf Deutsch.
- Schichtgrenzen einhalten; keine Geschäftsregeln in `ui` oder `infrastructure`.
- Fehlerfälle liefern fachliche Fehlermeldungen und lassen den Zustand unverändert.
- Keine neuen Abhängigkeiten, Frameworks oder Entwurfsmuster ohne ADR in `docs/adr/`
  (mit verworfener Alternative). Muster nur für ein benanntes Problem.
- Swing-Komponenten nur im Event-Dispatch-Thread anlegen/ändern
  (`SwingUtilities.invokeLater`). Lange Operationen (Dateizugriff) nicht im EDT.

## Tests
- JUnit 5. Normal-, Fehler- und Grenzfälle; Fehlerfälle prüfen den unveränderten Zustand.
- Unit-Tests unter `src/test/java/.../domain` (bzw. passende Schicht),
  Integrationstests mit echter Dateisystem-Infrastruktur unter `.../infrastructure`
  (temporäre Verzeichnisse, z. B. JUnit `@TempDir`; nie in `data/`).
- Keine Tests nur wegen hoher Coverage; Tests müssen fachliches Verhalten prüfen.

## Dokumentation
- `REQUIREMENTS.md`, `ARCHITECTURE.md`, `TESTING.md`, `CHANGELOG.md` und ADRs in `docs/adr/`
  sind Teil der Qualitätssicherung.
- Änderungen an Anforderungen oder Architektur müssen passend dokumentiert werden.
