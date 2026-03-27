package tasklist;

/*FUNCIONALIDAD DE LAS TAREAS ESTATICAS*/

public class FluidTask extends TaskListAbstract {

	String taskStatus; // No time limit, Pending, Done, Late
	
	//Constructor
	public FluidTask(String taskName, String taskDescription) {
		super(taskName, taskDescription);
		// TODO Auto-generated constructor stub
	}

}
