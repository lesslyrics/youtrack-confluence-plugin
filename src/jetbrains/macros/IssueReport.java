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
import jetbrains.macros.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import youtrack.Issue;
import youtrack.issues.fields.BaseIssueField;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IssueReport extends YouTrackAuthAwareMacroBase {
    private static final Logger LOG = LoggerFactory.getLogger(IssueReport.class);
    private final PageManager pageManager;

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

    private int intValueOf(final String str, int defaultValue) {
        if (str == null) return defaultValue;
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public String execute(Map params, String body, RenderContext renderContext)
            throws MacroException {
        try {
            final Map<String, Object> context = MacroUtils.defaultVelocityContext();
            String project = (String) params.get(Strings.PROJECT);
            if (project == null || project.trim().isEmpty()) project = Strings.ALL_PROJECTS;
            final String query = (String) params.get(Strings.QUERY);
            String fieldList = (String) params.get(Strings.REPORT_FIELD_LIST);
            if (fieldList == null || fieldList.isEmpty()) fieldList = Strings.DEFAULT_REPORT_FIELD_LIST;

            final StringBuilder result = new StringBuilder();
            if (query != null) {
                tryGetItem(youTrack.issues, Strings.EMPTY, 2);
                final StringBuilder rows = new StringBuilder();
                final int pageSize = intValueOf((String) params.get(Strings.PAGE_SIZE), 25);
                final HttpServletRequest request = ServletActionContext.getRequest();
                final int currentPage = request == null ? 1 : intValueOf(request.getParameter(Strings.PAGINATION_PARAM), 1);
                final int numPages = intValueOf((String) params.get(Strings.TOTAL_PAGES), 10);
                final StringBuilder pagination = new StringBuilder();
                final PageContext pageContext = (PageContext) renderContext;
                final Page page = pageManager.getPage(pageContext.getSpaceKey(), pageContext.getPageTitle());
                final String thisPageUrl = page.getUrlPath();
                final Map<String, Object> myContext = new HashMap<String, Object>();
                final int startIssue = currentPage == 1 ? 0 : (currentPage - 1) * pageSize + 1;
                final List<Issue> issues = youTrack.issues.query((Strings.ALL_PROJECTS.equalsIgnoreCase(project) ?
                        Strings.EMPTY : "project: " + project + " ") + query, startIssue, pageSize);

                final LinkedList<IssueFieldDescriptor> fields = new LinkedList<IssueFieldDescriptor>();

                for (final String fieldData : fieldList.split(",")) {
                    fields.add(new IssueFieldDescriptor(fieldData));
                }
                StringBuilder header = new StringBuilder();
                header.append("<th>Issue</th>");
                for (final IssueFieldDescriptor desc : fields) {
                    header.append("<th>");
                    header.append(desc.title);
                    header.append("</th>");
                }

                for (final Issue sIssue : issues) {
                    myContext.clear();
                    myContext.putAll(context);
                    final Issue issue = sIssue.createSnapshot();

                    myContext.put(Strings.STYLE, (issue.isResolved()) ? "line-through" : "normal");
                    myContext.put(Strings.BASE, getProperty(Strings.HOST).replace(Strings.REST_PREFIX, Strings.EMPTY));
                    context.put(Strings.LINKBASE, getProperty(Strings.HOST).replace(Strings.REST_PREFIX, Strings.EMPTY));
                    String linkbase = getProperty(Strings.LINKBASE);
                    if(null!=linkbase && !linkbase.isEmpty()){
                        context.put(Strings.LINKBASE,linkbase.replace(Strings.REST_PREFIX, Strings.EMPTY));
                    }
                    rows.append("<tr class=\"yt yt-report-row\">");
                    rows.append("<td>");
                    rows.append(MessageFormat.format("<a class=\"yt yt-issuelink\" href=\"{0}/issue/{1}\" target=\"blank\" style=\"text-decoration:{2};\">{1}</a>",
                            context.get(Strings.LINKBASE), sIssue.getId(), issue.isResolved() ? "line-through" : "normal"));
                    rows.append("</td>");
                    for (final IssueFieldDescriptor desc : fields) {
                        rows.append("<td>");
                        final BaseIssueField field = issue.getFields().get(desc.code);
                        rows.append(field == null ? "?" : field.getStringValue());
                        rows.append("</td>");
                    }
                    rows.append("</tr>");
                }
                for (int i = 1; i <= numPages; i++) {
                    myContext.clear();
                    myContext.putAll(context);
                    myContext.put("num", String.valueOf(i));
                    myContext.put("param", Strings.PAGINATION_PARAM);
                    myContext.put("url", thisPageUrl);
                    myContext.put("style", i == currentPage ? "font-weight:bold;" : "font-weight:normal;");
                    pagination.append(VelocityUtils.getRenderedTemplate(Strings.PAGINATION_SINGLE, myContext));
                }
                context.put("pagination", request != null ? pagination.toString() : Strings.EMPTY);
                context.put("rows", rows.toString());
                context.put("hasIssues", issues.size() > 0 ? String.valueOf(true) : null);
                context.put("title", query + " from " + project);
                context.put("header", header);
                result.append(VelocityUtils.getRenderedTemplate(Strings.BODY_REPORT, context));
            }
            return result.toString();
        } catch (Exception ex) {
            LOG.error("YouTrack report macro encounters error", ex);
            throw new MacroException(ex);
        }
    }
}