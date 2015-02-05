package jetbrains.macros.base;

import com.atlassian.bandana.BandanaManager;
import com.atlassian.confluence.setup.bandana.ConfluenceBandanaContext;
import com.atlassian.renderer.v2.macro.BaseMacro;
import com.sun.istack.internal.NotNull;

public abstract class MacroWithPersistableSettingsBase extends BaseMacro {
    public static final String SETTINGS_KEY = "jetbrains-macro-storage-storage-key#02022015";
    @NotNull
    protected final BandanaManager bm;
    @NotNull
    protected SettingsCache settings;

    public MacroWithPersistableSettingsBase(BandanaManager bandanaManager) {
        this.bm = bandanaManager;
        settings = (SettingsCache) bm.getValue(new ConfluenceBandanaContext(), SETTINGS_KEY);
        if (settings == null) {
            settings = new SettingsCache();
            persist();
        }
    }

    protected void persist() {
        bm.setValue(new ConfluenceBandanaContext(), SETTINGS_KEY, settings);
    }
}