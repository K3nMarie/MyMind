package moodtracker;

/*FUNCIONALIDAD DEL TRACKEADOR DE ESTADO DE ANIMO*/

public class MoodTrackerFunc {
	
	//Atributos
	int id;
	String currentMood;
	String date; // guardamos fecha para saber que mood fue en que dia
	
	//Constructor
	public MoodTrackerFunc() {}

	public MoodTrackerFunc(String mood, String date) {
		this.currentMood = mood;
		this.date = date;
	}
	
	//Metodos
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getMood() { return currentMood; }
	public void setMood(String mood) { this.currentMood = mood; }

	public String getDate() { return date; }
	public void setDate(String date) { this.date = date; }

}