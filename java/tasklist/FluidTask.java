package tasklist;
/*
/*FUNCIONALIDAD DE LAS TAREAS ESTATICAS*/
/*
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
*/


import java.sql.Connection;
import java.sql.PreparedStatement;

public class FluidTask extends TaskListAbstract {
    String taskStatus;

    public FluidTask(String taskName, String taskDescription) {
        super(taskName, taskDescription);
        this.taskStatus = "sin hacer"; 
    }

    @Override
    public void saveToDatabase(String dueDate) {
        String sql = "INSERT INTO tareas (titulo, descripcion, tipo, due_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, this.taskName);
            ps.setString(2, this.taskDescription);
            ps.setString(3, "Fluida");
            ps.setString(4, dueDate);
            ps.setString(5, this.taskStatus);
            
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDetails() {
        return "[Fluida] " + taskName + " (" + taskStatus + ")";
    }
}