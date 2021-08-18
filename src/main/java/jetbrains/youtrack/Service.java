package jetbrains.youtrack;

import java.util.HashMap;
import java.util.Map;

public class Service {

    public static Map<String, Object> createContext(final String... src) {
        final Map<String, Object> result = new HashMap<>();
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
        return isEmpty(value) ? defaultValue : value;
    }

    public static Map<String, Object> createContext(final Map<String, Object> base, final String... src) {
        final Map<String, Object> result = new HashMap<>();
        result.putAll(base);
        result.putAll(createContext(src));
        return result;
    }

    public static int intValueOf(final String str, final int defaultValue) {
        if (str == null) return defaultValue;
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static boolean isEmpty(final String s) {
        return s == null || s.trim().isEmpty();
    }
}
