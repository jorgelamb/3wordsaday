package com.lamboratory.threewordsaday.client;

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
import com.lamboratory.threewordsaday.shared.Word;

public class Threewordsaday implements EntryPoint {

	private static final String USER_ID = "USER_ID";

	private final WordServiceAsync wordService = GWT.create(WordService.class);

	public void onModuleLoad() {

		final TabPanel tabs = new TabPanel();

		addWordsPanel(tabs);

		tabs.selectTab(0);

		RootPanel.get().add(tabs);
	}

	final Label wordLabel = new Label();
	final TextBox textBox = new TextBox();

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

		tabs.add(wordsList, "Words");
	}

	private Word currentWord = null;

	private void getWord() {
		wordService.get("es", "en", USER_ID, new AsyncCallback<Word>() {
			public void onFailure(Throwable caught) {
			}

			public void onSuccess(Word word) {
				currentWord = word;
				wordLabel.setText(word.getWord());
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
				if (currentWord.isCorrect(translation)) {
					Window.alert("correct!");
				} else {
					Window.alert("wrong!");
				}
			}
		}
	}
}
