# KI-Anweisung: Initiales Projekt-Setup „Photo Album"

> **Zielgruppe dieses Dokuments:** ein Coding-Agent (KI), der das Repository
> `felixrau2105/se2-git-group-3` von Grund auf einrichtet.
> **Ergebnis:** ein lauffähiges, getestetes, CI-fähiges Projektgerüst mit sichtbarem
> Platzhalter-Fenster, das als **initialer Commit** gepusht werden kann. Jedes Teammitglied
> kann nach `git clone` sofort `./mvnw clean verify` und `./mvnw exec:java` ausführen, ohne
> weitere Installation außer Git und Java 21.

---

## 0. Kontext und Auftrag

Das Projekt ist ein Gruppenprojekt der Lehrveranstaltung *Software Engineering II.1*
(DHBW Heilbronn, WS 2026/27): eine kleine Java-Anwendung zur Erstellung und Verwaltung
digitaler Fotoalben. Leitidee: **nicht viel Software, sondern nachvollziehbar
entwickelte Software.**

**Deine Aufgabe in diesem Dokument ist ausschließlich das Projektgerüst**
(Build, Struktur, CI, Doku-Platzhalter, Konfiguration, Platzhalter-Fenster).
Du implementierst **keine** Fachlogik (kein Album, kein Photo, keine Geschäftsregeln).
Das folgt in Block 4, nachdem Anforderungen (Block 2) und Architektur (Block 3) feststehen.
Sobald Klassen-, Sequenz- und Architekturdiagramme vorliegen, wird auf diesem Gerüst
die eigentliche Funktionalität implementiert.

### Feste technische Vorgaben (nicht verändern)

| Thema | Vorgabe |
|---|---|
| Sprache | Java 21 |
| Build | Maven **mit Maven Wrapper** (`mvnw`, `mvnw.cmd`), Maven 3.9.9 |
| Tests | JUnit 5 (Jupiter) |
| Coverage | JaCoCo, Report unter `target/site/jacoco/index.html` |
| CI | GitHub Actions (`.github/workflows/ci.yml`), Build + Tests bei jedem Pull Request |
| Datenhaltung | lokal im Dateisystem, **keine Datenbank**, keine Cloud, kein Docker |
| UI | **Swing** (im JDK enthalten, keine Zusatzabhängigkeit), vorerst nur **Platzhalter-Fenster** ohne Funktion. Einstiegspunkt `de.dhbw.photoalbum.ui.Main` |
| Root-Package | `de.dhbw.photoalbum` |
| Maven-Koordinaten | `groupId=de.dhbw`, `artifactId=photo-album`, `version=0.1.0-SNAPSHOT` |
| Architektur | Schichten: `domain`, `application`, `infrastructure`, `ui` |

Warum Swing: kein Plugin, keine plattformabhängigen Bibliotheken, läuft bei allen
Teammitgliedern sofort. Ein späterer Wechsel (z. B. JavaFX) wäre ein ADR plus eine
Änderung in `ui/`; die Schichten darunter sind davon nicht betroffen.

### Verbote

- Keine zusätzlichen Abhängigkeiten oder Plugins außer den hier genannten
  (Neues nur nach ADR, siehe `AGENTS.md`).
- Keine Fachklassen, keine Beispiel-Features, keine „nützlichen Extras".
- Die Oberfläche ist ein reiner Platzhalter: alle Bedienelemente sind deaktiviert und rufen
  nichts auf. Keine Anbindung an Fachlogik, kein Dateizugriff.
- Keine Secrets, keine persönlichen Pfade, keine IDE-Dateien im Commit.
- **Nicht pushen.** Du bereitest den Commit nur vor. Das Pushen macht der Mensch (Abschnitt 11).
- Nichts erfinden: Wo Informationen fehlen (Teamnamen, Vorgehensmodell, Modellkennung),
  schreibst du `TODO` und listest es im Abschlussbericht auf.

---

## 1. Vorab-Prüfung

Führe aus und brich mit klarer Meldung an den Menschen ab, falls etwas fehlt:

```bash
java -version     # muss Java 21 (oder neuer) melden
git --version
git remote -v     # origin sollte auf github.com/felixrau2105/se2-git-group-3 zeigen
git status        # Arbeitsverzeichnis = Repo-Wurzel
```

- Das **Repo-Wurzelverzeichnis ist das Projektverzeichnis.** Alle Pfade unten sind relativ dazu.
  Lege **keinen** Unterordner `photo-album/` an.
