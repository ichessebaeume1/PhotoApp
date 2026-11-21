# Diagramme mit PlantUML exportieren

Erstellt mit PlantUML 1.2025.2 und Graphviz, lokal ohne Upload von Projektinhalten.

1. Java installieren; für Klassendiagramme außerdem Graphviz (`dot`).
2. PlantUML-JAR 1.2025.2 aus dem offiziellen Maven-Central-Artefakt laden:
   https://repo.maven.apache.org/maven2/net/sourceforge/plantuml/plantuml/1.2025.2/plantuml-1.2025.2.jar
3. Vom Projektverzeichnis aus ausführen (JAR-Pfad anpassen):

```bash
java -DPLANTUML_LIMIT_SIZE=12000 -jar /pfad/plantuml.jar -tpng 'docs/design/*.puml'
```

Alternativ jede `.puml` in IntelliJ/VS Code mit PlantUML öffnen und als PNG exportieren.
Bei Änderungen sowohl die Quelle als auch das PNG aktualisieren. Die PDF-Lesefassung ist
ein ergänzender Stand; maßgeblich bleiben die versionierten Markdown-/PlantUML-Quellen.
