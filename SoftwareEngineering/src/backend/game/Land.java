package backend.game;

public abstract class Land {
	private String name;
	
	protected Land(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
