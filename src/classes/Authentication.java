package classes;

import base.User;
import interfaces.AuthenticationListener;

import java.util.HashMap;
import java.util.Map;

public class Authentication {
	private Map<String, User> weStudyUsers;
	private AuthenticationListener listener;

	public Authentication() {
		this.weStudyUsers = new HashMap<>();
	}

	public Authentication(Map<String, User> weStudyUsers) {
		this.weStudyUsers = weStudyUsers;
	}

	public void setListener(AuthenticationListener listener) {
		this.listener = listener;
	}

	public boolean registerUser(User user) {
		if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
			return false;
		}

		if (weStudyUsers.containsKey(user.getUsername())) {
			if (listener != null) {
				listener.alreadyExists(user.getUsername());
			}
			return false; // Username already exists
		}

		weStudyUsers.put(user.getUsername(), user);
		if (listener != null) {
			listener.onUserRegistered(user.getUsername());
		}
		return true;
	}

	public User getUser(String username) {
		return weStudyUsers.get(username);
	}

	public User loginUser(String username, String password) {
		User user = weStudyUsers.get(username);

		if (user != null && user.login(password)) {
			if (listener != null) {
				listener.onUserLoggedIn(username);
			}
			return user;
		}

		if (listener != null) {
			listener.invalidCredentials(username);
		}
		return null;
	}
}
