package youtrack.command.result;

import youtrack.issue.Issue;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class Result {

	private final Issue issue;
	private final String authToken;
	private final Error error;
	private final int responseCode;

	public Result(Issue issue, String authToken, Error error, int responseCode) {
		this.issue = issue;
		this.authToken = authToken;
		this.error = error;
		this.responseCode = responseCode;
	}

	public Error getError() {
		return error;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public Issue getIssue() {
		return issue;
	}


	public String getAuthToken() {
		return authToken;
	}
}
