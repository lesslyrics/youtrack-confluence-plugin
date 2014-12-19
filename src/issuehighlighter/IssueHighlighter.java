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

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;

public class IssueHighlighter extends BaseMacro {
    public static final String SETTINGS_KEY = "issue-highlighter-cached-acs-3a";
    public static final String DETAILED = "detailed";
    public static final String SHORT = "short";
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
            final Map<String, Object> context = MacroUtils.defaultVelocityContext();
            String issueId = (String) params.get("id");
            String style = (String) params.get("style");
            if (style != null && DETAILED.equals(style)) {
                style = DETAILED;
            } else {
                style = SHORT;
            }
            final Properties prop = new Properties();
            final ClassLoader loader = getClass().getClassLoader();
            final InputStream stream = loader.getResourceAsStream("/resources/settings.properties");
            prop.load(stream);
            final String userName = prop.getProperty("username");
            final String password = prop.getProperty("password");
            final String baseHost = prop.getProperty("host");
            youTrack = YouTrack.getInstance(baseHost);
            boolean cacheUsed = false;
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
                context.put("title", MessageFormat.format("Reporter: {0}, Priority: {1},  State: {2}, Assignee: {3}, Votes: {4}, Type: {5}",
                        issue.getReporter(), issue.getPriority(), issue.getState(), issue.getAssignee().getFullName(), issue.getVotes(), issue.getType()));
                final User currentUser = AuthenticatedUserThreadLocal.get();
                if (currentUser != null) {
                    context.put("user", currentUser.getFullName());
                }
            } else {
                context.put("error", (issueId != null ? "ISSUE NOT FOUND " + issueId : "ISSUE NOT SPECIFIED"));
            }
            return VelocityUtils.getRenderedTemplate((style.equals(SHORT) ? BODY : BODY_DETAILED), context);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MacroException(ex);
        }
    }

    private Issue tryGetIssue(String issueId) throws Exception {

        IssueId id = new IssueId(issueId);
        Project project = youTrack.project(id.projectId);
        return project.issues.item(issueId);
    }
}