package jetbrains.macros.base;

import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import jetbrains.macros.util.Strings;
import youtrack.BaseItem;
import youtrack.CommandBasedList;
import youtrack.YouTrack;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;


public abstract class YouTrackAuthAwareMacroBase extends MacroWithPersistableSettingsBase {
    protected YouTrack youTrack;

    public YouTrackAuthAwareMacroBase(PluginSettingsFactory pluginSettingsFactory,
                                      TransactionTemplate transactionTemplate) {
        super(pluginSettingsFactory, transactionTemplate);
        youTrack = YouTrack.getInstance(getProperty(Strings.HOST));
        youTrack.setAuthorization(getProperty(Strings.AUTH_KEY));
    }


    protected <O extends BaseItem, I extends BaseItem> I tryGetItem(CommandBasedList<O, I> list, String id)
            throws CommandExecutionException, AuthenticationErrorException {
        I result;
        try {
            result = list.item(id);
        } catch (CommandExecutionException e) {
            refreshStoredAuthKey();
            result = list.item(id);
        }
        return result;
    }

    protected void refreshStoredAuthKey() throws AuthenticationErrorException {
        try {
            youTrack.login(getProperty(Strings.LOGIN),
                    getProperty(Strings.PASSWORD));
            setProperty(Strings.AUTH_KEY, youTrack.getAuthorization());
        } catch (CommandExecutionException e) {
            e.printStackTrace();
        }
    }
}