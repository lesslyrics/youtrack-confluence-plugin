package jetbrains.macros.base;

import com.atlassian.renderer.v2.macro.BaseMacro;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import jetbrains.macros.util.Strings;

import java.util.Properties;

public abstract class MacroWithPersistableSettingsBase extends BaseMacro {
    @NotNull
    protected final PluginSettingsFactory pluginSettingsFactory;
    protected final TransactionTemplate transactionTemplate;
    protected Properties storage;

    public MacroWithPersistableSettingsBase(PluginSettingsFactory pluginSettingsFactory,
                                            TransactionTemplate transactionTemplate) {
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.transactionTemplate = transactionTemplate;
        storage = get(Strings.MAIN_KEY);
        if (storage == null) storage = new Properties();
    }

    protected <T> void set(final @NotNull String key, final @Nullable T value) {
        final PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
        pluginSettings.put(key, value);
    }

    protected <T> T get(final @NotNull String key) {
        return transactionTemplate.execute(new TransactionCallback<T>() {
            @Override
            public T doInTransaction() {
                final PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
                //noinspection unchecked
                return (T) pluginSettings.get(key);
            }
        });
    }
}