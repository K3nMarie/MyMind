package tasklist;

import java.time.LocalDate;

public class RepeatingTask extends TaskListAbstract {

	protected LocalDate dueDate;

	public RepeatingTask(String taskName, String taskDescription, LocalDate dueDate) {
	    super(taskName, taskDescription, dueDate);
	}

    public String getStatus() { return taskStatus; }
    public LocalDate getDueDate() { return dueDate; }

    @Override
    public String getDetails() {
        return "[Repetitiva] " + taskName;
    }
}