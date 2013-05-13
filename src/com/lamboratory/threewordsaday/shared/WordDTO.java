package com.lamboratory.threewordsaday.shared;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class WordDTO implements Serializable {

	private static final long serialVersionUID = -6582171363258733594L;
	
	private String from;
	private String to;
	private String word;
	private Set<String> translations;
	int tries = 0;
	boolean correct = false;
	
	public WordDTO() {
	}
	
	public WordDTO(String from, String to, String word, Set<String> translations) {
		super();
		this.from = from;
		this.to = to;
		this.word = word;
		this.translations = new HashSet<String>();
		this.translations.addAll(translations);
	}
	
	public String getWord() {
		return word;
	}
	
	public boolean isCorrect(String translation) {
		return translations.contains(translation.trim().toLowerCase());
	}
	
	public int getTries() {
		return this.tries;
	}
	public void setTries(int tries) {
		this.tries = tries;
	}
	
	public boolean isCorrect() {
		return this.correct;
	}
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
}
