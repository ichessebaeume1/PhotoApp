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
- **Übernommener Vorschlag:** Ursprünglich ImageFileValidator als Port mit Dateiadapter, damit
  Anwendung keinen direkten Dateizugriff enthält. Im Architekturreview #17 präzisiert zu
  ImageFactsInspector plus fachlicher Entscheidung durch ImportPolicy. Konsistenzprüfung:
  Pfeilnamen gegen Empfängeroperationen.
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

## Issue #17 – Review Architektur v1 (Block 3)

- **Aufgabe/Ziel** Reviewhinweise zu fachlichem Regelbesitz, Versionsprüfung, Sequenz- und
  Klassendiagrammkonsistenz sowie UI-/Domänengrenze umsetzen.
- **Werkzeug und Modell** Copilot SDK in VS Code; genaue Modellvariante nicht ausgewiesen.
- **Ergebnis** ImportPolicy entscheidet anhand technischer ImageFacts; Album ist alleiniger
  Besitzer der Bestätigungsversion; PhotoView und TrashResult schließen fehlende DTO-Verträge.
  CRC, Schnittstellen, Architektur, ADR-003 und PlantUML-Quellen entsprechend abgeglichen.
- **Übernommen** Technische Dateifakten vom Adapter trennen; Fachentscheidung in die Domäne
  verlagern. Veraltete Löschbestätigungen als `VersionConflict` im Album ablehnen. UI erhält
  nur PhotoView. Fehlende Rückgabetypen und der Konfliktzweig sind ergänzt.
- **Bereits im Checkout erledigt** Die im Review erwähnten Links auf nicht vorhandene
  Block-3-Status-/PDF-/Prüfprotokolldateien waren in der aktuellen README und Architekturdatei
  nicht mehr enthalten; daher keine weitere Linkänderung.
- **Bewertung/Abgrenzung** Der Review betrifft den Block-3-Zielentwurf; noch nicht vorhandene
  Use Cases und Adapter wurden nicht als produktiver Code vorgezogen. Benannte
  Import-/Domänenfehler in der Java-Grundlage bleiben für Block 4 vorgemerkt.
- **Überprüfung und Reviewstand** Maven-Validierung nach Dokument-/Diagrammänderung;
  erneute Teamprüfung steht aus.

## Vorlage für künftige Einträge

- **Issue oder PR** …
- **Aufgabe/Ziel** …
- **Werkzeug und Modell, soweit bekannt** …
- **Ergebnis und betroffene Dateien** …
- **Übernommene oder verworfene Vorschläge mit Begründung** …
- **Überprüfung und Reviewstand** …
