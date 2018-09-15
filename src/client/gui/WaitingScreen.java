package client.gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Timer;
import java.util.TimerTask;

import java.awt.Font;

public class WaitingScreen extends JPanel {
	private static final long serialVersionUID = 1L;

	public WaitingScreen(String username) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 30, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 10, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0 };
		setLayout(gridBagLayout);

		JLabel waitMessageLabel = new JLabel("waiting...");
		waitMessageLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_waitMessageLabel = new GridBagConstraints();
		gbc_waitMessageLabel.gridwidth = 2;
		gbc_waitMessageLabel.insets = new Insets(0, 0, 5, 5);
		gbc_waitMessageLabel.gridx = 0;
		gbc_waitMessageLabel.gridy = 0;
		add(waitMessageLabel, gbc_waitMessageLabel);

		JLabel timerLabel = new JLabel("0");
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			private int time = 0;

			@Override
			public void run() {
				timerLabel.setText(++time + "");
			}
		}, 1000, 1000);
		timerLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_timerLabel = new GridBagConstraints();
		gbc_timerLabel.insets = new Insets(0, 0, 5, 0);
		gbc_timerLabel.gridx = 2;
		gbc_timerLabel.gridy = 0;
		add(timerLabel, gbc_timerLabel);

		JLabel userNameLabel = new JLabel("Your username: " + username);
		userNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_userNameLabel = new GridBagConstraints();
		gbc_userNameLabel.insets = new Insets(0, 0, 5, 0);
		gbc_userNameLabel.gridwidth = 3;
		gbc_userNameLabel.gridx = 0;
		gbc_userNameLabel.gridy = 1;
		add(userNameLabel, gbc_userNameLabel);
	}

}
