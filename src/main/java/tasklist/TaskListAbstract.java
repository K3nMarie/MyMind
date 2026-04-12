package tasklist;

import java.time.LocalDate;

/*CLASE ABSTRACTA DE LAS TAREAS*/

public abstract class TaskListAbstract {

	 protected int id;
	    protected String taskName;
	    protected String taskDescription;
	    protected String taskStatus;
	    protected LocalDate dueDate;

	    public TaskListAbstract(String taskName, String taskDescription, LocalDate dueDate) {
	        this.taskName = taskName;
	        this.taskDescription = taskDescription;
	        this.dueDate = dueDate;
	        this.taskStatus = "sin hacer";
	    }

    public String getTaskName() { return taskName; }
    public String getTaskDescription() { return taskDescription; }

    // Ahora ya NO guarda en DB
    public abstract String getDetails();
}