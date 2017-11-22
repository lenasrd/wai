# WAI-Projekt WS17-18 Gruppe x

## Protokoll

### Aufgabenverteilung
- Lars: Kameraanbindung
- Andreas: Servlets & Aktivitätsdiagramme
- Lena: jsps erstellen, Wireframes aktualisieren

### To do
- Datenbankanbindung
- naming conventions

### Sonstiges
nächstes Treffen: 17.11. 09:45 DSV Labor
-> merge jsps und Java-Code

--Testinput



### todo (Stand 22.11.2017)

Priorität:
- Schauen wie das mit den Debug-Levels funktioniert und System.out.println's ersetzen

- HistoryServlet mit Datenbank erknüpfen: 
      - Formular auslesen und entsprechende Bilder anfordern
      - Für Übersicht Funktion zur generierung von Thumbnails implementieren
      
- Administration.jsp
      - Auflisten der User (Der Request bekommt beim Aufruf eine List<UserBean>, aufrufbar über den Key "UserList")
      - Beim Drücken des Delete/Edit button soll die ID des Users irgendwie mitgesendet werden


Allgemein
- DebugLevel konfogurierbar machen
- Quarz zum laufen bringen

Doku
- PowerPoint

## View
- Administrator edit,delete integrieren

## Model
- KameraBean erstellen
- KameraDao erstellen
- ImageBean funktion Thumbnails generieren



## Controller
HistoryServlet
- Liste von Thumbnails versenden

ImageViewer
- Bilder in Datenbank erfassen
- Bild in voller Größe versenden

AppCore
- Automatisches Speichern der Bilder in Ordner
- Neue Bilder in Datenbank eintragen







### Vorschlag Ordnerstruktur Bilder
WebContent
+--Images
  +--CAM_XX_Name
  +--CAM_02_Name
    +--Y2017                      // Jahr
      +--M11                      // Monat
        +--D17                    // Tag
          +--H14                  // Stunde
            +--02201711171405.png // [CAM_ID, Jahr, Monat, Tag, Stunde, Minute].png
            
            
 ### Vorschlag Datenbank
 Datenbankname: WAI_DB
 
 Table User
 ID             Integer
 Name           Text
 Passwort       Text
 PermissionLvL  Integer
 CAMS           Integer[]
 
 Table Cam
 ID     Integer
 URL    Text
 Name   Text
 
 Table Image
 ID     Integer
 CAM_ID Integer
 Path   Text
 Date   Timestamp
 
 
 
 
 
