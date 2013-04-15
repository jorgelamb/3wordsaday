package com.lamboratory.threewordsaday.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.lamboratory.threewordsaday.shared.Word;

@RemoteServiceRelativePath("word")
public interface WordService extends RemoteService {
	Word get(String userId, String from, String to) throws IllegalArgumentException;
}
