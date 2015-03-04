package jetbrains.macros.base;

import com.atlassian.bandana.BandanaManager;
import com.sun.istack.internal.Nullable;
import jetbrains.macros.util.SettingsManager;
import youtrack.BaseItem;
import youtrack.CommandBasedList;
import youtrack.YouTrack;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;


public abstract class YouTrackAuthAwareMacroBase extends MacroWithPersistableSettingsBase {
    protected YouTrack youTrack;

    public YouTrackAuthAwareMacroBase(BandanaManager bandanaManager) {
        super(bandanaManager);
        youTrack = YouTrack.getInstance(SettingsManager.getInstance(bm).getStoredHost());
        final String authKey = SettingsManager.getInstance(bm).getAuthKey();
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
            youTrack.login(SettingsManager.getInstance(bm).getStoredLogin(),
                    SettingsManager.getInstance(bm).getStoredPassword());
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
        SettingsManager.getInstance(bm).storeAuthKey(youTrack.getAuthorization());
    }
}