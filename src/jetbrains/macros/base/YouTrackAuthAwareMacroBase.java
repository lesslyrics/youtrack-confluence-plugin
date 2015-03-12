package jetbrains.macros.base;

import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.sun.istack.internal.Nullable;
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
        youTrack = YouTrack.getInstance(storage.getProperty(Strings.HOST, ""));
        final String authKey = storage.getProperty(Strings.AUTH_KEY);
        if (authKey != null) {
            youTrack.setAuthorization(authKey);
        } else {
            refreshStoredAuthKey();
        }
    }

    @Nullable
    protected <O extends BaseItem, I extends BaseItem> I tryGetItem(CommandBasedList<O, I> list, String id) throws CommandExecutionException, AuthenticationErrorException {
        I result;
        try {
            result = list.item(id);
            if (result == null) {
                refreshStoredAuthKey();
                result = list.item(id);
            }
        } catch (CommandExecutionException e) {
            refreshStoredAuthKey();
            result = list.item(id);
        }
        return result;
    }

    protected void refreshStoredAuthKey() {
        try {
            youTrack.login(storage.getProperty(Strings.LOGIN),
                    storage.getProperty(Strings.PASSWORD));
            storeCurrentAuth();
        } catch (AuthenticationErrorException e) {
            e.printStackTrace();
        } catch (CommandExecutionException e) {
            e.printStackTrace();
        }
    }

    protected void storeCurrentAuth() {
        set(Strings.MAIN_KEY, storage);
    }
}