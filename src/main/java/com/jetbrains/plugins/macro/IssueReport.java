package com.jetbrains.plugins.macro;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.renderer.PageContext;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.jetbrains.plugins.base.YouTrackAuthAwareMacroBase;
import com.opensymphony.webwork.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import youtrack.CommandBasedList;
import youtrack.Issue;
import youtrack.IssueComment;
import youtrack.issues.fields.BaseIssueField;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.jetbrains.plugins.util.Service.*;
import static com.jetbrains.plugins.util.Strings.*;

@Named("IssueReport")
public class IssueReport extends YouTrackAuthAwareMacroBase {
    private static final Logger LOG = LoggerFactory.getLogger(IssueReport.class);
    private final PageManager pageManager;
    private static final String logPrefix = "YTMacro-ReportDebug: ";

    @Override
    protected String getLoggingPrefix() {
        return logPrefix;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    private class IssueFieldDescriptor {
        final String code;
        final String title;

        IssueFieldDescriptor(String src) {
            final String[] parts = src.split(":");
            this.code = parts[0];
            this.title = parts.length > 1 ? parts[1] : parts[0];
        }
    }

    @Inject
    public IssueReport(@ComponentImport PluginSettingsFactory pluginSettingsFactory,
                       @ComponentImport TransactionTemplate transactionTemplate,
                       @ComponentImport PageManager pageManager) {
        super(pluginSettingsFactory, transactionTemplate);
        this.pageManager = pageManager;
    }

    public boolean isInline() {
        return false;
    }

    public boolean hasBody() {
        return false;
    }

    public String execute(Map<String, String> params, String s, ConversionContext conversionContext) throws MacroExecutionException {
        try {
            logMessage("Report macro invoked.");
            final Map<String, Object> context = MacroUtils.defaultVelocityContext();
            logMessage("Processing parameters.");

            final String project = defaultIfNullOrEmpty((String) params.get(PROJECT), ALL_PROJECTS);
            final String query = (String) params.get(QUERY);
            final String fieldList = defaultIfNullOrEmpty((String) params.get(REPORT_FIELD_LIST), DEFAULT_REPORT_FIELD_LIST);

            logMessage(MessageFormat.format(" Project: {0} Query: {1} Fields: {2}", project, query, fieldList));

            final StringBuilder result = new StringBuilder();
            if (query != null) {

                logMessage("Starting to query YouTrack.");

                tryGetItem(youTrack.issues, EMPTY, 2);

                logMessage("Access token updated.");

                final StringBuilder rows = new StringBuilder();
                final int pageSize = intValueOf((String) params.get(PAGE_SIZE), 25);

                logMessage("Page size determined: " + pageSize);

                final HttpServletRequest request = ServletActionContext.getRequest();
                final int currentPage = request == null ? 1 : intValueOf(request.getParameter(PAGINATION_PARAM), 1);

                logMessage("Current page determined: " + currentPage);

                final StringBuilder pagination = new StringBuilder();
                final PageContext pageContext = conversionContext.getPageContext();
                final Page page = pageManager.getPage(pageContext.getSpaceKey(), pageContext.getPageTitle());
                final String thisPageUrl = page == null ? null : page.getUrlPath();

                logMessage("Page URL is (null for new, not saved pages): " + thisPageUrl);

                final int startIssue = currentPage == 1 ? 0 : (currentPage - 1) * pageSize + 1;

                logMessage("Start issue: " + startIssue);

                logMessage("Preparing field info.");
                final LinkedList<IssueFieldDescriptor> reportFields = new LinkedList<IssueFieldDescriptor>();

                for (final String fieldData : fieldList.split(",")) {
                    logMessage("Reading field: " + fieldData);
                    reportFields.add(new IssueFieldDescriptor(fieldData));
                }

                logMessage("Building report header.");
                final StringBuilder header = new StringBuilder();
                header.append("<th>Issue</th>");
                for (final IssueFieldDescriptor desc : reportFields) {
                    header.append("<th>");
                    header.append(desc.title);
                    header.append("</th>");
                }

                String linkbase = getProperty(LINKBASE);
                if (linkbase.endsWith("/")) linkbase = linkbase.substring(0, linkbase.lastIndexOf("/"));

                logMessage("Determining linkbase: " + linkbase);

                context.put(LINKBASE, linkbase.replace(REST_PREFIX, EMPTY));

                final String finalQuery = (ALL_PROJECTS.equalsIgnoreCase(project) ?
                        EMPTY : "project: " + project + " ") + query;

                logMessage("Running query: " + finalQuery);

                final List<Issue> issues = youTrack.issues.query(finalQuery, startIssue, pageSize);

                logMessage("Number of isses in query result: " + issues.size());
                for (final Issue originalIssue : issues) {

                    logMessage("Rendering report row for " + originalIssue.getId());

                    final Issue snapshot = originalIssue.createSnapshot();

                    rows.append("<tr class=\"yt yt-report-row\">");
                    rows.append("<td>");

                    final Map<String, Object> issueLinkContext = createContext(context,
                            ISSUE_ID, originalIssue.getId()
                    );

                    rows.append(VelocityUtils.getRenderedTemplate(REPORT_ISSUE_LINK, issueLinkContext));
                    rows.append("</td>");

                    for (final IssueFieldDescriptor reportField : reportFields) {
                        logMessage("Rendering field " + reportField.code);
                        rows.append("<td>");

                        final HashMap<String, BaseIssueField> issueFields = snapshot.getFields();
                        if (issueFields != null && !issueFields.isEmpty()) {
                            final BaseIssueField field = issueFields.get(reportField.code);
                            final boolean verbose = "comments-verbose".equals(reportField.code);

                            if ("comments".equals(reportField.code) || verbose) {

                                logMessage("Rendering comments.");

                                final CommandBasedList<Issue, IssueComment> comments = originalIssue.comments;
                                if (comments != null) {
                                    final List<IssueComment> issueComments = comments.list();
                                    logMessage("Total comments: " + issueComments.size());

                                    for (int i = 0; i < issueComments.size(); i++) {
                                        logMessage("Adding comment: " + i);
                                        final IssueComment issueComment = issueComments.get(i);
                                        String commentText = issueComment.getText();

                                        final Map<String, Object> commentContext = createContext(context,
                                                ISSUE_ID, defaultIfNull(issueComment.getIssueId(), UNKNOWN),
                                                COMMENT_BODY, commentText == null ? EMPTY :
                                                        commentText.replace("(\\r|\\n)", EMPTY).replaceAll("\"<[^>]*>\"", EMPTY),
                                                COMMENT_AUTHOR, defaultIfNull(issueComment.getAuthor(), UNKNOWN),
                                                COMMENT_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm").
                                                        format(new Date(defaultIfNull(issueComment.getCreated(), Calendar.getInstance().getTimeInMillis()))),
                                                COMMENT_ID, issueComment.getId()
                                        );

                                        if (verbose) {
                                            rows.append(VelocityUtils.getRenderedTemplate(REPORT_COMMENT_HEAD, commentContext));
                                        }
                                        rows.append(VelocityUtils.getRenderedTemplate(REPORT_COMMENT_BODY, commentContext));

                                        if (i == 10) {
                                            rows.append(VelocityUtils.getRenderedTemplate(REPORT_COMMENT_MORE, commentContext));
                                            break;
                                        }
                                    }
                                } else {
                                    rows.append("No commentes so far.");
                                }
                            } else
                                rows.append(field == null ? UNKNOWN : defaultIfNull(field.getStringValue(), UNKNOWN));
                            rows.append("</td>");
                        }
                    }
                    rows.append("</tr>");
                }
                if (thisPageUrl != null && request != null) {
                    logMessage("Processing pagination");
                    if (currentPage != 1 || issues.size() >= pageSize) {

                        int maxPages = intValueOf((String) params.get(TOTAL_PAGES), 10);

                        if (currentPage > 1) {
                            if (issues.size() < pageSize) maxPages = currentPage;
                        }

                        for (int i = 1; i <= maxPages; i++) {
                            final Map<String, Object> paginationContext = createContext(context,
                                    "num", String.valueOf(i),
                                    "param", PAGINATION_PARAM,
                                    "url", thisPageUrl,
                                    "style", i == currentPage ? "font-weight:bold;" : "font-weight:normal;"
                            );
                            pagination.append(VelocityUtils.getRenderedTemplate(PAGINATION_SINGLE, paginationContext));
                        }
                    }
                }
                context.put("pagination", pagination.toString());
                context.put("rows", rows.toString());
                context.put("hasIssues", issues.isEmpty() ? null : String.valueOf(true));
                context.put("title", query + " from " + project);
                context.put("header", header);
                logMessage("Final rendering.");
                result.append(VelocityUtils.getRenderedTemplate(BODY_REPORT, context));
            }
            logMessage("Returning results.");
            return result.toString();
        } catch (Exception ex) {
            LOG.error("YouTrack report macro encounters error", ex);
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