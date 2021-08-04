package jetbrains.macros.base;

import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import jetbrains.macros.util.Service;
import org.slf4j.Logger;
import youtrack.BaseItem;
import youtrack.CommandBasedList;
import youtrack.Issue;
import youtrack.YouTrack;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.CommandNotAvailableException;
import youtrack.issues.fields.BaseIssueField;
import youtrack.issues.fields.values.MultiUserFieldValue;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static jetbrains.macros.util.Strings.*;


public abstract class YouTrackAuthAwareMacroBase extends MacroWithPersistableSettingsBase {
    protected YouTrack youTrack;
    protected final int retries = Service.intValueOf(getProperty(RETRIES), 10);

    public YouTrackAuthAwareMacroBase(PluginSettingsFactory pluginSettingsFactory,
                                      TransactionTemplate transactionTemplate) {
        super(pluginSettingsFactory, transactionTemplate);
        youTrack = YouTrack.getInstance(getProperty(HOST), Boolean.parseBoolean(getProperty(TRUST_ALL)));
    }

    protected abstract String getLoggingPrefix();

    protected abstract Logger getLogger();

    private void init() {
        if (youTrack.getHostAddress().isEmpty()) {
            youTrack = YouTrack.getInstance(getProperty(HOST), Boolean.parseBoolean(getProperty(TRUST_ALL)));
        }
        youTrack.setUseTokenAuthorization(true);
        youTrack.setAuthorization(getProperty(AUTH_KEY));
    }

    protected <O extends BaseItem, I extends BaseItem<O>> I tryGetItem(final CommandBasedList<O, I> list, final String id, final int retry)
            throws CommandExecutionException, IOException, CommandNotAvailableException {
        init();
        return list.item(id);
    }

    protected void logMessage(final String msg) {
        if (getProperty(EXTENDED_DEBUG, "false").equals("true")) getLogger().warn(getLoggingPrefix() + msg);
    }

    protected void setContext(Map<String, Object> context, String strikeMode, String linkTextTemplate, Issue issueModel) throws IOException, CommandExecutionException {
        String summaryTextTemplate;
        Issue issue = issueModel.createSnapshot();
        final HashMap<String, BaseIssueField> fields = issue.getFields();
        for (final String fieldName : fields.keySet()) {
            context.put(fieldName, fields.get(fieldName).getStringValue());
        }
        if (issueModel.getId() != null) {
            context.put(ISSUE, issueModel.getId());
        } else {
            context.put(ISSUE, issueModel.getProjectId() + "-???");
        }
        context.put(BASE, getProperty(HOST).replace(REST_PREFIX, EMPTY));
        context.put(LINKBASE, getProperty(HOST).replace(REST_PREFIX, EMPTY));
        String linkBase = getProperty(LINKBASE);
        if (null != linkBase && !linkBase.isEmpty()) {
            context.put(LINKBASE, linkBase.replace(REST_PREFIX, EMPTY));
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
    }
}