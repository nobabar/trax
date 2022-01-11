public interface GameObserver {
	void nextturn(Colors player);
	void win(Colors winner);
}
