package jetbrains.macros.base;

import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import jetbrains.macros.util.Service;
import jetbrains.macros.util.Strings;
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


public abstract class YouTrackAuthAwareMacroBase extends MacroWithPersistableSettingsBase {
    protected YouTrack youTrack;
    protected final int retries = Service.intValueOf(getProperty(Strings.RETRIES), 10);

    public YouTrackAuthAwareMacroBase(PluginSettingsFactory pluginSettingsFactory,
                                      TransactionTemplate transactionTemplate) {
        super(pluginSettingsFactory, transactionTemplate);
        init();
    }

    private void init() {
        youTrack = YouTrack.getInstance(getProperty(Strings.HOST), Boolean.parseBoolean(getProperty(Strings.TRUST_ALL)));
        youTrack.setAuthorization(Strings.AUTH_KEY);
    }

    protected <O extends BaseItem, I extends BaseItem<O>> I tryGetItem(final CommandBasedList<O, I> list, final String id, final int retry)
            throws CommandExecutionException, AuthenticationErrorException, IOException, CommandNotAvailableException {
        try {
            if (!getProperty(Strings.HOST).equals(youTrack.getHostAddress())) init();
            return list.item(id);
        } catch (CommandExecutionException e) {
            if (retry > 0) {
                youTrack.login(getProperty(Strings.LOGIN), getProperty(Strings.PASSWORD));
                setProperty(Strings.AUTH_KEY, youTrack.getAuthorization());
                return tryGetItem(list, id, retry - 1);
            }
        }
        return null;
    }

    protected void debugMessage(final Logger logger, final String msg) {
        if (getProperty(Strings.EXTENDED_DEBUG, "false").equals("true")) logger.debug(msg);
    }

    protected <O extends BaseItem, I extends BaseItem<O>> List<I> tryQuery(final CommandBasedList<O, I> list, final String query, final int start, final int pageSize, final int retry)
            throws CommandExecutionException, AuthenticationErrorException, IOException, CommandNotAvailableException {
        try {
            if (!getProperty(Strings.HOST).equals(youTrack.getHostAddress())) init();
            return list.query(query, start, pageSize);
        } catch (CommandExecutionException e) {
            if (retry > 0) {
                youTrack.login(getProperty(Strings.LOGIN), getProperty(Strings.PASSWORD));
                setProperty(Strings.AUTH_KEY, youTrack.getAuthorization());
                return tryQuery(list, query, start, pageSize, retry - 1);
            }
        }
        return Collections.emptyList();
    }
}