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
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLSocket;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.http.util.EntityUtils.consumeQuietly;

public class YouTrackAPIImpl implements YouTrackAPI {

    private static final Logger LOG = LoggerFactory.getLogger(YouTrackAPIImpl.class);
    private static final String FIELDS = "$type,comments(id,text,created,author(fullName)),customFields($type,id,name,projectCustomField($type,field(name,fieldType(id)),emptyFieldText,id),value($type,archived,avatarUrl,fullName,id,localizedName,login,minutes,name,presentation,ringId,text)),id,idReadable,created,id,isDraft,numberInProject,project(id,ringId,shortName),reporter(id,login,fullName),resolved,summary,votes";

    private final String token;
    private final String url;
    private HttpClient client;
    private final ObjectMapper mapper;

    public YouTrackAPIImpl(String token, String url, boolean trustAll) {
        this.token = token;
        this.url = url;

        try {
            SSLContextBuilder sslBuilder = new SSLContextBuilder();
            if (trustAll) {
                sslBuilder.loadTrustMaterial((KeyStore) null, new TrustSelfSignedStrategy());
            }
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslBuilder.build()) {
                protected void prepareSocket(SSLSocket socket) {
                    String[] enabledCipherSuites = socket.getEnabledCipherSuites();
                    String version = System.getProperty("java.version");
                    List<String> list = new ArrayList<>(Arrays.asList(enabledCipherSuites));
                    if (!version.startsWith("1.8")) {
                        list.remove("TLS_DHE_RSA_WITH_AES_128_CBC_SHA");
                        list.remove("SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA");
                        list.remove("TLS_DHE_RSA_WITH_AES_256_CBC_SHA");
                    }
                    socket.setEnabledCipherSuites(list.toArray(new String[0]));
                }
            };
            this.client = HttpClients.custom().setSSLSocketFactory(sslsf).setRedirectStrategy(new LaxRedirectStrategy()).useSystemProperties().build();
        } catch (Exception e) {
            LOG.error("exception creating youtrack client", e);

            this.client = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).useSystemProperties().build();
        }
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
        get.addHeader("Authorization", "Bearer " + token);
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
            if (response.getStatusLine().getStatusCode() == 404) {
                return null;
            }
            throw new IllegalStateException("GET " + get.getURI() + "\n received result is " + response.getStatusLine().getStatusCode());
        }
    }
}

