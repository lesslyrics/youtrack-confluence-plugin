package com.jetbrains.plugins.macro;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.jetbrains.plugins.base.YouTrackAuthAwareMacroBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import youtrack.Issue;
import youtrack.issues.fields.BaseIssueField;
import youtrack.issues.fields.values.MultiUserFieldValue;

import javax.inject.Inject;
import javax.inject.Named;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static com.jetbrains.plugins.util.Strings.*;

@Named("IssueLink")
public class IssueLink extends YouTrackAuthAwareMacroBase {
    private static final Logger LOG = LoggerFactory.getLogger(IssueLink.class);
    private static final String logPrefix = "YTMacro-LinkDebug: ";

    @Inject
    public IssueLink(@ComponentImport PluginSettingsFactory pluginSettingsFactory, @ComponentImport TransactionTemplate transactionTemplate) {
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

    public String execute(Map<String, String> params, String s, ConversionContext conversionContext) throws MacroExecutionException {
        try {
            final Map<String, Object> context = MacroUtils.defaultVelocityContext();
            final String issueId = (String) params.get(ID);
            String strikeMode = (String) params.get(STRIKE_THRU_PARAM);
            if (strikeMode == null) strikeMode = ID_ONLY;
            String linkTextTemplate = (String) params.get(TEMPLATE_PARAM);
            String summaryTextTemplate;
            if (linkTextTemplate == null || linkTextTemplate.isEmpty()) linkTextTemplate = DEFAULT_TEMPLATE;
            String style = (String) params.get(STYLE);
            if (!DETAILED.equals(style)) {
                style = SHORT;
            }
            if (issueId != null && !issueId.isEmpty()) {
                Issue issue = tryGetItem(youTrack.issues, issueId, retries, conversionContext.getSpaceKey());
                if (issue != null) {
                    issue = issue.createSnapshot();
                    final HashMap<String, BaseIssueField> fields = issue.getFields();
                    for (final String fieldName : fields.keySet()) {
                        context.put(fieldName, fields.get(fieldName).getStringValue());
                    }
                    context.put(ISSUE, issueId);
                    context.put(BASE, getProperty(HOST).replace(REST_PREFIX, EMPTY));
                    context.put(LINKBASE, getProperty(HOST).replace(REST_PREFIX, EMPTY));
                    String linkbase = getProperty(LINKBASE);
                    if (null != linkbase && !linkbase.isEmpty()) {
                        context.put(LINKBASE, linkbase.replace(REST_PREFIX, EMPTY));
                    }
                    final String thru = (ALL.equals(strikeMode) || ID_ONLY.equals(strikeMode)) && issue.isResolved() ? "line-through" : NORMAL;
                    if (ID_ONLY.equals(strikeMode)) {
                        linkTextTemplate = linkTextTemplate.replace("$issue", MessageFormat.format(STRIKE_THRU, thru, "$issue"));
                        summaryTextTemplate = MessageFormat.format(STRIKE_THRU, NORMAL, "$summary");
                    } else {
                        linkTextTemplate = MessageFormat.format(STRIKE_THRU, thru, linkTextTemplate);
                        summaryTextTemplate = MessageFormat.format(STRIKE_THRU, thru, "$summary");
                    }
                    final MultiUserFieldValue assignee = issue.getAssignee();
                    context.put("title", "Title: " + issue.getSummary() + ", Reporter: " + issue.getReporter() + ", Priority: " + issue.getPriority() + ", State: " +
                            issue.getState() + ", Assignee: " + (assignee == null ? UNASSIGNED : assignee.getFullName()) +
                            ", Votes: " + issue.getVotes() + ", Type: " + issue.getType());
                    context.put(ISSUE_LINK_TEXT, VelocityUtils.getRenderedContent(linkTextTemplate, context));
                    context.put(SUMMARY_FORMATTED, VelocityUtils.getRenderedContent(summaryTextTemplate, context));
                } else context.put(ERROR, "Issue not found: " + issueId);
            } else {
                context.put(ERROR, "Issue not specified.");
            }
            return VelocityUtils.getRenderedTemplate(BODY_LINK + style + TEMPLATE_EXT, context);
        } catch (Exception ex) {
            LOG.error("YouTrack link macro encounters error", ex);
            throw new MacroExecutionException(ex);
        }
    }

    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }
}