package com.jetbrains.plugins.base;

import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.jetbrains.plugins.util.Service;
import com.jetbrains.plugins.util.Strings;
import org.slf4j.Logger;
import youtrack.BaseItem;
import youtrack.CommandBasedList;
import youtrack.Issue;
import youtrack.YouTrack;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.CommandNotAvailableException;
import youtrack.issues.fields.BaseIssueField;
import youtrack.issues.fields.values.MultiUserFieldValue;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jetbrains.plugins.util.Strings.*;


public abstract class YouTrackAuthAwareMacroBase extends MacroWithPersistableSettingsBase {
    protected YouTrack youTrack;
    protected final int retries = Service.intValueOf(getProperty(Strings.RETRIES), 10);

    public YouTrackAuthAwareMacroBase(@ComponentImport PluginSettingsFactory pluginSettingsFactory,
                                      @ComponentImport TransactionTemplate transactionTemplate) {
        super(pluginSettingsFactory, transactionTemplate);
        youTrack = YouTrack.getInstance(getProperty(Strings.HOST), Boolean.parseBoolean(getProperty(Strings.TRUST_ALL)));
    }

    protected abstract String getLoggingPrefix();

    protected abstract Logger getLogger();

    private boolean isLoginAuth() {
        return !"true".equals(getProperty(USE_TOKEN));
    }

    private void init() throws AuthenticationErrorException, IOException, CommandExecutionException {
        if (youTrack.getHostAddress().isEmpty()) {
            youTrack = YouTrack.getInstance(getProperty(Strings.HOST), Boolean.parseBoolean(getProperty(Strings.TRUST_ALL)));
        }
        if (isLoginAuth()) {
            youTrack.setUseTokenAuthorization(false);
            youTrack.login(getProperty(Strings.LOGIN), getProperty(Strings.PASSWORD));
        } else {
            youTrack.setUseTokenAuthorization(true);
            youTrack.setAuthorization(getProperty(Strings.AUTH_KEY));
        }
    }

    protected <O extends BaseItem, I extends BaseItem<O>> I tryCreateItem(final CommandBasedList<O, I> list, I issue)
            throws CommandExecutionException, AuthenticationErrorException, IOException, CommandNotAvailableException {
        init();
        return list.add(issue);
    }

    protected <O extends BaseItem, I extends BaseItem<O>> I tryGetItem(final CommandBasedList<O, I> list, final String id, final int retry)
            throws CommandExecutionException, AuthenticationErrorException, IOException, CommandNotAvailableException {
        try {
            init();
            return list.item(id);
        } catch (CommandExecutionException e) {
            if (retry > 0 && isLoginAuth()) {
                youTrack.login(getProperty(Strings.LOGIN), getProperty(Strings.PASSWORD));
                setProperty(Strings.AUTH_KEY, youTrack.getAuthorization());
                return tryGetItem(list, id, retry - 1);
            }
        }
        return null;
    }

    protected void logMessage(final String msg) {
        if (getProperty(Strings.EXTENDED_DEBUG, "false").equals("true")) getLogger().warn(getLoggingPrefix() + msg);
    }

    protected <O extends BaseItem, I extends BaseItem<O>> List<I> tryQuery(final CommandBasedList<O, I> list, final String query, final int start, final int pageSize, final int retry)
            throws CommandExecutionException, AuthenticationErrorException, IOException, CommandNotAvailableException {
        try {
            init();
            return list.query(query, start, pageSize);
        } catch (CommandExecutionException e) {
            if (retry > 0 && isLoginAuth()) {
                youTrack.login(getProperty(Strings.LOGIN), getProperty(Strings.PASSWORD));
                setProperty(Strings.AUTH_KEY, youTrack.getAuthorization());
                return tryQuery(list, query, start, pageSize, retry - 1);
            }
        }
        return Collections.emptyList();
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
        context.put(ISSUE_LINK_TEXT, VelocityUtils.getRenderedContent((CharSequence) linkTextTemplate, context));
        context.put(SUMMARY_FORMATTED, VelocityUtils.getRenderedContent((CharSequence) summaryTextTemplate, context));
    }
}