package client.gui;

import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JList;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ScoreScreen extends JPanel {
	public ScoreScreen(ArrayList<String> playerList, ArrayList<String> scoreList, GUI gui) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0, 0};
		gridBagLayout.rowHeights = new int[]{15, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel userLabel = new JLabel("Users");
		userLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		GridBagConstraints gbc_userLabel = new GridBagConstraints();
		gbc_userLabel.insets = new Insets(0, 0, 5, 5);
		gbc_userLabel.gridx = 0;
		gbc_userLabel.gridy = 0;
		add(userLabel, gbc_userLabel);
		
		JLabel pointsLabel = new JLabel("Points");
		pointsLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		GridBagConstraints gbc_pointsLabel = new GridBagConstraints();
		gbc_pointsLabel.insets = new Insets(0, 0, 5, 0);
		gbc_pointsLabel.gridx = 1;
		gbc_pointsLabel.gridy = 0;
		add(pointsLabel, gbc_pointsLabel);
		
		JList<String> usersList = new JList<String>();
		usersList.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_usersList = new GridBagConstraints();
		gbc_usersList.insets = new Insets(0, 0, 5, 5);
		gbc_usersList.fill = GridBagConstraints.BOTH;
		gbc_usersList.gridx = 0;
		gbc_usersList.gridy = 1;
		add(usersList, gbc_usersList);
		
		JList<String> pointsList = new JList<String>();
		pointsList.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_pointsList = new GridBagConstraints();
		gbc_pointsList.insets = new Insets(0, 0, 5, 0);
		gbc_pointsList.fill = GridBagConstraints.BOTH;
		gbc_pointsList.gridx = 1;
		gbc_pointsList.gridy = 1;
		add(pointsList, gbc_pointsList);
		
		DefaultListModel<String> userslm = new DefaultListModel<String>();
		for (int i = 0; i < playerList.size(); i++) {
			userslm.addElement(playerList.get(i));
		}
		usersList.setModel(userslm);
		
		//action Listeners
		usersList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				pointsList.setSelectedIndex(usersList.getSelectedIndex());
			}
		});
		
		pointsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				usersList.setSelectedIndex(pointsList.getSelectedIndex());
			}
		});
		
		
		DefaultListModel<String> pointslm = new DefaultListModel<String>();
		for (int i = 0; i < scoreList.size(); i++) {
			pointslm.addElement(scoreList.get(i));
		}
		pointsList.setModel(pointslm);
		
		JButton btnNewButton = new JButton("NEW GAME");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(() -> {
					new GUI();
				}).start();
				gui.dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 2;
		add(btnNewButton, gbc_btnNewButton);
	}

}
