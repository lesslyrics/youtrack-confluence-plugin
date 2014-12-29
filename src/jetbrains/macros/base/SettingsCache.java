package jetbrains.macros.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by egor.malyshev on 23.01.14.
 */
public class SettingsCache implements Serializable {
    Map<String, Object> storage = new HashMap<String, Object>();

    public SettingsCache() {
    }
}