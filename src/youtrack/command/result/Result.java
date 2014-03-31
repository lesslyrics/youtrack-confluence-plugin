package youtrack.command.result;

import youtrack.issue.Issue;

/**
 * Created by egor.malyshev on 31.03.2014.
 */
public class Result {

	private Issue issue;
	private String authToken;
	private Error error;


	public Result(Issue issue, String authToken, Error error) {
		this.issue = issue;
		this.authToken = authToken;
		this.error = error;
	}

	public Result() {

	}

	public void setIssue(Issue issue) {
		this.issue = issue;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public Issue getIssue() {
		return issue;
	}

	public String getAuthToken() {
		return authToken;
	}

	public Error getError() {
		return error;
	}
}
