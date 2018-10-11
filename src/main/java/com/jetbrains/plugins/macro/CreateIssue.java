package com.jetbrains.plugins.macro;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.content.render.xhtml.DefaultConversionContext;
import com.atlassian.confluence.content.render.xhtml.XhtmlException;
import com.atlassian.confluence.core.ContentEntityObject;
import com.atlassian.confluence.core.DefaultSaveContext;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.confluence.xhtml.api.MacroDefinition;
import com.atlassian.confluence.xhtml.api.MacroDefinitionHandler;
import com.atlassian.confluence.xhtml.api.MacroDefinitionUpdater;
import com.atlassian.confluence.xhtml.api.XhtmlContent;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.jetbrains.plugins.base.YouTrackAuthAwareMacroBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import youtrack.Issue;
import youtrack.exceptions.CommandExecutionException;
import youtrack.issues.fields.BaseIssueField;
import youtrack.issues.fields.values.MultiUserFieldValue;

import javax.inject.Inject;
import javax.inject.Named;
import java.awt.image.renderable.RenderContext;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static com.jetbrains.plugins.util.Strings.*;

@Named("CreateIssue")
public class CreateIssue extends YouTrackAuthAwareMacroBase {
    private static final Logger LOG = LoggerFactory.getLogger(CreateIssue.class);
    private static final String logPrefix = "YTMacro-LinkDebug: ";
    private final XhtmlContent xhtmlContent;
    private final PageManager pageManager;
    private String defId = "";
    private static AtomicLong macroDefinitionCount = new AtomicLong(0);

