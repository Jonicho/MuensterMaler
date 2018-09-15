package server.console;

import java.io.PrintStream;

import javax.swing.*;

public class ConsoleWindow extends JPanel {
	private static final long serialVersionUID = 1L;

	public ConsoleWindow() {

		JTextArea ta = new JTextArea(25, 65);
		ta.setFont(ta.getFont().deriveFont(30.0f));
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ConsoleOutputStream os = new ConsoleOutputStream(ta, "server");

		System.setOut(new PrintStream(os));

		JFrame frame = new JFrame("Server Console");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(ta);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}