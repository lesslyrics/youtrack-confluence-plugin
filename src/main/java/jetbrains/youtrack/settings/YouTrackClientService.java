package jetbrains.youtrack.settings;

import jetbrains.youtrack.client.YouTrackClient;

import java.util.function.Supplier;

public class YouTrackClientService {

    private static volatile YouTrackClient client = null;

    public static YouTrackClient client() {
        return client;
    }

    public static void useClient(YouTrackClient client) {
        YouTrackClientService.client = client;
    }

    public static void useClientIfAbsent(Supplier<YouTrackClient> factory) {
        if (YouTrackClientService.client != null) {
            return;
        }
        YouTrackClientService.client = factory.get();
    }
}
