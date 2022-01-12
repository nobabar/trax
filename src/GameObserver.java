/**
 * Interface between game board and frame.
 */
public interface GameObserver {
	void nextTurn(Colors player); // indicates players' turn
	void win(Colors winner); // indicates game over
}
