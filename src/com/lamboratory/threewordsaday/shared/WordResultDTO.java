package com.lamboratory.threewordsaday.shared;

import java.io.Serializable;

public class WordResultDTO implements Serializable {

	private static final long serialVersionUID = 5149238428790557642L;
	
	private String from;
	private String to;
	private String word;
	int tries = 0;
	boolean correct = false;
	String date;
	
	public WordResultDTO() {
	}
	
	public WordResultDTO(String from, String to, String word, int tries, boolean correct, String date) {
		super();
		this.from = from;
		this.to = to;
		this.word = word;
		this.tries = tries;
		this.correct = correct;
		this.date = date;
	}
	
	public String getWord() {
		return word;
	}
	
	public boolean isCorrect() {
		return correct;
	}
	
	public int getTries() {
		return this.tries;
	}
	public void setTries(int tries) {
		this.tries = tries;
	}
	
	public String getFrom() {
		return from;
	}
	public String getTo() {
		return to;
	}
}
