package journal;

import java.time.LocalDate;

/*FUNCIONALIDAD DEL DIARIO (modelo de datos para las entradas)*/

public class JournalFunc {
    
    //Atributos
    int id; //ID para que la base de datos identifique cada entrada
    String entryTitle; //Titulo de la entrada del diario
    String entryContent; //Contenido principal de la entrada
    private LocalDate entryDate; //Fecha de la entrada (clave para agrupar o filtrar)

    public JournalFunc() {
        
    }

    public JournalFunc(String Title, String content, LocalDate date) {
        this.entryTitle = Title;
        this.entryContent = content;
        this.entryDate = date;
    }

    //Metodos (getters y setters)
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