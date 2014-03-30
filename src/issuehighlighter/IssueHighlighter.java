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
import youtrack.YouTrack;

import java.util.Map;

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
		String access;
		String issueId = (String) params.get("id");
		String style = (String) params.get("style");
		if (style != null && style.equals("detailed")) {
			style = "detailed";
		} else {
			style = "short";
		}
		YouTrack youTrack = new YouTrack();
		Boolean cacheUsed = false;
		SettingsCache settings = (SettingsCache) bm.getValue(new ConfluenceBandanaContext(), SETTINGS_KEY);
		if (settings == null) {
			settings = new SettingsCache();
			settings.setAuthKey(youTrack.getAuth());
			access = "(Direct)";
			bm.setValue(new ConfluenceBandanaContext(), SETTINGS_KEY, settings);
		} else {
			access = "(Cached)";
			cacheUsed = true;
		}

		String authToken = settings.getAuthKey();
		Issue issue = youTrack.getIssue(issueId, authToken);

		if (issue == null && cacheUsed) {
			authToken = youTrack.getAuth();
			access = "(Re-cached)";
			issue = youTrack.getIssue(issueId, authToken);
		}

		if (settings.getAuthKey().equals(authToken)) {
			settings.setAuthKey(authToken);
			bm.setValue(new ConfluenceBandanaContext(), SETTINGS_KEY, settings);
		}

		if (issue != null) {

			context.put("issue", issueId);
			context.put("access", access);
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