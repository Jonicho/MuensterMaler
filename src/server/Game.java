package server;

import java.util.*;

public class Game implements Runnable {

	private ArrayList<Player> players;
	private int timePainting, timeGuessing, timeWaiting, status, pointsForDrawer, pointsForGuesser;
	private ArrayList<Drawing> drawings;
	private OnStatusChange gameServer;
	private Drawing currentGuessDrawing;
	private Comparision compare;

	// Creates a new game with specific painting and guessing time.
	public Game(ArrayList<Player> pPlayers, int pTimePainting, int pTimeGuessing, int pTimeWaiting,
			OnStatusChange pGameServer) {
		players = pPlayers;
		timePainting = pTimePainting;
		timeGuessing = pTimeGuessing;
		timeWaiting = pTimeWaiting;
		status = 0;
		gameServer = pGameServer;
		currentGuessDrawing = null;
		compare = new Comparision();
		pointsForDrawer = 10;
		pointsForGuesser = 20;
	}

	// Gameloop
	public void run() {
		// S1
		status = 1;
		drawings = new ArrayList<Drawing>();
		ArrayList<String[]> words = new Words().getWords(players.size());
		for (int i = 0; i < players.size(); i++) {
			drawings.add(new Drawing(players.get(i), words.get(i), players));
		}

		for (Drawing drawing : drawings) {
			ArrayList<Player> temp = new ArrayList<>();
			temp.add(drawing.getDrawer());
			gameServer.sendMessage("S1:" + drawing.getWords()[0] + ":" + Integer.toString(timePainting), temp);
		}
		// Wait [timePainting] for the players to draw their picture
		try {
			Thread.sleep(timePainting);
		} catch (InterruptedException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		// S2
		status = 2;
		gameServer.sendMessage("S2", players);
		// Waits for [timeWaiting] for the players to submit their drawings, if at least
		// one player doesn't submit a drawing all players are disconnected
		int timer = 0;
		while (!allDrawingsSent()) {
			if (timer > timeWaiting) {
				gameServer.closeConnection(players);
				return;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
			timer++;
		}

		// S3
		status = 3;
		gameServer.sendMessage("S3", players);
		// G
		for (int i = 0; i < drawings.size(); i++) {
			currentGuessDrawing = drawings.get(i);
			System.out.println("Now guessing picture of: " + currentGuessDrawing.getDrawer().getName());
			for (int a = 0; a < players.size(); a++) {
				ArrayList<Player> temp = new ArrayList<Player>();
				temp.add(players.get(a));
				// G0
				// Different message to the player who drew the current drawing.
				if (players.get(a).equals(drawings.get(i).getDrawer())) {
					gameServer.sendMessage(
							"G0:" + drawings.get(i).getPicture() + ":" + Integer.toString(timeGuessing) + ":yours",
							temp);
				} else {
					gameServer.sendMessage(
							"G0:" + drawings.get(i).getPicture() + ":" + Integer.toString(timeGuessing) + ":guess",
							temp);
				}
			}
			// Wait [timeGuessing] for the players to finish guessing
			for (int j = 0; j < timeGuessing / 1000; j++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// Auto-generated catch block
					e.printStackTrace();
				}
				if (currentGuessDrawing.getQualityGuesses(2).size() >= players.size() - 1) {
					j = timeGuessing;
				}
			}
			// G1
			gameServer.sendMessage("G1", players);

			// Wait for 1 more second to make up for delay
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// Auto-generated catch block

				e.printStackTrace();
			}

			// G3
			gameServer.sendMessage(
					"G3:" + currentGuessDrawing.getDrawer().getName() + ":" + currentGuessDrawing.getWords()[0],
					players);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// Auto-generated catch block

				e.printStackTrace();
			}

			// Add points to every player for the drawing that is currently guessed.
			givePoints();
		}
		// S4
		status = 4;
		gameServer.sendMessage("S4", players);