- Falls `origin` nicht gesetzt ist:
  `git remote add origin https://github.com/felixrau2105/se2-git-group-3.git`
- Falls Maven (`mvn`) nicht installiert ist, wird es **nur einmalig** zum Erzeugen des
  Wrappers benötigt (Schritt 3). Installiere es temporär (z. B. `brew install maven`,
  `sudo apt install maven`, `sdk install maven`) oder frage den Menschen. Danach
  arbeitest du ausschließlich mit `./mvnw`.

---

## 2. Verzeichnisstruktur anlegen

Ziel (Dateien in Klammern entstehen in den späteren Schritten):

```
.
├── .github/
│   ├── workflows/ci.yml
│   └── pull_request_template.md
├── .mvn/wrapper/maven-wrapper.properties      (vom Wrapper-Plugin erzeugt)
├── docs/
│   ├── adr/0000-template.md
│   ├── ai/prompts/00-initial-project-setup.md  (Kopie dieses Dokuments)
│   ├── design/.gitkeep
│   ├── presentation/.gitkeep
│   └── REFACTORING.md
├── src/
│   ├── main/
│   │   ├── java/de/dhbw/photoalbum/
│   │   │   ├── domain/package-info.java
│   │   │   ├── application/package-info.java
│   │   │   ├── infrastructure/package-info.java
│   │   │   └── ui/{package-info.java, Main.java, MainWindow.java}
│   │   └── resources/.gitkeep
│   └── test/java/de/dhbw/photoalbum/
│       ├── ProjectSetupSmokeTest.java
│       ├── ui/MainWindowTest.java
│       ├── domain/.gitkeep
│       └── infrastructure/.gitkeep
├── .editorconfig
├── .gitattributes
├── .gitignore
├── AGENTS.md
├── AI_USAGE.md
├── ARCHITECTURE.md
├── CHANGELOG.md
├── CHANGE_REQUEST.md
├── README.md
├── REQUIREMENTS.md
├── RISK_ANALYSIS.md
├── TESTING.md
├── mvnw
├── mvnw.cmd
└── pom.xml
```

**Nicht anlegen / nicht committen:** `data/` (wird von der Anwendung zur Laufzeit erzeugt
und ist git-ignoriert) und `target/`.

Leere Ordner, die Git sonst nicht speichert, bekommen eine leere Datei `.gitkeep`
(siehe Baum).

---

## 3. `pom.xml` und Maven Wrapper

### 3.1 `pom.xml` (Wurzel)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>de.dhbw</groupId>
  <artifactId>photo-album</artifactId>
  <version>0.1.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <name>Photo Album</name>
  <description>SE II.1 Gruppenprojekt: Verwaltung digitaler Fotoalben (DHBW Heilbronn)</description>

  <properties>
    <maven.compiler.release>21</maven.compiler.release>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>

    <junit.version>5.11.4</junit.version>
    <jacoco.version>0.8.12</jacoco.version>
    <main.class>de.dhbw.photoalbum.ui.Main</main.class>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter</artifactId>
      <version>${junit.version}</version>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.13.0</version>
      </plugin>

      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.5.2</version>
      </plugin>

      <plugin>
        <groupId>org.jacoco</groupId>
        <artifactId>jacoco-maven-plugin</artifactId>
        <version>${jacoco.version}</version>
        <executions>
          <execution>
            <id>prepare-agent</id>
            <goals><goal>prepare-agent</goal></goals>
          </execution>
          <execution>
            <id>report</id>
            <phase>test</phase>
            <goals><goal>report</goal></goals>
          </execution>
        </executions>
      </plugin>

      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.4.2</version>
        <configuration>
          <archive>
            <manifest>
              <mainClass>${main.class}</mainClass>
            </manifest>
          </archive>
        </configuration>
      </plugin>

      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <version>3.5.0</version>
        <configuration>
          <mainClass>${main.class}</mainClass>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

Hinweis: Der JaCoCo-Report ist an die Phase `test` gebunden, damit
`./mvnw clean test` bereits `target/site/jacoco/index.html` erzeugt. Es gibt bewusst
**keine** Mindest-Coverage-Schranke (hohe Coverage ist kein Qualitätsnachweis).

### 3.2 Maven Wrapper erzeugen

Im Projektstamm ausführen (einmalig; Modus `only-script` vermeidet eine Binärdatei im Repo):

