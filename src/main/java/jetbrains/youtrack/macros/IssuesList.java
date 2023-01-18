package jetbrains.youtrack.macros;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import jetbrains.youtrack.Strings;
import jetbrains.youtrack.client.IssuePresentation;
import jetbrains.youtrack.client.api.Issue;
import jetbrains.youtrack.client.api.IssueComment;
import com.atlassian.core.filters.ServletContextThreadLocal;
import jetbrains.youtrack.settings.YouTrackClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static jetbrains.youtrack.Service.*;
import static jetbrains.youtrack.Strings.*;

@Named("IssueList")
public class IssuesList extends YouTrackAuthAwareMacroBase {
    private static final Logger LOG = LoggerFactory.getLogger(IssuesList.class);
    private final PageManager pageManager;
    private static final String logPrefix = "YTMacro-ReportDebug: ";

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
    public IssuesList(@ComponentImport PluginSettingsFactory pluginSettingsFactory,
                      @ComponentImport TransactionTemplate transactionTemplate,
                      @ComponentImport PageManager pageManager) {
        super(pluginSettingsFactory, transactionTemplate);
        this.pageManager = pageManager;
    }

    @Override
    public String execute(Map<String, String> params, String s, ConversionContext renderContext) {
        try {
            logMessage("Report macro invoked.");
            final Map<String, Object> context = MacroUtils.defaultVelocityContext();
            logMessage("Processing parameters.");

            final String project = defaultIfNullOrEmpty(params.get(PROJECT), ALL_PROJECTS);
            final String query = params.get(QUERY);
            final String fieldList = defaultIfNullOrEmpty(params.get(REPORT_FIELD_LIST), DEFAULT_REPORT_FIELD_LIST);

            logMessage(MessageFormat.format(" Project: {0} Query: {1} Fields: {2}", project, query, fieldList));

            final StringBuilder result = new StringBuilder();
            if (query != null) {
                logMessage("Starting to query YouTrack.");

                final StringBuilder rows = new StringBuilder();
                final int pageSize = intValueOf(params.get(PAGE_SIZE), 25);

                logMessage("Page size determined: " + pageSize);

                final HttpServletRequest request = ServletContextThreadLocal.getRequest();
                final int currentPage = request == null ? 1 : intValueOf(request.getParameter(PAGINATION_PARAM), 1);

                logMessage("Current page determined: " + currentPage);

                final StringBuilder pagination = new StringBuilder();
                final Page page = pageManager.getPage(renderContext.getSpaceKey(), renderContext.getPageContext().getPageTitle());
                final String thisPageUrl = page == null ? null : page.getUrlPath();

                logMessage("Page URL is (null for new, not saved pages): " + thisPageUrl);

                final int startIssue = currentPage == 1 ? 0 : (currentPage - 1) * pageSize + 1;

                logMessage("Start issue: " + startIssue);

                logMessage("Preparing field info.");
                final List<IssueFieldDescriptor> reportFields = new ArrayList<>();

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
                if (isEmpty(linkbase)) {
                    linkbase = getProperty(HOST);
                }
                linkbase = Strings.fixURL(linkbase);
                logMessage("Determining linkbase: " + linkbase);

                context.put(LINKBASE, linkbase);

                final String finalQuery = (ALL_PROJECTS.equalsIgnoreCase(project) ?
                        EMPTY : "project: " + project + " ") + query;

                logMessage("Running query: " + finalQuery);

                final List<Issue> issues = YouTrackClientService.client().getApi().issues(finalQuery, startIssue, pageSize);

                logMessage("Number of isses in query result: " + issues.size());
                for (final Issue originalIssue : issues) {
                    IssuePresentation issuePresentation = new IssuePresentation(originalIssue);

                    logMessage("Rendering report row for " + originalIssue.getId());

                    rows.append("<tr class=\"yt yt-report-row\">");
                    rows.append("<td>");

                    final Map<String, Object> issueLinkContext = createContext(context,
                            ISSUE_ID, originalIssue.getIdReadable()
                    );

                    rows.append(VelocityUtils.getRenderedTemplate(REPORT_ISSUE_LINK, issueLinkContext));
                    rows.append("</td>");

                    for (final IssueFieldDescriptor reportField : reportFields) {
                        logMessage("Rendering field " + reportField.code);
                        rows.append("<td>");

                        Map<String, String> fieldValues = issuePresentation.getFieldValues();
                        if (!fieldValues.isEmpty()) {
                            final String fieldValue = fieldValues.get(reportField.code);
                            final boolean verbose = "comments-verbose".equals(reportField.code);

                            if ("comments".equals(reportField.code) || verbose) {
                                logMessage("Rendering comments.");

                                final List<IssueComment> comments = originalIssue.getComments();
                                if (comments != null) {
                                    logMessage("Total comments: " + comments.size());

                                    for (int i = 0; i < comments.size(); i++) {
                                        logMessage("Adding comment: " + i);
                                        final IssueComment issueComment = comments.get(i);
                                        String commentText = issueComment.getText();

                                        final Map<String, Object> commentContext = createContext(context,
                                                ISSUE_ID, defaultIfNull(originalIssue.getIdReadable(), UNKNOWN),
                                                COMMENT_BODY, commentText == null ? EMPTY :
                                                        commentText.replace("(\\r|\\n)", EMPTY).replaceAll("\"<[^>]*>\"", EMPTY),
                                                COMMENT_AUTHOR, defaultIfNull(issueComment.getAuthor().getFullName(), UNKNOWN),
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
                            } else {
                                rows.append(fieldValue == null ? UNKNOWN : defaultIfNull(fieldValue, UNKNOWN));
                            }
                            rows.append("</td>");
                        }
                    }
                    rows.append("</tr>");
                }
                if (thisPageUrl != null && request != null) {
                    logMessage("Processing pagination");
                    if (currentPage != 1 || issues.size() >= pageSize) {

                        int maxPages = intValueOf(params.get(TOTAL_PAGES), 10);

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
        }

        return "Issue not specified";
    }

    @Override
    protected String getLoggingPrefix() {
        return logPrefix;
    }

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }
}