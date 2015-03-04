package jetbrains.macros.base;

import com.atlassian.bandana.BandanaManager;
import com.atlassian.renderer.v2.macro.BaseMacro;
import com.sun.istack.internal.NotNull;
import jetbrains.macros.util.Service;

public abstract class MacroWithPersistableSettingsBase extends BaseMacro {
    @NotNull
    protected final BandanaManager bm;
    @NotNull
    protected SettingsCache settings;

    public MacroWithPersistableSettingsBase(BandanaManager bandanaManager) {
        this.bm = bandanaManager;
        settings = Service.getSettingsCache(bm);
        if (settings == null) {
            settings = new SettingsCache();
            persist();
        }
    }

    protected void persist() {
        Service.storeSettinsCache(settings, bm, Service.CONTEXT);
    }
}