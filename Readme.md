# Project Managment System

## Requirements
* Docker https://docs.docker.com/get-docker/
* Docker compose (bei Windows und Mac schon in Docker enthalten) https://docs.docker.com/compose/install/

## Endpunkt
```
http://localhost:8089
```
## Swagger
```
http://localhost:8089/swagger
```
## Bekannte Bugs
- LocalDateTimes werden in Swagger im normalen Java-Format angezeigt, haben jedoch eigentlich ein anderes Format
  - Swaggers Format: e.g. 2022-11-16T17:20:49.688Z
  - Eigentliches Format (was eingegeben werden muss: 2022-11-16_17_20_49


# Postgres
### Terminal öffnen
für alles gilt, im Terminal im Ordner docker/local sein
```bash
cd docker/local
```
### Postgres starten
```bash
docker compose up
```
Achtung: Der Docker-Container läuft dauerhaft! Wenn er nicht mehr benötigt wird, sollten Sie ihn stoppen.

### Postgres stoppen
```bash
docker compose down
```

### Postgres Datenbank wipen, z.B. bei Problemen
```bash
docker compose down
docker volume rm local_lf8_starter_postgres_data
docker compose up
```

### Intellij-Ansicht für Postgres Datenbank einrichten
```bash
1. Lasse den Docker-Container mit der PostgreSQL-Datenbank laufen
2. im Ordner resources die Datei application.properties öffnen und die URL der Datenbank kopieren
3. rechts im Fenster den Reiter Database öffnen
4. In der Database-Symbolleiste auf das Datenbanksymbol mit dem Schlüssel klicken
5. auf das Pluszeichen klicken
6. Datasource from URL auswählen
7. URL der DB einfügen und PostgreSQL-Treiber auswählen, mit OK bestätigen
8. Username lf8_starter und Passwort secret eintragen (siehe application.properties), mit Apply bestätigen
9. im Reiter Schemas alle Häkchen entfernen und lediglich vor lf8_starter_db und public Häkchen setzen
10. mit Apply und ok bestätigen 
```
# Keycloak

### Keycloak Token
1. Auf der Projektebene [GetBearerToken.http](./GetBearerToken.http) öffnen.
2. Neben der Request auf den grünen Pfeil drücken
3. Aus dem Reponse das access_token kopieren
