package com.jetbrains.plugins.macro;

import com.atlassian.confluence.content.ContentProperties;
import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.core.ContentEntityObject;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.confluence.xhtml.api.MacroDefinition;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.jetbrains.plugins.base.YouTrackAuthAwareMacroBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import youtrack.Issue;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

import static com.jetbrains.plugins.util.Strings.*;

@Named("CreateIssue")
public class CreateIssue extends YouTrackAuthAwareMacroBase {
    private static final Logger LOG = LoggerFactory.getLogger(CreateIssue.class);
    private static final String logPrefix = "YTMacro-LinkDebug: ";

    @Inject
    public CreateIssue(@ComponentImport PluginSettingsFactory pluginSettingsFactory,
                       @ComponentImport TransactionTemplate transactionTemplate) {
        super(pluginSettingsFactory, transactionTemplate);
    }

    @Override
    protected String getLoggingPrefix() {
        return logPrefix;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    public String execute(Map<String, String> params, String s, ConversionContext conversionContext) throws MacroExecutionException {
        try {
            final Map<String, Object> context = MacroUtils.defaultVelocityContext();

            String projectId = params.get(PROJECT_ID);
            String summary = params.get(SUMMARY);
            String description = params.get(DESCRIPTION);

            String strikeMode = params.get(STRIKE_THRU_PARAM);
            if (strikeMode == null) strikeMode = ID_ONLY;
            String linkTextTemplate = params.get(TEMPLATE_PARAM);
            if (linkTextTemplate == null || linkTextTemplate.isEmpty()) linkTextTemplate = DEFAULT_TEMPLATE;
            String style = params.get(STYLE);
            if (!DETAILED.equals(style)) {
                style = SHORT;
            }

            MacroDefinition macroDefinition = (MacroDefinition) conversionContext.getProperty("macroDefinition");
            final ContentEntityObject entity = conversionContext.getEntity();
            Issue issue;

            if (macroDefinition.getMacroId().isDefined() && entity != null) {

                final ContentProperties properties = entity.getProperties();

                String macroId = macroDefinition.getMacroId().get().getId();
                String issueId = properties.getStringProperty(macroId);

                if (issueId == null) {
                    issue = tryCreateItem(youTrack.issues, Issue.createIssue(projectId, summary, description));
                } else {
                    issue = tryGetItem(youTrack.issues, issueId, retries);
                }

                properties.setStringProperty(macroId, issue.getId());

                setContext(context, issue.getId(), strikeMode, linkTextTemplate, issue);

            } else {
                return "New task";
            }

            return VelocityUtils.getRenderedTemplate(BODY_LINK + style + TEMPLATE_EXT, context);
        } catch (Exception ex) {
            LOG.error("Something went wrong", ex);
            throw new MacroExecutionException(ex);
        }
    }

    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }
}