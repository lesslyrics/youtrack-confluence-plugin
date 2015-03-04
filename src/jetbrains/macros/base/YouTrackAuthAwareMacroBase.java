package jetbrains.macros.base;

import com.atlassian.bandana.BandanaManager;
import com.sun.istack.internal.Nullable;
import youtrack.BaseItem;
import youtrack.CommandBasedList;
import youtrack.YouTrack;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;

import java.io.IOException;


public abstract class YouTrackAuthAwareMacroBase extends MacroWithPersistableSettingsBase {
    public static final String YOUTRACK_AUTH_KEY = "youtrack-auth-key-cached";
    protected String userName;
    protected String password;
    protected String baseHost;
    protected YouTrack youTrack;
    protected String cachedAuthKey;

    public YouTrackAuthAwareMacroBase(BandanaManager bandanaManager) throws IOException, AuthenticationErrorException, CommandExecutionException {
        super(bandanaManager);
        init();
        if (cachedAuthKey != null) {
            youTrack.setAuthorization(cachedAuthKey);
        } else {
            refreshStoredAuthKey();
        }
    }

    private void init() {
        baseHost = settings.getValue("host");
        baseHost = settings.getValue("host");
        userName = settings.getValue("login");
        password = settings.getValue("password");
        youTrack = YouTrack.getInstance(baseHost);
        cachedAuthKey = settings.getValue(YOUTRACK_AUTH_KEY);
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
            init();
            youTrack.login(userName, password);
            storeCurrentAuth();
        } catch (AuthenticationErrorException e) {
            e.printStackTrace();
        } catch (CommandExecutionException e) {
            e.printStackTrace();
        }
    }

    protected boolean isErrorLoginExpired(final @Nullable youtrack.Error error) {
        return error != null && error.getCode() == 403;
    }

    protected void storeCurrentAuth() {
        settings.setValue(YOUTRACK_AUTH_KEY, youTrack.getAuthorization());
        persist();
    }
}