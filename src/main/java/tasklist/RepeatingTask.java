package tasklist;

public class RepeatingTask extends TaskListAbstract {

    String dueDate;
    String taskStatus;

    public RepeatingTask(String taskName, String taskDescription, String dueDate) {
        super(taskName, taskDescription);
        this.dueDate = dueDate;
        this.taskStatus = "sin hacer";
    }

    public String getStatus() { return taskStatus; }
    public String getDueDate() { return dueDate; }

    @Override
    public String getDetails() {
        return "[Repetitiva] " + taskName;
    }
}