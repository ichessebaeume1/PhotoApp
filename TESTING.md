# Testkonzept

## Ziel

Das Projekt prüft das Java-21-Maven-Gerüst sowie die fachlichen Abnahmeszenarien Core-Erstellung, Fotoimport und Wiederherstellung. Bis diese Szenarien implementiert sind, sind Smoke- und Platzhaltertests kein Nachweis der Abnahme.

## Abnahmeszenarien

Die folgenden drei Szenarien müssen in `clean verify` auf allen drei CI-Plattformen bestehen:

1. **Core-Erstellung:** gültiger Core-Name, optional gültige Beschreibung und dauerhafte lokale Anlage.
2. **Fotoimport:** unterstützt JPG, JPEG, PNG und SVG gemäß der angenommenen Formatliste; Originalbytes, Größenlimit, Titel und SHA-256-Duplikate werden geprüft.
3. **Wiederherstellung:** eine Memory wird innerhalb der 30-Tage-Frist bei freiem Platz und ohne aktives Duplikat wiederhergestellt.

Die Testklassen müssen die Szenarien mit eindeutigen JUnit-Testnamen abbilden. Smoke- und Platzhaltertests allein zählen nicht als Abnahmetests.

## Plattformabdeckung

Die GitHub-Actions-Matrix führt Maven-Wrapper, Java 21 und `clean verify` auf `ubuntu-latest`, `windows-latest` und `macos-latest` aus. Die verwendete Runner-Image-Version und Java-Version müssen aus dem CI-Lauf nachvollziehbar sein.

## Performance

Für NFA-01 liegen vor der Messung 100 lokale Bilddateien und 100 bereits erzeugte lokale Vorschaudateien auf dem Datenträger. Gemessen wird ausschließlich die Zeit vom Öffnen des Cores bis zur Anzeige des 100. Vorschaubilds. Vorschauerzeugung ist nicht Teil der Messung.

## Löschfrist

Die Abnahmetests für FA-10 prüfen manuelle Wiederherstellung, manuelle endgültige Löschung und automatische Bereinigung beim Anwendungsstart beziehungsweise im täglichen Lauf. Abgelaufene appverwaltete Daten dürfen höchstens 24 Stunden nach Fristablauf verbleiben.
