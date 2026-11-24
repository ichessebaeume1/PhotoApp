# Testkonzept

## Ziel

Die CI führt den Build und die vorhandenen Domänentests aus. Die unten beschriebenen fachlichen Abnahmetests sind mit den zugehörigen Funktionen umzusetzen.

## Vorhandene Domänentests

[AlbumTest](src/test/java/de/dhbw/photoalbum/domain/AlbumTest.java) prüft vier Fälle:

- Eine ungültige Umbenennung lässt den Albumnamen unverändert.
- Identischer Inhalt wird im selben Album abgelehnt und ist in einem anderen Album zulässig.
- Das Album akzeptiert 10.000 Fotos. Das nächste Foto wird abgelehnt und der Inhalt bleibt unverändert.
- Eine wiederverwendete Foto-ID wird abgelehnt und der Albuminhalt bleibt unverändert.

JaCoCo erstellt seinen Bericht unter `target/site/jacoco/index.html`.

## Abnahmeszenarien

Die folgenden drei Szenarien müssen in `clean verify` auf allen drei CI-Plattformen bestehen:

1. **Core-Erstellung:** gültiger Core-Name, optional gültige Beschreibung und dauerhafte lokale Anlage.
2. **Fotoimport:** unterstützt JPG, JPEG, PNG und SVG gemäß der angenommenen Formatliste; Originalbytes, Größenlimit, Titel und SHA-256-Duplikate werden geprüft.
3. **Wiederherstellung:** eine Memory wird innerhalb der 30-Tage-Frist bei freiem Platz und ohne aktives Duplikat wiederhergestellt.

Die Testklassen müssen die Szenarien mit eindeutigen JUnit-Testnamen abbilden. Smoke- und Platzhaltertests allein zählen nicht als Abnahmetests.

## Plattformabdeckung

Die GitHub-Actions-Matrix führt Maven-Wrapper, Java 21 und `clean verify` auf `ubuntu-latest`, `windows-latest` und `macos-latest` aus. Die verwendete Runner-Image-Version und Java-Version müssen aus dem CI-Lauf nachvollziehbar sein.

## Performance

NFA-01 verlangt höchstens zwei Sekunden auf dem Linux-CI-Runner mit Java 21. Für die Messung liegen vor der Messung 100 lokale Bilddateien und 100 bereits erzeugte lokale Vorschaudateien auf dem Datenträger. Gemessen wird ausschließlich die Zeit vom Öffnen des Cores bis zur Anzeige des 100. Vorschaubilds. Vorschauerzeugung ist nicht Teil der Messung.

## Löschfrist

Die Abnahmetests für FA-10 prüfen manuelle Wiederherstellung, manuelle endgültige Löschung und automatische Bereinigung beim Anwendungsstart beziehungsweise im täglichen Lauf. Abgelaufene appverwaltete Daten dürfen höchstens 24 Stunden nach Fristablauf verbleiben.
