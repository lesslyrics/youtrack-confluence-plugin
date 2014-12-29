package jetbrains.macros.base;

import com.atlassian.bandana.BandanaManager;
import com.sun.istack.internal.Nullable;
import youtrack.BaseItem;
import youtrack.CommandBasedList;
import youtrack.YouTrack;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;
import youtrack.exceptions.NoSuchIssueFieldException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public abstract class YouTrackAuthAwareMacroBase extends MacroWithPersistableSettingsBase {
    public static final String YOUTRACK_AUTH_KEY = "youtrack-auth-key";
    protected final String userName;
    protected final String password;
    protected final String baseHost;
    protected YouTrack youTrack;

    public YouTrackAuthAwareMacroBase(BandanaManager bandanaManager) throws IOException, AuthenticationErrorException, NoSuchIssueFieldException, CommandExecutionException {
        super(bandanaManager);
        final Properties prop = new Properties();
        final ClassLoader loader = getClass().getClassLoader();
        final InputStream stream = loader.getResourceAsStream("/resources/settings.properties");
        prop.load(stream);
        userName = prop.getProperty("username");
        password = prop.getProperty("password");
        baseHost = prop.getProperty("host");
        youTrack = YouTrack.getInstance(baseHost);
        String cachedAuthKey = (String) settings.storage.get(YOUTRACK_AUTH_KEY);
        if (cachedAuthKey != null) {
            youTrack.setAuthorization(cachedAuthKey);
        } else {
            youTrack.login(userName, password);
            storeCurrentAuth();
        }
    }

    @Nullable
    protected <O extends BaseItem, I extends BaseItem> I tryGetItem(CommandBasedList<O, I> list, String id) throws CommandExecutionException, IOException, NoSuchIssueFieldException, AuthenticationErrorException {
        I result = null;
        try {
            result = list.item(id);
        } catch (CommandExecutionException e) {
            if (isErrorLoginExpired(e.getError())) {
                refreshStoredAuthKey();
                storeCurrentAuth();
                result = list.item(id);
            }
        }
        return result;
    }

    protected void refreshStoredAuthKey() {
        try {
            youTrack.login(userName, password);
            storeCurrentAuth();
        } catch (AuthenticationErrorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchIssueFieldException e) {
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