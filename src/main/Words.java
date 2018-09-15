package main;

import java.io.*;
import java.util.*;

public class Words {

	private ArrayList<String[]> words = new ArrayList();
	private BufferedReader br = null;

	public Words() {
		initWordList();
	}

	public void initWordList() {
		try {
			// creating Reader
			br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("words.txt")));

			// variable s as buffer
			String s;

			// reading every line and adding it to the list
			while ((s = br.readLine()) != null) {

				String[] similarWords = s.split(",");

				for (int i = 0; i < similarWords.length; i++) {
					similarWords[i] = similarWords[i].toLowerCase();
				}

				words.add(similarWords);

			}

			// Catching Exceptions
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String[]> getWords(int count) {

		ArrayList<String[]> returnValue = new ArrayList();
		int length = words.size();
		Random rn = new Random();
		int range = length - 0;

		for (int i = 0; i < count; i++) {
			int random = rn.nextInt(range);

			if (!(returnValue.contains(words.get(random)))) {
				returnValue.add(words.get(random));
			} else {
				i--;
			}

		}

		return returnValue;
	}
}
