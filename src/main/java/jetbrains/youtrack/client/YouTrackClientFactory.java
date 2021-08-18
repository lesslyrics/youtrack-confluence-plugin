package jetbrains.youtrack.client;

import jetbrains.youtrack.client.impl.YouTrackClientImpl;

public class YouTrackClientFactory {

    public static YouTrackClient newClient(String url, String token, boolean trustAll) {
        return new YouTrackClientImpl(url, token, trustAll);
    }
}
