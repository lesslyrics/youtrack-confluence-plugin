package youtrack.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class AddComment implements Command {
	private final String comment;
	private final String authorization;
	private final String issueId;

	public AddComment(String comment, String authorization, String issueId) {
		this.comment = comment;
		this.authorization = authorization;
		this.issueId = issueId;
	}

	@Override
	public String getUrl() {
		return "issue/" + issueId + "/execute";
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("comment", comment);
		return result;
	}

	@Override
	public String getMethod() {
		return "POST";
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
