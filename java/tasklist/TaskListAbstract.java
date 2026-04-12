package tasklist;

/*CLASE ABSTRACTA DE LAS TAREAS*/

 /*public abstract class TaskListAbstract {
	
	String taskName; //Nombre de la tarea
	String taskDescription; //Descripcion de la tarea
	
	//Obtiene la informacion de las tareas (EN PROGRESO)
	//static void getTaskInfo() {
	//}
	
	//Constructor
	public TaskListAbstract(String taskName, String taskDescription) {
		this.taskName = taskName;
		this.taskDescription = taskDescription;
	}
	public String getTaskName() {
		return taskName;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public abstract String getDetails();
}
*/
/*
public abstract class TaskListAbstract {
    protected String taskName; // Cambiado a protected para facilidad
    protected String taskDescription;

    public TaskListAbstract(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    public String getTaskName() { return taskName; }
    public String getTaskDescription() { return taskDescription; }
    
    public abstract String getDetails();
}
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class TaskListAbstract {
    protected String taskName;
    protected String taskDescription;

    // Configuración centralizada
    private final String URL = "jdbc:mysql://localhost:3306/task_db";
    private final String USER = "root";
    private final String PASS = ""; // Tu contraseña

    public TaskListAbstract(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
    }

    // Método que heredarán las hijas para conectarse
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public String getTaskName() { return taskName; }
    public String getTaskDescription() { return taskDescription; }

    // Método que cada tarea implementará para guardarse en la DB
    public abstract void saveToDatabase(String dueDate);
    
    public abstract String getDetails();
}
