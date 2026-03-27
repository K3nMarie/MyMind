package tasklist;

/*CLASE ABSTRACTA DE LAS TAREAS*/

abstract class TaskListAbstract {
	
	String taskName; //Nombre de la tarea
	String taskDescription; //Descripcion de la tarea
	
	//Obtiene la informacion de las tareas (EN PROGRESO)
	static void getTaskInfo() {
	}
	
	//Constructor
	public TaskListAbstract(String taskName, String taskDescription) {
		this.taskName = taskName;
		this.taskDescription = taskDescription;
	}
	
}
