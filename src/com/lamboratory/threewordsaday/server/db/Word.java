package com.lamboratory.threewordsaday.server.db;

import java.io.Serializable;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Word implements Serializable {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent private String from;
	@Persistent private String to;
	@Persistent private String word;
	@Persistent private Set<String> translations;
	
	public Word(String from, String to, String word, Set<String> translations) {
		super();
		this.from = from;
		this.to = to;
		this.word = word;
		this.translations = translations;
	}
	
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
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
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	public Set<String> getTranslations() {
		return translations;
	}
	public void setTranslations(Set<String> translations) {
		this.translations = translations;
	}
}
