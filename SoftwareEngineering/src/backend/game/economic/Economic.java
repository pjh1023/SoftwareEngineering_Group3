package backend.game.economic;

public interface Economic {
	abstract public int pay(Economic to, int amount);
	abstract public int paid(int amount);
}