```bash
mvn -B wrapper:wrapper -Dmaven=3.9.9 -Dtype=only-script
```

Das erzeugt `mvnw`, `mvnw.cmd` und `.mvn/wrapper/maven-wrapper.properties`.
**Alle drei Dateien müssen committet werden.**

Ausführbar-Bit sicherstellen, damit macOS/Linux-Kollegen `./mvnw` direkt starten können:

```bash
chmod +x mvnw
git add --chmod=+x mvnw      # nach dem ersten git add; Kontrolle in Abschnitt 8
```

---

## 4. Java-Quellcode (nur Gerüst)

### 4.1 `package-info.java` je Schicht

Diese Dateien dokumentieren die Verantwortung und die **vorläufige** Abhängigkeitsrichtung
(wird in Block 3 per `ARCHITECTURE.md` und ADR verbindlich festgelegt).

`src/main/java/de/dhbw/photoalbum/domain/package-info.java`
```java
/**
 * Domänenschicht: Entitäten und Geschäftsregeln.
 * Vorläufige Regel: hängt von keiner anderen Schicht ab (nur JDK).
 */
package de.dhbw.photoalbum.domain;
```

`src/main/java/de/dhbw/photoalbum/application/package-info.java`
```java
/**
 * Anwendungsschicht: Use Cases, Services und Ports (Schnittstellen).
 * Vorläufige Regel: darf von domain abhängen, nicht von infrastructure oder ui.
 */
package de.dhbw.photoalbum.application;
```

`src/main/java/de/dhbw/photoalbum/infrastructure/package-info.java`
```java
/**
 * Infrastrukturschicht: Dateisystem-Anbindung, Implementierung der Ports.
 * Vorläufige Regel: darf von application und domain abhängen.
 */
package de.dhbw.photoalbum.infrastructure;
```

`src/main/java/de/dhbw/photoalbum/ui/package-info.java`
```java
/**
 * Oberflächenschicht (Swing): Eingabe/Ausgabe, ruft Use Cases der Anwendungsschicht auf.
 * Vorläufige Regel: darf von application abhängen, enthält keine Geschäftsregeln.
 */
package de.dhbw.photoalbum.ui;
```

### 4.2 Einstiegspunkt und Platzhalter-Fenster

`src/main/java/de/dhbw/photoalbum/ui/Main.java`
```java
package de.dhbw.photoalbum.ui;

import java.awt.GraphicsEnvironment;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Einstiegspunkt der Anwendung. Startet aktuell nur das Platzhalter-Fenster.
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("Keine grafische Umgebung gefunden - Oberfläche kann nicht gestartet werden.");
            return;
        }
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Standard-Look-and-Feel verwenden
            }
            new MainWindow().setVisible(true);
        });
    }
}
```

`src/main/java/de/dhbw/photoalbum/ui/MainWindow.java`
```java
package de.dhbw.photoalbum.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

/**
 * Platzhalter-Hauptfenster: zeigt das grobe Layout (Albumliste links, Fotobereich rechts).
 * Alle Aktionen sind deaktiviert. Keine Fachlogik, keine Dateizugriffe.
 */
final class MainWindow extends JFrame {

    static final String TITLE = "Photo Album";
    private static final String HINT = "Platzhalter - Funktionen folgen ab Block 4";

    private final JToolBar toolBar = createToolBar();

    MainWindow() {
        super(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        add(toolBar, BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);

        JLabel status = new JLabel(HINT);
        status.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        add(status, BorderLayout.SOUTH);
    }

    JToolBar toolBar() {
        return toolBar;
    }

    private static JToolBar createToolBar() {
        JToolBar bar = new JToolBar();
        bar.setFloatable(false);
        for (String label : new String[] {
            "Neues Album", "Umbenennen", "Löschen", "Foto hinzufügen", "Foto entfernen"}) {
            JButton button = new JButton(label);
            button.setEnabled(false);
            button.setToolTipText("Folgt in Block 4");
            bar.add(button);
        }
        return bar;
    }

    private static JComponent createContent() {
        DefaultListModel<String> albums = new DefaultListModel<>();
        albums.addElement("(noch keine Alben)");
        JList<String> albumList = new JList<>(albums);
        albumList.setEnabled(false);

        JLabel photoArea = new JLabel("Hier erscheinen später die Fotos des Albums",
            SwingConstants.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            new JScrollPane(albumList), photoArea);
        split.setDividerLocation(220);
        return split;
    }
}
```

