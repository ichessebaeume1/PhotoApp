# Photo Album

Gruppenprojekt *Software Engineering II.1* an der DHBW Heilbronn (WS 2026/27). Ziel ist eine lokale Desktop-Anwendung zur Verwaltung digitaler Fotoalben. In den Anforderungen heißt ein Album **Core** und ein Foto **Memory**.

## Schnellstart

Installiere Git und ein Java-21-JDK. Prüfe die Java-Version mit `java -version` und setze `JAVA_HOME` bei Bedarf auf das JDK-Verzeichnis. Maven ist über den Wrapper verfügbar.

```bash
git clone https://github.com/ichessebaeume1/PhotoApp.git
cd PhotoApp
```

| Aktion | macOS / Linux | Windows (PowerShell) |
|---|---|---|
| Bauen | `./mvnw clean compile` | `.\mvnw.cmd clean compile` |
| Tests und Coverage | `./mvnw clean test` | `.\mvnw.cmd clean test` |
| Voller Build wie in der CI | `./mvnw clean verify` | `.\mvnw.cmd clean verify` |
| Anwendung starten | `./mvnw compile exec:java` | `.\mvnw.cmd compile exec:java` |

Der JaCoCo-Bericht liegt unter `target/site/jacoco/index.html`. In der IDE lässt sich die `pom.xml` als Maven-Projekt mit Java 21 öffnen.

## Implementierungsstand
vom 23.11.2026 - Bei neuem Implementierungsstand auch bitte entsprechend ändern.

Beim Start öffnet sich die Swing-Platzhalteroberfläche. In Umgebungen ohne grafische Oberfläche gibt die Anwendung eine Meldung aus. Die Klassen `Album` und `Photo` bilden erste Domänenregeln ab, darunter Albumname, Mengenlimit und Duplikate innerhalb eines Albums. Fotoimport, lokale Speicherung und Papierkorb sind Zielanforderungen.


Der Prüfungsumfang umfasst Windows, macOS und Linux. Auf diesen Plattformen führt die CI den Maven-Build mit Java 21 aus. Cloud, Web, Mobile und Videos liegen außerhalb des aktuellen Prüfungsumfangs.

| Pfad | Inhalt |
|---|---|
| `src/main/java/de/dhbw/photoalbum/domain` | Domänenklassen `Album` und `Photo` |
| `src/main/java/de/dhbw/photoalbum/ui` | Swing-Platzhalter in `Main` |
| `src/main/java/de/dhbw/photoalbum/application` und `infrastructure` | Paketgerüst für Anwendungsfälle und Dateisystem-Anbindung |
| `src/test/java` | Domänentests |
| `docs/` | Architekturentscheidungen, UML-Diagramme und Entwurfsdetails |

## Projektdokumentation

- [Anforderungen](REQUIREMENTS.md) und [Architektur mit Stand des Entwurfs](ARCHITECTURE.md#6-stand-und-offene-entscheidungen)
- [Testkonzept](TESTING.md), [Risikoanalyse](RISK_ANALYSIS.md) und [Änderungsanträge](CHANGE_REQUEST.md)
- [KI-Nutzung](AI_USAGE.md), [Agentenregeln](AGENTS.md) und [Changelog](CHANGELOG.md)

## Git-Workflow

Änderungen beginnen mit einem Issue und einem eigenen Branch. Commits folgen Conventional Commits mit Issue-ID, zum Beispiel `docs(#4): update project documentation`. Der Pull Request geht gegen `main` und benötigt eine grüne CI sowie die Freigabe eines anderen Teammitglieds. KI-unterstützte PRs erhalten das Label `ai-assisted` und einen Eintrag im KI-Protokoll.
