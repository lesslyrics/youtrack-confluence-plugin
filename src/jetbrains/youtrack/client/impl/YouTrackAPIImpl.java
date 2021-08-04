package jetbrains.youtrack.client.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jetbrains.youtrack.client.api.Issue;
import jetbrains.youtrack.client.api.Me;
import jetbrains.youtrack.client.api.YouTrackAPI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.InputStream;
import java.util.List;

import static org.apache.http.util.EntityUtils.consumeQuietly;

public class YouTrackAPIImpl implements YouTrackAPI {

    private static final String FIELDS = "$type,comments(id,text),customFields($type,id,name,projectCustomField($type,bundle(id),emptyFieldText,id),value($type,archived,avatarUrl,fullName,id,localizedName,login,minutes,name,presentation,ringId,text)),id,idReadable,created,id,isDraft,numberInProject,project(id,ringId,shortName),reporter(id),resolved,summary";

    private final String token;
    private final String url;
    private final boolean trustAll;
    private final HttpClient client;
    private final ObjectMapper mapper;

    public YouTrackAPIImpl(String token, String url, boolean trustAll) {
        this.token = token;
        this.url = url;
        this.trustAll = trustAll;
        this.client = HttpClientBuilder.create().build();
        mapper = new ObjectMapper()
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public List<Issue> issues(String finalQuery, int startIssue, int pageSize) {
        try {
            URIBuilder builder = new URIBuilder(this.url + "/api/issues")
                    .setParameter("fields", FIELDS)
                    .setParameter("query", finalQuery != null ? finalQuery : "")
                    .setParameter("$skip", String.valueOf(startIssue))
                    .setParameter("$top", String.valueOf(pageSize));

            HttpGet get = withAuth(new HttpGet(builder.build()));
            return execute(get, (in) -> {
                return mapper.readValue(in, new TypeReference<List<Issue>>() {
                });
            });
        } catch (Exception e) {
            throw new IllegalStateException(e);

        }
    }

    @Override
    public Issue getIssue(String idReadable) {
        try {
            URIBuilder builder = new URIBuilder(this.url + "/api/issues/" + idReadable)
                    .setParameter("fields", FIELDS);
            HttpGet get = withAuth(new HttpGet(builder.build()));
            return execute(get, (in) -> mapper.readValue(in, Issue.class));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public Me me() {
        try {
            URIBuilder builder = new URIBuilder(this.url + "/api/users/me")
                    .setParameter("fields", "fullName,login");
            HttpGet get = withAuth(new HttpGet(builder.build()));
            return execute(get, (in) -> mapper.readValue(in, Me.class));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private HttpGet withAuth(HttpGet get) {
        get.addHeader("Authorization", token);
        return get;
    }

    private <T> T execute(HttpGet get, APIParseFunction<InputStream, T> call) throws Exception {
        HttpResponse response = client.execute(get);
        if (response.getStatusLine().getStatusCode() == 200) {
            try (InputStream content = response.getEntity().getContent()) {
                return call.apply(content);
            }
        } else {
            consumeQuietly(response.getEntity());
            throw new IllegalStateException("GET " + get.getURI() + "\n received result is " + response.getStatusLine().getStatusCode());
        }
    }
}

