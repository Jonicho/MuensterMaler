package server.console;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ConsoleOutputStream extends OutputStream {

	private final JTextArea textArea;
	private final StringBuilder sb = new StringBuilder();
	private String title;

	public ConsoleOutputStream(final JTextArea textArea, String title) {
		this.textArea = textArea;
		this.title = title;
		sb.append(title + "> ");
	}

	public void flush() {
	}

	public void close() {
	}

	public void write(int b) throws IOException {

		if (b == '\r')
			return;

		if (b == '\n') {
			final String text = sb.toString() + "\n";
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					textArea.setText(text + textArea.getText());
				}
			});
			sb.setLength(0);
			sb.append(title + "> ");
			return;
		}

		sb.append((char) b);
	}
}