package jetbrains.macros.util;

import com.atlassian.bandana.BandanaManager;
import com.atlassian.confluence.setup.bandana.ConfluenceBandanaContext;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Egor.Malyshev on 04.03.2015.
 */
public class SettingsManager {

    public static final String PREFIX = "jetbrains-macro-storage-key#04047015";
    public static final String YOUTRACK_AUTH_KEY = PREFIX + "auth-key";
    public static final String HOST = PREFIX + "host";
    public static final String LOGIN = PREFIX + "login";
    public static final String PASSWORD = PREFIX + "password";
    public static final String EMPTY_STRING = "";
    private static final Map<BandanaManager, SettingsManager> INSTANCES = new HashMap<BandanaManager, SettingsManager>();
    private final ConfluenceBandanaContext context = new ConfluenceBandanaContext();
    private final BandanaManager bm;

    private SettingsManager(final @NotNull BandanaManager bm) {
        this.bm = bm;
    }

    public static SettingsManager getInstance(final @NotNull BandanaManager bm) {
        if (!INSTANCES.containsKey(bm)) INSTANCES.put(bm, new SettingsManager(bm));
        return INSTANCES.get(bm);
    }

    @SuppressWarnings("unchecked")
    private <T> T get(String key) {
        return (T) bm.getValue(context, key);
    }

    @Nullable
    public String getAuthKey() {
        return get(YOUTRACK_AUTH_KEY);
    }

    @Nullable
    public String getStoredHost() {
        return get(HOST);
    }

    @Nullable
    public String getStoredLogin() {
        return get(LOGIN);
    }

    @Nullable
    public String getStoredPassword() {
        return get(PASSWORD);
    }

    public void storeHost(final @Nullable String host) {
        bm.setValue(context, HOST, host);
    }

    public void storeLogin(final @Nullable String login) {
        bm.setValue(context, LOGIN, login);
    }

    public void storePassword(final @Nullable String password) {
        bm.setValue(context, PASSWORD, password);
    }

    public void storeAuthKey(final @NotNull String authKey) {
        bm.setValue(context, YOUTRACK_AUTH_KEY, authKey);
    }
}