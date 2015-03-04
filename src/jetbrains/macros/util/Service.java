package jetbrains.macros.util;

import com.atlassian.bandana.BandanaContext;
import com.atlassian.bandana.BandanaManager;
import com.atlassian.confluence.setup.bandana.ConfluenceBandanaContext;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import jetbrains.macros.base.SettingsCache;

/**
 * Created by Egor.Malyshev on 04.03.2015.
 */
public class Service {
    public static final ConfluenceBandanaContext CONTEXT = new ConfluenceBandanaContext();
    public static final String SETTINGS_KEY = "jetbrains-macro-storage-storage-key#02027015";
    public static final String HOST = "host";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    @Nullable
    public static SettingsCache getSettingsCache(final @NotNull BandanaManager bm) {
        return (SettingsCache) bm.getValue(new ConfluenceBandanaContext(), SETTINGS_KEY);
    }

    public static void storeSettinsCache(final @NotNull SettingsCache cache,
                                         final @NotNull BandanaManager bm,
                                         final @NotNull BandanaContext bc) {
        bm.setValue(bc, SETTINGS_KEY, cache);
    }

    @Nullable
    public static String getStoredHost(final @NotNull SettingsCache cache) {
        return cache.getValue(HOST);
    }

    @Nullable
    public static String getStoredLogin(final @NotNull SettingsCache cache) {
        return cache.getValue(LOGIN);
    }

    @Nullable
    public static String getStoredPassword(final @NotNull SettingsCache cache) {
        return cache.getValue(PASSWORD);
    }

    public static void storeHost(final @Nullable String host, final @NotNull SettingsCache cache) {
        cache.setValue(HOST, host);
    }

    public static void storeLogin(final @Nullable String login, final @NotNull SettingsCache cache) {
        cache.setValue(LOGIN, login);
    }

    public static void storePassword(final @Nullable String password, final @NotNull SettingsCache cache) {
        cache.setValue(PASSWORD, password);
    }
}
