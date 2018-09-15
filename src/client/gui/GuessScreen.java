package client.gui;

import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;

import client.Picture;
import client.StringCallback;

import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class GuessScreen extends JPanel implements KeyListener {
	private JLabel feedbackLabel;
	private JTextField guessTextField;
	private StringCallback guessCallback;
	public GuessScreen(Picture picture, int timeToGuess, StringCallback guessCallback) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {225, 0};
		gridBagLayout.rowHeights = new int[] {100, 0, 0, 0, 10};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(null);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		
		PaintLabel paintLabel = new PaintLabel();
		paintLabel.setBounds(0, 0, 450, 184);
		paintLabel.setPicture(picture);
		paintLabel.setPaintingAllowed(false);
		panel.add(paintLabel);
		paintLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton guessButton = new JButton("Guess");
		guessButton.setFont(new Font("Tahoma", Font.PLAIN, 40));
		guessButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guessCallback.fire(guessTextField.getText());
			}
		});
		GridBagConstraints gbc_guessButton = new GridBagConstraints();
		gbc_guessButton.insets = new Insets(0, 0, 5, 0);
		gbc_guessButton.fill = GridBagConstraints.VERTICAL;
		gbc_guessButton.gridx = 1;
		gbc_guessButton.gridy = 1;
		add(guessButton, gbc_guessButton);
		
		guessTextField = new JTextField();
		guessTextField.setFont(new Font("Tahoma", Font.PLAIN, 40));
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		add(guessTextField, gbc_textField);
		guessTextField.setColumns(10);
		
		feedbackLabel = new JLabel("Feedback");
		feedbackLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		GridBagConstraints gbc_feedbackLabel = new GridBagConstraints();
		gbc_feedbackLabel.insets = new Insets(0, 0, 5, 5);
		gbc_feedbackLabel.gridx = 0;
		gbc_feedbackLabel.gridy = 2;
		add(feedbackLabel, gbc_feedbackLabel);
		
		JLabel timerLabel = new JLabel("99");

		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			private int time = timeToGuess / 1000;
			@Override
			public void run() {
				timerLabel.setText(--time + "");
			}
		}, 1000, 1000);
		
		timerLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		GridBagConstraints gbc_timerLabel = new GridBagConstraints();
		gbc_timerLabel.insets = new Insets(0, 0, 5, 0);
		gbc_timerLabel.gridx = 1;
		gbc_timerLabel.gridy = 2;
		add(timerLabel, gbc_timerLabel);
		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int size = panel.getWidth();
				if (size > panel.getHeight()) {
					size = panel.getHeight();
				}
				paintLabel.setBounds(0, 0, size, size);
			}
		});
		
		this.addKeyListener(this);
		guessTextField.addKeyListener(this);
	}
	
	public void setGuessFeedback(boolean status, String feedback) {
		if (status) {
			feedbackLabel.setText("Correct");
		} else {
			feedbackLabel.setText(feedback == null ? "Wrong answer": feedback);
			if(feedback==null) {
				guessTextField.setText("");
			}
			}
	}
	
	public void setCorrect(String drawer, String correctTitle) {
		System.out.println(drawer+";"+correctTitle+"@ GuessScreen");
		feedbackLabel.setText("Drawn by " + drawer + ". Correct word: " + correctTitle);
	}

	public String getGuess() {
		return guessTextField.getText();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode()==KeyEvent.VK_ENTER) {
			guessCallback.fire(guessTextField.getText());
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
