# Reviews und Releases

## Pull Requests

Bearbeitet jede Änderung über ein Issue und einen davon abgeleiteten Branch von `main`. Verknüpft den Pull Request mit dem Issue und holt ein dokumentiertes Review eines anderen Teammitglieds ein. Mergt nach Freigabe und grüner CI. Beim Squash-Merge muss auch die endgültige Commit-Nachricht auf `main` dem vereinbarten Format folgen, etwa `docs(#9): complete block 4 repository documentation`. Verwendet keine pauschalen `fix`- oder `WIP`-Nachrichten.

### Bisher geprüfte Review-Nachweise

- `ichessebaeume1` – Änderungsanforderung und anschließende Freigabe in [PR #15](https://github.com/ichessebaeume1/PhotoApp/pull/15).
- `felixrau2105` – Review-Kommentare und Freigabe in [PR #18](https://github.com/ichessebaeume1/PhotoApp/pull/18).

Gleicht die Nachweise vor dem Block-Abschluss mit der vollständigen Teamliste ab. Ergänzt für jedes weitere Teammitglied einen Link zu einem selbst abgegebenen Code-Review.

## Release-Tags

| Meilenstein | Tag |
| --- | --- |
| Block 4 abgeschlossen | `v0.1.0` |
| Pflichtfunktionalität vollständig | `v1.0.0` |
| Change Request umgesetzt | `v1.1.0` |

Beim Release die zugehörigen Einträge aus `[Unreleased]` in einen datierten Versionsabschnitt in `CHANGELOG.md` verschieben und diesen per PR mergen. Nach dem Merge aller Block-4-Aufgaben und grüner CI auf dem Abschlussstand beide Tags auf denselben Commit setzen.

```powershell
git switch main
git pull --ff-only origin main
git tag -a v0.1.0 -m "Release v0.1.0"
git tag -a block-4 -m "Abschluss Block 4"
git push --atomic origin refs/tags/v0.1.0 refs/tags/block-4
```
