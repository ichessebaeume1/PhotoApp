# KI-Nutzung
## Block 3 – Architekturentwurf und Konsistenzprüfung
- **Aufgabe/Ziel:** Block-3-Artefakte anhand Anforderungen, Anleitung, Feedback und bestehendem
  Projektgerüst erstellen; 10.000 Fotos pro Album berücksichtigen.
- **Werkzeug/Modell:** ChatGPT / Codex, GPT-6; genaue Laufzeitvariante nicht angezeigt.
  [Arbeitsauftrag und Kontext](docs/ai/prompts/03-block3-architecture.md).
- **Ergebnis:** ARCHITECTURE.md, vier ADRs, CRC-Karten, Schnittstellen, KI-Entwurf,
  vier PlantUML-Diagramme mit PNGs sowie Album/Photo-Grundlagen mit Invariantentests.
- **Kritische Bewertung (KI-Selbstprüfung):** Lehrbeispiel 100 Fotos passt nicht zu eigenen
  Anforderungen; 10.000 beibehalten. Das vorhandene Screenshot-Sequenzdiagramm zeigt
  keinen Speicher-/Move-Fehlerpfad; ergänzte Pfade sind notwendig. Status zuerst allein schützt
  nicht gegen teilweise destruktives Verschieben: Copy-before-delete und Recovery erforderlich.
  Diagramme sind Zielentwurf, kein Nachweis implementierter Persistenz oder Web-App.
- **Übernommener Vorschlag:** ImageFileValidator als Port mit Dateiadapter, damit Anwendung
  keinen direkten Dateizugriff enthält. Konsistenzprüfung: Pfeilnamen gegen Empfängeroperationen.
- **Verworfener Vorschlag:** Einfach nach beliebigem Move-Fehler `deletedAt` zurücksetzen.
  Begründung: nach einem Teil-Move könnten Originale fehlen; stattdessen definierter
  Copy-before-delete-Vertrag (ADR-003). Ebenso keine ungeklärte 100-Foto-Grenze übernehmen.
- **Entscheidung/Stand:** KI liefert einen prüfbaren Entwurf; menschliche fachliche Bewertung,
  Partnerreview und Freigabe stehen aus. Dieser Eintrag behauptet keine Teamentscheidung.

## Noch fehlende historische Einträge
Der vorhandene Platzhalter nannte Initial-Setup; konkrete Prompts und Entscheidungen dazu
liegen hier nicht nachweisbar vor. Das Team ergänzt tatsächliche Einsätze aus Block 1/2.
Nicht mehrere historische KI-Einsätze aus diesem einen Arbeitsauftrag erfinden.
