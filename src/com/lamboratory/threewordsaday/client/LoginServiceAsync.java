package com.lamboratory.threewordsaday.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface LoginServiceAsync {
	void login(String user, String password, AsyncCallback<String> callback) throws IllegalArgumentException;
	void register(String user, String password, AsyncCallback<String> callback) throws IllegalArgumentException;
}
