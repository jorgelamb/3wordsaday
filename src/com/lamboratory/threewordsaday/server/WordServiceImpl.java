package com.lamboratory.threewordsaday.server;

import java.util.Arrays;
import java.util.HashSet;

import com.lamboratory.threewordsaday.client.WordService;
import com.lamboratory.threewordsaday.shared.Word;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class WordServiceImpl extends RemoteServiceServlet implements WordService {

	@Override
	public Word get(String userId, String from, String to) throws IllegalArgumentException {
		String[] translations = {"prueba", "test"};
		return new Word("es", "en", "test", new HashSet<String>(Arrays.asList(translations)));
	}

}