    @Inject
    public CreateIssue(@ComponentImport PluginSettingsFactory pluginSettingsFactory,
                       @ComponentImport TransactionTemplate transactionTemplate,
                       @ComponentImport XhtmlContent xhtmlContent,
                       @ComponentImport PageManager pageManager) {
        super(pluginSettingsFactory, transactionTemplate);
        this.xhtmlContent = xhtmlContent;
        this.pageManager = pageManager;
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
            String strikeMode = (String) params.get(STRIKE_THRU_PARAM);
            String projectId = (String) params.get(PROJECT_ID);
            String summary = (String) params.get(SUMMARY);
            String description = (String) params.get(DESCRIPTION);
            if (strikeMode == null) strikeMode = ID_ONLY;
            String linkTextTemplate = (String) params.get(TEMPLATE_PARAM);
            if (linkTextTemplate == null || linkTextTemplate.isEmpty()) linkTextTemplate = DEFAULT_TEMPLATE;
            String style = (String) params.get(STYLE);
            if (!DETAILED.equals(style)) {
                style = SHORT;
            }

            final ContentEntityObject ceo = conversionContext.getEntity();
            String contentStatus = ceo.getContentStatus();
//            conversionContext.getEntity().getHistoricalVersions().get(0).getEntity().getProperty("macroDefinition")
            MacroDefinition macroDefinition = (MacroDefinition)conversionContext.getProperty("macroDefinition");
            macroDefinition.setParameter("test", "test");
            String issueId = conversionContext.getEntity().getProperties().getStringProperty(defId);

            ConversionContext c = new DefaultConversionContext(ceo.toPageContext());
//            xhtmlContent.handleMacroDefinitions(ceo.getBodyAsString(), c, macroDefinition1 -> {
//                macroDefinition.setParameter("11111", "111111");
//            });

            String pageString = xhtmlContent.updateMacroDefinitions(ceo.getBodyAsString(), c, new MacroDefinitionUpdater()
            {
                @Override
                public MacroDefinition update(MacroDefinition macroDefinition)
                {
                    macroDefinition.setName("xyz");
                    return macroDefinition;
                }
            });
            ceo.setBodyAsString(pageString);

//            final List<MacroDefinition> macros = new ArrayList<MacroDefinition>();
//
//            try
//            {
//                xhtmlContent.handleMacroDefinitions(ceo.getBodyAsString(), conversionContext, new MacroDefinitionHandler()
//                {
//                    @Override
//                    public void handle(MacroDefinition macroDefinition)
//                    {
//                        macroDefinition.setParameter("11111", "111111");
//                        macros.add(macroDefinition);
//                    }
//                });
//            }
//            catch (XhtmlException e)
//            {
//                throw new MacroExecutionException(e);
//            }

//            updatePage(pageManager.)

//            ceo.setBodyAsString(xhtmlContent.updateMacroDefinitions(ceo.getBodyAsString(), conversionContext, new MacroDefinitionUpdater()
//            {
//                public MacroDefinition update(MacroDefinition macroDefinition)
//                {
//                    if ("create".equals(macroDefinition.getName()))
//                        macroDefinition.setName("cheese");
//
//                    return macroDefinition;
//                }
//            }));

//            try {
//                String body = xhtmlContent.updateMacroDefinitions(ceo.getBodyAsString(), conversionContext, new MacroDefinitionUpdater(){
//                    @Override
//                    public MacroDefinition update(MacroDefinition macroDefinition) {
//                        final Map<String, Object> macroDefinitionTypeParameters = macroDefinition.getTypedParameters();
//                        macroDefinitionTypeParameters.put("projectId", "TEST-21");
//                        macroDefinition.setTypedParameters(macroDefinitionTypeParameters);
//
//                        final Map<String, String> macroDefinitionParameters = macroDefinition.getParameters();
//                        macroDefinitionParameters.put("projectId", "TEST-21");
//                        macroDefinition.setParameters(macroDefinitionParameters);
//
//                        return macroDefinition;
//                    }
//                });
//                ceo.setBodyAsString(body);
//                final DefaultSaveContext saveContext = new DefaultSaveContext(true, false, true);
//                pageManager.saveContentEntity(ceo, saveContext);
//
//            } catch (XhtmlException e) {
//                throw new MacroExecutionException(e);
//            }
//
//            if (issueId == null) {
//                conversionContext.getEntity().getHistoricalVersions()
//                pageManager.getPreviousVersion(conversionContext.getEntity()).getProperties().getStringProperty(macroId)
//            }
//            conversionContext.getEntity().getLatestVersion();
            Issue issue = null;
            if (issueId == null && contentStatus.equals("current")) {
                issue = tryCreateItem(youTrack.issues, Issue.createIssue(projectId, summary, description));
                defId = String.valueOf(macroDefinitionCount.incrementAndGet());
                conversionContext.getEntity().getProperties().setStringProperty(defId, issue.getId());

                if (issue != null) {
                    String host = youTrack.getHostAddress().substring(0, youTrack.getHostAddress().indexOf("rest"));
                    linkTextTemplate = "<a href=\"" + host + "/issue/" + issue.getId() + "\">" + issue.getId() + "</a>";
                    setContext(context, issue.getId(), strikeMode, linkTextTemplate, issue);
                }
            } else if (issueId != null && !issueId.isEmpty()) {
                issue = tryGetItem(youTrack.issues, issueId, retries);
                if (issue != null) {
                    setContext(context, issueId, strikeMode, linkTextTemplate, issue);
                } else {
                    context.put(ERROR, "Issue not found: " + issueId);
                }
            } else {
                if (contentStatus.equals("draft")) {
                    context.put(ERROR, "Issue will be created after publishing page.");
                } else {
                    context.put(ERROR, "Something went wrong.");
                }
            }
            return VelocityUtils.getRenderedTemplate(BODY_LINK + style + TEMPLATE_EXT, context);
        } catch (Exception ex) {
            LOG.error("YouTrack link macro encounters error", ex);
            throw new MacroExecutionException(ex);
        }
    }

    private void setContext(Map<String, Object> context, String issueId, String strikeMode, String linkTextTemplate, Issue issue) throws IOException, CommandExecutionException {
        String summaryTextTemplate;
        issue = issue.createSnapshot();
        final HashMap<String, BaseIssueField> fields = issue.getFields();
        for (final String fieldName : fields.keySet()) {
            context.put(fieldName, fields.get(fieldName).getStringValue());
        }
        context.put(ISSUE, issueId);
        context.put(BASE, getProperty(HOST).replace(REST_PREFIX, EMPTY));
        context.put(LINKBASE, getProperty(HOST).replace(REST_PREFIX, EMPTY));
        String linkbase = getProperty(LINKBASE);
        if (null != linkbase && !linkbase.isEmpty()) {
            context.put(LINKBASE, linkbase.replace(REST_PREFIX, EMPTY));
        }
        final String thru = (ALL.equals(strikeMode) || ID_ONLY.equals(strikeMode)) && issue.isResolved() ? "line-through" : NORMAL;
        if (ID_ONLY.equals(strikeMode)) {
            linkTextTemplate = linkTextTemplate.replace("$issue", MessageFormat.format(STRIKE_THRU, thru, "$issue"));
            summaryTextTemplate = MessageFormat.format(STRIKE_THRU, NORMAL, "$summary");
        } else {
            linkTextTemplate = MessageFormat.format(STRIKE_THRU, thru, linkTextTemplate);
            summaryTextTemplate = MessageFormat.format(STRIKE_THRU, thru, "$summary");
        }
        final MultiUserFieldValue assignee = issue.getAssignee();
        context.put("title", "Title: " + issue.getSummary() + ", Reporter: " + issue.getReporter() + ", Priority: " + issue.getPriority() + ", State: " +
                issue.getState() + ", Assignee: " + (assignee == null ? UNASSIGNED : assignee.getFullName()) +
                ", Votes: " + issue.getVotes() + ", Type: " + issue.getType());
        context.put(ISSUE_LINK_TEXT, VelocityUtils.getRenderedContent((CharSequence) linkTextTemplate, context));
        context.put(SUMMARY_FORMATTED, VelocityUtils.getRenderedContent((CharSequence) summaryTextTemplate, context));
    }

    public BodyType getBodyType() {
        return BodyType.;
    }

    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }

    private String updatePage(final Page page, ConversionContext conversionContext) {
        try {
            return xhtmlContent.updateMacroDefinitions(page.getBodyAsString(),
                    conversionContext, new MacroDefinitionUpdater() {
                        @Override
                        public MacroDefinition update(MacroDefinition macroDefinition) {
                            if (macroDefinition.getName().equals("create")) {
                                macroDefinition.setTypedParameter("colour", "Yellow");
                                macroDefinition.setTypedParameter("title", "Pending");
                            }
                            return macroDefinition;
                        }
                    });
        } catch (XhtmlException e) {
            e.printStackTrace();
        }
        return page.getBodyAsString();
    }
}