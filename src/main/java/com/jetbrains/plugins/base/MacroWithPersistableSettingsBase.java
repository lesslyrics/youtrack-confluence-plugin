package com.jetbrains.plugins.base;

import com.atlassian.confluence.macro.Macro;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.jetbrains.plugins.util.Strings;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

@Named("MacroWithPersistableSettingsBase")
public abstract class MacroWithPersistableSettingsBase implements Macro {

    private final PluginSettingsFactory pluginSettingsFactory;

    private final TransactionTemplate transactionTemplate;

    private Properties storage;

    @Inject
    public MacroWithPersistableSettingsBase(@ComponentImport PluginSettingsFactory pluginSettingsFactory,
                                            @ComponentImport TransactionTemplate transactionTemplate) {
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


    protected String getProperty(final String key) {
        load();
        return storage.getProperty(key, Strings.EMPTY);
    }

    protected String getProperty(final String key, String defaultValue) {
        load();
        return storage.getProperty(key, defaultValue);
    }

    void setProperty(final String key, final String value) {
        storage.setProperty(key, value);
        persist();
    }
}