package backend.game;

public class Start extends Land {
	private int salary;
	public Start(String name, int salary) {
		super(name);
		this.salary = salary;
	}
	
	public int getSalary() {
		return salary;
	}
}
