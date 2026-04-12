package journal;

import java.time.LocalDate;

/*FUNCIONALIDAD DEL DIARIO*/

public class JournalFunc {
    
    //Atributos
    int id; //puse id para que la base de datos pueda indentificar las tareas del diario 
    String entryTitle; //El titulo de la entrada de diario
    String entryContent; //El contenido de la entrada de diario
    private LocalDate entryDate; //Fecha de la entrada (IMPORTANTE)

    public JournalFunc() {
        
    }

    public JournalFunc(String Title, String content, LocalDate date) {
        this.entryTitle = Title;
        this.entryContent = content;
        this.entryDate = date;
    }

    //Metodos
    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getEntryTitle() { return entryTitle; }

    public void setEntryTitle(String entryTitle) {
        this.entryTitle = entryTitle;
    }

    public String getEntryContent() { return entryContent; }

    public void setEntryContent(String entryContent) {
        this.entryContent = entryContent; 
    }

    public LocalDate getEntryDate() { return entryDate; }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }
}