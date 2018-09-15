package client.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import client.Picture;
import client.Picture.Line;

public class PaintLabel extends JLabel implements MouseMotionListener,MouseListener {
	
	public static final int CODE_WHITE = 0;
	public static final int CODE_BLACK = 1;
	public static final int CODE_BLUE = 2;
	public static final int CODE_GREEN = 3;
	public static final int CODE_RED = 4;
	public static final int CODE_YELLOW = 5;
	public static final int MODE_DRAW = 0;
	public static final int MODE_LINE = 1;
	public static final int MODE_FILL = 2;
	private static final long serialVersionUID = 1L;

	private static final float minDistance = 0.01f;
	private final Color background = Color.WHITE;
	private boolean paintingAllowed = true;
	private Picture picture;
	private int color = 1;
	private float size = (float) 0.002;
	private int paintMode = MODE_DRAW;
	private ArrayList<Integer> lastLines = new ArrayList<Integer>();

	private float lastX = -1, lastY = -1;

	public PaintLabel() {
		this.setOpaque(true);
		setBackground(background);
		picture = new Picture();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if (picture != null) {
			this.setBackground(Picture.colorCodeToColor(picture.getBackgroundColor()));
			// For every object in the list paint a line from Point 1 to Point 2
			List<Line> vectorList = picture.getLines();
			Iterator<Line> iterator = vectorList.iterator();
			while (iterator.hasNext()) {
				Line currentLine = iterator.next();
				g.setColor(Picture.colorCodeToColor(currentLine.getColor()));
				int x1 = (int) (currentLine.getX1() * getWidth());
				int y1 = (int) (currentLine.getY1() * getHeight());
				int x2 = (int) (currentLine.getX2() * getWidth());
				int y2 = (int) (currentLine.getY2() * getHeight());
				int size = (int) (currentLine.getThickness() * getHeight());
				g2d.setStroke(new BasicStroke(size));
				g2d.drawLine(x1, y1, x2, y2);
			}
		}

	}

	public void setPaintingAllowed(boolean allowed) {
		this.paintingAllowed = allowed;
	}

	public void addPoint(int x, int y) {
		if(paintingAllowed) {
			if (lastX == -1 && lastY == -1) {
				lastX = (float) x / getWidth();
				lastY = (float) y / getHeight();
				lastLines.add(lastLines.size(), 0);
			} else {
	
				float floatX = (float) x / getWidth();
				float floatY = (float) y / getHeight();
				if (Math.sqrt(Math.pow(floatX - lastX, 2) + Math.pow(floatY - lastY, 2)) > minDistance) {
					picture.addLine(new Line(floatX, floatY, this.lastX, this.lastY, color, size));
					lastX = floatX;
					lastY = floatY;
					int i = lastLines.get(lastLines.size()-1);
					lastLines.set(lastLines.size()-1, i++);
					repaint();
				}
			}
		}
	}
	public void addStaticPoint(int x,int y) {
		if(paintingAllowed) {
			if (lastX == -1 && lastY == -1) {
				lastX = (float) x / getWidth();
				lastY = (float) y / getHeight();
				lastLines.add(lastLines.size(), 0);
			} else {
	
				float floatX = (float) x / getWidth();
				float floatY = (float) y / getHeight();
				
					picture.addLine(new Line(floatX, floatY, this.lastX, this.lastY, color, size));
					lastX = floatX;
					lastY = floatY;
					int i = lastLines.get(lastLines.size()-1);
					lastLines.set(lastLines.size()-1, i++);
					repaint();
				
			}
		}
	}
	public void addRelativePoint(float x,float y) {
		if(paintingAllowed) {
			if (lastX == -1 && lastY == -1) {
				lastX = x;
				lastY = y;
				lastLines.add(lastLines.size(), 0);
			} else {
	
				picture.addLine(new Line(x, y, this.lastX, this.lastY, color, size));
				lastX = x;
				lastY = y;
				int i = lastLines.get(lastLines.size()-1);
				lastLines.set(lastLines.size()-1, i++);
				System.out.println(lastLines.get(lastLines.size()));
				repaint();
			}
		}
	}
	
	public void resetLastLines() {
		if(lastLines.size() == 0) {
			picture.removeLastLines(lastLines.get(lastLines.size()-1));
			lastLines.remove(lastLines.size()-1);
		}
		repaint();
	}
	
	public void resetAllLines() {
		picture.removeAllLines();
		repaint();
	}


	public void setBackground() {
		if(paintingAllowed) {
			picture.setBackgroundColor(color);
			repaint();
		}
	}
	

	public void setColor(int colorCode) {
		this.color = colorCode;
	}

	public void resetLast() {
		lastX = -1;
		lastY = -1;
	}

	public int getColor() {
		return color;
	}
	/**
	 * Returns the current picture
	 * @return the picture
	 */
	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
		repaint();
	}


	public float minThickness() {
		return 1 / getHeight();
	}

	public float maxThickness() {
		return this.getHeight();
	}
	
	public float defaultThickness() {
		return minThickness();
	}
	/**
	 * Sets the size of the lines, that will be drawed from now
	 * @param size the relative size
	 */
	public void setSize(float size) {
		if (size > 0 && size < 1) {
			this.size = size;
		}
	}
	/**
	 * Returns the picture as a string
	 * @return the picture converted into a string with toString() in Picture
	 * @see Picture
	 */
	public String toString() {
		return picture.toString();
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		if (paintMode == PaintLabel.MODE_DRAW)
			addPoint(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//nothing here

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (paintMode == PaintLabel.MODE_LINE)
			addPoint(arg0.getX(), arg0.getY());
		else if (paintMode == PaintLabel.MODE_FILL)
			setBackground();
		else if (paintMode == PaintLabel.MODE_DRAW) {
			resetLast();
			addStaticPoint(arg0.getX(),arg0.getY());
			addStaticPoint(arg0.getX()+5,arg0.getY()+5);
		}
	}
	


	@Override
	public void mouseEntered(MouseEvent arg0) {
		//nothing here
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		resetLast();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		//nothing here

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (paintMode == PaintLabel.MODE_DRAW)
			resetLast();

	}
	/**
	 * this methode changes the mode, this label is drawing. Your can choose between the mode
	 * @param mode_code
	 */
	public void changeMode(int mode_code) {
		switch (mode_code) {

		case PaintLabel.MODE_DRAW:
			paintMode = mode_code;
			break;

		case PaintLabel.MODE_LINE:
			paintMode = mode_code;
			break;

		case PaintLabel.MODE_FILL:
			paintMode = mode_code;
			break;

		}
		resetLast();
	}
}
