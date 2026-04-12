package tasklist;

public class FluidTask extends TaskListAbstract {

    String taskStatus;
    String dueDate;

    public FluidTask(String taskName, String taskDescription, String dueDate) {
        super(taskName, taskDescription);
        this.taskStatus = "sin hacer";
        this.dueDate = dueDate;
    }

    public String getStatus() { return taskStatus; }
    public String getDueDate() { return dueDate; }

    @Override
    public String getDetails() {
        return "[Fluida] " + taskName + " (" + taskStatus + ")";
    }
}