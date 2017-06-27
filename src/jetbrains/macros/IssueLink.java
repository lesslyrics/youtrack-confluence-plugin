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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import youtrack.Issue;
import youtrack.issues.fields.BaseIssueField;
import youtrack.issues.fields.values.MultiUserFieldValue;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class IssueLink extends YouTrackAuthAwareMacroBase {
    private static final Logger LOG = LoggerFactory.getLogger(IssueLink.class);
    private static final String logPrefix = "YTMacro-LinkDebug: ";

    public IssueLink(PluginSettingsFactory pluginSettingsFactory, TransactionTemplate transactionTemplate) {
        super(pluginSettingsFactory, transactionTemplate);
    }

    @Override
    protected String getLoggingPrefix() {
        return logPrefix;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
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
            final String issueId = (String) params.get(Strings.ID);
            String strikeMode = (String) params.get(Strings.STRIKE_THRU_PARAM);
            if (strikeMode == null) strikeMode = Strings.ID_ONLY;
            String linkTextTemplate = (String) params.get(Strings.TEMPLATE_PARAM);
            String summaryTextTemplate;
            if (linkTextTemplate == null || linkTextTemplate.isEmpty()) linkTextTemplate = Strings.DEFAULT_TEMPLATE;
            String style = (String) params.get(Strings.STYLE);
            if (!Strings.DETAILED.equals(style)) {
                style = Strings.SHORT;
            }
            if (issueId != null && !issueId.isEmpty()) {
                Issue issue = tryGetItem(youTrack.issues, issueId, retries);
                if (issue != null) {
                    issue = issue.createSnapshot();
                    final HashMap<String, BaseIssueField> fields = issue.getFields();
                    for (final String fieldName : fields.keySet()) {
                        context.put(fieldName, fields.get(fieldName).getStringValue());
                    }
                    context.put(Strings.ISSUE, issueId);
                    context.put(Strings.BASE, getProperty(Strings.HOST).replace(Strings.REST_PREFIX, Strings.EMPTY));
                    context.put(Strings.LINKBASE, getProperty(Strings.HOST).replace(Strings.REST_PREFIX, Strings.EMPTY));
                    String linkbase = getProperty(Strings.LINKBASE);
                    if (null != linkbase && !linkbase.isEmpty()) {
                        context.put(Strings.LINKBASE, linkbase.replace(Strings.REST_PREFIX, Strings.EMPTY));
                    }
                    final String thru = (Strings.ALL.equals(strikeMode) || Strings.ID_ONLY.equals(strikeMode)) && issue.isResolved() ? "line-through" : Strings.NORMAL;
                    if (Strings.ID_ONLY.equals(strikeMode)) {
                        linkTextTemplate = linkTextTemplate.replace("$issue", MessageFormat.format(Strings.STRIKE_THRU, thru, "$issue"));
                        summaryTextTemplate = MessageFormat.format(Strings.STRIKE_THRU, Strings.NORMAL, "$summary");
                    } else {
                        linkTextTemplate = MessageFormat.format(Strings.STRIKE_THRU, thru, linkTextTemplate);
                        summaryTextTemplate = MessageFormat.format(Strings.STRIKE_THRU, thru, "$summary");
                    }
                    final MultiUserFieldValue assignee = issue.getAssignee();
                    context.put("title", "Title: " + issue.getSummary() + ", Reporter: " + issue.getReporter() + ", Priority: " + issue.getPriority() + ", State: " +
                            issue.getState() + ", Assignee: " + (assignee == null ? Strings.UNASSIGNED : assignee.getFullName()) +
                            ", Votes: " + issue.getVotes() + ", Type: " + issue.getType());
                    context.put(Strings.ISSUE_LINK_TEXT, VelocityUtils.getRenderedContent(linkTextTemplate, context));
                    context.put(Strings.SUMMARY_FORMATTED, VelocityUtils.getRenderedContent(summaryTextTemplate, context));
                } else context.put(Strings.ERROR, "Issue not found: " + issueId);
            } else {
                context.put(Strings.ERROR, "Issue not specified.");
            }
            return VelocityUtils.getRenderedTemplate(Strings.BODY_LINK + style + Strings.TEMPLATE_EXT, context);
        } catch (Exception ex) {
            LOG.error("YouTrack link macro encounters error", ex);
            throw new MacroException(ex);
        }
    }
}