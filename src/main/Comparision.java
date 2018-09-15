package main;

public class Comparision {
	public Comparision() {

	}

	// Checks whether the given guess is a valid synonym to the given word
	public String isSynonym(String words[], String guess) {
		for (String word : words) {
			if (word.equals(guess)) {
				//System.out.println("Guess \'" + guess + "\' is synonym to \'" + words[0] + "\'");
				return "false:Falsches Wort, ähnliche Bedeutung!";
			}
		}
		return "false";
	}

	// Uses 2 substrings. A front and back one. Front one gets bigger, back one
	// smaller. Then always check if front + 1 char + back equals a valid word or
	// synonym
	public String missingLetter(String words[], String guess) {
		for (String word : words) {
			if (Math.abs(word.length() - guess.length()) <= 1) {
				for (int i = 0; i < word.length(); i++) {
					String guessSub1, guessSub2, wordSub1, wordSub2;
					guessSub1 = guess.substring(0, i);
					guessSub2 = guess.substring(i, guess.length());
					wordSub1 = word.substring(0, i);
					wordSub2 = word.substring(i + 1, word.length());
					////System.out.println(guessSub1 + "+" + guessSub2 + "; " + wordSub1 + "+" +
					// wordSub2);
					if (guessSub1.equals(wordSub1) && guessSub2.equals(wordSub2)) {
						//System.out.println("Guess \'" + guess + "\' lacks the letter \'" + word.charAt(i)
						//		+ "\' to be the word \'" + word + "\'");
						return "false:Knapp dran! Ein Buchstabe fehlt!";
					}
				}
			}
		}
		return "false";
	}

	// Uses 2 substrings. A front and back one. Front one gets bigger, back one
	// smaller. It always leaves out one char between them. Then check if front +
	// back equals a valid word or
	// synonym
	public String additionalLetter(String words[], String guess) {
		for (String word : words) {
			if (Math.abs(word.length() - guess.length()) <= 1) {
				for (int i = 0; i < guess.length(); i++) {
					String guessSub1, guessSub2, wordSub1, wordSub2;
					guessSub1 = guess.substring(0, i);
					guessSub2 = guess.substring(i + 1, guess.length());
					////System.out.println(guessSub1 + "+" + guessSub2);
					wordSub1 = word.substring(0, i);
					wordSub2 = word.substring(i, word.length());
					////System.out.println(guessSub1 + "+" + guessSub2 + "; " + wordSub1 + "+" +
					// wordSub2);
					if (guessSub1.equals(wordSub1) && guessSub2.equals(wordSub2)) {
						//System.out.println("Guess \'" + guess + "' has to lose the letter \'" + guess.charAt(i)
						//		+ "\' to be the word \'" + word + "\'");
						return "false:Knapp dran! Ein Buchstabe zu viel!";
					}
				}
			}
		}
		return "false";
	}

	// Always swaps 2 letters next to each other. Then checks if that word is a
	// valid word or a valid word with one additional/missing letter.
	public String swappedLetters(String words[], String guess) {

		for (int i = 0; i < guess.length() - 1; i++) {
			String res = guess.substring(0, i) + guess.charAt(i + 1) + guess.charAt(i)
					+ guess.substring(i + 2, guess.length());
			if (res.equals(words[0])) {
				//System.out.println("Guess \'" + guess + "\' has 2 letters swapped compared to \'" + words[0] + "\'");
				return "false:Buchstaben vertauscht!";
			}

			////System.out.println("no just swap");

			if (!additionalLetter(words, res).equals("false")) {
				//System.out.println("Guess \'" + guess + "\' has 2 letters swapped compared to \'" + words[0] + "\'");
				return additionalLetter(words, res);
			}

			////System.out.println("no additional");

			if (!missingLetter(words, res).equals("false")) {
				//System.out.println("Guess \'" + guess + "\' has 2 letters swapped compared to \'" + words[0] + "\'");
				return missingLetter(words, res);
			}

			////System.out.println("no missing");

			if (!isSynonym(words, res).equals("false")) {
				//System.out.println("Guess \'" + guess + "\' has 2 letters swapped compared to \'" + words[0] + "\'");
				return isSynonym(words, res);
			}
			////System.out.println("no synonym");
		}
		return "false";
	}

	public String wrongLetter(String words[], String guess) {
		for (String word : words) {
			if (Math.abs(word.length() - guess.length()) == 0) {
				for (int i = 0; i < guess.length(); i++) {
					String guessSub1, guessSub2, wordSub1, wordSub2;
					guessSub1 = guess.substring(0, i);
					guessSub2 = guess.substring(i + 1, guess.length());
					////System.out.println(guessSub1 + "+" + guessSub2);
					wordSub1 = word.substring(0, i);
					wordSub2 = word.substring(i + 1, word.length());
					////System.out.println(guessSub1 + "+" + guessSub2 + "; " + wordSub1 + "+" +
					// wordSub2);
					if (guessSub1.equals(wordSub1) && guessSub2.equals(wordSub2)) {
						//System.out.println("Guess \'" + guess + "' has to replace the letter \'" + guess.charAt(i)
						//		+ "\' with the letter \'" + word.charAt(i) + "\' to be the word \'" + word + "\'");
						return "false:Ein falscher Buchstabe!";
					}
				}
			}
			if(word.length() - guess.length() > 0) {
				return missingLetter(words, guess);
			}
			if(word.length() - guess.length() < 0) {
				return additionalLetter(words, guess);
			}
		}
		return "false";
	}
}
