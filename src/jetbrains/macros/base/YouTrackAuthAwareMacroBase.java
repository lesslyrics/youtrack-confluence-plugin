package jetbrains.macros.base;

import com.atlassian.bandana.BandanaManager;
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

    protected void storeCurrentAuth() {
        settings.storage.put(YOUTRACK_AUTH_KEY, youTrack.getAuthorization());
        persist();
    }
}