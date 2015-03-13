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
    private final PluginSettingsFactory pluginSettingsFactory;
    @NotNull
    private final TransactionTemplate transactionTemplate;
    @NotNull
    private Properties storage;

    public MacroWithPersistableSettingsBase(PluginSettingsFactory pluginSettingsFactory,
                                            TransactionTemplate transactionTemplate) {
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.transactionTemplate = transactionTemplate;
        load();
    }

    private void persist() {
        transactionTemplate.execute(new TransactionCallback<Properties>() {
            @Override
            public Properties doInTransaction() {
                final PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
                pluginSettings.put(Strings.MAIN_KEY, storage);
                return null;
            }
        });
    }

    private void load() {
        storage = transactionTemplate.execute(new TransactionCallback<Properties>() {
            @Override
            public Properties doInTransaction() {
                final PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
                final Object o = pluginSettings.get(Strings.MAIN_KEY);
                return o instanceof Properties ? (Properties) o : null;
            }
        });
        if (storage == null) storage = new Properties();
    }

    @NotNull
    protected String getProperty(final @NotNull String key) {
        load();
        return storage.getProperty(key, Strings.EMPTY);
    }

    protected void setProperty(final @NotNull String key, final @Nullable String value) {
        storage.setProperty(key, value);
        persist();
    }
}