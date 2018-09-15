package client.gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

import client.Picture;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class DrawingScreen extends JPanel {
	private PaintLabel drawLabel;
	private JPanel drawPanel;
	public DrawingScreen(String toDraw, int timeToDraw) {
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {80, 80, 80, 80, 300};
		gridBagLayout.rowHeights = new int[] {50, 50, 50, 50, 50, 50, 50, 50, 50};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
		
		JLabel guessingWordLabel = new JLabel(toDraw);
		guessingWordLabel.setFont(new Font("Dialog", Font.BOLD, 30));
		GridBagConstraints gbc_guessingWordLabel = new GridBagConstraints();
		gbc_guessingWordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_guessingWordLabel.gridwidth = 2;
		gbc_guessingWordLabel.gridx = 0;
		gbc_guessingWordLabel.gridy = 0;
		add(guessingWordLabel, gbc_guessingWordLabel);
		
		JLabel timerLabel = new JLabel();
		timerLabel.setFont(new Font("Dialog", Font.BOLD, 30));
		GridBagConstraints gbc_timerLabel = new GridBagConstraints();
		gbc_timerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_timerLabel.gridwidth = 2;
		gbc_timerLabel.gridx = 2;
		gbc_timerLabel.gridy = 0;
		add(timerLabel, gbc_timerLabel);
		
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			private int time = timeToDraw / 1000;
			@Override
			public void run() {
				timerLabel.setText(--time + "");
			}
		}, 1000, 1000);
		
		drawPanel = new JPanel();
		drawPanel.setBackground(Color.LIGHT_GRAY);
		drawPanel.setLayout(null);
		int newValue = 10;

		
		GridBagConstraints gbc_drawPanel = new GridBagConstraints();
		gbc_drawPanel.gridheight = 9;
		gbc_drawPanel.fill = GridBagConstraints.BOTH;
		gbc_drawPanel.gridx = 4;
		gbc_drawPanel.gridy = 0;
		add(drawPanel, gbc_drawPanel);
		
		drawLabel = new PaintLabel();
		drawLabel.setBounds(95, 5, 0, 0);
		drawPanel.add(drawLabel);
		float size = newValue / drawLabel.maxThickness();
		drawLabel.setSize(size);
		JButton greenColorButton = new JButton("Green");
		greenColorButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		greenColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawLabel.setColor(3);
			}
		});
		greenColorButton.setBackground(Color.GREEN);
		greenColorButton.setForeground(Color.BLACK);
		GridBagConstraints gbc_greenColorButton = new GridBagConstraints();
		gbc_greenColorButton.fill = GridBagConstraints.BOTH;
		gbc_greenColorButton.gridwidth = 2;
		gbc_greenColorButton.insets = new Insets(0, 0, 5, 5);
		gbc_greenColorButton.gridx = 0;
		gbc_greenColorButton.gridy = 1;
		add(greenColorButton, gbc_greenColorButton);
		
		JButton redColorButton = new JButton("Red");
		redColorButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		redColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawLabel.setColor(4);
			}
		});
		redColorButton.setBackground(Color.RED);
		GridBagConstraints gbc_redColorButton = new GridBagConstraints();
		gbc_redColorButton.fill = GridBagConstraints.BOTH;
		gbc_redColorButton.insets = new Insets(0, 0, 5, 5);
		gbc_redColorButton.gridwidth = 2;
		gbc_redColorButton.gridx = 2;
		gbc_redColorButton.gridy = 1;
		add(redColorButton, gbc_redColorButton);
		
		JButton yellowColorButton = new JButton("Yellow");
		yellowColorButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		yellowColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawLabel.setColor(5);
			}
		});
		yellowColorButton.setBackground(Color.YELLOW);
		GridBagConstraints gbc_yellowColorButton = new GridBagConstraints();
		gbc_yellowColorButton.fill = GridBagConstraints.BOTH;
		gbc_yellowColorButton.gridwidth = 2;
		gbc_yellowColorButton.insets = new Insets(0, 0, 5, 5);
		gbc_yellowColorButton.gridx = 0;
		gbc_yellowColorButton.gridy = 2;
		add(yellowColorButton, gbc_yellowColorButton);
		
		JButton blueColorButton = new JButton("Blue");
		blueColorButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		blueColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawLabel.setColor(2);
			}
		});
		blueColorButton.setBackground(Color.BLUE);
		GridBagConstraints gbc_blueColorButton = new GridBagConstraints();
		gbc_blueColorButton.insets = new Insets(0, 0, 5, 5);
		gbc_blueColorButton.fill = GridBagConstraints.BOTH;
		gbc_blueColorButton.gridwidth = 2;
		gbc_blueColorButton.gridx = 2;
		gbc_blueColorButton.gridy = 2;
		add(blueColorButton, gbc_blueColorButton);
		
		JButton brownColorButton = new JButton("Brown");
		brownColorButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		brownColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawLabel.setColor(6);
			}
		});
		brownColorButton.setBackground(Color.ORANGE);
		GridBagConstraints gbc_brownColorButton = new GridBagConstraints();
		gbc_brownColorButton.fill = GridBagConstraints.BOTH;
		gbc_brownColorButton.gridwidth = 2;
		gbc_brownColorButton.insets = new Insets(0, 0, 5, 5);
		gbc_brownColorButton.gridx = 0;
		gbc_brownColorButton.gridy = 3;
		add(brownColorButton, gbc_brownColorButton);
		
		JButton purpleColorButton = new JButton("Purple");
		purpleColorButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		purpleColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawLabel.setColor(7);
			}
		});
		purpleColorButton.setBackground(Color.MAGENTA);
		GridBagConstraints gbc_purpleColorButton = new GridBagConstraints();
		gbc_purpleColorButton.insets = new Insets(0, 0, 5, 5);
		gbc_purpleColorButton.fill = GridBagConstraints.BOTH;
		gbc_purpleColorButton.gridwidth = 2;
		gbc_purpleColorButton.gridx = 2;
		gbc_purpleColorButton.gridy = 3;
		add(purpleColorButton, gbc_purpleColorButton);
		
		JButton blackColorButton = new JButton("Black");
		blackColorButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		blackColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawLabel.setColor(1);
			}
		});
		blackColorButton.setForeground(Color.WHITE);
		blackColorButton.setBackground(Color.BLACK);
		GridBagConstraints gbc_blackColorButton = new GridBagConstraints();
		gbc_blackColorButton.gridwidth = 2;
		gbc_blackColorButton.fill = GridBagConstraints.BOTH;
		gbc_blackColorButton.insets = new Insets(0, 0, 5, 5);
		gbc_blackColorButton.gridx = 0;
		gbc_blackColorButton.gridy = 4;
		add(blackColorButton, gbc_blackColorButton);
		
		JButton whiteColorButton = new JButton("White");
		whiteColorButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		whiteColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawLabel.setColor(0);
			}
		});
		whiteColorButton.setBackground(Color.WHITE);
		GridBagConstraints gbc_whiteColorButton = new GridBagConstraints();
		gbc_whiteColorButton.insets = new Insets(0, 0, 5, 5);
		gbc_whiteColorButton.gridwidth = 2;
		gbc_whiteColorButton.fill = GridBagConstraints.BOTH;
		gbc_whiteColorButton.gridx = 2;
		gbc_whiteColorButton.gridy = 4;
		add(whiteColorButton, gbc_whiteColorButton);
		
		JRadioButton drawRadioButton = new JRadioButton("draw Mode");
		drawRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_drawRadioButton = new GridBagConstraints();
		gbc_drawRadioButton.fill = GridBagConstraints.BOTH;
		gbc_drawRadioButton.gridwidth = 2;
		gbc_drawRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_drawRadioButton.gridx = 0;
		gbc_drawRadioButton.gridy = 5;
		add(drawRadioButton, gbc_drawRadioButton);
		
		JLabel thicknessLabel = new JLabel("Thickness:");
		thicknessLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_thicknessLabel = new GridBagConstraints();
		gbc_thicknessLabel.gridwidth = 2;
		gbc_thicknessLabel.insets = new Insets(0, 0, 5, 5);
		gbc_thicknessLabel.gridx = 2;
		gbc_thicknessLabel.gridy = 5;
		add(thicknessLabel, gbc_thicknessLabel);
		
		JSlider drawSizeSlider = new JSlider();
		drawSizeSlider.setFont(new Font("Tahoma", Font.PLAIN, 25));
		drawSizeSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int newValue = drawSizeSlider.getValue();
				float size = newValue / drawLabel.maxThickness();
				drawLabel.setSize(size);
			}
		});
		drawSizeSlider.setMajorTickSpacing(10);
		drawSizeSlider.setMinorTickSpacing(5);
		drawSizeSlider.setToolTipText("Thickness");
		drawSizeSlider.setValue(10);
		drawSizeSlider.setSnapToTicks(true);
		drawSizeSlider.setPaintTicks(true);
		drawSizeSlider.setPaintLabels(true);
		drawSizeSlider.setMaximum(50);
		GridBagConstraints gbc_drawSizeSlider = new GridBagConstraints();
		gbc_drawSizeSlider.fill = GridBagConstraints.BOTH;
		gbc_drawSizeSlider.gridheight = 2;
		gbc_drawSizeSlider.gridwidth = 2;
		gbc_drawSizeSlider.insets = new Insets(0, 0, 5, 5);
		gbc_drawSizeSlider.gridx = 2;
		gbc_drawSizeSlider.gridy = 6;
		add(drawSizeSlider, gbc_drawSizeSlider);
		
		JButton backButton = new JButton("back");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawLabel.resetLastLines();
			}
		});
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.insets = new Insets(0, 0, 0, 5);
		gbc_backButton.gridwidth = 2;
		gbc_backButton.fill = GridBagConstraints.BOTH;
		gbc_backButton.gridx = 2;
		gbc_backButton.gridy = 8;
		add(backButton, gbc_backButton);
		
		JButton clearAllButton = new JButton("clear All");
		clearAllButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		clearAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawLabel.resetAllLines();
			}
		});
		GridBagConstraints gbc_clearAllButton = new GridBagConstraints();
		gbc_clearAllButton.fill = GridBagConstraints.BOTH;
		gbc_clearAllButton.gridwidth = 2;
		gbc_clearAllButton.insets = new Insets(0, 0, 0, 5);
		gbc_clearAllButton.gridx = 0;
		gbc_clearAllButton.gridy = 8;
		add(clearAllButton, gbc_clearAllButton);
		
		JRadioButton fillRadioButton = new JRadioButton("fill Mode");
		fillRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_fillRadioButton = new GridBagConstraints();
		gbc_fillRadioButton.gridwidth = 2;
		gbc_fillRadioButton.fill = GridBagConstraints.BOTH;
		gbc_fillRadioButton.anchor = GridBagConstraints.WEST;
		gbc_fillRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_fillRadioButton.gridx = 0;
		gbc_fillRadioButton.gridy = 7;
		add(fillRadioButton, gbc_fillRadioButton);
		
		JRadioButton lineRadioButton = new JRadioButton("line Mode");
		lineRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 25));
		GridBagConstraints gbc_lineRadioButton = new GridBagConstraints();
		gbc_lineRadioButton.fill = GridBagConstraints.BOTH;
		gbc_lineRadioButton.gridwidth = 2;
		gbc_lineRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_lineRadioButton.gridx = 0;
		gbc_lineRadioButton.gridy = 6;
		add(lineRadioButton, gbc_lineRadioButton);
		

		drawPanel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				int size = drawPanel.getWidth();
				if (size > drawPanel.getHeight()) {
					size = drawPanel.getHeight();
				}
				drawLabel.setBounds(0, 0, size, size);
			}
		});
		
		drawRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawRadioButton.setSelected(true);
				fillRadioButton.setSelected(false);
				lineRadioButton.setSelected(false);
				drawLabel.changeMode(PaintLabel.MODE_DRAW);
			}
		});
		fillRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawRadioButton.setSelected(false);
				fillRadioButton.setSelected(true);
				lineRadioButton.setSelected(false);
				drawLabel.changeMode(PaintLabel.MODE_FILL);
			}
		});
		lineRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawRadioButton.setSelected(false);
				fillRadioButton.setSelected(false);
				lineRadioButton.setSelected(true);
				drawLabel.changeMode(PaintLabel.MODE_LINE);
			}
		});
		drawRadioButton.setSelected(true);
	}

	public Picture getPicture() {
		return drawLabel.getPicture();
	}

}
