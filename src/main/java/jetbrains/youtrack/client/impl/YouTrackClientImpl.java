package jetbrains.youtrack.client.impl;

import jetbrains.youtrack.client.IssuePresentation;
import jetbrains.youtrack.client.YouTrackClient;
import jetbrains.youtrack.client.api.Issue;
import jetbrains.youtrack.client.api.YouTrackAPI;
import jetbrains.youtrack.Strings;

public class YouTrackClientImpl implements YouTrackClient {

    private final YouTrackAPIImpl api;

    public YouTrackClientImpl(String url, String token, boolean trustAll) {
        if (token == null) {
            throw new IllegalStateException("token should be not null");
        }
        if (url == null) {
            throw new IllegalStateException("token should be not null");
        }
        this.api = new YouTrackAPIImpl(token, Strings.fixURL(url), trustAll);
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