### 4.3 Tests (laufen auch headless in der CI)

`src/test/java/de/dhbw/photoalbum/ProjectSetupSmokeTest.java`
```java
package de.dhbw.photoalbum;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Prüft nur das Projektgerüst. Zählt NICHT zu den geforderten 10 Unit-Tests
 * und darf entfernt werden, sobald echte Tests existieren.
 */
class ProjectSetupSmokeTest {

    @Test
    void runsOnJava21OrNewer() {
        assertTrue(Runtime.version().feature() >= 21);
    }
}
```

`src/test/java/de/dhbw/photoalbum/ui/MainWindowTest.java`
```java
package de.dhbw.photoalbum.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import org.junit.jupiter.api.Test;

/**
 * Prüft nur das Platzhalter-Fenster. Wird übersprungen, wenn keine grafische Umgebung
 * vorhanden ist (z. B. in der CI). Darf entfernt werden, sobald die echte UI existiert.
 */
class MainWindowTest {

    @Test
    void placeholderWindowHasTitleAndOnlyDisabledButtons() throws Exception {
        assumeFalse(GraphicsEnvironment.isHeadless(), "keine grafische Umgebung");

        AtomicReference<MainWindow> ref = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> ref.set(new MainWindow()));
        MainWindow window = ref.get();
        try {
            assertEquals(MainWindow.TITLE, window.getTitle());
            for (Component c : window.toolBar().getComponents()) {
                if (c instanceof JButton button) {
                    assertFalse(button.isEnabled());
                }
            }
        } finally {
            SwingUtilities.invokeAndWait(window::dispose);
        }
    }
}
```

---

## 5. Repository-Konfiguration

### 5.1 `.gitignore`

```gitignore
# Build
target/

# Lokale Daten (Alben/Fotos/Metadaten) - niemals committen
/data/

# IDEs
.idea/
*.iml
.vscode/
.classpath
.project
.settings/
*.swp

# OS
.DS_Store
Thumbs.db

# Logs / Sonstiges
*.log
```

Der Wrapper (`mvnw`, `mvnw.cmd`, `.mvn/wrapper/`) darf **nicht** ignoriert werden.

### 5.2 `.gitattributes` (verhindert Zeilenende-Probleme zwischen Windows/macOS/Linux)

```gitattributes
* text=auto
*.java  text eol=lf
*.xml   text eol=lf
*.md    text eol=lf
*.yml   text eol=lf
mvnw    text eol=lf
*.cmd   text eol=crlf
```

### 5.3 `.editorconfig`

```ini
root = true

[*]
charset = utf-8
end_of_line = lf
insert_final_newline = true
trim_trailing_whitespace = true
indent_style = space
indent_size = 4

[*.{yml,yaml,xml,md}]
indent_size = 2

[*.cmd]
end_of_line = crlf
```

---

## 6. CI und GitHub-Vorlagen

### 6.1 `.github/workflows/ci.yml`

```yaml
name: CI

on:
  pull_request:
    branches: [main]
  push:
    branches: [main]

permissions:
  contents: read

jobs:
  build:
    name: build
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - uses: actions/setup-java@v4
        with:
          distribution: temurin
          java-version: '21'
          cache: maven

      - name: Make wrapper executable
        run: chmod +x mvnw

      - name: Build and test
        run: ./mvnw -B -ntp clean verify

      - name: Upload coverage report
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: jacoco-report
          path: target/site/jacoco
          if-no-files-found: ignore
```

Der Jobname **`build`** wird später in der Branch-Protection als Pflicht-Check verwendet.
Nicht umbenennen. Die CI läuft headless; UI-Tests werden dort übersprungen.

### 6.2 `.github/pull_request_template.md`

~~~markdown
## Beschreibung
<!-- Was ändert dieser PR und warum? -->

Closes #<Issue-ID>

## Checkliste
- [ ] Branch ist vom aktuellen `main` abgezweigt (`feature/FA-xx-kurzbeschreibung`)
- [ ] Commits folgen Conventional Commits mit Issue-/Anforderungs-ID (z. B. `feat(#12): ...`)
- [ ] `./mvnw clean verify` lokal grün
- [ ] Neue/geänderte Logik ist durch Tests abgedeckt (Normal-, Fehler-, Grenzfälle)
- [ ] Doku angepasst (REQUIREMENTS/ARCHITECTURE/TESTING/CHANGELOG, falls betroffen)
- [ ] Mindestens ein anderes Teammitglied ist als Reviewer eingetragen

