# Optionale KI und Agent „Fotos aufräumen“
Status: Won’t implement im aktuellen Umfang; nur Entwurf, keine Tool-Ausführung.
DescriptionSuggester.suggest(request) wird durch LlmDescriptionSuggester umgesetzt.
Eine Beschreibung ist eine einzelne Anfrage, kein Agentenlauf.

## Zwei Guardrails
1. **Vor Anfrage:** explizite Zustimmung für dieses Foto, erlaubtes Bildformat/Größe,
   Metadaten minimieren (EXIF/GPS entfernen); bei fehlender Freigabe keine Anfrage.
2. **Nach Antwort:** Text als nicht vertrauenswürdig behandeln, maximale Ausgabelänge anhand
   noch festzulegender Policy prüfen, unzulässige Inhalte/HTML/Toolanweisungen abweisen;
   Rückgabe als Vorschlag. Ohne gültige Längenpolicy bleibt der Adapter deaktiviert.
Nutzende übernehmen/ändern den Vorschlag bewusst; kein automatisches Speichern.

## ToolSpec-Liste (maximal vier)
| Tool / Parameter | Beschreibung | Kategorie | Begründung |
|---|---|---|---|
| deleteAlbum(albumId, expectedVersion) | Album nach Vorschau in Papierkorb legen | nur mit Freigabe | Destruktiv: explizite Freigabe bindet Album, Anzahl und Version. |
| removePhoto(albumId, photoId, expectedVersion) | Foto aus Album entfernen | nur mit Freigabe | Eine Fehlentscheidung darf kein Foto ohne Zustimmung entfernen. |
| renameAlbum(albumId, newName, expectedVersion) | Albumname ändern | nur mit Freigabe | Auch reversible Änderungen sollen die gewählte Organisation nicht still überschreiben. |
| shareAlbum(albumId, recipient, permission) | Externen Zugriff erlauben | gar nicht anbieten | Teilen offenbart private Inhalte; Identitäts-/Rechtemodell fehlt. |

Keine der vier zustandsändernden Funktionen wird direkt ausgeführt. Ein Dispatcher erlaubt nur
bekannte Toolnamen, validiert Schema und IDs und prüft Freigabetoken im Code; shareAlbum
ist nicht registriert. Token gilt einmalig für genaue Parameter/Version und läuft ab.
Toolargumente und Fototexte sind untrusted, keine Pfade/Shellbefehle akzeptieren.
Geplantes Budget: höchstens vier Toolversuche pro Lauf, danach stoppen und Ergebnis zeigen;
Abbruch und Fehler führen nicht zu Wiederholung destruktiver Aktionen ohne neue Freigabe.