		// Sort players by points
		players.sort(new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				return o2.getPoints() - o1.getPoints();
			}
		});

		// S5
		status = 5;
		String result = "";
		for (int i = 0; i < players.size(); i++) {
			result = result + ":" + players.get(i).getName() + ":" + Integer.toString(players.get(i).getPoints());
		}
		gameServer.sendMessage("S5" + result, players);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		// gameServer.closeConnection(players);
	}

	// Checks whether the player has a guess to the drawing, returns the Guess if
	// yes and null if no
	public Guess guessExists(Drawing drawing, Player guesser) {
		for (Guess i : drawing.getGuesses()) {
			if (i.getPlayer().equals(guesser)) {
				return i;
			}
		}
		return null;
	}

	// Gives feedback to the guess the server got from the client and might
	// overwrite the old guess if it has an inferior quality than the new Guess
	public void checkGuess(Drawing drawing, Guess newGuess, Player guesser) {
		ArrayList<Player> temp = new ArrayList<>();
		temp.add(guesser);
		// System.out.println(drawing.getWords()[0] + " = " + newGuess.getGuess() + ":
		// ");
		// System.out.println(drawing.getWords()[0].equals(newGuess.getGuess()));
		if (drawing.getWords()[0].equals(newGuess.getGuess())) { // Right guess
			gameServer.sendMessage("G2:" + "true", temp);
			Guess oldGuess = guessExists(drawing, guesser);
			// System.out.println(oldGuess);
			if (oldGuess != null) {
				if (oldGuess.getQuality() < 2) {
					// System.out.println("Replaced an inferior guess with a quality 2 guess");
					int place = currentGuessDrawing.getQualityGuesses(2).size();
					switch (place) {
					case 0:
						guesser.addPoints(pointsForGuesser);
						System.out.println("Added " + Integer.toString(pointsForGuesser) + " to " + guesser.getName());
					case 1:
						guesser.addPoints(pointsForGuesser / 2);
						// System.out.println(
						// "Added " + Integer.toString(pointsForGuesser / 2) + " to " +
						// guesser.getName());
					}
					drawing.appendGuess(newGuess);
				}
			}
		} else if (!isSimilar(drawing.getWords(), newGuess.getGuess()).equals("false")) { // Similar guess
			gameServer.sendMessage("G2:" + isSimilar(drawing.getWords(), newGuess.getGuess()), temp);
			Guess oldGuess = guessExists(drawing, guesser);
			if (oldGuess != null) {
				if (oldGuess.getQuality() < 1) {
					drawing.appendGuess(newGuess);
				}
			}
		} else { // Wrong guess
			// System.out.println("Got a quality 0 guess");
			gameServer.sendMessage("G2:" + "false", temp);
		}
	}

	// Checks, whether 2 Strings are similar regarding meaning, or if one is a
	// version of the other one with a typing mistake. Returns "false:[hint message
	// regarding similarity]" if they're similar and "false" if they aren't.
	public String isSimilar(String words[], String guess) {
		if (!compare.swappedLetters(words, guess).equals("false")) {
			return compare.swappedLetters(words, guess);
		}
		if (!compare.missingLetter(words, guess).equals("false")) {
			return compare.missingLetter(words, guess);
		}
		if (!compare.additionalLetter(words, guess).equals("false")) {
			return compare.additionalLetter(words, guess);
		}
		if (!compare.isSynonym(words, guess).equals("false")) {
			return compare.isSynonym(words, guess);
		}
		if (!compare.wrongLetter(words, guess).equals("false")) {
			return compare.wrongLetter(words, guess);
		}
		return "false";
	}

	// Returns the quality a guess is supposed to have

	public int getGuessQuality(Drawing drawing, Guess guess) {
		if (drawing.getWords()[0].equals(guess.getGuess())) {
			return 2;
		} else if (!isSimilar(drawing.getWords(), guess.getGuess()).equals("false")) {
			return 1;
		} else {
			return 0;
		}
	}

	public String changeChars(String guess) {
		guess = guess.toLowerCase();
		for (int i = 0; i < guess.length(); i++) {
			String sub1 = guess.substring(0, i);
			String sub2 = guess.substring(i + 1, guess.length());
			if (guess.charAt(i) == '\u00FC') {
				guess = sub1 + "ue" + sub2;
			}
			if (guess.charAt(i) == '\u00E4') {
				guess = sub1 + "ae" + sub2;
			}
			if (guess.charAt(i) == '\u00F6') {
				guess = sub1 + "oe" + sub2;
			}
			if (guess.charAt(i) == '\u00DF') {
				guess = sub1 + "ss" + sub2;
			}
		}
		return guess;
	}

	// Adds a new Guess, sets its quality and uses the checkGuess() method to send a
	// feedback to the client
	public void addGuess(String ip, int port, String guess) {

		// Find the player who submitted the guess
		Player guesser = null;
		for (Player player : players) {
			if (player.getIp().equals(ip) && player.getPort() == port) {
				guesser = player;
			}
		}
		guess = changeChars(guess);
		// System.out.println("Guesser: " + guesser.getName());
		// Only accepts the guess if the player isn't the original drawer
		// System.out.println(currentGuessDrawing);
		// System.out.println("Drawer: " + currentGuessDrawing.getDrawer().getName());
		if (!currentGuessDrawing.getDrawer().equals(guesser)) {
			Guess temp = new Guess(guesser, guess);
			// Adds the guess to the current drawing
			currentGuessDrawing.appendGuess(temp);
			System.out.println("added guess from " + guesser.getName() + ": " + guess);
			// Give the guess a proper quality
			currentGuessDrawing.getGuesses()[players.indexOf(guesser)]
					.setQuality(getGuessQuality(currentGuessDrawing, temp));
			if (currentGuessDrawing.getGuesses()[players.indexOf(guesser)].getQuality() == 2) {
				int place = currentGuessDrawing.getQualityGuesses(2).size();
				switch (place) {
				case 0:
					guesser.addPoints(pointsForGuesser);
				case 1:
					guesser.addPoints(pointsForGuesser / 2);
				}
			}
			// Send a feedback to the client regarding the quality of the guess
			checkGuess(currentGuessDrawing, temp, guesser);
		}

	}

	// Add points for every right guess to the guesser. Add one less point for every
	// semi-right guess. Add point(s) for correct guesses to the drawer.
	// Only applies to the current drawing
	public void givePoints() {
		// Gives points for all right guesses
		for (int j = 0; j < currentGuessDrawing.getQualityGuesses(2).size(); j++) {
			if (!currentGuessDrawing.getQualityGuesses(2).get(j).equals(currentGuessDrawing.getDrawer())) {
				currentGuessDrawing.getQualityGuesses(2).get(j).addPoints(pointsForGuesser);
				// System.out.println("Added " + Integer.toString(pointsForGuesser) + " to "
				// + currentGuessDrawing.getQualityGuesses(2).get(j).getName());
				currentGuessDrawing.getDrawer().addPoints(pointsForDrawer);
				// System.out.println("Added " + Integer.toString(pointsForDrawer) + " to "
				// + currentGuessDrawing.getDrawer().getName());
				// System.out.println(currentGuessDrawing.getQualityGuesses(2).get(j).getName());
			}

		}
		// Gives points for all semi-right guesses
		for (int j = 0; j < currentGuessDrawing.getQualityGuesses(1).size(); j++) {
			if (!currentGuessDrawing.getQualityGuesses(1).get(j).equals(currentGuessDrawing.getDrawer())) {
				currentGuessDrawing.getQualityGuesses(1).get(j).addPoints(pointsForGuesser - pointsForDrawer / 2);
				// System.out.println("Added " +
				// Integer.toString(pointsForGuesser-pointsForDrawer / 2) + " to "
				// + currentGuessDrawing.getQualityGuesses(1).get(j).getName());
				// System.out.println(currentGuessDrawing.getQualityGuesses(1).get(j).getName());
			}

		}
	}

	// Checks whether every player did submit a drawing by checking if all Drawings
	// have a picture added to them
	public boolean allDrawingsSent() {
		for (Drawing drawing : drawings) {
			if (drawing.getPicture().equals("")) {
				return false;
			}
		}
		return true;
	}

	public int getStatus() {
		return status;
	}

	// Checks whether this game has a player with the given ip and port
	public boolean containsPlayer(String ip, int port) {
		for (Player i : players) {
			if (i.getIp().equals(ip) && i.getPort() == port) {
				return true;
			}
		}
		return false;
	}

	// Sets a picture to the drawing created for the player with the given ip and
	// port
	public void addPicture(String ip, int port, String picture) {
		for (Drawing drawing : drawings) {
			if (drawing.getDrawer().getIp().equals(ip) && drawing.getDrawer().getPort() == port) {
				drawing.setPicture(picture);
				System.out.println("got picture from: " + drawing.getDrawer().getName());
			}
		}
	}

	public ArrayList<Drawing> getDrawings() {
		return drawings;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void receiveChatMessage(String ip, int port, String message) {
		for (Player player : players) {
			if (player.getIp().equals(ip) && player.getPort() == port) {
				gameServer.sendMessage("C1:" + player.getName() + ":" + message, players);
			}
		}
	}

}
