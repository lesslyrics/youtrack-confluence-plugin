package jetbrains.youtrack.macros;

import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import jetbrains.youtrack.Strings;
import jetbrains.youtrack.client.IssuePresentation;
import jetbrains.youtrack.client.YouTrackClientFactory;
import jetbrains.youtrack.client.api.Issue;
import jetbrains.youtrack.client.api.User;
import jetbrains.youtrack.settings.YouTrackClientService;
import org.slf4j.Logger;

import java.text.MessageFormat;
import java.util.Map;

import static jetbrains.youtrack.Strings.*;


public abstract class YouTrackAuthAwareMacroBase extends MacroWithPersistableSettingsBase {

    public YouTrackAuthAwareMacroBase(PluginSettingsFactory pluginSettingsFactory,
                                      TransactionTemplate transactionTemplate) {
        super(pluginSettingsFactory, transactionTemplate);
        YouTrackClientService.useClientIfAbsent(() -> YouTrackClientFactory.newClient(getProperty(HOST), getProperty(AUTH_KEY), Boolean.parseBoolean(getProperty(TRUST_ALL))));
    }

    protected abstract String getLoggingPrefix();

    protected abstract Logger getLogger();

    protected void logMessage(final String msg) {
        if (getProperty(EXTENDED_DEBUG, "false").equals("true")) getLogger().warn(getLoggingPrefix() + msg);
    }

    protected void setContext(Map<String, Object> context, String strikeMode, String linkTextTemplate, Issue issue) {
        String summaryTextTemplate;
        IssuePresentation issuePresentation = new IssuePresentation(issue);
        Map<String, String> fields = issuePresentation.getFieldValues();
        for (final String fieldName : fields.keySet()) {
            context.put(fieldName, fields.get(fieldName));
        }
        String idReadable = issue.getIdReadable();
        if (idReadable != null) {
            context.put(ISSUE, idReadable);
        } else {
            context.put(ISSUE, issue.getProject().getShortName() + "-???");
        }
        context.put(BASE, Strings.fixURL(getProperty(HOST)));
        String linkBase = getProperty(LINKBASE);
        if (null != linkBase && !linkBase.isEmpty()) {
            context.put(LINKBASE, Strings.fixURL(linkBase));
        } else {
            context.put(LINKBASE, Strings.fixURL(getProperty(HOST)));
        }
        final String thru = (ALL.equals(strikeMode) || ID_ONLY.equals(strikeMode)) && issue.getResolved() != null ? "line-through" : NORMAL;
        if (ID_ONLY.equals(strikeMode)) {
            linkTextTemplate = linkTextTemplate.replace("$issue", MessageFormat.format(STRIKE_THRU, thru, "$issue"));
            summaryTextTemplate = MessageFormat.format(STRIKE_THRU, NORMAL, "$summary");
        } else {
            linkTextTemplate = MessageFormat.format(STRIKE_THRU, thru, linkTextTemplate);
            summaryTextTemplate = MessageFormat.format(STRIKE_THRU, thru, "$summary");
        }
        final String assignee = issuePresentation.getAssignee();
        User reporter = issue.getReporter();
        context.put("title", "Title: " + issue.getSummary() + ", Reporter: " + (reporter.getFullName() == null ? reporter.getLogin() : reporter.getFullName()) + ", Priority: " + issuePresentation.getPriority() + ", State: " +
                issuePresentation.getState() + ", Assignee: " + (assignee == null ? UNASSIGNED : assignee) +
                ", Votes: " + issue.getVotes() + ", Type: " + issuePresentation.getType());
        context.put(ISSUE_LINK_TEXT, VelocityUtils.getRenderedContent(linkTextTemplate, context));
        context.put(SUMMARY_FORMATTED, VelocityUtils.getRenderedContent(summaryTextTemplate, context));
    }
}