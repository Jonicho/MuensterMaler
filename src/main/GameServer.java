package main;

import java.util.*;
import console.ConsoleWindow;
import netzklassen.Server;

public class GameServer extends Server implements OnStatusChange {
	private ArrayList<Player> playerList = new ArrayList();
	private ArrayList<Player> readyClients = new ArrayList();
	private ArrayList<Game> gameList = new ArrayList();
	private int PLAYER_COUNT = 3;

	public static void main(String[] args) {
		new ConsoleWindow();
		new GameServer(1111);
	}

	public GameServer(int pPort) {
		super(pPort);
	}

	public void processNewConnection(String pClientIP, int pClientPort) {
		playerList.add(new Player(pClientIP, pClientPort));
		System.out.println("new player connected from" + pClientIP + ":" + pClientPort);
	}

	public void processMessage(String pClientIP, int pClientPort, String pMessage) {

		// splitting the messages
		String[] messages = pMessage.split(":");
		String protocolMessage = "";
		String message = "";
		if (messages.length > 1) {
			protocolMessage = messages[0];
			message = messages[1];
		} else {
			send(pClientIP, pClientPort, "invalid message!");
		}

		// test comment to be able to push to git
		// differentiating the protocol messages
		switch (protocolMessage) {

		case protocol.PROTOCOL.CS_REQUEST_USERNAME:
			send(pClientIP, pClientPort, "S0");

			break;
		// username was send to server and needs to be added to the client object
		case protocol.PROTOCOL.CS_USERNAME:
			// looking for right player to edit name
			for (int i = 0; i < playerList.size(); i++) {
				if (pClientIP.equalsIgnoreCase(playerList.get(i).getIp())) {
					if (pClientPort == playerList.get(i).getPort()) {
						// changing name
						playerList.get(i).setName(message);
						break;
					}
				}
			}
			// Checking if 4 clients are ready to start a new game
			checkForReadyGame();
			break;
		// Drawing was sent by a user to the server
		case protocol.PROTOCOL.CS_SEND_DRAWING:
			// checking which game contains the player, who has sent the message
			for (int i = 0; i < gameList.size(); i++) {
				Game current = gameList.get(i);
				// adding drawing to the game if player is in the game
				if (current.containsPlayer(pClientIP, pClientPort)) {
					current.addPicture(pClientIP, pClientPort, message);
				}
			}
			break;
		// Guess was made by a player about a specific drawing
		case protocol.PROTOCOL.CS_SEND_GUESS:

			// checking which game contains the player, who has sent the message
			for (int i = 0; i < gameList.size(); i++) {
				Game current = gameList.get(i);
				// adding guess to the game if player is in the game
				if (current.containsPlayer(pClientIP, pClientPort)) {
					current.addGuess(pClientIP, pClientPort, message);
				}
			}
			break;

		case protocol.PROTOCOL.CS_CHAT:

			for (int i = 0; i < gameList.size(); i++) {
				Game current = gameList.get(i);
				// adding chat message to the game if player is in the game
				if (current.containsPlayer(pClientIP, pClientPort)) {
					current.receiveChatMessage(pClientIP, pClientPort, message);
				}
			}

			break;

		// Default error message
		default:
			send(pClientIP, pClientPort, "message arrived. Was not defined in protocol");
		}
	}

	public void startNewGame(ArrayList<Player> participants) {
		// Starting new game
		System.out.println("!!!starting new game!!!");
		// remove clients of the new game from waiting List
		playerList.removeAll(participants);
		Game game = new Game(participants, 40000, 20000, 10, this);
		gameList.add(game);
		// starting game as a new thread
		new Thread(game).start();
	}

	private void checkForReadyGame() {
		readyClients.removeAll(readyClients);
		for (int i = 0; i < playerList.size(); i++) {
			if (playerList.get(i).getName() != null) {
				readyClients.add(playerList.get(i));
				System.out.println("player " + playerList.get(i).getName() + " added to readyList");
			}
			if (readyClients.size() == PLAYER_COUNT) {
				startNewGame(readyClients);
				break;
			}
		}

	}

	public void sendMessage(String message, ArrayList<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			Player current = players.get(i);
			send(current.getIp(), current.getPort(), message);
		}
	}

	public void closeConnection(ArrayList<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			closeConnection(player.getIp(), player.getPort());
		}
	}
}