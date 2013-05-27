package com.lamboratory.threewordsaday.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.lamboratory.threewordsaday.shared.TranslationDTO;
import com.lamboratory.threewordsaday.shared.WordDTO;
import com.lamboratory.threewordsaday.shared.WordResultDTO;

public class Threewordsaday implements EntryPoint {

	private static final String USER_ID_KEY = "USER_ID";
	private String userId = "";

	public static final int MAX_TRIES = 3;
	
	private final WordServiceAsync wordService = GWT.create(WordService.class);

	public void onModuleLoad() {

		getUserId();

		final TabPanel tabs = new TabPanel();

		List<TranslationDTO> translations = new ArrayList<TranslationDTO>();
		translations.add(new TranslationDTO("en", "es"));
		translations.add(new TranslationDTO("es", "en"));

		for(TranslationDTO translation : translations) {
			addWordsPanel(translation, tabs);
		}

		tabs.selectTab(0);

		RootPanel.get().add(tabs);
	}

	private void getUserId() {
		if(Storage.isLocalStorageSupported()) {
			Storage localStorage = Storage.getLocalStorageIfSupported();
			String userId = localStorage.getItem(USER_ID_KEY);
			if(userId == null || userId.isEmpty()) {
				userId = ""+Random.nextInt();
				localStorage.setItem(USER_ID_KEY, userId);
			}
			this.userId = userId;
		}
	}

	final Map<TranslationDTO, Label> wordLabels = new HashMap<TranslationDTO, Label>();
	final Map<TranslationDTO, TextBox> textBoxes = new HashMap<TranslationDTO, TextBox>();
	final Map<TranslationDTO, VerticalPanel> previousWordsPanels = new HashMap<TranslationDTO, VerticalPanel>();
	final Map<TranslationDTO, WordDTO> currentWords = new HashMap<TranslationDTO, WordDTO>();

	private void addWordsPanel(TranslationDTO translation, final TabPanel tabs) {
		Panel wordsList = new VerticalPanel();

		Panel currentWordPanel = new FlowPanel();
		wordsList.add(currentWordPanel);

		Label wordLabel = new Label();
		wordLabel.setText("Loading...");
		currentWordPanel.add(wordLabel);
		wordLabels.put(translation, wordLabel);

		TextBox textBox = new TextBox();
		textBox.setText("");
		currentWordPanel.add(textBox);
		textBoxes.put(translation, textBox);

		Button check = new Button();
		currentWordPanel.add(check);

		CheckWordHandler handler = new CheckWordHandler(translation);
		check.addClickHandler(handler);
		textBox.addKeyUpHandler(handler);

		getWord(translation);

		VerticalPanel previousWords = new VerticalPanel();
		previousWordsPanels.put(translation, previousWords);
		getPreviousWords(translation);
		wordsList.add(previousWords);

		tabs.add(wordsList, translation.getFrom() + " -> " + translation.getTo());
	}

	private void getWord(final TranslationDTO translation) {
		wordLabels.get(translation).setText("Loading...");
		textBoxes.get(translation).setText("");
		wordService.get(userId, translation.getFrom(), translation.getTo(), new AsyncCallback<WordDTO>() {
			public void onFailure(Throwable caught) {
				wordLabels.get(translation).setText("No more words for today");
			}

			public void onSuccess(WordDTO word) {
				currentWords.put(translation, word);
				wordLabels.get(translation).setText(word.getWord());
			}
		});
	}

	private void getPreviousWords(TranslationDTO translation) {
		wordService.getPreviousWords(userId, translation.getFrom(), translation.getTo(),
				new AsyncCallback<List<WordResultDTO>>() {
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<WordResultDTO> words) {
						for (WordResultDTO wordResult : words) {
							addPreviousWord(wordResult);
						}
					}
				});
	}

	private void addPreviousWord(WordResultDTO wordResult) {
		previousWordsPanels.get(new TranslationDTO(wordResult.getFrom(), wordResult.getTo())).add(new Label(wordResult.getWord() + " "
				+ wordResult.isCorrect()));
	}

	private void sendResult(final TranslationDTO translation) {
		wordService.sendResult(userId, currentWords.get(translation),
				new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Boolean result) {
						WordResultDTO wordResult = new WordResultDTO(currentWords.get(translation));
						addPreviousWord(wordResult);
						getWord(translation);
					}
				});
	}

	class CheckWordHandler implements ClickHandler, KeyUpHandler {

		TranslationDTO translation;
		public CheckWordHandler(TranslationDTO translation) {
			this.translation = translation;
		}
		
		@Override
		public void onKeyUp(KeyUpEvent event) {
			if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
				checkWord();
			}
		}

		@Override
		public void onClick(ClickEvent event) {
			checkWord();
		}

		private void checkWord() {
			WordDTO currentWord = currentWords.get(translation);
			if (currentWord != null) {
				String translationText = textBoxes.get(translation).getText();
				int tries = currentWord.getTries() + 1;
				currentWord.setTries(tries);
				if (currentWord.isCorrect(translationText)) {
					Window.alert("correct!");
					currentWord.setCorrect(true);
					sendResult(translation);
				} else {
					Window.alert("wrong!");
					if (tries >= MAX_TRIES) {
						sendResult(translation);
					}
				}
			}
		}
	}
}
