package client.gui;

import java.awt.Dimension;

import java.util.ArrayList;

import javax.swing.JFrame;

import client.Picture;
import client.StringCallback;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	/*
	 * Basic Panels
	 */

	private LoginScreen loginScreen;
	private WaitingScreen waitingScreen;
	private DrawingScreen drawingScreen;
	private GuessScreen guessScreen;
	private ScoreScreen scoreScreen;

	private boolean active = false;

	private StringCallback guessCallback;

	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		setResizable(true);
		setTitle("M\u00fcnster Maler");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		openLoginScreen();
		pack();
		setVisible(true);
	}

	public void openLoginScreen() {
		loginScreen = new LoginScreen(this);
		getContentPane().add(loginScreen);
		validate();
		System.out.println("[GUI] Open login screen");
	}

	public void closeLoginScreen() {
		getContentPane().removeAll();
		System.out.println("[GUI] Close login screen");
	}

	/**
	 * This method opens the Painter. It will be finished and closed by
	 * closePaintWindow()
	 *
	 * @param pictureTitle
	 *                         the title of the painting to be drawn.
	 * @param timeToPaint
	 *                         the period of time the user can draw
	 * @return nothing
	 */
	public void openDrawingScreen(String pictureTitle, int timeToDraw) {
		if (!active) {
			drawingScreen = new DrawingScreen(pictureTitle, timeToDraw);
			getContentPane().add(drawingScreen);
			validate();
			active = true;
			System.out.println("[GUI] Open draw screen");
		}
	}

	/**
	 * This method close the painter window and returns the picture as a Picture.
	 *
	 * @return The picture drawn by the user or null, if the user is not drawing
	 * @see Picture
	 */
	public Picture closeDrawingScreen() {
		if (drawingScreen != null) {
			// TODO add endSession into new JPanel
			Picture result = drawingScreen.getPicture();
			drawingScreen.setVisible(false);
			getContentPane().removeAll();
			active = false;
			System.out.println("[GUI] Close draw screen");
			return result;
		}
		return null;
	}

	/**
	 * This method sets the players taking place and their score, if available. This
	 * image is the image, which content the player will guess.
	 *
	 * @param playerList
	 *                       the player list.If the value equals null, nothing will
	 *                       be done.
	 * @param scoreList
	 *                       the score list. The score of player at position i is
	 *                       the object in the scorelist at this position
	 */
	public void openScoreScreen(ArrayList<String> playerList, ArrayList<String> scoreList) {
		if (!active) {
			active = true;
			scoreScreen = new ScoreScreen(playerList, scoreList, this);
			getContentPane().add(scoreScreen);
			validate();
			active = true;
			System.out.println("[GUI] Open results Window");
		}
	}

	/**
	 * This method open the waiting room in the current window when
	 * 
	 * @param username
	 */
	public void openWaitingScreen(String username) {
		if (!active) {
			active = true;
			waitingScreen = new WaitingScreen(username);
			getContentPane().add(waitingScreen);
			validate();
			System.out.println("[GUI] Open waiting screen");
		}
	}

	/**
	 * this method closes the waiting room, if one is active
	 */
	public void closeWaitingScreen() {
		if (waitingScreen != null) {
			getContentPane().removeAll();
			active = false;
			System.out.println("[GUI] Close waiting screen");
		}
	}

	/**
	 * This method opens the menu to a new Guess. This image is the image, which
	 * content the player will guess.
	 *
	 * @param picture
	 *                    the picture to be shown
	 * @param time
	 *                    the period of time the player has to draw his image
	 * @see Picture
	 */
	public void openGuessingScreen(Picture picture, int time) {
		if (!active) {
			active = true;
			guessScreen = new GuessScreen(picture, time, guessCallback);
			getContentPane().add(guessScreen);
			validate();
			active = true;
			System.out.println("[GUI] Open guess window");
		}
	}

	/**
	 * this method gives feedback to given guesses
	 * 
	 * @param status
	 *                     true if the answer is correct false if it is wrong
	 * @param feedback
	 *                     a hint if their is something
	 */
	public void setGuessFeedback(boolean status, String feedback) {
		if (active && guessScreen != null) {
			guessScreen.setGuessFeedback(status, feedback);
			System.out.println("Correct :" + status + ";Hint: " + feedback);
		}
	}

	/**
	 * the method ends the current guess period.
	 *
	 * @return the guess as String or null, if there is no current guess
	 * @see Picture
	 */
	public String closeGuessScreen() {
		if (guessScreen != null) {
			getContentPane().removeAll();
			String result = guessScreen.getGuess();
			active = false;
			System.out.println("[GUI] Close guess screen");
			return result;
		}
		return "";
	}

	public void setCallback(StringCallback callback) {
		this.guessCallback = callback;
	}

	public void setCorrectOnGuess(String drawer, String correctTitle) {
		if (guessScreen != null) {
			guessScreen.setCorrect(drawer, correctTitle);
		}
	}

	public static void main(String[] args) {
		new GUI();
	}
}
