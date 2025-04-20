package interfaces;

public interface AuthenticationListener {
	public void onUserRegistered(String username);
	public void onUserLoggedIn(String username);
	public void alreadyExists(String username);
	public void invalidCredentials(String username);
}
