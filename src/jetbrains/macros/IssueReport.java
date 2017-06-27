package jetbrains.macros;

import com.atlassian.confluence.pages.Page;
import com.atlassian.confluence.pages.PageManager;
import com.atlassian.confluence.renderer.PageContext;
import com.atlassian.confluence.renderer.radeox.macros.MacroUtils;
import com.atlassian.confluence.util.velocity.VelocityUtils;
import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.MacroException;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;
import com.opensymphony.webwork.ServletActionContext;
import jetbrains.macros.base.YouTrackAuthAwareMacroBase;
import jetbrains.macros.util.Service;
import jetbrains.macros.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import youtrack.CommandBasedList;
import youtrack.Issue;
import youtrack.IssueComment;
import youtrack.issues.fields.BaseIssueField;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class IssueReport extends YouTrackAuthAwareMacroBase {
    private static final Logger LOG = LoggerFactory.getLogger(IssueReport.class);
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

    public IssueReport(PluginSettingsFactory pluginSettingsFactory,
                       TransactionTemplate transactionTemplate,
                       PageManager pageManager) {
        super(pluginSettingsFactory, transactionTemplate);
        this.pageManager = pageManager;
    }

    public boolean isInline() {
        return false;
    }

    public boolean hasBody() {
        return false;
    }

    public RenderMode getBodyRenderMode() {
        return RenderMode.NO_RENDER;
    }

    public String execute(Map params, String body, RenderContext renderContext)
            throws MacroException {
        try {
            logMessage(LOG, logPrefix + "Report macro invoked.");
            final Map<String, Object> context = MacroUtils.defaultVelocityContext();
            logMessage(LOG, logPrefix + "Processing parameters.");

            final String project = Service.defaultIfNullOrEmpty((String) params.get(Strings.PROJECT), Strings.ALL_PROJECTS);
            final String query = (String) params.get(Strings.QUERY);
            final String fieldList = Service.defaultIfNullOrEmpty((String) params.get(Strings.REPORT_FIELD_LIST), Strings.DEFAULT_REPORT_FIELD_LIST);

            logMessage(LOG, logPrefix + MessageFormat.format(" Project: {0} Query: {1} Fields: {2}", project, query, fieldList));

            final StringBuilder result = new StringBuilder();
            if (query != null) {

                logMessage(LOG, logPrefix + "Starting to query YouTrack.");

                tryGetItem(youTrack.issues, Strings.EMPTY, 2);

                logMessage(LOG, logPrefix + "Access token updated.");

                final StringBuilder rows = new StringBuilder();
                final int pageSize = Service.intValueOf((String) params.get(Strings.PAGE_SIZE), 25);

                logMessage(LOG, logPrefix + "Page size determined: " + pageSize);

                final HttpServletRequest request = ServletActionContext.getRequest();
                final int currentPage = request == null ? 1 : Service.intValueOf(request.getParameter(Strings.PAGINATION_PARAM), 1);

                logMessage(LOG, logPrefix + "Current page determined: " + currentPage);

                final StringBuilder pagination = new StringBuilder();
                final PageContext pageContext = (PageContext) renderContext;
                final Page page = pageManager.getPage(pageContext.getSpaceKey(), pageContext.getPageTitle());
                final String thisPageUrl = page == null ? null : page.getUrlPath();

                logMessage(LOG, logPrefix + "Page URL is (null for new, not saved pages): " + thisPageUrl);

                final int startIssue = currentPage == 1 ? 0 : (currentPage - 1) * pageSize + 1;

                logMessage(LOG, logPrefix + "Start issue: " + startIssue);

                logMessage(LOG, logPrefix + "Preparing field info.");
                final LinkedList<IssueFieldDescriptor> reportFields = new LinkedList<IssueFieldDescriptor>();

                for (final String fieldData : fieldList.split(",")) {
                    logMessage(LOG, logPrefix + "Reading field: " + fieldData);
                    reportFields.add(new IssueFieldDescriptor(fieldData));
                }

                logMessage(LOG, logPrefix + "Building report header.");
                final StringBuilder header = new StringBuilder();
                header.append("<th>Issue</th>");
                for (final IssueFieldDescriptor desc : reportFields) {
                    header.append("<th>");
                    header.append(desc.title);
                    header.append("</th>");
                }

                String linkbase = getProperty(Strings.LINKBASE);

                if (Service.isEmpty(linkbase)) {
                    if (linkbase.endsWith("/")) linkbase = linkbase.substring(0, linkbase.lastIndexOf("/"));
                } else linkbase = getProperty(Strings.HOST);

                logMessage(LOG, logPrefix + "Determining linkbase: " + linkbase);

                context.put(Strings.LINKBASE, linkbase.replace(Strings.REST_PREFIX, Strings.EMPTY));

                final String finalQuery = (Strings.ALL_PROJECTS.equalsIgnoreCase(project) ?
                        Strings.EMPTY : "project: " + project + "") + query;

                logMessage(LOG, logPrefix + "Running query: " + finalQuery);

                final List<Issue> issues = youTrack.issues.query(finalQuery, startIssue, pageSize);

                logMessage(LOG, logPrefix + "Number of isses in query result: " + issues.size());
                for (final Issue originalIssue : issues) {

                    logMessage(LOG, logPrefix + "Rendering report row for " + originalIssue.getId());

                    final Issue snapshot = originalIssue.createSnapshot();

                    rows.append("<tr class=\"yt yt-report-row\">");
                    rows.append("<td>");

                    final Map<String, Object> issueLinkContext = Service.createContext(context,
                            Strings.ISSUE_ID, originalIssue.getId()
                    );

                    rows.append(VelocityUtils.getRenderedTemplate(Strings.REPORT_ISSUE_LINK, issueLinkContext));
                    rows.append("</td>");

                    for (final IssueFieldDescriptor reportField : reportFields) {
                        logMessage(LOG, logPrefix + "Rendering field " + reportField.code);
                        rows.append("<td>");

                        final HashMap<String, BaseIssueField> issueFields = snapshot.getFields();
                        if (issueFields != null && !issueFields.isEmpty()) {
                            final BaseIssueField field = issueFields.get(reportField.code);
                            final boolean verbose = "comments-verbose".equals(reportField.code);

                            if ("comments".equals(reportField.code) || verbose) {

                                logMessage(LOG, logPrefix + "Rendering comments.");

                                final CommandBasedList<Issue, IssueComment> comments = originalIssue.comments;
                                if (comments != null) {
                                    final List<IssueComment> issueComments = comments.list();
                                    logMessage(LOG, logPrefix + "Total comments: " + issueComments.size());

                                    for (int i = 0; i < issueComments.size(); i++) {
                                        logMessage(LOG, logPrefix + "Adding comment: " + i);
                                        final IssueComment issueComment = issueComments.get(i);
                                        String commentText = issueComment.getText();

                                        final Map<String, Object> commentContext = Service.createContext(context,
                                                Strings.ISSUE_ID, Service.defaultIfNull(issueComment.getIssueId(), Strings.UNKNOWN),
                                                Strings.COMMENT_BODY, commentText == null ? Strings.EMPTY :
                                                        commentText.replace("(\\r|\\n)", Strings.EMPTY).replaceAll("\"<[^>]*>\"", Strings.EMPTY),
                                                Strings.COMMENT_AUTHOR, Service.defaultIfNull(issueComment.getAuthor(), Strings.UNKNOWN),
                                                Strings.COMMENT_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm").
                                                        format(new Date(Service.defaultIfNull(issueComment.getCreated(), Calendar.getInstance().getTimeInMillis()))),
                                                Strings.COMMENT_ID, issueComment.getId()
                                        );

                                        if (verbose) {
                                            rows.append(VelocityUtils.getRenderedTemplate(Strings.REPORT_COMMENT_HEAD, commentContext));
                                        }
                                        rows.append(VelocityUtils.getRenderedTemplate(Strings.REPORT_COMMENT_BODY, commentContext));

                                        if (i == 10) {
                                            rows.append(VelocityUtils.getRenderedTemplate(Strings.REPORT_COMMENT_MORE, commentContext));
                                            break;
                                        }
                                    }
                                } else {
                                    rows.append("No commentes so far.");
                                }
                            } else
                                rows.append(field == null ? Strings.UNKNOWN : Service.defaultIfNull(field.getStringValue(), Strings.UNKNOWN));
                            rows.append("</td>");
                        }
                    }
                    rows.append("</tr>");
                }
                if (thisPageUrl != null && request != null) {
                    logMessage(LOG, logPrefix + "Processing pagination");
                    if (currentPage != 1 || issues.size() >= pageSize) {

                        int maxPages = Service.intValueOf((String) params.get(Strings.TOTAL_PAGES), 10);

                        if (currentPage > 1) {
                            if (issues.size() < pageSize) maxPages = currentPage;
                        }

                        for (int i = 1; i <= maxPages; i++) {
                            final Map<String, Object> paginationContext = Service.createContext(context,
                                    "num", String.valueOf(i),
                                    "param", Strings.PAGINATION_PARAM,
                                    "url", thisPageUrl,
                                    "style", i == currentPage ? "font-weight:bold;" : "font-weight:normal;"
                            );
                            pagination.append(VelocityUtils.getRenderedTemplate(Strings.PAGINATION_SINGLE, paginationContext));
                        }
                    }
                }
                context.put("pagination", pagination.toString());
                context.put("rows", rows.toString());
                context.put("hasIssues", issues.isEmpty() ? null : String.valueOf(true));
                context.put("title", query + "from " + project);
                context.put("header", header);
                logMessage(LOG, logPrefix + "Final rendering.");
                result.append(VelocityUtils.getRenderedTemplate(Strings.BODY_REPORT, context));
            }
            logMessage(LOG, logPrefix + "Returning results.");
            return result.toString();
        } catch (Exception ex) {
            LOG.error("YouTrack report macro encounters error", ex);
            throw new MacroException(ex);
        }
    }
}