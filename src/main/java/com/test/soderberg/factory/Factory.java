package com.test.soderberg.factory;

import com.test.soderberg.managers.HTTPRequestsManager;
import com.test.soderberg.managers.IHTTPRequestsManager;

public class Factory {

	/**
	 * Factory for for creating the object to perform the HTTP requests
	 * @return the instance of the object
	 */
	public static IHTTPRequestsManager getHTTPRequestManager() {
		return new HTTPRequestsManager();
	}
}
