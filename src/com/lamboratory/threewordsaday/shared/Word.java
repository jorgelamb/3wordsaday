package com.lamboratory.threewordsaday.shared;

import java.io.Serializable;
import java.util.Set;

public class Word implements Serializable {

	private static final long serialVersionUID = -6582171363258733594L;
	
	private String from;
	private String to;
	private String word;
	private Set<String> translations;
	
	public Word() {
	}
	
	public Word(String from, String to, String word, Set<String> translations) {
		super();
		this.from = from;
		this.to = to;
		this.word = word;
		this.translations = translations;
	}
	
	public String getWord() {
		return word;
	}
	
	public boolean isCorrect(String translation) {
		return translations.contains(translation);
	}
}
