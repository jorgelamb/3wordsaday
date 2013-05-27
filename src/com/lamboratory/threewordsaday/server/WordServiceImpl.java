package com.lamboratory.threewordsaday.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.lamboratory.threewordsaday.client.WordService;
import com.lamboratory.threewordsaday.server.db.PMF;
import com.lamboratory.threewordsaday.server.db.Word;
import com.lamboratory.threewordsaday.server.db.WordResult;
import com.lamboratory.threewordsaday.shared.WordDTO;
import com.lamboratory.threewordsaday.shared.WordResultDTO;
import com.lamboratory.threewordsaday.shared.exception.TooManyAnswersException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class WordServiceImpl extends RemoteServiceServlet implements WordService {

	private static Random r = new Random();
	
	@Override
	public WordDTO get(String userId, String from, String to) throws IllegalArgumentException, TooManyAnswersException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if(!canUserReplyMoreQuestions(pm, userId, from, to)) {
				throw new TooManyAnswersException();
			}
			
			Query query = pm.newQuery(Word.class, "from == :from && to == :to");
			List<Word> results = (List<Word>)query.execute(from, to);
			if(results.isEmpty()) {
				addWord(pm, from, to, "es", "en", "hola", "hi", "hello");
				addWord(pm, from, to, "es", "en", "adios", "bye", "good bye");
				addWord(pm, from, to, "es", "en", "prueba", "test");
				
				addWord(pm, from, to, "en", "es", "test", "prueba", "test");
				addWord(pm, from, to, "en", "es", "hello", "hola", "buenos días");
				addWord(pm, from, to, "en", "es", "bye", "adios");
				return get(userId, from, to);
			}
			
			Word word = results.get(r.nextInt(results.size()));
			return new WordDTO(word.getFrom(), word.getTo(), word.getWord(), word.getTranslations());
		} finally {
			pm.close();
		}
	}
	
	private void addWord(PersistenceManager pm, String queryFrom, String queryTo, String from, String to, String word, String... translations) {
		if(from.equals(queryFrom) && to.equals(queryTo)) {
			Word wordObject = new Word(from, to, word, new HashSet<String>(Arrays.asList(translations)));
			pm.makePersistent(wordObject);
		}
	}
	
	@Override
	public boolean sendResult(String userId, WordDTO word) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			if(canUserReplyMoreQuestions(pm, userId, word.getFrom(), word.getTo())) {
				WordResult wordResult = new WordResult(userId, word.getWord(), word.getFrom(), word.getTo(), word.isCorrect(), word.getTries());
				pm.makePersistent(wordResult);
				return true;
			} else {
				return false;
			}
		} finally {
			pm.close();
		}
	}
	
	private boolean canUserReplyMoreQuestions(PersistenceManager pm, String userId, String from, String to) {
		Query query = pm.newQuery(WordResult.class, "userId == :userId && date == \""+WordResult.getDate()+"\" && from == \""+safeLanguageName(from)+"\" && to == \""+safeLanguageName(to)+"\"");
		List<WordResult> results = (List<WordResult>)query.execute(userId);
		System.out.println("results: "+results.toString());
		return results.size() < 3;
	}

	private String safeLanguageName(String language) {
		return language.replaceAll("\\W", "");
	}

	@Override
	public List<WordResultDTO> getPreviousWords(String userId, String from, String to) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(WordResult.class, "userId == :userId && from == \""+safeLanguageName(from)+"\" && to == \""+safeLanguageName(to)+"\"");
			query.setOrdering("date desc");
			List<WordResult> results = (List<WordResult>)query.execute(userId);
			List<WordResultDTO> resultsDto = new ArrayList<WordResultDTO>(results.size());
			for(WordResult result : results) {
				resultsDto.add(new WordResultDTO(result.getFrom(), result.getTo(), result.getWordId(), result.getTries(), result.isCorrect(), result.getDate()));
			}
			return resultsDto;
		} finally {
			pm.close();
		}
	}
}
