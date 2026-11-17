# Block 3 – Abgabestatus und nächste Schritte
## Im Änderungspaket enthalten
- [x] Architektur mit Schichten, Abhängigkeiten und Verantwortlichkeiten
- [x] Klassendiagramm und Sequenz „Foto hinzufügen“ mit Fehlerpfaden
- [x] Zusatzsequenz „Album löschen“, zuerst Status und sicherer Papierkorb
- [x] CRC-Karten, Schnittstellen, vier ADRs mit verworfenen Alternativen
- [x] KI-Port/Adapter, zwei Guardrails, maximal vier kategorisierte Agententools, Won’t
- [x] PlantUML-Quellen und lokal gerenderte PNGs
- [x] Java-21/Maven/JUnit-5-Gerüst erhalten; Album/Photo mit ersten Invarianten ergänzt
- [x] Tatsächlicher KI-Einsatz dokumentiert, offene Werte explizit markiert

## Vor der finalen Abgabe gemeinsam erledigen
- [ ] Formate, Metadatenlängen und weitere offene Entscheidungen klären (design/OFFENE_ENTSCHEIDUNGEN.md).
- [ ] REQUIREMENTS.md im Repo aus dem tatsächlichen Block-2-Stand ergänzen (derzeit Platzhalter).
- [ ] Partnerteam reviewt Diagramme, Antworten im Review-Issue und im PR dokumentieren.
- [ ] Reviewkorrekturen einarbeiten, CI grün bestätigen, mindestens Teamfreigabe einholen.
- [ ] PR mergen, danach Merge-Commit mit `block-3` taggen und Tag pushen.
- [ ] Sammel-Issue abschließen; historische AI_USAGE-Einträge wahrheitsgemäß ergänzen.

Kein Partnerreview, Approval oder Merge ist durch diese Checkliste ersetzt.
Build- und Rendernachweise stehen in [PRUEFPROTOKOLL.md](PRUEFPROTOKOLL.md).

## Einfach erklärt: Was du vorstellst
„Wir trennen Anzeige, Anwendungsablauf, Geschäftsregeln und Dateizugriff. Das Album prüft
selbst das Limit von 10.000 Fotos und Duplikate. Beim Import wird zuerst validiert und gehasht,
dann eine neue Datei gespeichert und zuletzt die Albumdatei aktualisiert. Löschen braucht eine
Bestätigung und einen sicheren Papierkorb. KI ist nur entworfen und im Produkt nicht aktiv.“

Beim PNG die Pfeile von oben nach unten lesen; `alt` trennt Erfolg und Fehlerfälle.
Ein Port ist ein Vertrag, ein Adapter seine technische Umsetzung. Ein ADR erklärt,
warum ihr euch so entschieden habt und welche andere Lösung ihr verworfen habt.
