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
import youtrack.Issue;
import youtrack.Project;
import youtrack.YouTrack;
import youtrack.util.IssueId;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class IssueHighlighter extends BaseMacro {
	public static final String SETTINGS_KEY = "issue-highlighter-cached-acs-3a";
	private static final String BODY = "templates/body.vm";
	private static final String BODY_DETAILED = "templates/body-detailed.vm";
	private final BandanaManager bm;
	private YouTrack youTrack;

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
		try {
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

			youTrack = YouTrack.getInstance(baseHost);
			Boolean cacheUsed = false;

			SettingsCache settings = (SettingsCache) bm.getValue(new ConfluenceBandanaContext(), SETTINGS_KEY);

			if (settings == null) {
				youTrack.login(userName, password);
				settings = new SettingsCache();
				settings.setAuthKey((youTrack.getAuthorization()));
				bm.setValue(new ConfluenceBandanaContext(), SETTINGS_KEY, settings);

			} else {
				youTrack.setAuthorization(settings.getAuthKey());
				cacheUsed = true;
			}

			Issue issue = tryGetIssue(issueId);

			if (issue == null && cacheUsed) {
				youTrack.login(userName, password);
				issue = tryGetIssue(issueId);
			}

			if (!settings.getAuthKey().equals(youTrack.getAuthorization())) {
				settings.setAuthKey(youTrack.getAuthorization());
				bm.setValue(new ConfluenceBandanaContext(), SETTINGS_KEY, settings);
			}

			if (issue != null) {
				issue = issue.createSnapshot();
				context.put("issue", issueId);
				context.put("summary", issue.getSummary());
				context.put("style", (issue.isResolved()) ? "line-through" : "normal");
				context.put("title", "Reporter: " + issue.getReporter() + ", Priority: " + issue.getPriority() + ", State: "
						+ issue.getState() + ", Assignee: " + issue.getAssignee().getFullName() + ", Votes: " + issue.getVotes() + ", Type: " + issue.getType());

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

		} catch (Exception ex) {
			throw new MacroException(ex);
		}
	}

	private Issue tryGetIssue(String issueId) throws Exception {

		IssueId id = new IssueId(issueId);
		Project project = youTrack.project(id.projectId);
		return project.issues.item(issueId);
	}
}