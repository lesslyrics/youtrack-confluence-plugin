package jetbrains.macros.base;

import com.atlassian.bandana.BandanaManager;
import com.atlassian.renderer.v2.macro.BaseMacro;
import com.sun.istack.internal.NotNull;

public abstract class MacroWithPersistableSettingsBase extends BaseMacro {
    @NotNull
    protected final BandanaManager bm;

    public MacroWithPersistableSettingsBase(BandanaManager bandanaManager) {
        this.bm = bandanaManager;
    }
}