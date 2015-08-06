package jetbrains.macros.base;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import jetbrains.macros.util.Strings;
import youtrack.BaseItem;
import youtrack.CommandBasedList;
import youtrack.YouTrack;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NotLoggedInException;
public abstract class YouTrackAuthAwareMacroBase extends MacroWithPersistableSettingsBase {
    protected YouTrack youTrack;
    public YouTrackAuthAwareMacroBase(PluginSettingsFactory pluginSettingsFactory,
                                      TransactionTemplate transactionTemplate) {
        super(pluginSettingsFactory, transactionTemplate);
        init();
    }
    private void init() {
        youTrack = YouTrack.getInstance(getProperty(Strings.HOST));
        youTrack.setAuthorization(Strings.AUTH_KEY);
    }
    protected <O extends BaseItem, I extends BaseItem<O>> I tryGetItem(final CommandBasedList<O, I> list, final String id, final boolean retrying)
            throws CommandExecutionException, AuthenticationErrorException {
        I result = null;
        try {
            if(!getProperty(Strings.HOST).equals(youTrack.getHostAddress())) init();
            result = list.item(id);
        } catch(CommandExecutionException e) {
            if((((e.getError() != null && e.getError().getCode() == 403)) ||
                    (e.getInnerException() instanceof NotLoggedInException)) && !retrying) {
                youTrack.login(getProperty(Strings.LOGIN), getProperty(Strings.PASSWORD));
                setProperty(Strings.AUTH_KEY, youTrack.getAuthorization());
                return tryGetItem(list, id, true);
            }
        }
        return result;
    }
}