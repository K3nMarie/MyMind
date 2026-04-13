package moodtracker;

/*FUNCIONALIDAD DEL TRACKEADOR DE ESTADO DE ANIMO*/

public class MoodTrackerFunc {
	
	//Atributos
	//String currentMood;
	
	//Metodos
public enum Mood{
	GOOD,
	SAD,
	ANGRY
}
private Mood currentMood;
public MoodTrackerFunc() {
	currentMood = Mood.GOOD;
}
public void setMood(Mood mood) {
	this.currentMood = mood;
}
public Mood getMood() {
	return currentMood;
}
public String getMoodImage() {
	switch (currentMood) {
	case GOOD: return "/happy.png";
	case SAD: return "/sad.png";
	case ANGRY: return "/angry.png";
	default:
		return "/normal.png";
	}
  }
}
