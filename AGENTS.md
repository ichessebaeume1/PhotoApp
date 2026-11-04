# AGENTS.md ? Regeln f?r Coding-Agents

Diese Regeln gelten f?r jede KI, die in diesem Repository Code oder Doku ?ndert.
Die Verantwortung f?r das Ergebnis liegt beim Team.

## Projekt?berblick
Java 21, Maven Wrapper, JUnit 5, JaCoCo, lokale Dateisystem-Persistenz, Swing-UI.
Schichten: `domain` -> (keine Abh?ngigkeit), `application` -> `domain`,
`infrastructure` -> `application`/`domain`, `ui` -> `application`.
(Verbindlich erst nach `ARCHITECTURE.md`, Block 3.)

## Befehle
- Bauen: `./mvnw clean compile`
- Tests + Coverage: `./mvnw clean test`
- Voller Check wie CI: `./mvnw clean verify`
- Starten: `./mvnw exec:java`

**Vor Abschluss jeder Aufgabe muss `./mvnw clean verify` gr?n sein.**

## Arbeitsweise
1. Nie direkt auf `main`. Immer: Issue -> Branch -> Pull Request -> Review -> CI -> Merge.
2. Commits: Conventional Commits mit Issue-/Anforderungs-ID, z. B. `feat(#12): ...`,
   `test(#14): ...`, `refactor(#20): ...`. Refactoring-Commits ?ndern kein Verhalten
   und werden getrennt von fachlichen ?nderungen committet.
3. Jeder PR von einem Agenten erh?lt das Label `ai-assisted` und einen Eintrag in `AI_USAGE.md`.
4. Kleine, fokussierte ?nderungen. Nur umsetzen, was im Issue / in `REQUIREMENTS.md` steht.
   Keine zus?tzlichen Features.

## Code-Konventionen
- Bezeichner und Code auf Englisch, Dokumentation und Fehlermeldungen f?r Nutzer:innen auf Deutsch.
- Schichtgrenzen einhalten; keine Gesch?ftsregeln in `ui` oder `infrastructure`.
- Fehlerf?lle liefern fachliche Fehlermeldungen und lassen den Zustand unver?ndert.
- Keine neuen Abh?ngigkeiten, Frameworks oder Entwurfsmuster ohne ADR in `docs/adr/`
  (mit verworfener Alternative). Muster nur f?r ein benanntes Problem.
- Swing-Komponenten nur im Event-Dispatch-Thread anlegen/?ndern
  (`SwingUtilities.invokeLater`). Lange Operationen (Dateizugriff) nicht im EDT.

## Tests
- JUnit 5. Normal-, Fehler- und Grenzf?lle; Fehlerf?lle pr?fen den unver?nderten Zustand.
- Unit-Tests unter `src/test/java/.../domain` (bzw. passende Schicht),
  Integrationstests mit echter Dateisystem-Infrastruktur unter `.../infrastructure`
  (tempor?re Verzeichnisse, z. B. JUnit `@TempDir`; nie in `data/`).
- Keine Tests nur wegen hoher Coverage; Tests m?ssen fachliches Verhalten pr?fen.

## Dokumentation
- `REQUIREMENTS.md`, `ARCHITECTURE.md`, `TESTING.md`, `CHANGELOG.md` und ADRs in `docs/adr/`
  sind Teil der Qualit?tssicherung.
- ?nderungen an Anforderungen oder Architektur m?ssen passend dokumentiert werden.