## KI-Nutzung
- [ ] Dieser PR enthält KI-generierten Code -> Label `ai-assisted` gesetzt und Eintrag in `AI_USAGE.md`
- [ ] Keine KI verwendet
~~~

---

## 7. Dokumente anlegen

Sprache aller Dokumente: **Deutsch**. Platzhalter-Dokumente tragen oben eine Statuszeile
`> Status: Platzhalter – wird in Block X befüllt.` und enthalten die Abschnittsüberschriften
laut Projektbeschreibung, **ohne erfundene Inhalte**. Ergänze nichts, was Auftraggeber oder
Team erst entscheiden müssen.

### 7.1 `README.md` (vollständig befüllen)

~~~markdown
# Photo Album

Gruppenprojekt *Software Engineering II.1* (DHBW Heilbronn, WS 2026/27).
Eine kleine Java-Anwendung zur Erstellung und Verwaltung digitaler Fotoalben.
Fokus: nachvollziehbare Spezifikation, Architektur, Tests und kontrollierte Weiterentwicklung.

## Voraussetzungen

- **Git**
- **Java 21 JDK** (z. B. Eclipse Temurin). `JAVA_HOME` gesetzt, `java -version` zeigt 21.
- **Keine** globale Maven-Installation nötig (Maven Wrapper ist enthalten).
- Keine Datenbank, keine Cloud: Daten liegen lokal im Dateisystem.

## Schnellstart

```bash
git clone https://github.com/felixrau2105/se2-git-group-3.git
cd se2-git-group-3
```

| Aktion | macOS / Linux | Windows (cmd/PowerShell) |
|---|---|---|
| Bauen | `./mvnw clean compile` | `.\mvnw.cmd clean compile` |
| Tests + Coverage | `./mvnw clean test` | `.\mvnw.cmd clean test` |
| Voller Build wie CI | `./mvnw clean verify` | `.\mvnw.cmd clean verify` |
| Anwendung starten | `./mvnw exec:java` | `.\mvnw.cmd exec:java` |

Erwartet: `BUILD SUCCESS`.
Coverage-Report: `target/site/jacoco/index.html` im Browser öffnen.

Beim Start öffnet sich ein Platzhalter-Fenster (Swing). Es gibt keine Funktion, nur das
Layout. Auf Systemen ohne grafische Oberfläche (z. B. CI, SSH) wird stattdessen eine
Meldung ausgegeben.

