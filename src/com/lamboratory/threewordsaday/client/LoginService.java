package com.lamboratory.threewordsaday.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	String login(String user, String pwd) throws IllegalArgumentException;
	String register(String user, String pwd) throws IllegalArgumentException;
}
