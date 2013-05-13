package com.lamboratory.threewordsaday.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
import com.lamboratory.threewordsaday.server.db.WordResult;
import com.lamboratory.threewordsaday.shared.WordDTO;
import com.lamboratory.threewordsaday.shared.WordResultDTO;

public class Threewordsaday implements EntryPoint {

	private static final String USER_ID = "USER_ID";

	public static final int MAX_TRIES = 3;

	private final WordServiceAsync wordService = GWT.create(WordService.class);

	public void onModuleLoad() {

		final TabPanel tabs = new TabPanel();

		addWordsPanel(tabs);

		tabs.selectTab(0);

		RootPanel.get().add(tabs);
	}

	final Label wordLabel = new Label();
	final TextBox textBox = new TextBox();
	final Panel previousWords = new VerticalPanel();

	private void addWordsPanel(final TabPanel tabs) {
		Panel wordsList = new VerticalPanel();

		Panel currentWordPanel = new FlowPanel();
		wordsList.add(currentWordPanel);

		wordLabel.setText("Loading...");
		currentWordPanel.add(wordLabel);

		textBox.setText("");
		currentWordPanel.add(textBox);

		Button check = new Button();
		currentWordPanel.add(check);

		CheckWordHandler handler = new CheckWordHandler();
		check.addClickHandler(handler);
		textBox.addKeyUpHandler(handler);

		getWord();

		getPreviousWords();
		wordsList.add(previousWords);

		tabs.add(wordsList, "Words");
	}

	private WordDTO currentWord = null;

	private void getWord() {
		wordLabel.setText("Loading...");
		textBox.setText("");
		wordService.get(USER_ID, "es", "en", new AsyncCallback<WordDTO>() {
			public void onFailure(Throwable caught) {
				Window.alert("no more available questions");
			}

			public void onSuccess(WordDTO word) {
				currentWord = word;
				wordLabel.setText(word.getWord());
			}
		});
	}

	private void getPreviousWords() {
		wordService.getPreviousWords(USER_ID, "es", "en", new AsyncCallback<List<WordResultDTO>>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<WordResultDTO> words) {
				for(WordResultDTO wordResult : words) {
					previousWords.add(new Label(wordResult.getWord()+" "+wordResult.isCorrect()));
				}
			}
		});
	}

	private void sendResult() {
		wordService.sendResult(USER_ID, currentWord, new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(Boolean result) {
				getWord();
			}
		});
	}

	class CheckWordHandler implements ClickHandler, KeyUpHandler {

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
			if (currentWord != null) {
				String translation = textBox.getText();
				int tries = currentWord.getTries()+1;
				currentWord.setTries(tries);
				if (currentWord.isCorrect(translation)) {
					Window.alert("correct!");
					currentWord.setCorrect(true);
					sendResult();
				} else {
					Window.alert("wrong!");
					if(tries>=MAX_TRIES) {
						sendResult();
					}
				}
			}
		}
	}
}
