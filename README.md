# WAI-Projekt WS17-18 Gruppe x

## Protokoll

### todo (Stand 22.11.2017)

Priorität:

- HistoryServlet mit Datenbank erknüpfen: 
      - Formular auslesen und entsprechende Bilder anfordern
      
- Administration.JSP
      - Auflisten der User (Der Request bekommt beim Aufruf eine List<UserBean>, aufrufbar über den Key "UserList")
      - Beim Drücken des Delete/Edit button soll die ID des Users irgendwie mitgesendet werden



Doku
- PowerPoint


## DAO
- ImageDao an aktuelle Tabellenstruktur anpassen
- Einheitlicher Zeitstempel integrieren


## View
- Administrator edit,delete integrieren
- Administrator.jsp, return button einbauen
- history, explore usw return button einbauen


## Controller
HistoryServlet
- Liste von Thumbnails versenden
        
            
### Vorschlag Datenbank
Datenbankname: WAI_DB
Struktur: Siehe Bilder im root-Verzeichnis
