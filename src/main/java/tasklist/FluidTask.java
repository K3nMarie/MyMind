package tasklist;

import java.time.LocalDate;

public class FluidTask extends TaskListAbstract {

	public FluidTask(String taskName, String taskDescription, LocalDate dueDate) {
	    super(taskName, taskDescription, dueDate);
	}

    public String getStatus() { return taskStatus; }

    @Override
    public String getDetails() {
        return "[Fluida] " + taskName + " (" + taskStatus + ")";
    }
}