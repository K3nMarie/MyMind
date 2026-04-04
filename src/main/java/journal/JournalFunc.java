package journal;

/*FUNCIONALIDAD DEL DIARIO*/

public class JournalFunc {
	
	//Atributos
	int id; //puse id para que la base de datos pueda indentificar las tareas del diario 
	String entryTitle; //El titulo de la entrada de diario
	String entryContent; //El contenido de la entrada de diario
	//ENTRY DATE
	public JournalFunc() {
		
	}
	public JournalFunc ( String Title, String content) {
		
		this.entryTitle= Title;
		this.entryContent = content;
	}
	//Metodos
	public int getid() { return id;}
	public void setentryid(int id) {
		this.id = id;
	}
	
	public String getentryTitle() {	return entryTitle;}
	public void setentryTitle(String entryTitle) {
		this.entryTitle= entryTitle;
	}
	public String getentryContent(){ return entryContent; }
	public void setentryContent(String entryContent) {
		this.entryTitle= entryContent;
	}

}
