package main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComparisionTest {

	@Test
	public void testSynonyms() {
		String[] words = new String[2];
		words[0] = "broetchen";
		words[1] = "semmel";
		String guess = "semmel";
		Comparision compare = new Comparision();
		assertEquals("false:Falsches Wort, �hnliche Bedeutung!", compare.isSynonym(words, guess));
	}

	@Test
	public void testMissingLetter1() {
		String[] words = new String[1];
		words[0] = "alle";
		String guess = "lle";
		Comparision compare = new Comparision();
		assertEquals("false:Knapp dran! Ein Buchstabe fehlt!", compare.missingLetter(words, guess));
	}

	@Test
	public void testMissingLetter2() {
		String[] words = new String[1];
		words[0] = "donau";
		String guess = "doau";
		Comparision compare = new Comparision();
		assertEquals("false:Knapp dran! Ein Buchstabe fehlt!", compare.missingLetter(words, guess));
	}

	@Test
	public void testMissingLetter3() {
		String[] words = new String[1];
		words[0] = "fehlerlos";
		String guess = "fehlerlo";
		Comparision compare = new Comparision();
		assertEquals("false:Knapp dran! Ein Buchstabe fehlt!", compare.missingLetter(words, guess));
	}

	@Test
	public void testMissingLetter4() {
		String[] words = new String[1];
		words[0] = "perfekt";
		String guess = "perfekt";
		Comparision compare = new Comparision();
		assertEquals("false", compare.missingLetter(words, guess));
	}

	@Test
	public void testMissingLetterSynonym() {
		String[] words = new String[2];
		words[0] = "perfekt";
		words[1] = "super";
		String guess = "suer";
		Comparision compare = new Comparision();
		assertEquals("false:Knapp dran! Ein Buchstabe fehlt!", compare.missingLetter(words, guess));
	}

	@Test
	public void testAdditionalLetter1() {
		String[] words = new String[1];
		words[0] = "angel";
		String guess = "mangel";
		Comparision compare = new Comparision();
		assertEquals("false:Knapp dran! Ein Buchstabe zu viel!", compare.additionalLetter(words, guess));
	}

	@Test
	public void testAdditionalLetter2() {
		String[] words = new String[1];
		words[0] = "angel";
		String guess = "angsel";
		Comparision compare = new Comparision();
		assertEquals("false:Knapp dran! Ein Buchstabe zu viel!", compare.additionalLetter(words, guess));
	}

	@Test
	public void testAdditionalLetter3() {
		String[] words = new String[1];
		words[0] = "angel";
		String guess = "angeln";
		Comparision compare = new Comparision();
		assertEquals("false:Knapp dran! Ein Buchstabe zu viel!", compare.additionalLetter(words, guess));
	}

	@Test
	public void testAdditionalLetter4() {
		String[] words = new String[1];
		words[0] = "angel";
		String guess = "angel";
		Comparision compare = new Comparision();
		assertEquals("false", compare.additionalLetter(words, guess));
	}

	@Test
	public void testAdditionalLetterSynonym() {
		String[] words = new String[2];
		words[0] = "angel";
		words[1] = "rute";
		String guess = "route";
		Comparision compare = new Comparision();
		assertEquals("false:Knapp dran! Ein Buchstabe zu viel!", compare.additionalLetter(words, guess));
	}

	@Test
	public void testSwappedLetters1() {
		String[] words = new String[1];
		words[0] = "broetchen";
		String guess = "rboetchen";
		Comparision compare = new Comparision();
		assertEquals("false:Buchstaben vertauscht!", compare.swappedLetters(words, guess));
	}

	@Test
	public void testSwappedLetters2() {
		String[] words = new String[1];
		words[0] = "broetchen";
		String guess = "breotchen";
		Comparision compare = new Comparision();
		assertEquals("false:Buchstaben vertauscht!", compare.swappedLetters(words, guess));
	}

	@Test
	public void testSwappedLetters3() {
		String[] words = new String[1];
		words[0] = "broetchen";
		String guess = "broetchne";
		Comparision compare = new Comparision();
		assertEquals("false:Buchstaben vertauscht!", compare.swappedLetters(words, guess));
	}

	@Test
	public void testSwappedLetters4() {
		String[] words = new String[1];
		words[0] = "broetchen";
		String guess = "broetchen";
		Comparision compare = new Comparision();
		assertEquals("false", compare.swappedLetters(words, guess));
	}

	@Test
	public void testSwappedLettersSynonym() {
		String[] words = new String[2];
		words[0] = "nonsense";
		words[1] = "crap";
		String guess = "rcap";
		Comparision compare = new Comparision();
		assertEquals("false:Falsches Wort, �hnliche Bedeutung!", compare.swappedLetters(words, guess));
	}

	@Test
	public void testSwappedLettersMissing() {
		String[] words = new String[1];
		words[0] = "nonsense";
		String guess = "nosnene";
		Comparision compare = new Comparision();
		assertEquals("false:Knapp dran! Ein Buchstabe fehlt!", compare.swappedLetters(words, guess));
	}

	@Test
	public void testSwappedLettersAdditional() {
		String[] words = new String[1];
		words[0] = "nonsense";
		String guess = "nosnenese";
		Comparision compare = new Comparision();
		assertEquals("false:Knapp dran! Ein Buchstabe zu viel!", compare.swappedLetters(words, guess));
	}

	@Test
	public void testWrongLetter1() {
		String[] words = new String[1];
		words[0] = "angel";
		String guess = "ongel";
		Comparision compare = new Comparision();
		assertEquals("false:Ein falscher Buchstabe!", compare.wrongLetter(words, guess));
	}

	@Test
	public void testWrongLetter2() {
		String[] words = new String[1];
		words[0] = "angel";
		String guess = "ankel";
		Comparision compare = new Comparision();
		assertEquals("false:Ein falscher Buchstabe!", compare.wrongLetter(words, guess));
	}

	@Test
	public void testWrongLetter3() {
		String[] words = new String[1];
		words[0] = "angel";
		String guess = "anger";
		Comparision compare = new Comparision();
		assertEquals("false:Ein falscher Buchstabe!", compare.wrongLetter(words, guess));
	}

	@Test
	public void testWrongLetterSynonym() {
		String[] words = new String[2];
		words[0] = "angel";
		words[1] = "rute";
		String guess = "rule";
		Comparision compare = new Comparision();
		assertEquals("false:Ein falscher Buchstabe!", compare.wrongLetter(words, guess));
	}
}
