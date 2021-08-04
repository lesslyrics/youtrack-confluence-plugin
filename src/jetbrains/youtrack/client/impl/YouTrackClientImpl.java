package jetbrains.youtrack.client.impl;

import jetbrains.youtrack.client.YouTrackClient;
import jetbrains.youtrack.client.api.YouTrackAPI;
import org.apache.commons.lang3.StringUtils;

public class YouTrackClientImpl implements YouTrackClient {

    private final YouTrackAPIImpl api;

    public YouTrackClientImpl(String url, String token, boolean trustAll) {
        if (token == null) {
            throw new IllegalStateException("token should be not null");
        }
        if (url == null) {
            throw new IllegalStateException("token should be not null");
        }
        String fixedUrl;
        if (url.contains("/rest")) {
            fixedUrl = url.substring(0, url.indexOf("/rest"));
        } else {
            fixedUrl = StringUtils.removeEnd(url, "/");
        }
        this.api = new YouTrackAPIImpl(token, fixedUrl, trustAll);
    }

    @Override
    public boolean isValid() {
        try {
            api.me();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public YouTrackAPI getApi() {
        return api;
    }

}
