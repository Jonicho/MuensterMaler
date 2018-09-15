package client;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import client.gui.GUI;
import netzklassen.Client;
import protocol.PROTOCOL;

public class GameClient extends Client {
	private GUI gui;
	private String username;

	public GameClient(String ServerIP, int port, String username, int connectTimeout, GUI gui)
			throws SocketTimeoutException {
		super(ServerIP, port, connectTimeout);
		gui.setCallback((String guess) -> {
			send(PROTOCOL.CS_SEND_GUESS + PROTOCOL.TRENNER + guess);
			System.out.println("Sent guess with callback: " + guess);
		});
		this.gui = gui;
		if (username.contains(PROTOCOL.TRENNER)) {
			throw new IllegalArgumentException("username must not contain \"" + PROTOCOL.TRENNER + "\"");
		}
		this.username = username;
		send(PROTOCOL.CS_REQUEST_USERNAME + PROTOCOL.TRENNER + "a");
		System.out.println("Sent R1 protocol");
		gui.closeLoginScreen();
	}

	public void processMessage(String message) {
		String[] msgParts = message.split(PROTOCOL.TRENNER);
		System.out.println("Got message: " + message);
		switch (msgParts[0]) {
		case PROTOCOL.SC_CLIENT_ACCEPTED:
			sendUsername();
			gui.openWaitingScreen(username);
			break;
		case PROTOCOL.SC_SEND_WORD:
			handleStartDrawing(msgParts);
			break;
		case PROTOCOL.SC_DRAWING_PHASE_END:
			sendDrawing(msgParts);
			break;
		case PROTOCOL.SC_START_GUESSING:

			break;
		case PROTOCOL.SC_SEND_DRAWING_FOR_GUESSING:
			startGuessing(msgParts);
			break;
		case PROTOCOL.SC_SEND_ANSWER:
			sendAnswer();
			break;
		case PROTOCOL.SC_END_GUESSING:

			break;
		case PROTOCOL.SC_SEND_WINNERS:
			showResults(msgParts);
			break;
		case PROTOCOL.SC_GUESS_FEEDBACK:
			handleGuessFeedback(msgParts);
			break;
		case PROTOCOL.SC_RIGHT_GUESS:
			handleRightGuess(msgParts);
			break;
		}
	}

	private void handleRightGuess(String[] msgParts) {
		try {
			String drawer = msgParts[1];
			String correctAnswer = msgParts[2];
			System.out.println(drawer + ";" + correctAnswer + "@ GameClient");
			gui.setCorrectOnGuess(drawer, correctAnswer);
		} catch (Exception e) {
			System.out.println("Failed to recive correct answer");
		}
	}

	private void sendUsername() {
		send(PROTOCOL.CS_USERNAME + PROTOCOL.TRENNER + username);
		System.out.println("Sent username \"" + username + "\"");
	}

	private void handleStartDrawing(String[] msgParts) {
		String word = msgParts[1];
		int drawTime = Integer.parseInt(msgParts[2]);
		gui.closeWaitingScreen();
		gui.openDrawingScreen(word, drawTime);
	}

	private void sendDrawing(String[] msgParts) {
		Picture p = gui.closeDrawingScreen();
		send(PROTOCOL.CS_SEND_DRAWING + PROTOCOL.TRENNER + p.toString());
		System.out.println(p);
		System.out.println("Sent picture");
	}

	private void startGuessing(String[] msgParts) {
		Picture p = Picture.fromString(msgParts[1]);
		int time = Integer.parseInt(msgParts[2]);
		gui.openGuessingScreen(p, time);
	}

	private void sendAnswer() {
		String guess = gui.closeGuessScreen();
		send(PROTOCOL.CS_SEND_GUESS + PROTOCOL.TRENNER + guess);
		System.out.println("Sent guess: " + guess);
	}

	public void showResults(String[] msgParts) {
		ArrayList<String> playerList = new ArrayList<>();
		ArrayList<String> scoreList = new ArrayList<>();
		for (int i = 1; i < msgParts.length; i += 2) {
			playerList.add(msgParts[i]);
			scoreList.add(msgParts[i + 1]);
		}
		gui.openScoreScreen(playerList, scoreList);
	}

	private void handleGuessFeedback(String[] msgParts) {
		boolean correct = Boolean.parseBoolean(msgParts[1]);
		String hint = msgParts.length > 2 ? msgParts[2] : null;
		gui.setGuessFeedback(correct, hint);
	}

	public void endSession() {
		gui.dispose();
	}
}
