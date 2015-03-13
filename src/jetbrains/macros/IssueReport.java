package jetbrains.macros;

import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.MacroException;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import jetbrains.macros.base.YouTrackAuthAwareMacroBase;
import jetbrains.macros.util.Strings;
import youtrack.Issue;
import youtrack.Project;
import youtrack.exceptions.AuthenticationErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IssueReport extends YouTrackAuthAwareMacroBase {

    public IssueReport(PluginSettingsFactory pluginSettingsFactory, TransactionTemplate transactionTemplate) {
        super(pluginSettingsFactory, transactionTemplate);
    }

    public boolean isInline() {
        return false;
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
            final String project = (String) params.get(Strings.PROJECT);
            final String query = (String) params.get(Strings.QUERY);
            final StringBuilder result = new StringBuilder();
            final StringBuilder rows = new StringBuilder();
            if (project != null && query != null) {
                final Project prj = tryGetItem(youTrack.projects, project);
                if (prj != null) {
                    final Map<String, Object> myContext = new HashMap<String, Object>();
                    final List<Issue> issues = prj.issues.query(query);
                    for (final Issue sIssue : issues) {
                        myContext.clear();
                        myContext.putAll(context);
                        final Issue issue = sIssue.createSnapshot();
                        myContext.put(Strings.ISSUE, sIssue.getId());
                        myContext.put(Strings.BASE, getProperty(Strings.HOST).replace(Strings.REST_PREFIX, Strings.EMPTY));
                        myContext.put(Strings.STATE, issue.getState());
                        myContext.put(Strings.SUMMARY, issue.getSummary());
                        myContext.put(Strings.ASSIGNEE, issue.getAssignee() != null ? issue.getAssignee().getFullName() : Strings.UNASSIGNED);
                        rows.append(VelocityUtils.getRenderedTemplate(Strings.ROW, myContext));
                    }
                    context.put("rows", rows.toString());
                    context.put("title", query + " from " + project);
                    context.put("total", issues.size());
                    result.append(VelocityUtils.getRenderedTemplate(Strings.BODY_REPORT, context));
                } else result.append("Project ").append(project).append(" not found");
            }
            return result.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new MacroException(ex);
        }
    }
}