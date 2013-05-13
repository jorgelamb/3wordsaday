package com.lamboratory.threewordsaday.server.db;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class WordResult {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent private String userId;
	@Persistent private String wordId;
	@Persistent private boolean correct;
	@Persistent private int tries;
	@Persistent String date;
	@Persistent private String from;
	@Persistent private String to;

	private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	public WordResult(String userId, String wordId, String from, String to, boolean correct, int tries) {
		this.userId = userId;
		this.wordId = wordId;
		this.from = from;
		this.to = to;
		this.correct = correct;
		this.tries = tries;
		this.date = WordResult.getDate();
	}
	
	public static String getDate() {
		return dateFormat.format(new Date());
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWordId() {
		return wordId;
	}

	public void setWordId(String wordId) {
		this.wordId = wordId;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
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

	public void setDate(String date) {
		this.date = date;
	}
}
