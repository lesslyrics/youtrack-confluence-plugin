package jetbrains.macros.actions;

/**
 * Created by Egor.Malyshev on 12.03.2015.
 */

import com.atlassian.confluence.setup.settings.SettingsManager;
import com.atlassian.sal.api.ApplicationProperties;
import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;
import jetbrains.macros.util.Strings;

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
    private final UserManager userManager;
    private final LoginUriProvider loginUriProvider;
    private final TemplateRenderer renderer;
    private final SettingsManager settingsManager;
    private final ApplicationProperties applicationProperties;
    private final PluginSettingsFactory pluginSettingsFactory;
    private final TransactionTemplate transactionTemplate;
    private boolean justSaved = false;

    public ConfigurationServlet(UserManager userManager,
                                LoginUriProvider loginUriProvider,
                                TemplateRenderer renderer,
                                SettingsManager settingsManager,
                                ApplicationProperties applicationProperties,
                                PluginSettingsFactory pluginSettingsFactory,
                                TransactionTemplate transactionTemplate) {
        this.userManager = userManager;
        this.loginUriProvider = loginUriProvider;
        this.renderer = renderer;
        this.settingsManager = settingsManager;
        this.applicationProperties = applicationProperties;
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final HttpServletRequest request = req;
        transactionTemplate.execute(new TransactionCallback<Properties>() {
            @Override
            public Properties doInTransaction() {
                final PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
                final Properties storage = new Properties();
                storage.setProperty(Strings.HOST, request.getParameter(Strings.HOST));
                storage.setProperty(Strings.LOGIN, request.getParameter(Strings.LOGIN));
                storage.setProperty(Strings.PASSWORD, request.getParameter(Strings.PASSWORD));
                pluginSettings.put(Strings.MAIN_KEY, storage);
                return null;
            }
        });
        justSaved = true;
        doGet(req, resp);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final String username = userManager.getRemoteUsername(request);
        if (username == null || !userManager.isSystemAdmin(username)) {
            redirectToLogin(request, response);
            return;
        }
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("baseUrl", applicationProperties.getBaseUrl());
        Properties storage = transactionTemplate.execute(new TransactionCallback<Properties>() {
            @Override
            public Properties doInTransaction() {
                final PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
                return (Properties) pluginSettings.get(Strings.MAIN_KEY);
            }
        });
        if (storage == null) storage = new Properties();
        params.put(Strings.HOST, storage.getProperty(Strings.HOST, Strings.EMPTY));
        params.put(Strings.PASSWORD, storage.getProperty(Strings.PASSWORD, Strings.EMPTY));
        params.put(Strings.LOGIN, storage.getProperty(Strings.LOGIN, Strings.EMPTY));
        if (justSaved) params.put("justSaved", true);
        response.setContentType("text/html;charset=utf-8");
        renderer.render("/templates/settings-servlet.vm", params, response.getWriter());
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(loginUriProvider.getLoginUri(getUri(request)).toASCIIString());
    }

    private URI getUri(HttpServletRequest request) {
        final StringBuffer builder = request.getRequestURL();
        if (request.getQueryString() != null) {
            builder.append("?");
            builder.append(request.getQueryString());
        }
        return URI.create(builder.toString());
    }

}