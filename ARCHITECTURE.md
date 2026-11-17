# Architektur und Zielentwurf

> Status: Abgleich von Block 3 mit den Anforderungen aus Block 2. Die Zielarchitektur ist noch nicht vollständig beschlossen oder implementiert.

## Begriffe und Implementierungsstand

Die fachlichen Anforderungen verwenden **Core** für ein Album und **Memory** für ein Foto. Bestehende Projekt- und Java-Bezeichner wie `Album` und `Photo` können während der Umsetzung beibehalten werden; die Zuordnung muss dabei eindeutig bleiben.

Das aktuelle Projekt stellt ein Java-21-/Maven-Gerüst mit einer Swing-Platzhalteroberfläche bereit. Fotoimport, Persistenz, Papierkorb, Cloud-Synchronisierung und die geforderte plattformübergreifende Nutzung sind noch nicht implementiert. Die folgenden Punkte beschreiben das Ziel, nicht den aktuellen Funktionsumfang.

## Ziel und Abhängigkeiten

Die Anwendung ist lokal nutzbar. Eine Übertragung in die Cloud erfolgt nur nach ausdrücklicher Auswahl durch Nutzer:innen; geräteübergreifend verfügbare Daten müssen zuvor erfolgreich synchronisiert worden sein. Das bestätigte Cloud-Kontingent beträgt 500 GB je Nutzerkonto; nur aktive Inhalte zählen zur aktiven Quote. Papierkorbinhalte belegen weiterhin physischen Speicher. Es ist kein Chat vorgesehen.

Vorgesehene Abhängigkeitsrichtung:

`ui -> application -> domain`

`infrastructure -> application/domain`

- **domain**: Core/Album, Memory/Foto und fachliche Regeln. Dazu gehören höchstens 10.000 aktive Fotos je Core, albumlokale Duplikatprüfung, Core-Namen mit 1–50 Zeichen nach Trimmen, optionale Core-Beschreibungen bis 500 Zeichen und Pflicht-Fototitel mit 1–100 Zeichen. Die Grenzen der optionalen Memory-Beschreibung und die Anrechnung von Videos sind offen.
- **application**: Anwendungsfälle für Erstellen, Anzeigen, Importieren, Bearbeiten, Löschen, Wiederherstellen und optionales Synchronisieren. Anwendungsfälle verwenden Ports statt konkrete Dateisystem- oder Cloud-Implementierungen.
- **infrastructure**: Adapter für lokale Metadaten- und Originaldateispeicherung sowie – nach Nutzerwahl – einen noch zu spezifizierenden Cloud-Synchronisierungsdienst. Originaldateien bleiben unverändert; die Infrastruktur soll die Anzeige mit bis zu 100 zunächst geladenen Bildern unterstützen.
- **ui**: Darstellung und Eingabe, ohne Geschäftsregeln. Die vorhandene Swing-Oberfläche ist nur ein Platzhalter.

## Import, Synchronisierung und Löschung

Beim Import werden Dateien geprüft und lokal gespeichert. Die Anforderungen nennen JPG/JPEG, HEIC, PNG, GIF, RAW einschließlich CR2 sowie MP4; ein Bild darf höchstens 50 MB groß sein. Der SHA-256-Hash der vollständigen Originaldatei dient der Duplikatprüfung innerhalb eines Cores. Ein Pflicht-Fototitel umfasst 1–100 Zeichen. Eine optionale Cloud-Synchronisierung ist ein getrennter Schritt; ein Synchronisierungsfehler darf einen erfolgreichen lokalen Import nicht rückgängig machen.

Gelöschte Memories liegen 30 Tage im Papierkorb. Sie zählen nicht zur aktiven Fotoanzahl; für eine Wiederherstellung müssen Frist, Ziel-Core, Platz und Duplikatregeln erfüllt sein. Endgültiges Löschen und Cloud-/Offline-Kopien sind noch genauer abzustimmen.

Cores werden standardmäßig nach Datum angezeigt; alphabetische und benutzerdefinierte Sortierung sind ebenfalls vorgesehen. Datumsart, Sortierrichtung und Nachladegröße sind noch offen.

Diese Regeln enthalten noch ausdrücklich offene Details: Video-Codecs, Videogröße und Anrechnung auf das Mengenlimit, Speicherquoten-Berechnung, einige Metadatenlängen sowie Fehler- und Konfliktfälle der Synchronisierung. Maßgeblich ist [REQUIREMENTS.md](REQUIREMENTS.md).

## Offene Architekturentscheidungen

- **Plattformen (NFA-03):** Die Anforderungen verlangen lokale Kernfunktionen auf iOS, iPadOS, macOS, Windows, Android und Linux in freigegebenen App-/Browser-Varianten. Swing allein erfüllt das nicht. Die technische Umsetzung und ihre Vereinbarkeit mit dem Java-Prüfungsrahmen sind noch zu entscheiden.
- **Performance (NFA-01):** Ladezeit, Testgerät und Messbedingungen sind nicht festgelegt. Es wird kein Zielwert erfunden.
- **Cloud und gemeinsame Alben:** Synchronisierung, Rechte, Einladungen und Konflikte sind fachlich beziehungsweise technisch noch nicht vollständig spezifiziert.
- **Medien und Metadaten:** Video-Regeln, einige Zeichen- und Längengrenzen und die Sortierdetails bleiben offen.

Architekturentscheidungen und verworfene Alternativen werden nach Teamentscheidung in `docs/adr/` dokumentiert. Die Block-3-Diagramme müssen mit diesem Requirements-Stand abgeglichen werden; dieses Dokument behauptet nicht, dass die beschriebenen Adapter oder Anwendungsfälle bereits vorhanden sind.
