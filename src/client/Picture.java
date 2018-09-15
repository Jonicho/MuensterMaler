package client;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Picture {
	private ArrayList<Line> lines = new ArrayList<Line>();
	private int backgroundColor = 0;

	/**
	 * Adds a line
	 * 
	 * @param l
	 */
	public void addLine(Line l) {
		lines.add(l);
	}

	/**
	 * Returns an unmodifiable list of lines representing this vector graphic
	 * 
	 * @return
	 */
	public List<Line> getLines() {
		return Collections.unmodifiableList(lines);
	}

	public void removeLastLines(int value) {
		for (int i = 0; i < value; i++) {
			lines.remove(lines.size() - 1);
		}
	}

	public void removeAllLines() {
		lines = new ArrayList<Line>();
	}

	/**
	 * Returns the background color as a color code
	 * 
	 * @return
	 */
	public int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Sets the background color code
	 * 
	 * @param backgroundColor
	 */
	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * Creates a vector graphic from a string
	 * 
	 * @param s
	 *              the string
	 * @return the vector graphic represented by the string
	 */
	public static Picture fromString(String s) {
		try {
			Picture result = new Picture();
			String[] lines = s.split("_");
			int backgroundColor = Integer.parseInt(lines[0]);
			result.setBackgroundColor(backgroundColor);
			for (int i = 1; i < lines.length; i++) {
				result.addLine(Line.fromString(lines[i]));
			}
			return result;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString() {
		String result = "" + backgroundColor;
		for (int i = 0; i < lines.size(); i++) {
			result += "_" + lines.get(i).toString();
		}
		return result;
	}

	/**
	 * Represents a line of a vector graphic with normalized coordinates (between 0
	 * and 1)
	 *
	 */
	public static class Line {
		private float x1;
		private float y1;
		private float x2;
		private float y2;
		private int color;
		private float thickness;

		/**
		 * Creates a new line from (x1,y1) to (x2,y2) All coordinates have to be between
		 * 0 and 1
		 * 
		 * @param x1
		 * @param y1
		 * @param x2
		 * @param y2
		 * 
		 * @throws IllegalArgumentException
		 *                                      if the coordinates are not between 0 and
		 *                                      1
		 */
		public Line(float x1, float y1, float x2, float y2, int color, float thickness) {
			if (x1 < 0 || x1 > 1 || y1 < 0 || y1 > 1 || x2 < 0 || x2 > 1 || y2 < 0 || y2 > 1) {

			} else {
				this.x1 = x1;
				this.y1 = y1;
				this.x2 = x2;
				this.y2 = y2;
				this.color = color;
				this.thickness = thickness;
			}
		}

		public float getX1() {
			return x1;
		}

		public float getX2() {
			return x2;
		}

		public float getY1() {
			return y1;
		}

		public float getY2() {
			return y2;
		}

		/**
		 * Returns the color of this line as a color code
		 * 
		 * @return
		 */
		public int getColor() {
			return color;
		}

		public float getThickness() {
			return thickness;
		}

		public static Line fromString(String s) {
			String[] sParts = s.split(";");
			float x1 = Float.parseFloat(sParts[0]);
			float y1 = Float.parseFloat(sParts[1]);
			float x2 = Float.parseFloat(sParts[2]);
			float y2 = Float.parseFloat(sParts[3]);
			int color = Integer.parseInt(sParts[4]);
			float thickness = Float.parseFloat(sParts[5]);
			return new Line(x1, y1, x2, y2, color, thickness);

		}

		@Override
		public String toString() {
			return x1 + ";" + y1 + ";" + x2 + ";" + y2 + ";" + color + ";" + thickness;
		}
	}

	/**
	 * Maps a color code to a awt color
	 * 
	 * @param colorCode
	 * @return the color
	 */
	public static Color colorCodeToColor(int colorCode) {
		switch (colorCode) {
		case 0:
			return Color.WHITE;
		case 1:
			return Color.BLACK;
		case 2:
			return Color.BLUE;
		case 3:
			return Color.GREEN;
		case 4:
			return Color.RED;
		case 5:
			return Color.YELLOW;
		case 6:
			return Color.ORANGE;
		case 7:
			return Color.MAGENTA;

		default:
			return null;
		}
	}
}
