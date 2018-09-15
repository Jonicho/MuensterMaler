package protocol;

public class PROTOCOL {
	// SERVER - CLIENT PROTOCOL

	// Client accepted
	public static final String SC_CLIENT_ACCEPTED = "S0";

	// Start of drawing phase; including word
	public static final String SC_SEND_WORD = "S1";
	// End of drawing phase; client must send drawing string to server
	public static final String SC_DRAWING_PHASE_END = "S2";
	// Start of guessing phase
	public static final String SC_START_GUESSING = "S3";

	// start of guessing; clients gets drawing from server
	public static final String SC_SEND_DRAWING_FOR_GUESSING = "G0";

	// end of guessing; server awaits answer from client
	public static final String SC_SEND_ANSWER = "G1";

	// guess feedback
	public static final String SC_GUESS_FEEDBACK = "G2";

	// guess feedback
	public static final String SC_RIGHT_GUESS = "G3";

	// end of guessing phase; no need to listen for G0; G1
	public static final String SC_END_GUESSING = "S4";
	// honoring of the winners; including score data and player names
	public static final String SC_SEND_WINNERS = "S5";

	// CLIENT - SERVER PROTOCOL

	// client requests allowness to send username
	public static final String CS_REQUEST_USERNAME = "R1";

	// Client sends username as answer to being accepted
	public static final String CS_USERNAME = "S0A";

	// Client sends drawing after drawing phase
	public static final String CS_SEND_DRAWING = "S2A";

	// Client sends guess to server
	public static final String CS_SEND_GUESS = "G1A";

	// TRENNER
	public static final String TRENNER = ":";

	public static String getTrenner() {
		return TRENNER;
	}

}