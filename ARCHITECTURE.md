# Architektur und Zielentwurf

> Status: Abgleich von Block 3 mit den Anforderungen aus Block 2. Die Zielarchitektur ist noch nicht vollständig beschlossen oder implementiert.

## Begriffe und Implementierungsstand

Die fachlichen Anforderungen verwenden **Core** für ein Album und **Memory** für ein Foto. Bestehende Projekt- und Java-Bezeichner wie `Album` und `Photo` können während der Umsetzung beibehalten werden; die Zuordnung muss dabei eindeutig bleiben.

Das aktuelle Projekt stellt ein Java-21-/Maven-Gerüst mit einer Swing-Platzhalteroberfläche bereit. Fotoimport, lokale Persistenz und Papierkorb sind noch nicht implementiert. Die folgenden Punkte beschreiben das Ziel, nicht den aktuellen Funktionsumfang.

## Ziel und Abhängigkeiten

Die Anwendung ist eine lokale Java-21-Desktopanwendung für Windows, macOS und Linux. Cloud-, Web-, iOS- und Android-Zugriff liegen außerhalb der aktuellen Systemgrenze und sind Vision. Die CI baut und testet den Maven-Wrapper auf allen drei Betriebssystemen ohne manuelle Maven-Installation. Es ist kein Chat vorgesehen.

Vorgesehene Abhängigkeitsrichtung:

`ui -> application -> domain`

`infrastructure -> application/domain`

- **domain**: Core/Album, Memory/Foto und fachliche Regeln. Dazu gehören höchstens 10.000 aktive Fotos je Core, Duplikatprüfung per SHA-256 im Core, Core-Namen mit 1–50 Zeichen nach Trimmen, optionale Core-Beschreibungen bis 500 Zeichen, Pflicht-Fototitel mit 1–100 Zeichen und eine als Annahme markierte maximale Fotobeschreibung von 500 Zeichen.
- **application**: Anwendungsfälle für Erstellen, Anzeigen, Importieren, Bearbeiten, Löschen und Wiederherstellen. Anwendungsfälle verwenden Ports statt konkrete Dateisystem-Implementierungen.
- **infrastructure**: Adapter für lokale Metadaten- und Originaldateispeicherung. Originaldateien bleiben unverändert; die Infrastruktur unterstützt das Vorladen von bis zu 100 Bildern.
- **ui**: Darstellung und Eingabe, ohne Geschäftsregeln. Die Oberfläche kann JavaFX, Swing oder Konsole verwenden; die vorhandene Swing-Oberfläche ist nur ein Platzhalter.

## Import, lokale Speicherung und Löschung

Beim Import werden ausschließlich JPG, JPEG, PNG und SVG akzeptiert und lokal gespeichert; ein Foto darf höchstens 50 MB groß sein. MP4 und andere Videos bleiben bis zur Klärung von Codec und Größenlimit außerhalb des Umfangs. Der SHA-256-Hash der vollständigen Originaldatei dient der Duplikatprüfung innerhalb eines Cores. Ein Pflicht-Fototitel umfasst 1–100 Zeichen.

Gelöschte Memories liegen 30 Tage im Papierkorb. Sie zählen nicht zur aktiven Fotoanzahl; für eine Wiederherstellung müssen Frist, Ziel-Core, Platz und Duplikatregeln erfüllt sein. Endgültiges Löschen betrifft appverwaltete lokale Daten.

Cores werden standardmäßig nach Datum angezeigt; alphabetische und benutzerdefinierte Sortierung sind ebenfalls vorgesehen. Sortierrichtung und Nachladeblock sind in den Anforderungen als Annahmen festgelegt.

Maßgeblich ist [REQUIREMENTS.md](REQUIREMENTS.md); explizite Annahmen und noch offene Vision-Themen sind dort voneinander getrennt.

## Offene Architekturentscheidungen

- **Plattformen (NFA-03):** CI testet Java 21 auf `ubuntu-latest`, `windows-latest` und `macos-latest`; die Versionen der Runner-Images werden protokolliert.
- **Performance (NFA-01):** Zielwert sind höchstens 2 Sekunden bis zur Anzeige der ersten 100 Vorschaubilder, gemessen auf dem Linux-CI-Runner. Messaufbau und Annahmen stehen in [REQUIREMENTS.md](REQUIREMENTS.md).
- **Medien und Metadaten:** MP4-/Videoregeln und die Bestätigung der angenommenen 500-Zeichen-Grenze der Fotobeschreibung bleiben offen; Videos sind bis dahin ausgeschlossen.
- **Vision:** Cloud, Web, Mobile und gemeinsame Alben liegen außerhalb der aktuellen Systemgrenze.

Architekturentscheidungen und verworfene Alternativen werden nach Teamentscheidung in `docs/adr/` dokumentiert. Die Block-3-Diagramme müssen mit diesem Requirements-Stand abgeglichen werden; dieses Dokument behauptet nicht, dass die beschriebenen Adapter oder Anwendungsfälle bereits vorhanden sind.
