package com.lamboratory.threewordsaday.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.lamboratory.threewordsaday.shared.WordDTO;
import com.lamboratory.threewordsaday.shared.WordResultDTO;

public interface WordServiceAsync {
	void get(String userId, String from, String to, AsyncCallback<WordDTO> callback) throws IllegalArgumentException;

	void sendResult(String userId, WordDTO word, AsyncCallback<Boolean> callback);

	void getPreviousWords(String userId, String from, String to, AsyncCallback<List<WordResultDTO>> callback);
}
