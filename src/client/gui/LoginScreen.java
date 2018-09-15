package client.gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

import client.GameClient;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.awt.event.ActionEvent;

public class LoginScreen extends JPanel implements KeyListener {
	private JTextField ipTextField;
	private JTextField postTextField;
	private JTextField usernameTextField;
	private GUI gui;
	private String lastNameEntry, lastIpEntry, lastPortEntry;
	private BufferedReader reader;
	private BufferedWriter writer;
	private FileWriter fw;

	public LoginScreen(GUI gui) {
		this.gui = gui;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 30, 0, 0, 30 };
		gridBagLayout.rowHeights = new int[] { 30, 10, 10, 10, 30 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0 };
		setLayout(gridBagLayout);

		JLabel muensterMalerLabel = new JLabel("M\u00fcnster Maler");
		muensterMalerLabel.setFont(new Font("Dialog", Font.BOLD, 35));
		GridBagConstraints gbc_muensterMalerLabel = new GridBagConstraints();
		gbc_muensterMalerLabel.gridwidth = 2;
		gbc_muensterMalerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_muensterMalerLabel.gridx = 1;
		gbc_muensterMalerLabel.gridy = 0;
		add(muensterMalerLabel, gbc_muensterMalerLabel);

		JLabel ipLabel = new JLabel("IP");
		ipLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_ipLabel = new GridBagConstraints();
		gbc_ipLabel.anchor = GridBagConstraints.EAST;
		gbc_ipLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ipLabel.gridx = 1;
		gbc_ipLabel.gridy = 1;
		add(ipLabel, gbc_ipLabel);

		ipTextField = new JTextField();
		ipTextField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_ipTextField = new GridBagConstraints();
		gbc_ipTextField.insets = new Insets(0, 0, 5, 5);
		gbc_ipTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_ipTextField.gridx = 2;
		gbc_ipTextField.gridy = 1;
		add(ipTextField, gbc_ipTextField);
		ipTextField.setColumns(10);

		JLabel portLabel = new JLabel("Port");
		portLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_portLabel = new GridBagConstraints();
		gbc_portLabel.anchor = GridBagConstraints.EAST;
		gbc_portLabel.insets = new Insets(0, 0, 5, 5);
		gbc_portLabel.gridx = 1;
		gbc_portLabel.gridy = 2;
		add(portLabel, gbc_portLabel);

		postTextField = new JTextField();
		postTextField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_postTextField = new GridBagConstraints();
		gbc_postTextField.insets = new Insets(0, 0, 5, 5);
		gbc_postTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_postTextField.gridx = 2;
		gbc_postTextField.gridy = 2;
		add(postTextField, gbc_postTextField);
		postTextField.setColumns(10);

		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_usernameLabel = new GridBagConstraints();
		gbc_usernameLabel.anchor = GridBagConstraints.EAST;
		gbc_usernameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_usernameLabel.gridx = 1;
		gbc_usernameLabel.gridy = 3;
		add(usernameLabel, gbc_usernameLabel);

		usernameTextField = new JTextField();
		usernameTextField.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_usernameTextField = new GridBagConstraints();
		gbc_usernameTextField.insets = new Insets(0, 0, 5, 5);
		gbc_usernameTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_usernameTextField.gridx = 2;
		gbc_usernameTextField.gridy = 3;
		add(usernameTextField, gbc_usernameTextField);
		usernameTextField.setColumns(10);

		// keyListener
		this.addKeyListener(this);
		usernameTextField.addKeyListener(this);
		postTextField.addKeyListener(this);
		ipTextField.addKeyListener(this);

		// textFiles
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream("lastLogin.txt"));
			reader = new BufferedReader(inputStreamReader);
			ipTextField.setText(reader.readLine());
			postTextField.setText(reader.readLine());
			usernameTextField.setText(reader.readLine());
		} catch (Exception e) {
			System.out.println("konnte Datei last login nicht �ffnen");
		}

		JButton connectButton = new JButton("Connect");
		connectButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		GridBagConstraints gbc_connectButton = new GridBagConstraints();
		gbc_connectButton.gridwidth = 2;
		gbc_connectButton.insets = new Insets(0, 0, 0, 5);
		gbc_connectButton.gridx = 1;
		gbc_connectButton.gridy = 4;
		add(connectButton, gbc_connectButton);
	}

	public void connect() {
		int port;
		try {
			port = Integer.parseInt(postTextField.getText());
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
			return;
		}
		new Thread(() -> {
			try {
				new GameClient(ipTextField.getText(), port, usernameTextField.getText(), 1000, gui);
			} catch (SocketTimeoutException e1) {
				e1.printStackTrace();
			}
		}).start();

		lastIpEntry = ipTextField.getText();
		lastPortEntry = postTextField.getText();
		lastNameEntry = usernameTextField.getText();
		try {
			fw = new FileWriter("lastLogin.txt");
			writer = new BufferedWriter(fw);
			writer.write(lastIpEntry);
			writer.newLine();
			writer.write(lastPortEntry);
			writer.newLine();
			writer.write(lastNameEntry);
			writer.close();
		} catch (IOException e) {
			System.out.println("konnte Datei zum beschreiben nicht �ffnen");
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			connect();
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
