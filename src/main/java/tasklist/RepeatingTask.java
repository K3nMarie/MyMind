package tasklist;

/*FUNCIONALIDAD DE LAS TAREAS QUE SE REPITEN*/

public abstract class RepeatingTask extends TaskListAbstract {
	
	//Constructor
	public RepeatingTask(String taskName, String taskDescription) {
		super(taskName, taskDescription);
		// TODO Auto-generated constructor stub
	}
    @Override 
	public String toString() {
		return "[REpetitiva]" + taskName + "-" + taskDescription + "(" + taskDescription + ")";
	}
}
