package jetbrains.macros.base;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 23.01.14.
 */
public class SettingsCache implements Serializable {
    private Map<String, Object> storage = new HashMap<String, Object>();

    public SettingsCache() {
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(final @NotNull String key) {
        return (T) storage.get(key);
    }

    public <T> void setValue(final @NotNull String key, final @Nullable T value) {
        storage.put(key, value);
    }

}