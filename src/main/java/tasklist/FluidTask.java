package tasklist;

/*FUNCIONALIDAD DE LAS TAREAS ESTATICAS*/

public abstract class FluidTask extends TaskListAbstract {

	String taskStatus; // No time limit, Pending, Done, Late
	
	//Constructor
	public FluidTask(String taskName, String taskDescription) {
		super(taskName, taskDescription);
		// TODO Auto-generated constructor stub
		this.taskStatus= "pending";//Estado inicial por defecto 
	}
@Override
public String toString() {
	return "[Fluida]" + taskName + "-" + taskDescription + "(" + taskStatus + ")";
}
}
