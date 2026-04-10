package tasklist;

/*FUNCIONALIDAD DE LAS TAREAS QUE SE REPITEN*/
/*
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
*/


import java.sql.Connection;
import java.sql.PreparedStatement;

public class RepeatingTask extends TaskListAbstract {

    public RepeatingTask(String taskName, String taskDescription) {
        super(taskName, taskDescription);
    }

    @Override
    public void saveToDatabase(String dueDate) {
        String sql = "INSERT INTO tareas (titulo, descripcion, tipo, due_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, this.taskName);
            ps.setString(2, this.taskDescription);
            ps.setString(3, "Repetitiva");
            ps.setString(4, dueDate);
            ps.setString(5, "sin hacer");
            
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDetails() {
        return "[Repetitiva] " + taskName;
    }
}