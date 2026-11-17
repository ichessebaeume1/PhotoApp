# Photo Album

Gruppenprojekt *Software Engineering II.1* (DHBW Heilbronn, WS 2026/27).
Eine kleine Java-Anwendung zur Erstellung und Verwaltung digitaler Fotoalben.
Fokus: nachvollziehbare Spezifikation, Architektur, Tests und kontrollierte Weiterentwicklung.

## Voraussetzungen

- **Git**
- **Java 21 JDK** (z. B. Eclipse Temurin). `JAVA_HOME` gesetzt, `java -version` zeigt 21.
- **Keine** globale Maven-Installation n?tig (Maven Wrapper ist enthalten).
- Keine Datenbank. Ziel ist lokale Speicherung mit optionaler Cloud-Synchronisierung nach ausdrücklicher Nutzerwahl; Cloud und Fotoverwaltung sind noch nicht implementiert.

## Schnellstart

```bash
git https://github.com/ichessebaeume1/PhotoApp.git
cd PhotoApp
```

| Aktion | macOS / Linux | Windows (cmd/PowerShell) |
|---|---|---|
| Bauen | `./mvnw clean compile` | `.\mvnw.cmd clean compile` |
| Tests + Coverage | `./mvnw clean test` | `.\mvnw.cmd clean test` |
| Voller Build wie CI | `./mvnw clean verify` | `.\mvnw.cmd clean verify` |
| Anwendung starten | `./mvnw exec:java` | `.\mvnw.cmd exec:java` |

Erwartet: `BUILD SUCCESS`.
Coverage-Report: `target/site/jacoco/index.html` im Browser öffnen.

Bei Error: 

`The JAVA_HOME environment variable is not defined correctly,
this environment variable is needed to run this program.`

```bash
$env:JAVA_HOME = "C:\tools\jdk-21.0.7+6"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
.\mvnw.cmd exec:java
```

Beim Start ?ffnet sich ein Platzhalter-Fenster (Swing). Es gibt keine Funktion, nur das
Layout. Auf Systemen ohne grafische Oberflöche (z. B. CI, SSH) wird stattdessen eine
Meldung ausgegeben.

IDE: Projekt als **Maven-Projekt** ?ffnen (IntelliJ: `pom.xml` -> ?Open as Project" /
?Add as Maven Project"), Projekt-SDK auf Java 21 stellen.

## Datenhaltung

**Ziel:** Originalfotos und Metadaten werden lokal gespeichert. Nutzer:innen können
zusätzlich eine Cloud-Synchronisierung wählen; das Kontingent beträgt laut Anforderungen
500 GB je Nutzerkonto. Die konkrete Speicherung ist noch nicht implementiert.

## Produktumfang und Implementierungsstand

Die Anforderungen verwenden **Core** für Album und **Memory** für Foto. Das Java-Projekt
enthält derzeit nur das Gerüst mit einer Swing-Platzhalteroberfläche. Import, Speicherung,
Papierkorb und Cloud-Synchronisierung sind Zielanforderungen, keine vorhandenen Funktionen.

Die Anforderungen sehen außerdem lokale Kernfunktionen auf iOS, iPadOS, macOS, Windows,
Android und Linux vor. Die vorhandene Swing-Oberfläche deckt diese Plattformanforderung
nicht ab; die technische Lösung ist noch offen. Einzelheiten und offene Punkte stehen in
[`REQUIREMENTS.md`](REQUIREMENTS.md) und [`ARCHITECTURE.md`](ARCHITECTURE.md).

## Projektstruktur

| Pfad | Inhalt |
|---|---|
| `src/main/java/de/dhbw/photoalbum/domain` | Entit?ten, Gesch?ftsregeln |
| `.../application` | Use Cases, Services, Ports |
| `.../infrastructure` | Dateisystem-Anbindung (Port-Implementierungen) |
| `.../ui` | Swing-Oberflöche, `Main` und `MainWindow` (aktuell Platzhalter) |
| `src/test/java/...` | Unit- und Integrationstests |
| `docs/` | ADRs, UML-Diagramme, Refactoring, Pr?sentation, KI-Prompts |

Weitere Dokumente: [REQUIREMENTS](REQUIREMENTS.md) ? [ARCHITECTURE](ARCHITECTURE.md) ?
[TESTING](TESTING.md) ? [CHANGE_REQUEST](CHANGE_REQUEST.md) ? [AI_USAGE](AI_USAGE.md) ?
[RISK_ANALYSIS](RISK_ANALYSIS.md) ? [AGENTS](AGENTS.md) ? [CHANGELOG](CHANGELOG.md)

## Git-Workflow (Team-Regeln)

`main` ist gesch?tzt ? **niemals direkt auf `main` committen.**

1. Issue anlegen (Requirement oder Bugfix).
2. Branch von `main`, z. B. `feature/FA-01-album-erstellen`.
3. Conventional Commits mit Issue-ID, z. B. `feat(#12): implement album creation`.
   Keine ?fix"- oder ?wip"-Commits auf `main`.
4. Pull Request gegen `main`.
5. CI (GitHub Actions, Job `build`) muss gr?n sein.
6. Mindestens ein anderes Teammitglied reviewt und gibt frei.
   KI-generierter Code erh?lt das Label `ai-assisted`.

## Vorgehensmodell (Block 1)


## Versionen

Java 21 ? Maven 3.9.9 (Wrapper) ? JUnit 5.11.4 ? JaCoCo 0.8.12 ? Swing (JDK)