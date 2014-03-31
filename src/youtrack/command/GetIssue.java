package youtrack.command;

import java.util.Map;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class GetIssue implements Command {
	private final String id;
	private final String authorization;

	public GetIssue(String id, String authorization) {
		this.id = id;
		this.authorization = authorization;
	}

	@Override
	public String getUrl() {
		return "issue/" + id;
	}

	@Override
	public Map<String, String> getParams() {
		return null;
	}

	@Override
	public String getMethod() {
		return "GET";
	}

	@Override
	public boolean usesAuthorization() {
		return true;
	}

	@Override
	public String getAuthorizationToken() {
		return authorization;
	}
}