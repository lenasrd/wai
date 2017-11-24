# WAI-Projekt WS17-18 Gruppe x

## Protokoll

### Aufgabenverteilung
- Lars: Kameraanbindung
- Andreas: Ausbau AdministrationServlet (edit/delete Vorbereiten)
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

- HistoryServlet mit Datenbank erknüpfen: 
      - Formular auslesen und entsprechende Bilder anfordern
      - Für Übersicht Funktion zur generierung von Thumbnails implementieren
      
- Administration.JSP
      - Auflisten der User (Der Request bekommt beim Aufruf eine List<UserBean>, aufrufbar über den Key "UserList")
      - Beim Drücken des Delete/Edit button soll die ID des Users irgendwie mitgesendet werden



Doku
- PowerPoint

## View
- Administrator edit,delete integrieren
- Administrator.jsp, return button einbauen
- history, explore usw return button einbauen

## Model
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
 id             Integer
 name           Text
 password       Text
 permission     Integer
 cams           Integer[]
 
 Table Cam
 id     Integer
 url    Text
 name   Text
 
 Table Image
 id     Integer
 cam_id Integer
 Path   Text
 year   Integer
 month  Integer
 day    Integer
 hour   Integer
 
 
 
 
 
