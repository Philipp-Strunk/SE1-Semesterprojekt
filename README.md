# Semesterprojekt SE-1

Dieses Projekt ist eine Erweiterung des Prio-Tools aus Übung 4-2.
Mit dem Tool können User Stories eingegeben, gespeichert, geladen, sortiert
ausgegeben und auf ihre Qualität geprüft werden.

## Starten

Das Projekt kann mit Maven gebaut und getestet werden:

```bash
mvn test
```

Start mit Datei-Persistenz:

```bash
mvn -q exec:java -Dexec.mainClass=Main
```

Start mit MongoDB-Persistenz:

```bash
mvn -q exec:java -Dexec.mainClass=Main -Dexec.args="--mongodb"
```

Für MongoDB muss der Zugriff auf die SEPP-Umgebung möglich sein. In der Regel
ist dafür der VPN-Zugang der Hochschule nötig.

## Befehle

| Befehl | Bedeutung |
| --- | --- |
| `enter` | neue User Story eingeben |
| `dump` | User Stories nach Priorität sortiert ausgeben |
| `store` | aktuellen Stand speichern |
| `load` | gespeicherten Stand laden |
| `analyze <id>` | Qualität einer User Story prüfen |
| `analyze --all` | durchschnittliche Qualität aller User Stories prüfen |
| `analyze <id> --details` | Defizite einer User Story anzeigen |
| `analyze <id> --details --hints` | Defizite und Hinweise anzeigen |
| `addElement --actor <Akteur>` | Akteur registrieren |
| `actors` | registrierte Akteure anzeigen |
| `undo` | letzte Eingabe einer User Story oder eines Akteurs rückgängig machen |
| `help` | Befehle anzeigen |
| `exit` | Programm beenden |

## Eingabe einer User Story

Bei `enter` werden folgende Werte abgefragt:

1. ID
2. Titel der User Story
3. Akzeptanzkriterium
4. Mehrwert von 1 bis 5
5. Strafe von 1 bis 5
6. Aufwand als positive Fibonacci-Zahl
7. Risiko von 1 bis 5
8. Projekt

Beispiel für einen Titel:

```text
Als Student möchte ich Lernräume suchen, um schneller einen Platz zu finden.
```

Die Priorität wird nach der Formel aus Übung 4-2 berechnet:

```text
(Mehrwert + Strafe) / (Aufwand + Risiko)
```

## Analyse

Die Analyse bewertet eine User Story in Prozent. Aktuell werden drei Punkte geprüft:

- Ist der Akteur bekannt?
- Enthält die User Story eine Funktionalität mit `möchte`?
- Enthält die User Story einen schriftlichen Mehrwert mit `um ...`?

Die Regeln sind als einzelne `QualityRule`-Klassen umgesetzt. Dadurch können weitere
Regeln ergänzt werden, ohne den Analyzer selbst groß umzubauen.

## Persistenz

Standardmäßig wird in die Datei `objects.ser` gespeichert.

Mit dem Startargument `--mongodb` wird stattdessen die MongoDB-Strategie verwendet.
Die Daten liegen in der Datenbank `demo` der SEPP-MongoDB.

Verwendete Collections:

```text
se1_pstrun2s_userstories
se1_pstrun2s_actors
```

Die MongoDB-Klassen im Package `org.hbrs.mongodb` orientieren sich an dem
bereitgestellten Repository aus der Aufgabenstellung.

## Kurzer Testablauf

Start mit MongoDB:

```bash
mvn -q exec:java -Dexec.mainClass=Main -Dexec.args="--mongodb"
```

Dann im Tool:

```text
addElement --actor Student
enter
store
exit
```

Danach erneut starten und prüfen:

```text
load
actors
dump
analyze 1 --details --hints
exit
```

Wenn alles korrekt geladen wurde, wird der Akteur wieder angezeigt und die Analyse
kennt den Akteur nach dem Laden weiterhin.

## Tests

Die JUnit-Tests prüfen unter anderem:

- Prioritätsberechnung
- Container-Verhalten
- Analyse guter und unvollständiger User Stories
- Erweiterbarkeit der Analyse-Regeln
- Datei-Persistenz
- Wechsel der Persistenzstrategie
- MongoDB-Mapping
- Undo für User Stories und Akteure

Ausführen:

```bash
mvn test
```
