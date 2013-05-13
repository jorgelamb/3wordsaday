package com.lamboratory.threewordsaday.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.lamboratory.threewordsaday.shared.WordDTO;
import com.lamboratory.threewordsaday.shared.WordResultDTO;
import com.lamboratory.threewordsaday.shared.exception.TooManyAnswersException;

@RemoteServiceRelativePath("word")
public interface WordService extends RemoteService {
	WordDTO get(String userId, String from, String to) throws IllegalArgumentException, TooManyAnswersException;
	boolean sendResult(String userId, WordDTO word);
	List<WordResultDTO> getPreviousWords(String userId, String from, String to);
}
