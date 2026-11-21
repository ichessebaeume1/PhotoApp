# ADR-004 – KI entwerfen, nicht aktivieren
Status: vorgeschlagen für Teamreview · Datum: 2026-11-16

## Kontext
Kurs verlangt Port, Adapter, Guardrails und Tool-Zuordnung. Präsentation sagt: keine KI-Funktion
im Produkt, Aufwand in Kernfunktionen investieren.

## Entscheidung
DescriptionSuggester und LlmDescriptionSuggester nur als Zielentwurf aufnehmen (Adapter-Muster,
Problem: Anbieter und Ein-/Ausgabeprüfung vom Anwendungsfall entkoppeln). Keine API-Schlüssel,
keine Übertragung, keine KI-Abhängigkeit im Gerüst. Request enthält nur explizit freigegebene,
minimierte Daten. Vor-/Nachprüfung und Agent-Toolrechte siehe ../design/KI.md.

## Verworfene Alternative
Sofort echte KI integrieren: kostet Aufwand ohne Beitrag zur Pflichtfunktionalität.
KI vollständig aus Entwurf streichen: erfüllt den geforderten Entwurfsnachweis nicht.

## Konsequenzen
Keine Laufzeitkosten oder Übertragung im aktuellen Projektgerüst; bei späterer Umsetzung
braucht es separate Einwilligung, Eval-Set, Datenschutzentscheidung und Tests.
