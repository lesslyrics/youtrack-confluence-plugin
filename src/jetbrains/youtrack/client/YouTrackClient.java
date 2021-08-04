package jetbrains.youtrack.client;

import jetbrains.youtrack.client.api.YouTrackAPI;

public interface YouTrackClient {

    boolean isValid();

    YouTrackAPI getApi();
}
