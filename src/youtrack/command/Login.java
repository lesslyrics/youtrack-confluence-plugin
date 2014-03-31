package youtrack.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class Login implements Command {
	private final String userName;
	private final String password;

	public Login(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Override
	public String getUrl() {
		return "useer/login";
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("username", userName);
		result.put("password", password);
		return result;
	}

	@Override
	public String getMethod() {
		return "POST";
	}

	@Override
	public boolean usesAuthorization() {
		return false;
	}

	@Override
	public String getAuthorizationToken() {
		return null;
	}
}