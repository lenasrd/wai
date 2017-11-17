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

Eclipse
- Quarz zum laufen bringen

Model
- Kamera-Model erstellen
- Image-Model erstellen

Administration
- Administrator-Menü add User
- Administrator-Menü delete User
- Administrator-Menü edit User
- Administrator-Menü add Cam
- Administrator-Menü delete Cam
- Administrator-Menü edit Cam

Doku
- PowerPoint

Login
- Login mit Datenbank verknüpfen (Andy)

MainMenü
- Admin-Menü im Mainmenü nur sichtbar, wenn User.PermissionLevel = 1

HistoryServlet
- history-Seite verlinken (Andy)
- Thumbnails generieren
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
 
 
 
 
 
