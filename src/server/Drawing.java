package server;

import java.util.*;

public class Drawing {

	private Guess[] guesses;
	private Player drawer;
	private String picture;
	private String[] words;
	ArrayList<Player> players;

	// Creates new drawing, before picture was painted and guesses were made.
	public Drawing(Player pDrawer, String[] pwords, ArrayList<Player> pPlayers) {
		drawer = pDrawer;
		words = pwords;
		picture = "";
		players = pPlayers;
		guesses = new Guess[players.size()];
		Player nub = new Player("NONE", Integer.MAX_VALUE);
		for (int i = 0; i < guesses.length; i++) {
			guesses[i] = new Guess(nub, "");
			guesses[i].setQuality(0);
		}
	}

	// Returns ArrayList of Players which guessed the correct words.
	public ArrayList<Player> getQualityGuesses(int quality) {
		ArrayList<Player> res = new ArrayList();
		for (int i = 0; i < guesses.length; i++) {
			if (guesses[i].getQuality() == quality) {
				res.add(guesses[i].getPlayer());
			}
		}
		return res;
	}

	public Guess[] getGuesses() {
		return guesses;
	}

	public Player getDrawer() {
		return drawer;
	}

	public String getPicture() {
		return picture;
	}

	public String[] getWords() {
		return words;
	}

	public void setPicture(String pic) {
		picture = pic;
	}

	public int getIndexOf(Guess pGuess) {
		for (int i = 0; i < guesses.length; i++) {
			if (guesses[i].equals(pGuess)) {
				return i;
			}
		}
		return 0;
	}

	public void appendGuess(Guess pGuess) {
		Player player = pGuess.getPlayer();
		guesses[players.indexOf(player)] = pGuess;
	}

	public int getValidGuesses() {
		int n = 0;
		for (Guess guess : guesses) {
			if (!guess.getPlayer().getIp().equals("NONE") && guess.getPlayer().getPort() != Integer.MAX_VALUE) {
				n++;
			}
		}
		return n;
	}
}
