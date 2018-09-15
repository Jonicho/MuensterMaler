package main;

public class Player {

	private String name;
	private int points;
	private String ip;
	private int port;

	// Creates new player without any points.
	public Player(String pIp, int pPort) {
		points = 0;
		ip = pIp;
		port = pPort;

	}

	// Getter/Setter.
	public void setName(String pName) {
		name = pName;
	}

	public String getName() {
		return name;
	}

	public int getPoints() {
		return points;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public void addPoints(int pPoints) {
		points += pPoints;
	}
}
