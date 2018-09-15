package main;

public class Guess 
{
	private Player player;
	private String guess;
	private int quality;
	
	// Creates new guess.
	public Guess(Player pPlayer, String pGuess)
	{
		pGuess = pGuess.toLowerCase();
		player = pPlayer;
		guess = pGuess;
		quality = 0;
	}
	public String getGuess() {
		return guess;
	}
	public Player getPlayer() {
		return player;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int pValue) {
		quality = pValue;
	}
}