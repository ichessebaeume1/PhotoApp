# KI-Nutzung

Dieses Protokoll hält fest, wie KI bei der Projektarbeit eingesetzt wurde und welche Vorschläge übernommen, verändert oder verworfen wurden. KI-unterstützte Pull Requests erhalten das Label `ai-assisted`.

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

## Issue #4 – Projektdokumentation

- **Aufgabe/Ziel** Abgleich der Markdown-Dateien mit dem Code, den Anforderungen und der Build-Konfiguration.
- **Werkzeug** Codex.
- **Ergebnis** Dokumentationskorrekturen, Risikoübersicht und Vorlagen für Änderungsanträge und KI-Einträge.
- **Bewertung** Belegte Projektentscheidungen und Annahmen aus den Anforderungen übernommen. Für das Vorgehensmodell fehlt im Checkout eine belastbare Quelle. Der bisher leere Abschnitt wurde entfernt.
- **Reviewstand** Teamreview ausstehend.

## Vorlage für künftige Einträge

- **Issue oder PR** …
- **Aufgabe/Ziel** …
- **Werkzeug und Modell, soweit bekannt** …
- **Ergebnis und betroffene Dateien** …
- **Übernommene oder verworfene Vorschläge mit Begründung** …
- **Überprüfung und Reviewstand** …
