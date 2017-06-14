package jetbrains.macros.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Egor.Malyshev on 6/14/2017.
 */
public class Service {

    public static Map<String, Object> createContext(String... src) {
        final Map<String, Object> result = new HashMap<String, Object>();
        for (int j = 0; j < src.length; j++) {
            result.put(defaultIfNull(src[j], Strings.UNKNOWN), j + 1 < src.length ? defaultIfNull(src[j + 1], Strings.EMPTY) : Strings.EMPTY);
            if (j < src.length) j++;
        }
        return result;
    }

    public static <T> T defaultIfNull(final T value, final T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public static String defaultIfNullOrEmpty(final String value, final String defaultValue) {
        return defaultIfNull(value, defaultValue).isEmpty() ? defaultValue : value;
    }

    public static Map<String, Object> createContext(Map<String, Object> base, String... src) {
        final Map<String, Object> result = new HashMap<String, Object>();
        result.putAll(base);
        result.putAll(createContext(src));
        return result;
    }

    public static int intValueOf(final String str, int defaultValue) {
        if (str == null) return defaultValue;
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

}
