package base;

public abstract class User {
	private String username;
	private String password;
	private boolean isLoggedIn;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.isLoggedIn = false;
	}

	protected boolean authenticate(String password) {
		return password.equals(this.password);
	}

	public boolean login(String password) {
		if (authenticate(password)) {
			this.isLoggedIn = true;
			return true;
		}
		return false;
	}

	public void logout() {
		this.isLoggedIn = false;
	}

	public String getUsername() {
		return username;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

}
