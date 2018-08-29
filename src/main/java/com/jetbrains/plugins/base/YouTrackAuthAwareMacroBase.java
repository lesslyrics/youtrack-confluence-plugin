package com.jetbrains.plugins.base;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.jetbrains.plugins.util.Service;
import com.jetbrains.plugins.util.Strings;
import org.slf4j.Logger;
import youtrack.BaseItem;
import youtrack.CommandBasedList;
import youtrack.YouTrack;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.CommandNotAvailableException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.jetbrains.plugins.util.Strings.USE_TOKEN;


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

    private void init(String forSpace) {
        String authKey = getProperty(forSpace + Strings.AUTH_KEY);
        if (authKey.isEmpty()) authKey = getProperty(Strings.AUTH_KEY);
        youTrack.setAuthorization(authKey);
    }

    protected <O extends BaseItem, I extends BaseItem<O>> I tryGetItem(final CommandBasedList<O, I> list, final String id, final int retry, final String forSpace)
            throws CommandExecutionException, AuthenticationErrorException, IOException, CommandNotAvailableException {
        try {
            init(forSpace);
            return list.item(id);
        } catch (CommandExecutionException e) {
            if (retry > 0 && !"true".equals(getProperty(USE_TOKEN))) {
                youTrack.login(getProperty(Strings.LOGIN), getProperty(Strings.PASSWORD));
                setProperty(Strings.AUTH_KEY, youTrack.getAuthorization());
                return tryGetItem(list, id, retry - 1, forSpace);
            }
        }
        return null;
    }

    protected void logMessage(final String msg) {
        if (getProperty(Strings.EXTENDED_DEBUG, "false").equals("true")) getLogger().warn(getLoggingPrefix() + msg);
    }

    protected <O extends BaseItem, I extends BaseItem<O>> List<I> tryQuery(final CommandBasedList<O, I> list, final String query, final int start, final int pageSize, final int retry, final String forSpace)
            throws CommandExecutionException, AuthenticationErrorException, IOException, CommandNotAvailableException {
        try {
            init(forSpace);
            return list.query(query, start, pageSize);
        } catch (CommandExecutionException e) {
            if (retry > 0 && !"true".equals(getProperty(USE_TOKEN))) {
                youTrack.login(getProperty(Strings.LOGIN), getProperty(Strings.PASSWORD));
                setProperty(Strings.AUTH_KEY, youTrack.getAuthorization());
                return tryQuery(list, query, start, pageSize, retry - 1, forSpace);
            }
        }
        return Collections.emptyList();
    }
}