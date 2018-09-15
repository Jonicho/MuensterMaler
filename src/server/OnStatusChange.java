package server;

import java.util.ArrayList;

public interface OnStatusChange {

	public void sendMessage(String message, ArrayList<Player> players);

	public void closeConnection(ArrayList<Player> players);

}