IDE: Projekt als **Maven-Projekt** öffnen (IntelliJ: `pom.xml` -> „Open as Project" /
„Add as Maven Project"), Projekt-SDK auf Java 21 stellen.

## Datenhaltung

Alben und Fotos werden lokal im Ordner `data/` gespeichert. Der Ordner ist per
`.gitignore` ausgeschlossen und wird von der Anwendung bei Bedarf angelegt.
Das konkrete Dateiformat wird in `ARCHITECTURE.md` dokumentiert (Block 3).

## Projektstruktur

| Pfad | Inhalt |
|---|---|
| `src/main/java/de/dhbw/photoalbum/domain` | Entitäten, Geschäftsregeln |
| `.../application` | Use Cases, Services, Ports |
| `.../infrastructure` | Dateisystem-Anbindung (Port-Implementierungen) |
| `.../ui` | Swing-Oberfläche, `Main` und `MainWindow` (aktuell Platzhalter) |
| `src/test/java/...` | Unit- und Integrationstests |
| `docs/` | ADRs, UML-Diagramme, Refactoring, Präsentation, KI-Prompts |

Weitere Dokumente: [REQUIREMENTS](REQUIREMENTS.md) · [ARCHITECTURE](ARCHITECTURE.md) ·
[TESTING](TESTING.md) · [CHANGE_REQUEST](CHANGE_REQUEST.md) · [AI_USAGE](AI_USAGE.md) ·
[RISK_ANALYSIS](RISK_ANALYSIS.md) · [AGENTS](AGENTS.md) · [CHANGELOG](CHANGELOG.md)

## Git-Workflow (Team-Regeln)

`main` ist geschützt – **niemals direkt auf `main` committen.**

1. Issue anlegen (Requirement oder Bugfix).
2. Branch von `main`, z. B. `feature/FA-01-album-erstellen`.
3. Conventional Commits mit Issue-ID, z. B. `feat(#12): implement album creation`.
   Keine „fix"- oder „wip"-Commits auf `main`.
4. Pull Request gegen `main`.
5. CI (GitHub Actions, Job `build`) muss grün sein.
6. Mindestens ein anderes Teammitglied reviewt und gibt frei.
   KI-generierter Code erhält das Label `ai-assisted`.

## Team

| Name | GitHub | Rolle |
|---|---|---|
| Felix Rau | @felixrau2105 | TODO |
| TODO | TODO | TODO |
| TODO | TODO | TODO |
| TODO | TODO | TODO |

## Vorgehensmodell (Block 1)

TODO: Vorgehensentscheidung des Teams (z. B. Scrum/Kanban/Wasserfall) mit kurzer Begründung.

## Versionen

Java 21 · Maven 3.9.9 (Wrapper) · JUnit 5.11.4 · JaCoCo 0.8.12 · Swing (JDK)
~~~

### 7.2 `AGENTS.md` (Regeln für Coding-Agents, vollständig befüllen)

~~~markdown
# AGENTS.md – Regeln für Coding-Agents

Diese Regeln gelten für jede KI, die in diesem Repository Code oder Doku ändert.
Die Verantwortung für das Ergebnis liegt beim Team.

## Projektüberblick
Java 21, Maven Wrapper, JUnit 5, JaCoCo, lokale Dateisystem-Persistenz, Swing-UI.
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
4. Kleine, fokussierte Änderungen. Nur umsetzen, was im Issue / in `REQUIREMENTS.md` steht.
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
  (temporäre Verzeichnisse, z. B. JUnit `@TempDir`; nie in `data/` schreiben).
- UI-Tests müssen in headless-Umgebungen (CI) übersprungen werden:
  `assumeFalse(GraphicsEnvironment.isHeadless())`. Fachlogik wird ohne UI getestet.
- Tests nicht abschwächen oder löschen, um einen Build grün zu bekommen.

## Verboten
- Secrets, Zugangsdaten, persönliche Pfade committen.
- `data/`, `target/` oder echte Fotos committen.
- Erfundene Anforderungen, Werte oder Quellen. Bei Unklarheit nachfragen oder als
  Annahme in `REQUIREMENTS.md` dokumentieren.

## Versionierte KI-Artefakte
Prompts, Skills und Modellkonfigurationen liegen unter `docs/ai/`.
~~~

### 7.3 `AI_USAGE.md`

~~~markdown
# AI_USAGE.md – Dokumentation des KI-Einsatzes

Anforderungen: mindestens 3 substanzielle Fälle (empfohlen 5, je einer in Block 2–6),
davon mindestens 1 begründet verworfener Vorschlag. Bewertet wird die kritische
Bewertung, nicht die Menge.

## Vorlage

| Feld | Inhalt |
|---|---|
| Aufgabe/Ziel | |
| Block | |
| Werkzeug/Modell | Produkt, Modellkennung, Prompt/Skill (Link ins Repo) |
| Ergebnis | |
| Kritische Bewertung | Was war richtig/falsch/erfunden? Woran erkannt? |
| Entscheidung | übernommen / angepasst / verworfen – mit Begründung |

---

## Fall 0 – Initiales Projekt-Setup (Block 1)

| Feld | Inhalt |
|---|---|
| Aufgabe/Ziel | Projektgerüst (Build, Struktur, CI, Doku-Platzhalter, Platzhalter-Fenster) erzeugen |
| Block | 1 |
| Werkzeug/Modell | TODO: Produkt und Modellkennung eintragen; Prompt: [docs/ai/prompts/00-initial-project-setup.md](docs/ai/prompts/00-initial-project-setup.md) |
| Ergebnis | Lauffähiges Maven-Projekt mit Schichtenstruktur, Swing-Platzhalter-Fenster, CI-Workflow und Dokumentvorlagen |
| Kritische Bewertung | TODO: vom Team nach dem Review auszufüllen (nicht von der KI) |
| Entscheidung | TODO |
~~~

### 7.4 `CHANGELOG.md`

~~~markdown
# Changelog

Format nach [Keep a Changelog](https://keepachangelog.com/de/1.1.0/),
Versionierung nach [Semantic Versioning](https://semver.org/lang/de/).

## [Unreleased]

### Added
- Initiales Projektgerüst: Maven Wrapper, Schichtenstruktur, JUnit 5, JaCoCo, CI-Workflow,
  Swing-Platzhalter-Fenster, Dokumentvorlagen.

<!-- Geplante Releases: v0.1.0 (nach Block 4), v1.0.0 (Pflichtfunktionalität), v1.1.0 (nach Change Request) -->
~~~

### 7.5 `docs/adr/0000-template.md`

~~~markdown
# ADR-NNNN: Titel der Entscheidung

- Status: vorgeschlagen | akzeptiert | ersetzt durch ADR-XXXX
- Datum: JJJJ-MM-TT
- Beteiligte: ...

## Kontext und Problem
Welches konkrete Problem soll gelöst werden? Welche Anforderungen (FA-xx/NFA-xx) sind betroffen?

## Entscheidung
Was wurde entschieden?

## Verworfene Alternative(n)
Welche Option wurde verworfen und warum?

## Konsequenzen
Vorteile, Nachteile, Folgen für Architektur, Tests und Wartung.
~~~

### 7.6 Platzhalter-Dokumente

Jedes Dokument beginnt mit `# Titel` und der Statuszeile. Überschriften:

| Datei | Status-Block | Abschnitte (aus der Projektbeschreibung) |
|---|---|---|
| `REQUIREMENTS.md` | Block 2 | Stakeholder und Systemgrenze (Kontextdiagramm) · Funktionale Anforderungen (FA-xx, SOPHIST, muss/sollte) · Nichtfunktionale Anforderungen (Qualitätsmerkmal, Messgröße, Bedingung) · Use Cases (≥ 2, Standard-/Alternativabläufe) · Akzeptanzkriterien (Given/When/Then, AK-xx.n) · UI-Prototyp · Auftraggeber-Entscheidungen · Annahmen · KI und Sicherheit |
| `ARCHITECTURE.md` | Block 3 | Architekturüberblick und Begründung (Schichten, Abhängigkeitsrichtung) · Komponenten, Verantwortlichkeiten, Ports · Datenmodell und Dateiformat · Klassendiagramm (→ `docs/design/`) · Sequenzdiagramm „Foto hinzufügen" inkl. Fehlerpfad · Entwurfsmuster (je Muster ein ADR) · Entwurf optionale KI-Komponente (Port, Adapter, Guardrails) · Verweis auf `docs/adr/` |
| `TESTING.md` | Block 5/6 | Teststrategie · Blackbox-Verfahren (Tabelle, Zuordnung zu Testmethoden) · Traceability (Akzeptanzkriterium → Verfahren → Testmethode) · Unit-/Integrationstests · Coverage-Interpretation je Schicht · Usability-Protokoll · Security-Tests für Dateieingaben · Performance-Messung für eine NFA · Regressionstests · Evaluation Set für KI-Funktion |
| `CHANGE_REQUEST.md` | Block 6 | Beschreibung des Change Requests · Betroffene Anforderungen und Akzeptanzkriterien (IDs) · Betroffene Architektur und Datenmodell, ungültige/neue ADRs · Rote und neue Tests · Risiken · Aufwandsschätzung je Teil und Reihenfolge · Rückblick (geschätzt vs. tatsächlich, Überraschungen) |
| `RISK_ANALYSIS.md` | Block 7 | Risikomatrix (≥ 3 Risiken, davon ≥ 1 Produkt- und ≥ 1 Projektrisiko) · Maßnahmen mit Verantwortlichen · Quality Gates (4–6, erfüllt/nicht erfüllt mit Beleg) · Rollout-Plan (Strategie, Migration, Rollback) · KI-Governance · Release-Entscheidung (ja / nein / unter Bedingungen) |
| `docs/REFACTORING.md` | Block 6 | Ausgangssituation (Klasse, Metrik, Smell mit Nachweis) · Problem · Änderung (Schritte, jeder mit grünen Tests) · Ergebnis · Absicherung durch Tests |

---

## 8. Verifikation (alles muss bestehen)

1. **Build und Tests:**
```bash
   ./mvnw -B clean verify
```
   Erwartet: `BUILD SUCCESS`, 2 Tests grün (auf Systemen ohne Bildschirm: 1 grün,
   1 übersprungen).
2. **Coverage-Report existiert:** `target/site/jacoco/index.html`.
3. **Start:** `./mvnw -q exec:java` startet das Platzhalter-Fenster (Titel „Photo Album",
   deaktivierte Buttons). Auf einem System ohne Bildschirm erscheint stattdessen die
   Meldung „Keine grafische Umgebung gefunden …". Beides ist korrekt. Fenster schließen
   beendet den Prozess.
4. **Wrapper korrekt im Index** (nach `git add -A`):
```bash
   git ls-files -s mvnw           # muss mit 100755 beginnen
   git ls-files .mvn/wrapper      # maven-wrapper.properties muss gelistet sein
```
5. **Nichts Unerwünschtes getrackt:**
```bash
   git ls-files | grep -E '^(target/|data/|\.idea/)' && echo "FEHLER" || echo "ok"
```
6. **Clean-Clone-Test** (beweist „out of the box"):
```bash
   git clone . /tmp/photo-album-verify
   cd /tmp/photo-album-verify && ./mvnw -B clean verify
```
   Muss ohne manuelle Eingriffe `BUILD SUCCESS` liefern. Danach das Temp-Verzeichnis löschen.
7. Prüfe, dass `.github/workflows/ci.yml` gültiges YAML ist und der Jobname `build` lautet.

Behebe Fehler selbst, bis alle Punkte bestehen. Falls ein Versions-Pin (Plugin oder
Bibliothek) nicht auflösbar ist, nimm die neueste stabile Version derselben Hauptlinie
und nenne die Änderung im Abschlussbericht.

---

## 9. Initialer Commit (nur vorbereiten, **nicht pushen**)

Kopiere dieses Dokument nach `docs/ai/prompts/00-initial-project-setup.md`, falls noch nicht geschehen.

```bash
git add -A
git status                      # Inhalt kontrollieren (Punkt 5 aus Abschnitt 8)
git commit -m "chore: initial project setup (maven wrapper, layered structure, swing placeholder, CI, docs)"
```

Falls `git` nach Autor fragt: den bereits konfigurierten Nutzer verwenden, keinen erfinden.
Der Branch muss `main` heißen (`git branch -M main`, falls nötig).

---

## 10. Abschlussbericht an den Menschen

Gib am Ende kurz aus:

- Ergebnis jedes Verifikationspunkts aus Abschnitt 8 (bestanden/nicht bestanden)
- Ob das Fenster gestartet werden konnte (grafische Umgebung vorhanden: ja/nein)
- Welche Versionen tatsächlich verwendet wurden (falls von Vorgabe abgewichen)
- Liste aller `TODO`-Stellen (Teamnamen, Vorgehensmodell, KI-Modellkennung)
- Hinweis, dass noch **nicht gepusht** wurde

---

## 11. Manuelle Schritte für den Menschen (kann ein Agent nicht)

1. **Pushen:** `git push -u origin main`
   (Der erste Push auf `main` ist der einzige direkte; danach wird `main` geschützt.)
2. **Kollegen einladen:** GitHub -> Settings -> Collaborators (alle 4 Teammitglieder).
3. **Label anlegen:** `ai-assisted` (Issues -> Labels -> New label).
4. **Branch-Schutz für `main`** (Settings -> Branches -> Add branch protection rule / Ruleset),
   erst **nach** dem ersten Push und CI-Lauf:
   - Require a pull request before merging, **mindestens 1 Approval**
   - Require status checks to pass: **`build`** (erscheint erst nach dem ersten CI-Lauf)
   - Block force pushes, Restrict deletions
5. **Ersten Test-PR** (z. B. Team-Tabelle in der README befüllen) öffnen und prüfen, dass
   CI grün wird und ein Review erzwungen wird.
6. **Block 1 abschließen:** Vorgehensentscheidung in der README ergänzen und den Tag setzen:
   `git tag block-1 && git push origin block-1`

---

## 12. Definition of Done

- [ ] `./mvnw clean verify` ist lokal und im Clean-Clone grün
- [ ] `./mvnw exec:java` öffnet das Platzhalter-Fenster (bzw. meldet fehlende Grafikumgebung)
- [ ] Wrapper (`mvnw` mit Ausführrecht, `mvnw.cmd`, `.mvn/wrapper/`) ist committet
- [ ] Struktur entspricht Abschnitt 2, kein `data/`, kein `target/`, keine IDE-Dateien im Commit
- [ ] CI-Workflow vorhanden, Jobname `build`
- [ ] Alle Dokumente aus Abschnitt 7 vorhanden, keine erfundenen Inhalte
- [ ] Ein Commit mit Conventional-Commit-Nachricht, **nicht gepusht**
- [ ] Abschlussbericht (Abschnitt 10) geliefert