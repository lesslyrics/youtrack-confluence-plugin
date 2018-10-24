package com.jetbrains.plugins.macro;

import com.atlassian.confluence.content.ContentProperties;
import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.content.render.xhtml.DefaultConversionContext;
import com.atlassian.confluence.core.ContentEntityObject;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.confluence.xhtml.api.MacroDefinition;
import com.atlassian.confluence.xhtml.api.XhtmlContent;
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

    private final XhtmlContent xhtmlContent;

    @Inject
    public CreateIssue(@ComponentImport PluginSettingsFactory pluginSettingsFactory,
                       @ComponentImport TransactionTemplate transactionTemplate,
                       @ComponentImport XhtmlContent xhtmlContent) {
        super(pluginSettingsFactory, transactionTemplate);
        this.xhtmlContent = xhtmlContent;
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
            Issue issueModel = Issue.createIssue(projectId, summary, description);
            Issue issue = null;

            if (macroDefinition.getMacroId().isDefined() && entity != null) {
                final ContentProperties properties = entity.getProperties();
                String macroId = macroDefinition.getMacroId().get().getId();
                String issueId = properties.getStringProperty(macroId);

                ConversionContext c = new DefaultConversionContext(entity.toPageContext());
                issue = handleCreateIssue(entity, c, issueModel, issueId, macroId);
            }

            if (issue != null) {
                setContext(context, strikeMode, linkTextTemplate, issue);
            } else {
                setContext(context, strikeMode, linkTextTemplate, issueModel);
            }

            return VelocityUtils.getRenderedTemplate(BODY_LINK + style + TEMPLATE_EXT, context);
        } catch (Exception ex) {
            LOG.error("Something went wrong", ex);
            throw new MacroExecutionException(ex);
        }
    }

    private Issue handleCreateIssue(ContentEntityObject ceo, ConversionContext conversionContext, Issue issue, String issueId, String macroId) {
        final Issue[] issueArray = new Issue[1];
        try {
            ceo.setBodyAsString(xhtmlContent.updateMacroDefinitions(ceo.getBodyAsString(), conversionContext, macroDefinition -> {
                String macroDefinitionId = macroDefinition.getMacroId().get().getId();
                if (macroDefinitionId.equals(macroId)) {
                    String issueIdByMacroId = conversionContext.getEntity().getProperties().getStringProperty(macroDefinitionId);

                    try {
                        if (issueIdByMacroId == null) {
                            issueArray[0] = tryCreateItem(youTrack.issues, issue);
                        } else {
                            issueArray[0] = tryGetItem(youTrack.issues, issueIdByMacroId, retries);
                        }
                    } catch (Exception e) {
                        LOG.error("Error create or get issue", e);
                    }

                    conversionContext.getEntity().getProperties().setStringProperty(macroDefinitionId, issueArray[0].getId());
                }
                return macroDefinition;
            }));
            if (issueArray[0] == null) {
                issueArray[0] = tryGetItem(youTrack.issues, issueId, retries);
            }
        } catch (Exception e) {
            LOG.error("Something went wrong", e);
        }

        return issueArray[0];
    }

    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }
}