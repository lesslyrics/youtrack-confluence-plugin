package issuehighlighter;

import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.spaces.SpaceManager;
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
    private static final String BODY = "templates/body.vm";

    public IssueHighlighter(SpaceManager spaceManager) {

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
        YouTrack youTrack = new YouTrack();
        String authToken = youTrack.getAuth();
        Issue issue = youTrack.getIssue(issueId, authToken);

        if (issue != null) {

            context.put("issue", issueId);
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

        return VelocityUtils.getRenderedTemplate(BODY, context);

    }
}