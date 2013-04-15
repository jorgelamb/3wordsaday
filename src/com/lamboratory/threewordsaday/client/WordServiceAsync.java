package com.lamboratory.threewordsaday.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.lamboratory.threewordsaday.shared.Word;

public interface WordServiceAsync {
	void get(String userId, String from, String to, AsyncCallback<Word> callback) throws IllegalArgumentException;
}
