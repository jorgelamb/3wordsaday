package com.lamboratory.threewordsaday.server;

import com.lamboratory.threewordsaday.client.LoginService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {

	public String login(String user, String password) throws IllegalArgumentException {
		return null;
	}

	public String register(String user, String password) throws IllegalArgumentException {
		return null;
	}
}
