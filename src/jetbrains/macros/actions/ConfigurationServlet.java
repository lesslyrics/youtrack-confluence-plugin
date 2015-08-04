package jetbrains.macros.actions;
/**
 * Created by Egor.Malyshev on 12.03.2015.
 */
import com.atlassian.sal.api.ApplicationProperties;
import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;
import jetbrains.macros.util.Strings;
import youtrack.YouTrack;
import youtrack.exceptions.AuthenticationErrorException;
import youtrack.exceptions.CommandExecutionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
public class ConfigurationServlet extends HttpServlet {
    public static final String URL_SEPARATOR = "/";
    private final UserManager userManager;
    private final LoginUriProvider loginUriProvider;
    private final TemplateRenderer renderer;
    private final ApplicationProperties applicationProperties;
    private final PluginSettingsFactory pluginSettingsFactory;
    private final TransactionTemplate transactionTemplate;
    private int justSaved = -1;
    public ConfigurationServlet(UserManager userManager,
                                LoginUriProvider loginUriProvider,
                                TemplateRenderer renderer,
                                ApplicationProperties applicationProperties,
                                PluginSettingsFactory pluginSettingsFactory,
                                TransactionTemplate transactionTemplate) {
        this.userManager = userManager;
        this.loginUriProvider = loginUriProvider;
        this.renderer = renderer;
        this.applicationProperties = applicationProperties;
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.transactionTemplate = transactionTemplate;
    }
    private void checkAdminRights(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final String username = userManager.getRemoteUsername(req);
        if(username == null || !userManager.isSystemAdmin(username)) {
            redirectToLogin(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        checkAdminRights(req, resp);
        final String hostAddressPassed = req.getParameter(Strings.HOST);
        final String hostAddress = hostAddressPassed.endsWith(URL_SEPARATOR) ? hostAddressPassed : hostAddressPassed + URL_SEPARATOR;
        final String password = req.getParameter(Strings.PASSWORD);
        final String login = req.getParameter(Strings.LOGIN);
        final YouTrack testYouTrack = YouTrack.getInstance(hostAddress);
        try {
            testYouTrack.login(login, password);
            transactionTemplate.execute(new TransactionCallback<Properties>() {
                @Override
                public Properties doInTransaction() {
                    final PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
                    final Properties storage = new Properties();
                    storage.setProperty(Strings.HOST, hostAddress);
                    storage.setProperty(Strings.LOGIN, login);
                    storage.setProperty(Strings.PASSWORD, password);
                    storage.setProperty(Strings.AUTH_KEY, testYouTrack.getAuthorization());
                    pluginSettings.put(Strings.MAIN_KEY, storage);
                    return null;
                }
            });
            justSaved = 0;
        } catch(CommandExecutionException e) {
            justSaved = -2;
        } catch(AuthenticationErrorException e) {
            justSaved = -2;
        }
        doGet(req, resp);
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        checkAdminRights(request, response);
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("baseUrl", applicationProperties.getBaseUrl());
        Properties storage = transactionTemplate.execute(new TransactionCallback<Properties>() {
            @Override
            public Properties doInTransaction() {
                final PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
                return (Properties) pluginSettings.get(Strings.MAIN_KEY);
            }
        });
        if(storage == null) storage = new Properties();
        params.put(Strings.HOST, storage.getProperty(Strings.HOST, Strings.EMPTY));
        params.put(Strings.PASSWORD, storage.getProperty(Strings.PASSWORD, Strings.EMPTY));
        params.put(Strings.LOGIN, storage.getProperty(Strings.LOGIN, Strings.EMPTY));
        params.put("justSaved", justSaved);
        justSaved = -1;
        response.setContentType("text/html;charset=utf-8");
        renderer.render("/templates/settings-servlet.vm", params, response.getWriter());
    }
    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(loginUriProvider.getLoginUri(getUri(request)).toASCIIString());
    }
    private URI getUri(HttpServletRequest request) {
        final StringBuffer builder = request.getRequestURL();
        if(request.getQueryString() != null) {
            builder.append("?");
            builder.append(request.getQueryString());
        }
        return URI.create(builder.toString());
    }
}