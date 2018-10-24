package com.jetbrains.plugins.macro;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.velocity.VelocityUtils;
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

@Named("IssueLink")
public class IssueLink extends YouTrackAuthAwareMacroBase {
    private static final Logger LOG = LoggerFactory.getLogger(IssueLink.class);
    private static final String logPrefix = "YTMacro-LinkDebug: ";

    @Inject
    public IssueLink(@ComponentImport PluginSettingsFactory pluginSettingsFactory, @ComponentImport TransactionTemplate transactionTemplate) {
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
            final String issueId = params.get(ID);
            String strikeMode = params.get(STRIKE_THRU_PARAM);
            if (strikeMode == null) strikeMode = ID_ONLY;
            String linkTextTemplate = params.get(TEMPLATE_PARAM);
            if (linkTextTemplate == null || linkTextTemplate.isEmpty()) linkTextTemplate = DEFAULT_TEMPLATE;
            String style = params.get(STYLE);
            if (!DETAILED.equals(style)) {
                style = SHORT;
            }

            if (issueId != null && !issueId.isEmpty()) {
                Issue issue = tryGetItem(youTrack.issues, issueId, retries);
                if (issue != null) {
                    setContext(context, strikeMode, linkTextTemplate, issue);
                } else context.put(ERROR, "Issue not found: " + issueId);
            } else {
                context.put(ERROR, "Issue not specified.");
            }
            return VelocityUtils.getRenderedTemplate(BODY_LINK + style + TEMPLATE_EXT, context);
        } catch (Exception ex) {
            LOG.error("YouTrack link macro encounters error", ex);
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