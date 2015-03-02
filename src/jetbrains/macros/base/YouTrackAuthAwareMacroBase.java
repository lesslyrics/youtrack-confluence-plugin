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
    protected final String userName;
    protected final String password;
    protected final String baseHost;
    protected YouTrack youTrack;

    public YouTrackAuthAwareMacroBase(BandanaManager bandanaManager) throws IOException, AuthenticationErrorException, CommandExecutionException {
        super(bandanaManager);
        baseHost = (String) settings.storage.get("host");
        userName = (String) settings.storage.get("login");
        password = (String) settings.storage.get("password");
        youTrack = YouTrack.getInstance(baseHost);
        String cachedAuthKey = (String) settings.storage.get(YOUTRACK_AUTH_KEY);
        if (cachedAuthKey != null) {
            youTrack.setAuthorization(cachedAuthKey);
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
        settings.storage.put(YOUTRACK_AUTH_KEY, youTrack.getAuthorization());
        persist();
    }
}