package issuehighlighter;

import java.io.Serializable;

/**
 * Created by egor.malyshev on 23.01.14.
 */
public class SettingsCache implements Serializable {
    private String authKey;

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
    public SettingsCache() {

    }
}
