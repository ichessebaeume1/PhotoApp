# Risikoanalyse

Die Risiken beziehen sich auf die geplante lokale Fotoverwaltung. Die Gegenmaßnahmen beschreiben den aktuellen Entwurfs- oder Teststand.

| Risiko | Auswirkung | Gegenmaßnahme und Stand |
|---|---|---|
| Dateioperation bricht während Import oder Löschen ab | Originale können verloren gehen oder Metadaten auf fehlende Dateien zeigen. | [ADR-003](docs/adr/ADR-003-fehler.md) beschreibt geprüfte Kopien vor dem Löschen und Wiederanlauf. Dateisystemintegration und Fehlerfälle sind noch zu testen. |
| Wiederherstellung kollidiert mit einem aktiven Foto oder einem vollen Album | Nach der Wiederherstellung könnten Duplikate auftreten oder das Mengenlimit verletzt werden. | GR-08 sieht eine Prüfung auf Kapazität und Inhaltsduplikate vor. Domänentests decken die Albumregeln ab. Der Wiederherstellungstest steht aus. |
| Unterschiede zwischen Windows, macOS und Linux | Dateipfade, Dateisperren und Schreiboperationen können sich unterschiedlich verhalten. | Der Build läuft in einer CI-Matrix für alle drei Plattformen. Integrationstests für die künftige Dateisystem-Infrastruktur stehen aus. |
| Große Alben verzögern die Anzeige | Das Leistungsziel für die erste Ansicht kann verfehlt werden. | Der Entwurf sieht seitenweises Laden von Vorschauen vor. Das [Testkonzept](TESTING.md#performance) beschreibt den geplanten Nachweis. |
| Die Formatannahme wird geändert | Importvalidierung und Testdateien müssen angepasst werden. | JPG, JPEG, PNG und SVG sind als Teamannahme A-09 dokumentiert. Die Bestätigung ist als O-02 erfasst. |

Die fachlichen Regeln und offenen Punkte stehen in [REQUIREMENTS.md](REQUIREMENTS.md).
