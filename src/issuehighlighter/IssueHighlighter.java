package issuehighlighter;

import com.atlassian.bandana.BandanaManager;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.setup.bandana.ConfluenceBandanaContext;
import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.BaseMacro;
import com.atlassian.renderer.v2.macro.MacroException;
import com.atlassian.user.User;
import youtrack.YouTrack;
import youtrack.command.GetIssue;
import youtrack.command.Login;
import youtrack.command.result.Result;
import youtrack.issue.Issue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class IssueHighlighter extends BaseMacro {
	public static final String SETTINGS_KEY = "issue-highlighter-cached-acs-3";
	private static final String BODY = "templates/body.vm";
	private static final String BODY_DETAILED = "templates/body-detailed.vm";
	private final BandanaManager bm;

	public IssueHighlighter(BandanaManager bandanaManager) {
		this.bm = bandanaManager;
	}

	public boolean isInline() {
		return true;
	}

	public boolean hasBody() {
		return false;
	}

	public RenderMode getBodyRenderMode() {
		return RenderMode.NO_RENDER;
	}

	public String execute(Map params, String body, RenderContext renderContext)
			throws MacroException {

		Map<String, Object> context = MacroUtils.defaultVelocityContext();

		String issueId = (String) params.get("id");
		String style = (String) params.get("style");
		if (style != null && style.equals("detailed")) {
			style = "detailed";
		} else {
			style = "short";
		}
		String userName = "";
		String password = "";
		String baseHost = "";
		Properties prop = new Properties();
		ClassLoader loader = getClass().getClassLoader();
		InputStream stream = loader.getResourceAsStream("/resources/settings.properties");

		try {
			prop.load(stream);
			userName = prop.getProperty("username");
			password = prop.getProperty("password");
			baseHost = prop.getProperty("host");
		} catch (IOException e) {
			e.printStackTrace();
		}

		YouTrack youTrack = new YouTrack(baseHost);
		Boolean cacheUsed = false;
		Result result;
		SettingsCache settings = (SettingsCache) bm.getValue(new ConfluenceBandanaContext(), SETTINGS_KEY);

		if (settings == null) {

			result = youTrack.execute(new Login(userName, password));
			settings = new SettingsCache();
			settings.setAuthKey(result.getAuthToken());
			bm.setValue(new ConfluenceBandanaContext(), SETTINGS_KEY, settings);

		} else {
			cacheUsed = true;
		}

		String authToken = settings.getAuthKey();

		result = youTrack.execute(new GetIssue(issueId, authToken));

		Issue issue = result.getIssue();

		if (issue == null && cacheUsed) {
			result = youTrack.execute(new Login(userName, password));
			authToken = result.getAuthToken();
			issue = youTrack.execute(new GetIssue(issueId, authToken)).getIssue();
		}

		if (settings.getAuthKey().equals(authToken)) {
			settings.setAuthKey(authToken);
			bm.setValue(new ConfluenceBandanaContext(), SETTINGS_KEY, settings);
		}

		if (issue != null) {

			context.put("issue", issueId);
			context.put("summary", issue.summary());
			context.put("style", (issue.isResolved()) ? "line-through" : "normal");
			context.put("title", "Reporter: " + issue.reporter() + ", Priority: " + issue.priority() + ", State: "
					+ issue.state() + ", Assignee: " + issue.assignee() + ", Votes: " + issue.votes() + ", Type: " + issue.type());

			User currentUser = AuthenticatedUserThreadLocal.getUser();

			if (currentUser != null) {
				context.put("user", currentUser.getFullName());
			}

		} else {

			if (issueId != null) {
				context.put("error", "ISSUE NOT FOUND " + issueId);
			} else {
				context.put("error", "ISSUE NOT SPECIFIED");
			}
		}

		return VelocityUtils.getRenderedTemplate((style.equals("short") ? BODY : BODY_DETAILED), context);

	}
}
