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



### todo (Stand 17.11.2017)

Priorität:
- HistoryServlet mit Datenbank erknüpfen: 
      - Formular auslesen und entsprechende Bilder anfordern
      - Für Übersicht Funktion zur generierung von Thumbnails implementieren
      
- Administration.jsp
      - jsp's zum editieren, adden und deleten für user und kameras hinzufügen
      - und einbauen


Allgemein
- SQL-Statements definieren
- DebugLevel konfogurierbar machen
- Quarz zum laufen bringen

Doku
- PowerPoint

## View
- Action/Button zum anschauen von Bilder in Vollbild einbauen
- Admin-Menü im Mainmenü nur sichtbar, wenn User.PermissionLevel = 1 
- Administrator-Menü add User
- Administrator-Menü delete User
- Administrator-Menü edit User
- Administrator-Menü add Cam
- Administrator-Menü delete Cam
- Administrator-Menü edit Cam

## Model
- UserDao erstellen (Andy, in progress)
- KameraBean erstellen
- KameraDao erstellen
- ImageBean erstellen
- ImageDAO erstellen
- ImageBean funktion Thumbnails generieren



## Controller
Login
- Login mit Datenbank verknüpfen (Andy, erledigt)

HistoryServlet
- history-Seite verlinken (Andy, erledigt)
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
 
 
 
 
 
