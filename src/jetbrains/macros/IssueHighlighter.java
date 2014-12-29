package jetbrains.macros;

import com.atlassian.bandana.BandanaManager;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.user.AuthenticatedUserThreadLocal;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.MacroException;
import com.atlassian.user.User;
import jetbrains.macros.base.YouTrackAuthAwareMacroBase;
import youtrack.Issue;
import youtrack.Project;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;
import youtrack.util.IssueId;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

public class IssueHighlighter extends YouTrackAuthAwareMacroBase {
    public static final String DETAILED = "detailed";
    public static final String SHORT = "short";
    private static final String BODY = "templates/body-link.vm";
    private static final String BODY_DETAILED = "templates/body-link-detailed.vm";

    public IssueHighlighter(BandanaManager bandanaManager) throws CommandExecutionException, NoSuchIssueFieldException, AuthenticationErrorException, IOException {
        super(bandanaManager);
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
            final String issueId = (String) params.get("id");
            String style = (String) params.get("style");
            if (DETAILED.equals(style)) {
                style = DETAILED;
            } else {
                style = SHORT;
            }
            if (issueId != null && !issueId.isEmpty()) {
                IssueId id = new IssueId(issueId);
                final Project project = tryGetItem(youTrack.projects, id.projectId);
                if (project != null) {
                    Issue issue = tryGetItem(project.issues, issueId);
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
                    }
                } else {
                    context.put("error", "ISSUE NOT FOUND " + issueId);
                }
            } else {
                context.put("error", "ISSUE NOT SPECIFIED");
            }
            return VelocityUtils.getRenderedTemplate((SHORT.equals(style) ? BODY : BODY_DETAILED), context);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MacroException(ex);
        }
    }
}