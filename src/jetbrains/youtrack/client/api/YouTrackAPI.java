package jetbrains.youtrack.client.api;

import java.util.List;

public interface YouTrackAPI {

    List<Issue> issues(String finalQuery, int startIssue, int pageSize);

    Issue getIssue(String idReadable);

}
