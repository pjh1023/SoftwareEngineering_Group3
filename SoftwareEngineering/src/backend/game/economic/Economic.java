package backend.game.economic;

public interface Economic {
	abstract public boolean pay(Economic to, int amount);
	abstract public int paid(int amount);
}
