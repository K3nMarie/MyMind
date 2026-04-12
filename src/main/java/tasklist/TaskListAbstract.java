package tasklist;

/*CLASE ABSTRACTA DE LAS TAREAS*/

public abstract class TaskListAbstract {

    protected String taskName;
    protected String taskDescription;

    public TaskListAbstract(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    public String getTaskName() { return taskName; }
    public String getTaskDescription() { return taskDescription; }

    // Ahora ya NO guarda en DB
    public abstract String getDetails();
}